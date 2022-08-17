package com.dodo.flutterbridge.call

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   : 可以添加和移除handler的容器
 *     version: 1.0
 */
interface CallHandleable<S> {

    fun addCallHandler(handler: CallHandler<S>)

    fun removeCallHandler(handler: CallHandler<S>)
}