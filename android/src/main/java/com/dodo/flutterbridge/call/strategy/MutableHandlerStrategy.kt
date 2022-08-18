package com.dodo.flutterbridge.call.strategy

import com.dodo.flutterbridge.call.Handler

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
        val name = handler.name
        var list = handlers[name]
        if (list == null) {
            list = mutableListOf()
            handlers[name] = list
        }
        list.add(handler)
    }

    override fun removeHandler(handler: Handler<A>) {
        val list = handlers[handler.name]
        list?.forEach {
            if (it == handler) {
                list.remove(it)
            }
        }
    }

    @Throws(HandlerNotFoundException::class, MutableHandlerException::class)
    override fun onCallStrategy(name: String, data: A): Any? {
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