package com.dodo.flutterbridge.function

import com.dodo.flutterbridge.call.CallAdapter
import com.dodo.flutterbridge.call.Disposable
import com.dodo.flutterbridge.call.InvokerNode
import com.dodo.flutterbridge.call.strategy.InvokerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy.ConflictType.Replace
import com.dodo.flutterbridge.convertFromJson
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.flow.Flow

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/18
 *     desc   : 可以和flutter交互的function
 *     version: 1.0
 */
class FlutterFunction<T, R>(
    override val name: String,
    val clazz: Class<R>? = null,
) : InvokerNode<T, T>, Disposable {

    override val invokerStrategy: InvokerStrategy<T> = SingleInvokerStrategy(Replace)

    private val parent = FunctionNamedInvokerNode.create<T>(name)

    init {
        attachInvoker(parent)
    }

    override fun encodeData(data: T): T = data

    override fun dispose() {
        detachInvoker(parent)
    }

    override fun invoke(data: T, callback: MethodChannel.Result?) {
        super.invoke(data, object : MethodChannel.Result {
            override fun success(result: Any?) {
                callback?.success(result.convertFromJson(clazz))
            }

            override fun error(errorCode: String, errorMessage: String?, errorDetails: Any?) {
                callback?.error(errorCode, errorMessage, errorDetails)
            }

            override fun notImplemented() {
                callback?.notImplemented()
            }

        })
    }

    fun invokeFlow(data: T): Flow<R> {
        return CallAdapter.invokeAdapter(this, data, Flow::class.java) as Flow<R>
    }
}