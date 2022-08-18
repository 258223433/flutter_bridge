package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.call.strategy.InvokerStrategy

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/18
 *     desc   :
 *     version: 1.0
 */
abstract class BaseInvokerNode<S, P>(
    override val invokerStrategy: InvokerStrategy<P>
) : InvokerNode<S, P>, InvokeAttachable<P> by invokerStrategy