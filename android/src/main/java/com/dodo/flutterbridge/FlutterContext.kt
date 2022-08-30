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
}