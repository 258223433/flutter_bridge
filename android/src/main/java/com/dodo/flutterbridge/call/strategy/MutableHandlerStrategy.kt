package com.dodo.flutterbridge.call.strategy

import com.dodo.flutterbridge.call.Handler
import com.dodo.flutterbridge.call.exception.HandlerNotFoundException
import com.dodo.flutterbridge.call.exception.MutableHandlerException

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   : 同一个名字保存多个handler
 *     version: 1.0
 */
class MutableHandlerStrategy<A> : StickyHandlerStrategy<A>() {

    private val handlers = mutableMapOf<String, MutableList<Handler<A>>>()

    override fun addHandler(handler: Handler<A>) {
        super.addHandler(handler)
        val name = handler.name
        var list = handlers[name]
        if (list == null) {
            list = mutableListOf()
            handlers[name] = list
        }
        list.add(handler)
    }

    override fun removeHandler(handler: Handler<A>) {
        val list = handlers[handler.name] ?: return
        val each = list.iterator()
        while (each.hasNext()) {
            if (handler == each.next()) {
                each.remove()
            }
        }
    }

    @Throws(HandlerNotFoundException::class, MutableHandlerException::class)
    override fun onCallStrategy(name: String, data: A): Any {
        val list = handlers[name]
        if (list.isNullOrEmpty()) {
            throw HandlerNotFoundException()
        } else {
            list.forEach {
                it.onCall(data)
            }
            throw MutableHandlerException()
        }
    }
}