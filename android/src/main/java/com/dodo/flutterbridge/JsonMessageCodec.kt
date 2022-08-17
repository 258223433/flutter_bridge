package com.dodo.flutterbridge

import com.dodo.flutterbridge.common.GsonUtil
import com.google.gson.Gson
import io.flutter.plugin.common.StandardMessageCodec
import java.io.ByteArrayOutputStream
import java.lang.reflect.Type
import java.nio.ByteBuffer

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/29
 *     desc   : JsonMessageCodec
 *     version: 1.0
 */
class JsonMessageCodec : StandardMessageCodec() {
    companion object {
        private const val JSON: Byte = 64
    }

    override fun writeValue(stream: ByteArrayOutputStream, value: Any) {
        try {
            super.writeValue(stream, value)
        } catch (e: IllegalArgumentException) {
            stream.write(JSON.toInt())
            writeBytes(stream, GsonUtil.gson.toJson(value).toByteArray())
        }
    }

    override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
        return try {
            super.readValueOfType(type, buffer)
        } catch (e: IllegalArgumentException) {
            when (type) {
                JSON -> {
                    JsonString(String(readBytes(buffer)), GsonUtil.gson)
                }
                else -> throw e
            }
        }
    }

    /**
     * 外部使用的JsonString
     * 可以获取到实例
     * @property jsonString String
     * @property gson Gson
     * @constructor
     */
    class JsonString(private val jsonString: String, private val gson: Gson) {
        fun <T> fromJson(typeOfT: Type): T {
            return gson.fromJson(jsonString, typeOfT)
        }

        fun <T> fromJson(classOfT: Class<T>): T {
            return gson.fromJson(jsonString, classOfT)
        }

        override fun toString(): String {
            return "JsonString(jsonString='$jsonString')"
        }
    }
}