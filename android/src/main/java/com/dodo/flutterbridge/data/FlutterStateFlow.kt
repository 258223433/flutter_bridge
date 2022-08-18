package com.dodo.flutterbridge.data

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.dodo.flutterbridge.call.CallLeaf
import com.dodo.flutterbridge.call.strategy.InvokerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy.ConflictType.Replace
import kotlinx.coroutines.flow.*


/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/13
 *     desc   : 可以和flutter交互的StateFlow
 *     version: 1.0
 */
class FlutterStateFlow<T : Any>(
    override val name: String,
    initialState: T,
    private val delegate: MutableStateFlow<T> = MutableStateFlow(initialState),
    owner: LifecycleOwner? = null,
    override val invokerStrategy: InvokerStrategy<T> = SingleInvokerStrategy(Replace)
) : MutableStateFlow<T> by delegate, CallLeaf<T, T>, InvokerStrategy<T> by invokerStrategy {


    private val parent = DataNamedCallNode.create(name, initialState.javaClass)

    init {
        linkParent(parent)

        owner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                dispose()
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    override var value: T
        get() = delegate.value
        set(value) {
            setFlutterValue(value)
        }

    override fun compareAndSet(expect: T, update: T): Boolean {
        return delegate.compareAndSet(expect, update).also {
            if (it) {
                setFlutterValue(update)
            }
        }
    }

    private fun setFlutterValue(value: T) {
        invoke(value, null)
    }

    fun dispose() {
        unlinkParent(parent)
        Log.d("dodo", "dispose")
    }

    override fun onCall(data: T): Any? {
        delegate.value = value
        return null
    }

    override fun encodeData(data: T): T = data
}