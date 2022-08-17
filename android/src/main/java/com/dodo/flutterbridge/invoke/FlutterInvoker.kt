package com.dodo.flutterbridge.invoke

import com.dodo.flutterbridge.call.CallGroup
import com.dodo.flutterbridge.call.CallInvoker
import com.dodo.flutterbridge.call.NamedCallInvoker

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   :
 *     version: 1.0
 */
class FlutterInvoker<T>(override val name: String) : CallInvoker<T, T> {
    override var parent: CallInvoker<T, *>? = NamedCallInvoker.create(name)

    override fun encodeData(data: T): T = data
}