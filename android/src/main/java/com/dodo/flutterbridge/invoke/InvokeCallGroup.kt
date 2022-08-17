package com.dodo.flutterbridge.invoke

import com.dodo.flutterbridge.call.BaseCallGroup
import com.dodo.flutterbridge.call.CallGroup.StrategyData
import com.dodo.flutterbridge.call.RootCallGroup
import com.dodo.flutterbridge.call.strategy.SingleHandlerStrategy
import com.dodo.flutterbridge.call.strategy.SingleHandlerStrategy.ConflictType.Replace
import com.dodo.flutterbridge.common.Constant
import com.dodo.flutterbridge.model.FlutterCallInfo

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   :
 *     version: 1.0
 */
object InvokeCallGroup :
    BaseCallGroup<FlutterCallInfo, FlutterCallInfo>(SingleHandlerStrategy(Replace)) {

    override val name: String = Constant.Name.type_invoke_name

    init {
        attach(RootCallGroup)
    }

    override fun decodeData(data: FlutterCallInfo): StrategyData<FlutterCallInfo> =
        StrategyData(
            data.methodInfo.name,
            false,
            data
        )

    override fun encodeData(data: FlutterCallInfo): FlutterCallInfo {
        data.methodInfo.sticky = false
        data.methodInfo.type = name
        return data
    }

}