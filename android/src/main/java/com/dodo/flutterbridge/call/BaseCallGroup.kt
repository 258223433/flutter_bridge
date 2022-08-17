package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.call.strategy.HandlerStrategy

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/12
 *     desc   :
 *     version: 1.0
 */
abstract class BaseCallGroup<S, P>(
    override val strategy: HandlerStrategy<S>
) : CallGroup<S, P>, CallHandleable<S> by strategy {

    override var parent: CallInvoker<P, *>? = null
}

