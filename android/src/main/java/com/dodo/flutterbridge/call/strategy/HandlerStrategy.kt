package com.dodo.flutterbridge.call.strategy

import com.dodo.flutterbridge.call.Handleable
import com.dodo.flutterbridge.call.exception.HandlerNotFoundException
import com.dodo.flutterbridge.call.exception.MutableHandlerException

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   : 保存和调用handler的策略
 *     version: 1.0
 */
interface HandlerStrategy<S> : Handleable<S> {

    /**
     * handler的策略
     * @param name String
     * @param sticky Boolean
     * @param data S
     * @return Any?
     * @throws HandlerNotFoundException
     * @throws MutableHandlerException
     */
    @Throws(HandlerNotFoundException::class, MutableHandlerException::class)
    fun onCallStrategy(name: String, sticky: Boolean, data: S): Any?
}