package com.dodo.flutterbridge.function

import com.dodo.flutterbridge.JsonMessageCodec
import com.dodo.flutterbridge.call.HandlerNode
import com.dodo.flutterbridge.call.strategy.HandlerStrategy
import com.dodo.flutterbridge.call.strategy.SingleHandlerStrategy
import com.dodo.flutterbridge.call.strategy.SingleHandlerStrategy.ConflictType.Replace
import com.dodo.flutterbridge.convertFromJson
import com.dodo.flutterbridge.model.FlutterCallInfo

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/18
 *     desc   : function的name节点,处理name的逻辑
 *     version: 1.0
 */
class FunctionNamedHandlerNode<T> private constructor(
    override val name: String,
    private val clazz: Class<T>? = null
) : HandlerNode<T, FlutterCallInfo> {

    override val handlerStrategy: HandlerStrategy<T> = SingleHandlerStrategy(Replace)

    companion object {

        /**
         * 缓存同名字的FunctionNamedHandlerNode
         */
        private val namedGroups = mutableMapOf<String, FunctionNamedHandlerNode<*>>()

        /**
         * 创建或者复用一个FunctionNamedHandlerNode
         * @param name String
         * @param clazz Class<T>
         * @return FunctionNamedHandlerNode<T>
         */
        fun <T> create(name: String, clazz: Class<T>?): FunctionNamedHandlerNode<T> {
            if (namedGroups[name] == null) {
                namedGroups[name] = FunctionNamedHandlerNode(name, clazz)
            }
            return namedGroups[name] as FunctionNamedHandlerNode<T>
        }
    }

    init {
        FunctionCallNode.addHandler(this)
    }

    /**
     * decode的时候,解析出泛型数据
     */
    @Suppress("UNCHECKED_CAST")
    override fun decodeData(data: FlutterCallInfo): HandlerNode.StrategyData<T> {
        val rawData = data.data.convertFromJson(clazz)

        return HandlerNode.StrategyData(
            data.methodInfo.name,
            false,
            rawData as T
        )
    }

}