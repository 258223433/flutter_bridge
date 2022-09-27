package com.dodo.flutterbridge

import com.dodo.flutterbridge.call.CallAdapter
import com.dodo.flutterbridge.call.CallRoot
import com.dodo.flutterbridge.call.HandlerNode.StrategyData
import com.dodo.flutterbridge.call.exception.HandlerNotFoundException
import com.dodo.flutterbridge.call.exception.MutableHandlerException
import com.dodo.flutterbridge.call.strategy.HandlerStrategy
import com.dodo.flutterbridge.call.strategy.SingleHandlerStrategy
import com.dodo.flutterbridge.call.strategy.SingleHandlerStrategy.ConflictType.Exception
import com.dodo.flutterbridge.common.Constant
import com.dodo.flutterbridge.common.GsonUtil
import com.dodo.flutterbridge.data.DataCallNode
import com.dodo.flutterbridge.function.FunctionCallNode
import com.dodo.flutterbridge.model.FlutterCallInfo
import com.dodo.flutterbridge.model.FlutterMethodInfo
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.StandardMessageCodec

/**
 *     author : liuduo
 *     e-mail : liuduo@gyenno.com
 *     time   : 2022/07/06
 *     desc   : 全局的MethodCallHandler
 *     version: 1.0
 */
internal object GlobalCallRoot : CallRoot<FlutterCallInfo, MethodCall>,
    MethodChannel.MethodCallHandler {

    override val name: String = Constant.Name.ROOT_NAME

    override val handlerStrategy: HandlerStrategy<FlutterCallInfo> =
        SingleHandlerStrategy(Exception)

    /**
     * 虽然flutter里面定义了null的类型,但是[StandardMessageCodec.writeValue]中写入数据为非空,所以这里定义一个默认的返回值
     */
    private const val defaultResult = "null"

    init {
        linkChild(DataCallNode)
        linkChild(FunctionCallNode)
    }

    /**
     * 顶层调用 super.onCall来传递数据并处理异常
     * @param call MethodCall
     * @param result Result
     */
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        try {
            CallAdapter.onCallAdapter(super.onCall(call), result)
        } catch (e: HandlerNotFoundException) {
            result.notImplemented()
        } catch (e: MutableHandlerException) {
            result.success(defaultResult)
        }
    }

    /**
     * 顶层处理invoke 调用[MethodChannel.invokeMethod]
     * @param data FlutterCallInfo
     * @param callback Result
     */
    override fun invoke(data: FlutterCallInfo, callback: MethodChannel.Result?) {
        val methodCall = data.toMethodCall()
        FlutterContext.globalChannel.invokeMethod(methodCall.method, methodCall.arguments, callback)
    }

    override fun decodeData(data: MethodCall): StrategyData<FlutterCallInfo> {
        val callInfo = data.toFlutterCallInfo()
        return StrategyData(
            callInfo.methodInfo.type,
            true,
            callInfo
        )
    }

}

fun MethodCall.toFlutterCallInfo(): FlutterCallInfo {
    val methodInfo = GsonUtil.gson.fromJson(method, FlutterMethodInfo::class.java)
    return FlutterCallInfo(methodInfo, arguments)
}

fun FlutterCallInfo.toMethodCall(): MethodCall {
    val method = GsonUtil.gson.toJson(methodInfo)
    return MethodCall(method, data)
}