package com.dodo.flutterbridge.model

import com.dodo.flutterbridge.call.Named

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/12
 *     desc   : method信息
 *     version: 1.0
 */
data class FlutterMethodInfo(
    /**
     * method名字
     */
    override val name: String,
) : Named {
    /**
     * method类型
     */
    lateinit var type: String
}