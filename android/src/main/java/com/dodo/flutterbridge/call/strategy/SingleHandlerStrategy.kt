package com.dodo.flutterbridge.call.strategy

import com.dodo.flutterbridge.call.CallHandler
import kotlin.jvm.Throws

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
    private val handlers = mutableMapOf<String, CallHandler<A>>()

    override fun addCallHandler(handler: CallHandler<A>) {
        if (handlers[handler.name] == null) {
            handlers[handler.name] = handler
        } else {
            conflictType(handlers, handler)
        }
    }

    override fun removeCallHandler(handler: CallHandler<A>) {
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
            handlers: MutableMap<String, CallHandler<A>>,
            handler: CallHandler<A>
        ) {
            when (this) {
                Replace -> handlers[handler.name] = handler
                Ignore -> {}
                Exception -> throw Exception("同名字的Handler已存在")
            }
        }
    }
}