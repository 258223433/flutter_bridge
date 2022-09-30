package com.dodo.flutterbridge.common

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   : 常量
 *     version: 1.0
 */
object Constant {

    object Channel {
        const val GLOBAL_FLUTTER_CHANNEL_NAME = "com.dodo.flutterbridge.global_flutter_channel"
    }

    object Method {
        const val FLUTTER_CHANNEL_METHOD_READY = "com.dodo.flutterbridge.method_ready"
    }

    object Value {
        const val FLUTTER_CHANNEL_VALUE_NULL = "com.dodo.flutterbridge.value_null"
    }

    /**
     * name常量
     */
    object Name {

        const val ROOT_NAME = "root"
        const val TYPE_DATA_NAME = "data"
        const val TYPE_FUNCTION_NAME = "function"
    }
}