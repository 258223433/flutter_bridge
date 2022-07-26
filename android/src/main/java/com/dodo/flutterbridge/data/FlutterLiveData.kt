package com.dodo.flutterbridge.data

import android.os.Looper
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.dodo.flutterbridge.call.CallLeaf
import com.dodo.flutterbridge.call.Disposable
import com.dodo.flutterbridge.call.strategy.InvokerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy.ConflictType.Replace

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/06
 *     desc   : 可以和flutter交互的LiveData
 *     version: 1.0
 */
class FlutterLiveData<T : Any>(
    override val name: String,
    clazz: Class<T>? = null,
    owner: LifecycleOwner? = null,
    override val invokerStrategy: InvokerStrategy<T> = SingleInvokerStrategy(Replace)
) : LiveData<T>(), CallLeaf<T, T>, Disposable, InvokerStrategy<T> by invokerStrategy {


    private val parent = DataNamedCallNode.create(name, clazz)

    init {
        linkParent(parent)

        owner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                owner.lifecycle.removeObserver(this)
                dispose()
            }
        })
    }

    constructor(name: String, value: T, owner: LifecycleOwner? = null) : this(
        name,
        value.javaClass,
        owner
    ) {
        if (Looper.getMainLooper().thread == Thread.currentThread()) {
            setValue(value)
        } else {
            postValue(value)
        }
    }

    public override fun postValue(value: T) {
        setFlutterValue(value)
    }

    public override fun setValue(value: T) {
        setFlutterValue(value)
    }

    private fun setFlutterValue(value: T) {
        invoke(value, null)
    }

    override fun dispose() {
        unlinkParent(parent)
    }

    override fun onCall(data: T): Any {
        Log.d("dodo", "FlutterLiveData onCall:${Thread.currentThread()}->$data")
        if (Looper.getMainLooper().thread == Thread.currentThread()) {
            super.setValue(data)
        } else {
            super.postValue(data)
        }
        return Any()
    }

    override fun encodeData(data: T): T = data
}
