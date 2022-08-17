package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.JsonMessageCodec
import com.dodo.flutterbridge.call.CallGroup.StrategyData
import com.dodo.flutterbridge.call.strategy.HandlerStrategy
import com.dodo.flutterbridge.model.FlutterCallInfo
import com.dodo.flutterbridge.model.FlutterMethodInfo
import io.flutter.plugin.common.MethodChannel

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/16
 *     desc   : 有名字的callGroup
 *     version: 1.0
 */
open class NamedCallGroup<T>(
    override val name: String,
    private val clazz: Class<T>,
    strategy: HandlerStrategy<T>
) :BaseCallGroup<T, FlutterCallInfo>(strategy) {

    /**
     * encode的时候,添加名字信息
     */
    override fun encodeData(data: T): FlutterCallInfo =
        FlutterCallInfo(FlutterMethodInfo(name), data)

    /**
     * decode的时候,解析出泛型数据
     */
    @Suppress("UNCHECKED_CAST")
    override fun decodeData(data: FlutterCallInfo): StrategyData<T> {
        var rawData = data.data
        if (rawData is JsonMessageCodec.JsonString) {
            rawData = rawData.fromJson(clazz)
        }
        return StrategyData(data.methodInfo.name, data.methodInfo.sticky, rawData as T)
    }
}