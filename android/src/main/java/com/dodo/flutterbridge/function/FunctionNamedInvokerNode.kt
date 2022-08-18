package com.dodo.flutterbridge.function

import com.dodo.flutterbridge.call.BaseInvokerNode
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy.ConflictType.Exception
import com.dodo.flutterbridge.model.FlutterCallInfo
import com.dodo.flutterbridge.model.FlutterMethodInfo

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/18
 *     desc   :
 *     version: 1.0
 */
class FunctionNamedInvokerNode<T> private constructor(
    override val name: String,
) : BaseInvokerNode<T, FlutterCallInfo>(SingleInvokerStrategy(Exception)) {

    companion object {

        /**
         * 缓存同名字的DataNamedCallGroup
         */
        private val namedGroups = mutableMapOf<String, FunctionNamedInvokerNode<*>>()

        /**
         * 创建或者复用一个DataNamedCallGroup
         * @param name String
         * @param clazz Class<T>
         * @return DataNamedCallGroup<T>
         */
        fun <T> create(name: String): FunctionNamedInvokerNode<T> {
            if (namedGroups[name] == null) {
                namedGroups[name] = FunctionNamedInvokerNode<T>(name)
            }
            return namedGroups[name] as FunctionNamedInvokerNode<T>
        }
    }

    init {
        attachInvoker(FunctionCallNode)
    }

    /**
     * encode的时候,添加名字信息
     */
    override fun encodeData(data: T): FlutterCallInfo =
        FlutterCallInfo(FlutterMethodInfo(name), data)
}