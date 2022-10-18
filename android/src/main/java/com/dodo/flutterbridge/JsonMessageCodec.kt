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
        //json数据类型
        private const val JSON: Byte = 64
    }

    override fun writeValue(stream: ByteArrayOutputStream, value: Any) {
        try {
            //先尝试使用父类来写数据
            super.writeValue(stream, value)
        } catch (e: IllegalArgumentException) {
            //如果父类不支持该类型数据并抛出异常的话,我们来写json类型数据

            //先写入json数据的类型
            stream.write(JSON.toInt())
            //再把对象转换成json并写入这个string
            writeBytes(stream, GsonUtil.gson.toJson(value).toByteArray())
        }
    }

    override fun readValueOfType(type: Byte, buffer: ByteBuffer): Any? {
        return try {
            //先尝试使用父类来读数据
            super.readValueOfType(type, buffer)
        } catch (e: IllegalArgumentException) {
            //如果父类不支持该类型数据并抛出异常的话,我们来读json数据类型
            when (type) {
                JSON -> {
                    //转换成自定义的对象提供给外面判断使用
                    JsonString(String(readBytes(buffer)), GsonUtil.gson)
                }
                //如果不是json类型的话继续抛出异常
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

internal fun <T> Any?.convertFromJson(clazz: Class<T>?): Any? {
    var rawData = this
    if (rawData is JsonMessageCodec.JsonString) {
        if (clazz == null) {
            throw Exception("类型为JsonString,clazz却为空")
        }
        rawData = rawData.fromJson(clazz)
    }
    return rawData
}