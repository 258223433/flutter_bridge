package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.call.strategy.HandlerNotFoundException
import com.dodo.flutterbridge.call.strategy.MutableHandlerException
import kotlin.jvm.Throws

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   : 可以接收数据并返回结果的handler
 *     version: 1.0
 */

interface CallHandler<P> : Named {

    @Throws(HandlerNotFoundException::class, MutableHandlerException::class)
    fun onCall(data: P): Any?
}