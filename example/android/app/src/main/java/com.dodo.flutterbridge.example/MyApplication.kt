package com.dodo.flutterbridge.example

import android.app.Application
import android.content.Intent
import com.dodo.flutterbridge.FlutterBridge
import com.dodo.flutterbridge.FlutterLiveData
import kotlin.concurrent.thread


/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/06/28
 *     desc   : Application
 *     version: 1.0
 */
class MyApplication : Application() {
    private lateinit var count:FlutterLiveData<Int>
    override fun onCreate() {
        super.onCreate()
        FlutterBridge.init(this){
            count = FlutterLiveData("count",0)
            thread {
                repeat(1000){
                    Thread.sleep(1000)
                    count.postValue((count.value?:0)+1)
                }
            }
        }.registerRoute { activity,options ->
            when (options.pageName){
                "mainActivity"->{
                    activity.startActivityForResult(Intent(activity,MainActivity::class.java),options.requestCode)
                }
            }
        }
    }
}