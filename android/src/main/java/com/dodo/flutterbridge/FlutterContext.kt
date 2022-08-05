package com.dodo.flutterbridge

import io.flutter.embedding.engine.FlutterEngine

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/13
 *     desc   : flutter平台相关对象
 *     version: 1.0
 */
object FlutterContext {
    lateinit var globalEngine: FlutterEngine
    lateinit var globalChannel: FlutterMethodChannel
    const val GLOBAL_FLUTTER_CHANNEL_NAME = "com.dodo.flutterbridge.global_flutter_channel"
    const val FLUTTER_CHANNEL_METHOD_READY = "com.dodo.flutterbridge.method_ready"
}