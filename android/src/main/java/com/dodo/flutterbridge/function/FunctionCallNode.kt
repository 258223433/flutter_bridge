package com.dodo.flutterbridge.function

import com.dodo.flutterbridge.call.CallNode
import com.dodo.flutterbridge.call.HandlerNode.StrategyData
import com.dodo.flutterbridge.call.strategy.HandlerStrategy
import com.dodo.flutterbridge.call.strategy.InvokerStrategy
import com.dodo.flutterbridge.call.strategy.SingleHandlerStrategy
import com.dodo.flutterbridge.call.strategy.SingleInvokerStrategy
import com.dodo.flutterbridge.common.Constant
import com.dodo.flutterbridge.model.FlutterCallInfo

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/08/11
 *     desc   : function节点,处理type为function的逻辑
 *     version: 1.0
 */
object FunctionCallNode : CallNode<FlutterCallInfo, FlutterCallInfo> {

    override val name: String = Constant.Name.TYPE_FUNCTION_NAME

    override val handlerStrategy: HandlerStrategy<FlutterCallInfo> =
        SingleHandlerStrategy(SingleHandlerStrategy.ConflictType.Exception)
    override val invokerStrategy: InvokerStrategy<FlutterCallInfo> =
        SingleInvokerStrategy(SingleInvokerStrategy.ConflictType.Exception)

    override fun decodeData(data: FlutterCallInfo): StrategyData<FlutterCallInfo> =
        StrategyData(
            data.methodInfo.name,
            false,
            data
        )

    override fun encodeData(data: FlutterCallInfo): FlutterCallInfo {
        data.methodInfo.type = name
        return data
    }
}