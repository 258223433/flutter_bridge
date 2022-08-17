package com.dodo.flutterbridge.model

import com.dodo.flutterbridge.call.Named

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/12
 *     desc   : 方法信息
 *     version: 1.0
 */
data class FlutterMethodInfo(
    override val name: String,
) : Named {
    lateinit var type: String
    var sticky: Boolean = false
}