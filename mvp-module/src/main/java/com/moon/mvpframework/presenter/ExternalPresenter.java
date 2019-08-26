package com.moon.mvpframework.presenter;

import android.support.annotation.MainThread;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arthur on 2019-08-26.
 */
public class ExternalPresenter {

    private final Map<String, Object> mBagOfTags = new HashMap<>();
    private volatile boolean mCleared = false;

    protected void onCleared() {

    }

    @MainThread
    final void clear() {
        mCleared = true;
        // Since clear() is final, this method is still called on mock objects
        // and in those cases, mBagOfTags is null. It'll always be empty though
        // because setTagIfAbsent and getTag are not final so we can skip
        // clearing it
        synchronized (mBagOfTags) {
            for (Object value : mBagOfTags.values()) {
                // see comment for the similar call in setTagIfAbsent
                closeWithRuntimeException(value);
            }
        }
        onCleared();
    }

    public  <T> T setTagIfAbsent(String key, T newValue) {
        T previous;
        synchronized (mBagOfTags) {
            //noinspection unchecked
            previous = (T) mBagOfTags.get(key);
            if (previous == null) {
                mBagOfTags.put(key, newValue);
            }
        }
        T result = previous == null ? newValue : previous;
        if (mCleared) {
            // It is possible that we'll call close() multiple times on the same object, but
            // Closeable interface requires close method to be idempotent:
            // "if the stream is already closed then invoking this method has no effect." (c)
            closeWithRuntimeException(result);
        }
        return result;
    }

    /**
     * Returns the tag associated with this viewmodel and the specified key.
     */
    @SuppressWarnings("TypeParameterUnusedInFormals")
    public <T> T getTag(String key) {
        //noinspection unchecked
        synchronized (mBagOfTags) {
            return (T) mBagOfTags.get(key);
        }
    }

    private static void closeWithRuntimeException(Object obj) {
        if (obj instanceof Closeable) {
            try {
                ((Closeable) obj).close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
