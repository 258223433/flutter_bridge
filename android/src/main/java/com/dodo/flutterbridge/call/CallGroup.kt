package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.call.strategy.HandlerNotFoundException
import com.dodo.flutterbridge.call.strategy.HandlerStrategy
import com.dodo.flutterbridge.call.strategy.MutableHandlerException
import kotlin.jvm.Throws

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   :
 *     version: 1.0
 */
interface CallGroup<S, P> : CallHandleable<S>, CallChild<S,P>{
    val strategy: HandlerStrategy<S>

    @Throws(HandlerNotFoundException::class, MutableHandlerException::class)
    override fun onCall(data: P): Any? {
        val decodeData = decodeData(data)
        return strategy.onCallStrategy(decodeData.name, decodeData.sticky, decodeData.data)
    }

    /**
     * 解析收到的数据,返回一组策略信息
     * @param data P
     * @return StrategyData<S>
     */
    fun decodeData(data: P): StrategyData<S>

    /**
     * 策略信息
     * @param D data的泛型
     * @property name String 名字
     * @property sticky Boolean 是否粘性
     * @property data D 数据
     * @constructor
     */
    data class StrategyData<D>(override val name: String, val sticky: Boolean, val data: D) : Named
}