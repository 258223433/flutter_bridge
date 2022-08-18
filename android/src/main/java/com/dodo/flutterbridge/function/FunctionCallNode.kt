package com.dodo.flutterbridge.function

import com.dodo.flutterbridge.call.BaseCallNode
import com.dodo.flutterbridge.call.GlobalCallRoot
import com.dodo.flutterbridge.call.HandlerNode.StrategyData
import com.dodo.flutterbridge.call.strategy.SingleHandlerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy
import com.dodo.flutterbridge.common.Constant
import com.dodo.flutterbridge.model.FlutterCallInfo

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   :
 *     version: 1.0
 */
object FunctionCallNode : BaseCallNode<FlutterCallInfo, FlutterCallInfo>(
    SingleHandlerStrategy(SingleHandlerStrategy.ConflictType.Exception),
    SingleInvokerStrategy(SingleInvokerStrategy.ConflictType.Exception)
) {

    override val name: String = Constant.Name.type_function_name

    init {
        linkParent(GlobalCallRoot)
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