package com.dodo.flutterbridge

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.*

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/13
 *     desc   : 可以和flutter交互的StateFlow
 *     version: 1.0
 */
class FlutterStateFlow<T:Any>(
    val name: String,
    initialState: T,
    private val delegate: MutableStateFlow<T> = MutableStateFlow(initialState),
    owner: LifecycleOwner?=null,
) : MutableStateFlow<T> by delegate,TypeOnCallObserver<T,Any?> {

    private val channel = FlutterContext.globalChannel
    private val type = initialState.javaClass

    init {
        channel.addObserver(name, this)

        owner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                channel.removeObserver(name,this@FlutterStateFlow)
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    override var value: T
        get() = delegate.value
        set(value) {
            setFlutterValue(value)
            delegate.value = value
        }

    override fun compareAndSet(expect: T, update: T): Boolean {
        return  delegate.compareAndSet(expect, update).also {
            if (it){
                setFlutterValue(update)
            }
        }
    }

    private fun setFlutterValue(value: T?) {
        channel.invokeMethod(name, value)
    }

    fun dispose() {
        channel.removeObserver(name,this)
        Log.d("dodo", "dispose")
    }

    override fun onCallOfType(data: T): Any? {
        Log.d("dodo", "${Thread.currentThread()}->$data")
        data.apply {
            delegate.value = this
        }
        return null
    }

    override fun fromJson(data: JsonMessageCodec.JsonString): T {
        return data.fromJson(type)
    }
}