package com.dodo.flutterbridge.call

import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import rx.Observable
import rx.Observer

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/09/27
 *     desc   : Call的适配器,可以像retrofit那样设计得很健壮,但是为了简单,只写几种固定的适配
 *     version: 1.0
 */
object CallAdapter {
    @OptIn(DelicateCoroutinesApi::class)
    fun onCallAdapter(callResult: Any, result: MethodChannel.Result) {
        when (callResult) {
            is Flow<*> -> {
                GlobalScope.launch {
                    callResult.catch {exception->
                        //解开flow中用CancellationException包裹的异常
                        var originalException = exception
                        val cause = exception.cause
                        if (exception is CancellationException && cause != null) {
                            originalException = cause
                        }
                        result.error("0",originalException.message,null)
                    }.collect {
                        result.success(it)
                    }
                }
            }
            is Observable<*> -> {
                callResult.subscribe(object : Observer<Any> {

                    override fun onError(e: Throwable?) {
                        result.error("1",e?.message,null)
                    }

                    override fun onNext(t: Any?) {
                        result.success(t)
                    }

                    override fun onCompleted() {
                    }

                })
            }
            else -> {
                result.success(callResult)
            }
        }
    }
}