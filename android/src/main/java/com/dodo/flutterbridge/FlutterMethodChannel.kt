package com.dodo.flutterbridge

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMethodCodec
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

    /**
     *  真正的MethodChannel
     *  使用了[JsonMessageCodec]作为[StandardMethodCodec] 的MessageCodec
     *  里面处理了json格式
     */
    private val delegate = MethodChannel(
        engine.dartExecutor.binaryMessenger, channelName,
        StandardMethodCodec(JsonMessageCodec())
    )


    init {
        delegate.setMethodCallHandler(GlobalCallRoot)
    }

    fun invokeMethod(method: String, arguments: Any?, callback: MethodChannel.Result? = null) {
        mainImmediateScope.launch {
            delegate.invokeMethod(method, arguments, callback)
        }
    }
}