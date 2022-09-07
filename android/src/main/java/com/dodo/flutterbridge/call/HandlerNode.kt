package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.call.exception.HandlerNotFoundException
import com.dodo.flutterbridge.call.exception.MutableHandlerException
import com.dodo.flutterbridge.call.strategy.HandlerStrategy

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   : 可以向下传递的handler节点
 *     version: 1.0
 */
interface HandlerNode<S, P> : Handleable<S>, Handler<P> {
    val handlerStrategy: HandlerStrategy<S>

    @Throws(HandlerNotFoundException::class, MutableHandlerException::class)
    override fun onCall(data: P): Any? {
        val decodeData = decodeData(data)
        return handlerStrategy.onCallStrategy(decodeData.name, decodeData.sticky, decodeData.data)
    }

    override fun addHandler(handler: Handler<S>) {
        handlerStrategy.addHandler(handler)
    }

    override fun removeHandler(handler: Handler<S>) {
        handlerStrategy.removeHandler(handler)
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