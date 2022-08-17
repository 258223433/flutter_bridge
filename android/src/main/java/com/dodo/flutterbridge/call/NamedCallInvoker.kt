package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.invoke.InvokeCallGroup
import com.dodo.flutterbridge.model.FlutterCallInfo
import com.dodo.flutterbridge.model.FlutterMethodInfo

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   :
 *     version: 1.0
 */
class NamedCallInvoker<T>private constructor(override val name: String) : CallInvoker<T, FlutterCallInfo> {

    override var parent: CallInvoker<FlutterCallInfo, *>? = InvokeCallGroup

    companion object {

        /**
         * 缓存同名字的NamedCallInvoker
         */
        private val namedGroups = mutableMapOf<String, NamedCallInvoker<*>>()

        /**
         * 创建或者复用一个NamedCallInvoker
         * @param name String
         * @param clazz Class<T>
         * @return DataNamedCallGroup<T>
         */
        fun <T> create(name: String): NamedCallInvoker<T> {
            if (namedGroups[name] == null) {
                namedGroups[name] = NamedCallInvoker<T>(name)
            }
            return namedGroups[name] as NamedCallInvoker<T>
        }
    }

    override fun encodeData(data: T): FlutterCallInfo =
        FlutterCallInfo(FlutterMethodInfo(name), data)
}