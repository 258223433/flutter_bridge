package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.call.strategy.HandlerNotFoundException
import com.dodo.flutterbridge.call.strategy.HandlerStrategy
import com.dodo.flutterbridge.call.strategy.MutableHandlerException
import kotlin.jvm.Throws

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   :
 *     version: 1.0
 */
interface CallNode<S, P> :CallRoot<S,P>,CallLeaf<S,P>