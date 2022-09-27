package com.dodo.flutterbridge.example

import android.app.Application
import android.content.Intent
import android.util.Log
import com.dodo.flutterbridge.FlutterBridge
import com.dodo.flutterbridge.data.FlutterLiveData
import kotlin.concurrent.thread


/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/06/28
 *     desc   : Application
 *     version: 1.0
 */
class MyApplication : Application() {
    private lateinit var count: FlutterLiveData<UserInfo>
    override fun onCreate() {
        super.onCreate()
        FlutterBridge.init(this) {
            count = FlutterLiveData("count", clazz = UserInfo::class.java)
            thread {
                repeat(10) {
                    Thread.sleep(1000)
                    if (count.value != null) {
                        Log.d("dodo","user count"+count.value!!.count)
                        count.value!!.count = count.value!!.count + 1
                        count.postValue(count.value!!)
                    }else{
                        count.postValue(UserInfo())
                    }
                }
            }
        }.registerRoute { activity, options ->
            when (options.pageName) {
                "mainActivity" -> {
                    activity.startActivityForResult(
                        Intent(activity, MainActivity::class.java),
                        options.requestCode
                    )
                }
            }
        }
    }
}

class UserInfo {
    val name = "native的name"
    var count = 0
}