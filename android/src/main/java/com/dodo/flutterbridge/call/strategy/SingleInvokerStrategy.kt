package com.dodo.flutterbridge.call.strategy

import com.dodo.flutterbridge.call.Invoker
import com.dodo.flutterbridge.call.exception.InvokerNotFoundException
import io.flutter.plugin.common.MethodChannel
import kotlin.jvm.Throws

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/18
 *     desc   : 只保存单个invoker,可设置冲突处理方式
 *     version: 1.0
 */
class SingleInvokerStrategy<A>(
    /**
     * 如果多次attach有冲突处理方式[ConflictType.Replace]替换,和[ConflictType.Ignore]忽略,和[ConflictType.Exception]异常
     */
    private val conflictType: ConflictType
) : InvokerStrategy<A> {

    private var invoker: Invoker<A>? = null

    override fun attachInvoker(invoker: Invoker<A>) {
        if (this.invoker == null) {

            this.invoker = invoker
        } else {
            conflictType(this, invoker)
        }
    }

    override fun detachInvoker(invoker: Invoker<A>) {
        this.invoker = null
    }

    @Throws(InvokerNotFoundException::class)
    override fun invokeStrategy(data: A, callback: MethodChannel.Result?) {
        invoker?.invoke(data, callback) ?: throw InvokerNotFoundException()
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
            owner: SingleInvokerStrategy<A>,
            invoker: Invoker<A>
        ) {
            when (this) {
                Replace -> owner.invoker = invoker
                Ignore -> {}
                Exception -> throw Exception("invoker已存在")
            }
        }
    }
}