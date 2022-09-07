package com.dodo.flutterbridge.call.strategy

import com.dodo.flutterbridge.call.InvokeAttachable
import com.dodo.flutterbridge.call.exception.InvokerNotFoundException
import io.flutter.plugin.common.MethodChannel

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   : invoker的策略
 *     version: 1.0
 */
interface InvokerStrategy<P> : InvokeAttachable<P> {

    /**
     * invoker的策略
     * @param data P
     * @param callback Result?
     * @throws InvokerNotFoundException
     */
    @Throws(InvokerNotFoundException::class)
    fun invokeStrategy(data: P, callback: MethodChannel.Result?)
}