package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.call.strategy.InvokerNotFoundException
import io.flutter.plugin.common.MethodChannel
import kotlin.jvm.Throws

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/15
 *     desc   :
 *     version: 1.0
 */
interface Invoker<S> : Named {

    @Throws(InvokerNotFoundException::class)
    fun invoke(data: S, callback: MethodChannel.Result?)
}