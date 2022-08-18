package com.dodo.flutterbridge.function

import com.dodo.flutterbridge.call.Handler

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   :
 *     version: 1.0
 */
class FlutterHandler<T>(
    override val name: String,
    clazz: Class<T>,
    private val onCall: (T) -> Any?
) : Handler<T> {

    private val parent = FunctionNamedHandlerNode.create(name, clazz)

    init {
        parent.addHandler(this)
    }

    override fun onCall(data: T): Any? {
        return this.onCall.invoke(data)
    }

    fun dispose() {
        parent.removeHandler(this)
    }
}