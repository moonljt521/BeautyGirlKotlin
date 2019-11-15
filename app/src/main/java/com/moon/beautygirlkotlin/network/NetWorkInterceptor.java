package com.moon.beautygirlkotlin.network;

import com.moon.beautygirlkotlin.utils.Logger;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * author: moon
 * created on: 18/5/17 下午12:58
 * description:
 */
public class NetWorkInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        if (Logger.INSTANCE.getDEBUG()) {
            String methodName = request.method();

            Set<String> headers = request.headers().names();


            if (methodName.equalsIgnoreCase("GET")) {
                Logger.INSTANCE.i(Logger.INSTANCE.getTAG(), "- url -" + methodName + "--" + request.url());
                for (String head : headers) {
                    Logger.INSTANCE.i(Logger.INSTANCE.getTAG(), "- head -" + head);

                }


            } else if (methodName.equalsIgnoreCase("POST")) {
                RequestBody mRequestBody = request.body();
                if (mRequestBody != null) {
                    String msg = "-url--" + methodName + "--" + request.url();
                    String content;
                    if (msg.contains("uploadFile")) {
                        content = "--上传文件内容--";
                    } else {
                        content = getParam(mRequestBody);
                    }
                    Logger.INSTANCE.i(Logger.INSTANCE.getTAG(), msg + content);
                }
            }
        }
        Response response = chain.proceed(request);
        return response;
    }

    /**
     * 读取参数
     *
     * @param requestBody
     * @return
     */
    private String getParam(RequestBody requestBody) {
        Buffer buffer = new Buffer();
        String logparm;
        try {
            requestBody.writeTo(buffer);
            logparm = buffer.readUtf8();
            logparm = URLDecoder.decode(logparm, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return logparm;
    }

}
