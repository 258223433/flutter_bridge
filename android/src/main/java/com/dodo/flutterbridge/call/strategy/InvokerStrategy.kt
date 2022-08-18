package com.dodo.flutterbridge.call.strategy

import com.dodo.flutterbridge.call.InvokeAttachable
import io.flutter.plugin.common.MethodChannel
import kotlin.jvm.Throws

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   :
 *     version: 1.0
 */
interface InvokerStrategy<P> : InvokeAttachable<P>{
    @Throws(InvokerNotFoundException::class)
    fun invokeStrategy(data: P, callback: MethodChannel.Result?)
}