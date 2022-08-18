package com.dodo.flutterbridge.call.strategy

import com.dodo.flutterbridge.call.Handleable
import kotlin.jvm.Throws

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   : 保存和调用Handler的策略
 *     version: 1.0
 */
interface HandlerStrategy<S> : Handleable<S> {

    @Throws(HandlerNotFoundException::class, MutableHandlerException::class)
    fun onCallStrategy(name: String, sticky: Boolean, data: S): Any?
}