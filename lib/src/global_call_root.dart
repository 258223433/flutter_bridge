import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bridge/src/call/call_root.dart';
import 'package:flutter_bridge/src/call/exception/handler_not_found_exception.dart';
import 'package:flutter_bridge/src/call/exception/mutable_handler_exception.dart';
import 'package:flutter_bridge/src/call/handler_node.dart';
import 'package:flutter_bridge/src/call/strategy/handler_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/single_handler_strategy.dart';
import 'package:flutter_bridge/src/common/constant.dart';
import 'package:flutter_bridge/src/data/data_call_node.dart';
import 'package:flutter_bridge/src/flutter_context.dart';
import 'package:flutter_bridge/src/function/function_call_node.dart';
import 'package:flutter_bridge/src/model/flutter_call_info.dart';
import 'package:flutter_bridge/src/model/flutter_method_info.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/21
///   desc   : 全局的MethodCallHandler
///   version: 1.0
class GlobalCallRoot
    with
        HandlerNode<FlutterCallInfo, MethodCall>,
        CallRoot<FlutterCallInfo, MethodCall> {
  Future<dynamic> methodCallHandler(MethodCall call) async {
    debugPrint("flutter_bridge methodCallHandler");
    try {
      var methodCall = MethodCall(call.method, call.arguments);
      return super.onCall(methodCall);
    } on HandlerNotFoundException {
      throw MissingPluginException();
    } on MutableHandlerException {
      return null;
    }
  }

  @override
  Future invoke(FlutterCallInfo data) async {
    var methodCall = data.toMethodCall();
    return FlutterContext.instance()
        .globalChannel
        .invokeMethod(methodCall.method, methodCall.arguments);
  }

  GlobalCallRoot() {
    linkChild(DataCallNode.instance());
    linkChild(FunctionCallNode.instance());
  }

  @override
  HandlerStrategy<FlutterCallInfo> handlerStrategy =
      SingleHandlerStrategy(SingleHandlerConflictType.exception);

  @override
  String name = Constant.rootName;

  @override
  StrategyData<FlutterCallInfo> decodeData(MethodCall data) {
    var callInfo = data.toFlutterCallInfo();
    return StrategyData(callInfo.methodInfo.type, true, callInfo);
  }
}

extension MethodCallExtension on MethodCall {
  FlutterCallInfo toFlutterCallInfo() {
    var methodInfo = FlutterMethodInfo.fromJson(json.decode(method));
    return FlutterCallInfo(methodInfo, arguments);
  }
}

extension FlutterCallInfoExtension on FlutterCallInfo {
  MethodCall toMethodCall() {
    return MethodCall(json.encode(methodInfo.toJson()), data);
  }
}
