package com.dodo.flutterbridge.invoke

import com.dodo.flutterbridge.call.CallHandler

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   :
 *     version: 1.0
 */
class FlutterHandler<T>(override val name: String) :CallHandler<T>{
    override fun onCall(data: T): Any? {
        TODO("Not yet implemented")
    }
}