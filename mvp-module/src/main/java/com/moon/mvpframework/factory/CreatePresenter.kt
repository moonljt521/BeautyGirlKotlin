package com.moon.mvpframework.factory


import com.moon.mvpframework.presenter.BaseMvpPresenter
import java.lang.annotation.Inherited
import kotlin.reflect.KClass

@Inherited
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class CreatePresenter(val value: KClass<out BaseMvpPresenter<*>>)
