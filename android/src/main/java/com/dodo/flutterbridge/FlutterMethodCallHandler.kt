package com.dodo.flutterbridge

import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.lang.ref.WeakReference
import io.flutter.plugin.common.StandardMessageCodec

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/06
 *     desc   : 全局的MethodCallHandler
 *     version: 1.0
 */
internal class FlutterMethodCallHandler : MethodChannel.MethodCallHandler {
    private val observers = mutableMapOf<String, MutableList<OnCallObserver>>()

    /**
     * 虽然flutter里面定义了null的类型,但是[StandardMessageCodec.writeValue]中写入数据为非空,所以这里定义一个默认的返回值
     */
    private val defaultResult = "null"


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
        val list = observers[call.method]
        if (list.isNullOrEmpty()) {
            result.notImplemented()
        } else {
            if (list.size == 1) {
                result.success(list[0].onCall(call.arguments) ?: defaultResult)
            } else {
                list.forEach {
                    it.onCall(call.arguments)
                }
                result.success(defaultResult)
            }
        }
    }
}

interface OnCallObserver {
    fun onCall(data: Any?): Any?
}