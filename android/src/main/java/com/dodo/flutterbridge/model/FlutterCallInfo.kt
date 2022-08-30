package com.dodo.flutterbridge.model

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/12
 *     desc   : call信息
 *     version: 1.0
 */
data class FlutterCallInfo(
    /**
     * method信息
     */
    val methodInfo: FlutterMethodInfo,
    /**
     * 数据
     */
    val data: Any?
)