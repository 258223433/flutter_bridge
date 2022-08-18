package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.call.strategy.InvokerNotFoundException
import com.dodo.flutterbridge.call.strategy.InvokerStrategy
import io.flutter.plugin.common.MethodChannel
import kotlin.jvm.Throws

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   :
 *     version: 1.0
 */
interface InvokerNode<S,P>:InvokeAttachable<P>,Invoker<S> {
    val invokerStrategy: InvokerStrategy<P>

    @Throws(InvokerNotFoundException::class)
    override
    fun invoke(data: S, callback: MethodChannel.Result?) {
        invokerStrategy.invokeStrategy(encodeData(data), callback)
    }

    fun encodeData(data: S): P
}