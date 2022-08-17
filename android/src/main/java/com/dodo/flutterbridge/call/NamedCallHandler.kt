package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.JsonMessageCodec
import com.dodo.flutterbridge.model.FlutterCallInfo

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   :
 *     version: 1.0
 */
class NamedCallHandler<T>(override val name: String,private val clazz: Class<T>) :CallHandler<FlutterCallInfo> {
    override fun onCall(data: FlutterCallInfo): Any? {
        TODO("Not yet implemented")
    }

}