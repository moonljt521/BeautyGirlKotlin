package com.moon.mvpframework.factory


import com.moon.mvpframework.presenter.BaseMvpPresenter

import java.lang.annotation.Inherited
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

/**
 * @author 刘镓旗
 * @date 2017/11/17
 * @description 标注创建Presenter的注解
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
annotation class CreatePresenter(val value: KClass<out BaseMvpPresenter<*>>)
