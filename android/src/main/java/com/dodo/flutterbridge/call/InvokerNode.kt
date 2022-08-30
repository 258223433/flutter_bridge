package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.call.exception.InvokerNotFoundException
import com.dodo.flutterbridge.call.strategy.InvokerStrategy
import io.flutter.plugin.common.MethodChannel
import kotlin.jvm.Throws

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   : 可以向上传递的invoker节点
 *     version: 1.0
 */
interface InvokerNode<S,P>:InvokeAttachable<P>,Invoker<S> {

    /**
     * invoker策略
     */
    val invokerStrategy: InvokerStrategy<P>

    /**
     * @param data
     * @param callback Result?
     * @throws InvokerNotFoundException
     */
    @Throws(InvokerNotFoundException::class)
    override
    fun invoke(data: S, callback: MethodChannel.Result?) {
        invokerStrategy.invokeStrategy(encodeData(data), callback)
    }

    override fun attachInvoker(invoker: Invoker<P>) {
        invokerStrategy.attachInvoker(invoker)
    }

    override fun detachInvoker(invoker: Invoker<P>) {
        invokerStrategy.detachInvoker(invoker)
    }

    /**
     * 编码为传递到父节点的数据
     * @param data S
     * @return P
     */
    fun encodeData(data: S): P
}