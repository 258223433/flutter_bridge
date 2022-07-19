package com.dodo.flutterbridge

import io.flutter.plugin.common.MethodChannel

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/13
 *     desc   :
 *     version: 1.0
 */

/**
 * FlutterLiveData的名字
 * 枚举名字作为[MethodChannel]传递的method
 * 同一个名字的不同[FlutterLiveData]对象，从flutter监听到的数据相同
 */

sealed class FlutterDataInfo {
    protected abstract val name: FlutterDataName
    val dataName: String
        get() = name.dataName

}

object Count : FlutterDataInfo() {
    override val name = FlutterDataName.Count
}

enum class FlutterDataName(val dataName: String) {
    Count("count"),
}