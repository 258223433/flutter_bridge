package com.dodo.flutterbridge.function

import com.dodo.flutterbridge.call.Disposable
import com.dodo.flutterbridge.call.InvokerNode
import com.dodo.flutterbridge.call.strategy.InvokerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy.ConflictType.Replace

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/18
 *     desc   : 可以和flutter交互的function
 *     version: 1.0
 */
class FlutterFunction<T>(
    override val name: String
) : InvokerNode<T, T>, Disposable {

    override val invokerStrategy: InvokerStrategy<T> = SingleInvokerStrategy(Replace)

    private val parent = FunctionNamedInvokerNode.create<T>(name)

    init {
        attachInvoker(parent)
    }

    override fun encodeData(data: T): T = data

    override fun dispose() {
        detachInvoker(parent)
    }
}