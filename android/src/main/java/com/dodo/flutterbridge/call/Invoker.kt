package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.call.exception.InvokerNotFoundException
import io.flutter.plugin.common.MethodChannel

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/15
 *     desc   : 调用的接口
 *     version: 1.0
 */
interface Invoker<S> : Named {

    /**
     * 调用
     * @param data S 调用的数据
     * @param callback Result? 返回数据的回调
     * @throws InvokerNotFoundException 异常
     */
    @Throws(InvokerNotFoundException::class)
    fun invoke(data: S, callback: MethodChannel.Result?)
}