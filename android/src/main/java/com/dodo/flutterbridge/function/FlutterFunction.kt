package com.dodo.flutterbridge.function

import com.dodo.flutterbridge.call.BaseInvokerNode
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy.ConflictType.Replace

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/18
 *     desc   :
 *     version: 1.0
 */
class FlutterFunction<T>(
    override val name: String
) : BaseInvokerNode<T, T>(
    SingleInvokerStrategy(Replace)
) {
    private val parent = FunctionNamedInvokerNode.create<T>(name)

    init {
        attachInvoker(parent)
    }

    override fun encodeData(data: T): T = data

    fun dispose() {
        detachInvoker(parent)
    }
}