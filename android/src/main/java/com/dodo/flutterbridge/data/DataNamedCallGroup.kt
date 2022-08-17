package com.dodo.flutterbridge.data

import com.dodo.flutterbridge.call.NamedCallGroup
import com.dodo.flutterbridge.call.strategy.HandlerNotFoundException
import com.dodo.flutterbridge.call.strategy.MutableHandlerException
import com.dodo.flutterbridge.call.strategy.MutableHandlerStrategy
import io.flutter.plugin.common.MethodChannel

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/16
 *     desc   : 带名字的data子group
 *              通过create方法创建
 *     version: 1.0
 */
class DataNamedCallGroup<T> private constructor(name: String, clazz: Class<T>) :
    NamedCallGroup<T>(name, clazz, MutableHandlerStrategy()) {

    companion object {

        /**
         * 缓存同名字的DataNamedCallGroup
         */
        private val namedGroups = mutableMapOf<String, DataNamedCallGroup<*>>()

        /**
         * 创建或者复用一个DataNamedCallGroup
         * @param name String
         * @param clazz Class<T>
         * @return DataNamedCallGroup<T>
         */
        fun <T> create(name: String, clazz: Class<T>): DataNamedCallGroup<T> {
            if (namedGroups[name] == null) {
                namedGroups[name] = DataNamedCallGroup(name, clazz)
            }
            return namedGroups[name] as DataNamedCallGroup<T>
        }
    }

    init {
        attach(DataCallGroup)
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
            strategy.onCallStrategy(name, true, data)
        } catch (e: HandlerNotFoundException) {

        } catch (e: MutableHandlerException) {

        }
    }
}