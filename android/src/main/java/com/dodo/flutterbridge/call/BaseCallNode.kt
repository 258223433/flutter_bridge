package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.call.strategy.HandlerStrategy
import com.dodo.flutterbridge.call.strategy.InvokerStrategy

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/12
 *     desc   :
 *     version: 1.0
 */
abstract class BaseCallNode<S, P>(
    override val handlerStrategy: HandlerStrategy<S>,
    override val invokerStrategy: InvokerStrategy<P>
) : CallNode<S, P>, Handleable<S> by handlerStrategy, InvokeAttachable<P> by invokerStrategy

