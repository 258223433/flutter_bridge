package com.dodo.flutterbridge.call

import com.dodo.flutterbridge.FlutterContext
import com.dodo.flutterbridge.call.HandlerNode.StrategyData
import com.dodo.flutterbridge.call.strategy.HandlerNotFoundException
import com.dodo.flutterbridge.call.strategy.MutableHandlerException
import com.dodo.flutterbridge.call.strategy.SingleHandlerStrategy
import com.dodo.flutterbridge.call.strategy.SingleHandlerStrategy.ConflictType.*
import com.dodo.flutterbridge.common.Constant
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
internal object GlobalCallRoot : BaseCallRoot<FlutterCallInfo, MethodCall>(
    SingleHandlerStrategy(Exception)
), MethodChannel.MethodCallHandler {

    override val name: String = Constant.Name.root_name

    /**
     * 虽然flutter里面定义了null的类型,但是[StandardMessageCodec.writeValue]中写入数据为非空,所以这里定义一个默认的返回值
     */
    private const val defaultResult = "null"

    /**
     * 顶层调用 super.onCall来传递数据并处理异常
     * @param call MethodCall
     * @param result Result
     */
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        try {
            super.onCall(call)
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
            callInfo.methodInfo.sticky,
            callInfo
        )
    }

}

fun MethodCall.toFlutterCallInfo(): FlutterCallInfo {
//    val methodInfo = GsonUtil.gson.fromJson(method, FlutterMethodInfo::class.java)
    val methodInfo = FlutterMethodInfo(method)
    methodInfo.type = "data"
    methodInfo.sticky = true
    FlutterCallInfo(methodInfo, arguments)
    return FlutterCallInfo(methodInfo, arguments)
}

fun FlutterCallInfo.toMethodCall(): MethodCall {
//    val method = GsonUtil.gson.toJson(methodInfo)
    return MethodCall(methodInfo.name, data)
}