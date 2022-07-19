package com.dodo.flutterbridge.example

import android.app.Application
import android.content.Intent
import com.dodo.flutterbridge.FlutterInitializer


/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/06/28
 *     desc   : Application
 *     version: 1.0
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FlutterInitializer.init(this){
            // TODO: 同步数据
        }.registerRoute { activity,options ->

            when (options.pageName){
                "nextActivity"->{
                    activity.startActivity(Intent(activity,MainActivity::class.java))
                }
            }
        }
    }
}