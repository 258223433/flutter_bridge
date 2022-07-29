package com.dodo.flutterbridge

import android.os.Looper
import android.util.Log
import androidx.lifecycle.*

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/06
 *     desc   : 可以和flutter交互的LiveData
 *     version: 1.0
 */
class FlutterLiveData<T>(private val name: String, owner: LifecycleOwner?=null) :
    LiveData<T>(),
    OnCallObserver {
    private val channel: FlutterMethodChannel = FlutterContext.globalChannel

    init {
        channel.addObserver(name, this)

        owner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                channel.removeObserver(name,this@FlutterLiveData)
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    constructor(name: String, value: T,owner: LifecycleOwner?=null) : this( name,owner) {
        if (Looper.getMainLooper().thread == Thread.currentThread()) {
            setValue(value)
        }else{
            postValue(value)
        }
    }

    public override fun postValue(value: T) {
        setFlutterValue(value)
        super.postValue(value)
    }

    public override fun setValue(value: T) {
        setFlutterValue(value)
        super.setValue(value)
    }

    private fun setFlutterValue(value: T) {
        channel.invokeMethod(name, value)
    }

    override fun onCall(data: Any?): Any? {
        Log.d("dodo", "${Thread.currentThread()}->$data")
        super.setValue(data as T)
        return null
    }

    fun dispose() {
        channel.removeObserver(name,this)
        Log.d("dodo", "dispose")
    }
}
