package com.dodo.flutterbridge.data

import com.dodo.flutterbridge.JsonMessageCodec
import com.dodo.flutterbridge.call.BaseCallNode
import com.dodo.flutterbridge.call.HandlerNode
import com.dodo.flutterbridge.call.strategy.HandlerNotFoundException
import com.dodo.flutterbridge.call.strategy.MutableHandlerException
import com.dodo.flutterbridge.call.strategy.MutableHandlerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy.ConflictType.Exception
import com.dodo.flutterbridge.model.FlutterCallInfo
import com.dodo.flutterbridge.model.FlutterMethodInfo
import io.flutter.plugin.common.MethodChannel

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/16
 *     desc   : 带名字的data子callNode
 *              通过create方法创建
 *     version: 1.0
 */
class DataNamedCallNode<T> private constructor(
    override val name: String,
    private val clazz: Class<T>,
) : BaseCallNode<T, FlutterCallInfo>(
    MutableHandlerStrategy(),
    SingleInvokerStrategy(Exception)
) {

    companion object {

        /**
         * 缓存同名字的DataNamedCallGroup
         */
        private val namedGroups = mutableMapOf<String, DataNamedCallNode<*>>()

        /**
         * 创建或者复用一个DataNamedCallGroup
         * @param name String
         * @param clazz Class<T>
         * @return DataNamedCallGroup<T>
         */
        fun <T> create(name: String, clazz: Class<T>): DataNamedCallNode<T> {
            if (namedGroups[name] == null) {
                namedGroups[name] = DataNamedCallNode(name, clazz)
            }
            return namedGroups[name] as DataNamedCallNode<T>
        }
    }

    init {
        linkParent(DataCallNode)
    }

    /**
     * encode的时候,添加名字信息
     */
    override fun encodeData(data: T): FlutterCallInfo =
        FlutterCallInfo(FlutterMethodInfo(name), data)

    /**
     * decode的时候,解析出泛型数据
     */
    @Suppress("UNCHECKED_CAST")
    override fun decodeData(data: FlutterCallInfo): HandlerNode.StrategyData<T> {
        var rawData = data.data
        if (rawData is JsonMessageCodec.JsonString) {
            rawData = rawData.fromJson(clazz)
        }
        return HandlerNode.StrategyData(
            data.methodInfo.name,
            data.methodInfo.sticky,
            rawData as T
        )
    }

    /**
     * data类型在invoke的时候广播给其他监听者
     * @param data T
     * @param callback Result?
     */
    override fun invoke(data: T, callback: MethodChannel.Result?) {
        super.invoke(data, callback)

        /**
         * 广播给其他监听(模拟收到数据)
         */
        try {
            handlerStrategy.onCallStrategy(name, true, data)
        } catch (e: HandlerNotFoundException) {

        } catch (e: MutableHandlerException) {

        }
    }
}