package com.dodo.flutterbridge.data

import com.dodo.flutterbridge.call.BaseCallGroup
import com.dodo.flutterbridge.call.CallGroup.StrategyData
import com.dodo.flutterbridge.call.RootCallGroup
import com.dodo.flutterbridge.call.strategy.SingleHandlerStrategy
import com.dodo.flutterbridge.call.strategy.SingleHandlerStrategy.ConflictType.Exception
import com.dodo.flutterbridge.common.Constant
import com.dodo.flutterbridge.model.FlutterCallInfo

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   :
 *     version: 1.0
 */
object DataCallGroup :
    BaseCallGroup<FlutterCallInfo, FlutterCallInfo>(SingleHandlerStrategy(Exception)) {

    override val name = Constant.Name.type_data_name

    init {
        attach(RootCallGroup)
    }

    override fun decodeData(data: FlutterCallInfo): StrategyData<FlutterCallInfo> =
        StrategyData(
            data.methodInfo.name,
            data.methodInfo.sticky,
            data
        )


    override fun encodeData(data: FlutterCallInfo): FlutterCallInfo {
        data.methodInfo.sticky = true
        data.methodInfo.type = name
        return data
    }

}