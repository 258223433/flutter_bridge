package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.call.strategy.HandlerStrategy

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/18
 *     desc   :
 *     version: 1.0
 */
abstract class BaseCallRoot<S, P>(
    override val handlerStrategy: HandlerStrategy<S>,
) : CallRoot<S, P>, Handleable<S> by handlerStrategy