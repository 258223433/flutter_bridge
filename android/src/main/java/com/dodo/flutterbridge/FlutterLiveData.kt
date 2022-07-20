package com.dodo.flutterbridge

import android.util.Log
import androidx.lifecycle.*
import io.flutter.plugin.common.MethodChannel

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/06
 *     desc   : 可以和flutter交互的LiveData
 *     version: 1.0
 */
class FlutterLiveData<T>(owner: LifecycleOwner, val name: String) :
    LiveData<T>(),
    OnCallObserver {
    private val channel = FlutterContext.globalChannel

    init {
        channel.addObserver(name, this)

        owner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
                channel.removeObserver(name)
                owner.lifecycle.removeObserver(this)
            }
        })
    }

    constructor(owner: LifecycleOwner,  name: String, value: T ):this(owner, name){
        setValue(value)
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
}
