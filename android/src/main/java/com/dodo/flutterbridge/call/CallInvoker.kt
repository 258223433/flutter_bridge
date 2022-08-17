package com.dodo.flutterbridge.call

import io.flutter.plugin.common.MethodChannel

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/15
 *     desc   :
 *     version: 1.0
 */
interface CallInvoker<S, P> : Named {
    var parent: CallInvoker<P, *>?

    fun detach() {
        this.parent = null
    }

    fun invoke(data: S, callback: MethodChannel.Result?) {
        parent?.invoke(encodeData(data), callback) ?: throw Exception("已经detach,不能invoke")
    }

    fun encodeData(data: S): P
}