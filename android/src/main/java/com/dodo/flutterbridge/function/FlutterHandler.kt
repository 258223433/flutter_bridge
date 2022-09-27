package com.dodo.flutterbridge.function

import com.dodo.flutterbridge.call.Disposable
import com.dodo.flutterbridge.call.Handler

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/17
 *     desc   : 可以和flutter交互的function的Handler
 *     version: 1.0
 */
class FlutterHandler<T>(
    override val name: String,
    clazz: Class<T>,
    private val onCall: (T) -> Any
) : Handler<T>, Disposable {

    private val parent = FunctionNamedHandlerNode.create(name, clazz)

    init {
        parent.addHandler(this)
    }

    override fun onCall(data: T): Any {
        return this.onCall.invoke(data)
    }

    override fun dispose() {
        parent.removeHandler(this)
    }
}