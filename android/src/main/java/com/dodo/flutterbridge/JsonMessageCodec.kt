package com.dodo.flutterbridge

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
    private val JSON: Byte = 64
    private var gson = Gson()

    override fun writeValue(stream: ByteArrayOutputStream, value: Any) {
        try {
            super.writeValue(stream, value)
        } catch (e: IllegalArgumentException) {
            stream.write(JSON.toInt())
            writeBytes(stream, gson.toJson(value).toByteArray())
        }
    }

    override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
        return try {
            super.readValueOfType(type, buffer)
        } catch (e: IllegalArgumentException) {
            when (type) {
                JSON -> {
                    JsonString(String(readBytes(buffer)), gson)
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
            return gson.fromJson(jsonString,classOfT)
        }

        override fun toString(): String {
            return "JsonString(jsonString='$jsonString')"
        }
    }
}