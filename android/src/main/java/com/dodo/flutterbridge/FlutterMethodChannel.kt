package com.dodo.flutterbridge

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.*

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/06
 *     desc   : FlutterChannel
 *     version: 1.0
 */
class FlutterMethodChannel(engine: FlutterEngine, channelName: String) {

    private val mainImmediateScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val delegate = MethodChannel(engine.dartExecutor.binaryMessenger,channelName)
    private val flutterMethodCallHandler = FlutterMethodCallHandler()
    init {
        delegate.setMethodCallHandler(flutterMethodCallHandler)
    }

    fun invokeMethod(method: String, arguments: Any?, callback: MethodChannel.Result? = null) {
        mainImmediateScope.launch {
            delegate.invokeMethod(method, arguments, callback)
        }
    }

    fun addObserver(name: String,observer: OnCallObserver) {
        flutterMethodCallHandler.addObserver(name, observer)
    }

    fun removeObserver(name: String) {
        flutterMethodCallHandler.removeObserver(name)
    }
}