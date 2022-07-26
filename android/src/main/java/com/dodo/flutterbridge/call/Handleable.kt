package com.dodo.flutterbridge.call

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   : 可以添加和移除handler的容器
 *     version: 1.0
 */
interface Handleable<S> {

    /**
     * 添加handler
     * @param handler Handler<S>
     */
    fun addHandler(handler: Handler<S>)

    /**
     * 移除handler
     * @param handler Handler<S>
     */
    fun removeHandler(handler: Handler<S>)
}