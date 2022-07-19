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
    private val observers = mutableMapOf<String, WeakReference<OnCallObserver>>()


    // TODO: 监听的泛型
    fun addObserver(name: String, observer: OnCallObserver) {
        observers[name] = WeakReference(observer)
    }

    fun removeObserver(name: String) {
        observers.remove(name)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        // TODO: 数据解析adapter看在哪里处理
        try {
            val observer = observers[call.method]?.get()
            if (observer == null) {
                result.notImplemented()
            } else {
                result.success(observer.onCall(call.arguments))
            }
        } catch (e: Throwable) {
            result.error("android业务异常", e.toString(), null)
        }
    }
}

interface OnCallObserver {
    fun onCall(data: Any?): Any?
}