package com.dodo.flutterbridge.call.strategy

import com.dodo.flutterbridge.call.Handler
import com.dodo.flutterbridge.call.exception.HandlerNotFoundException
import com.dodo.flutterbridge.call.strategy.SingleHandlerStrategy.ConflictType

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   : 同一个名字只保存单个handler,可设置冲突处理方式
 *     version: 1.0
 */
class SingleHandlerStrategy<A>(
    /**
     * 如果名字重复有冲突处理方式[ConflictType.Replace]替换,和[ConflictType.Ignore]忽略,和[ConflictType.Exception]异常
     */
    private val conflictType: ConflictType
) : StickyHandlerStrategy<A>() {
    private val handlers = mutableMapOf<String, Handler<A>>()

    override fun addHandler(handler: Handler<A>) {
        super.addHandler(handler)
        if (handlers[handler.name] == null) {
            handlers[handler.name] = handler
        } else {
            conflictType(handlers, handler)
        }
    }

    override fun removeHandler(handler: Handler<A>) {
        handlers.remove(handler.name)
    }

    @Throws(HandlerNotFoundException::class)
    override fun onCallStrategy(name: String, data: A): Any? {
        return handlers[name]?.run {
            return onCall(data)
        } ?: throw  HandlerNotFoundException()
    }

    enum class ConflictType {
        /**
         * 替换
         */
        Replace,

        /**
         * 忽略
         */
        Ignore,

        /**
         * 异常
         */
        Exception;

        operator fun <A> invoke(
            handlers: MutableMap<String, Handler<A>>,
            handler: Handler<A>
        ) {
            when (this) {
                Replace -> handlers[handler.name] = handler
                Ignore -> {}
                Exception -> throw Exception("同名字的handler已存在")
            }
        }
    }
}