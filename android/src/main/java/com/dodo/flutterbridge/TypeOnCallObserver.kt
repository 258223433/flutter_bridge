package com.dodo.flutterbridge

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/29
 *     desc   : 带泛型的OnCall
 *     version: 1.0
 */
interface TypeOnCallObserver<A, R> : OnCallObserver {
    override fun onCall(data: Any?): Any? {

        if (data is JsonMessageCodec.JsonString) {
            return onCallOfType(fromJson(data))
        }
        return onCallOfType(data as A)
    }

    fun onCallOfType(data: A): R

    fun fromJson(data :JsonMessageCodec.JsonString):A
}