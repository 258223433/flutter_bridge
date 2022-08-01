package com.dodo.flutterbridge

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.lang.ref.WeakReference

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/06
 *     desc   : 全局的MethodCallHandler
 *     version: 1.0
 */
internal class FlutterMethodCallHandler : MethodChannel.MethodCallHandler {
    private val observers = mutableMapOf<String, MutableList<OnCallObserver>>()


    fun addObserver(name: String, observer: OnCallObserver) {
        var list = observers[name]
        if (list == null) {
            list = mutableListOf()
            observers[name] = list
        }
        list.add(observer)
    }

    fun removeObserver(name: String, observer: OnCallObserver) {
        val list = observers[name]
        list?.forEach {
            if (it == observer) {
                list.remove(it)
            }
        }
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        try {

            val list = observers[call.method]
            if (list.isNullOrEmpty()) {
                result.notImplemented()
            } else {
                if (list.size == 1) {
                    result.success(list[0].onCall(call.arguments))
                } else {
                    list.forEach {
                        it.onCall(call.arguments)
                    }
                    result.success(null)
                }
            }
        } catch (e: Throwable) {
            result.error("android业务异常", e.toString(), null)
        }
    }
}

interface OnCallObserver {
    fun onCall(data: Any?): Any?
}