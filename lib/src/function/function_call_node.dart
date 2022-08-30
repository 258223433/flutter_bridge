import 'package:flutter_bridge/src/call/call_leaf.dart';
import 'package:flutter_bridge/src/call/call_node.dart';
import 'package:flutter_bridge/src/call/handler_node.dart';
import 'package:flutter_bridge/src/call/invoker_node.dart';
import 'package:flutter_bridge/src/call/strategy/handler_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/invoker_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/single_handler_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/single_invoker_strategy.dart';
import 'package:flutter_bridge/src/common/constant.dart';
import 'package:flutter_bridge/src/model/flutter_call_info.dart';

import '../call/call_root.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : function节点,处理type为function的逻辑
///   version: 1.0
class FunctionCallNode
    with
        HandlerNode<FlutterCallInfo, FlutterCallInfo>,
        CallRoot<FlutterCallInfo, FlutterCallInfo>,
        InvokerNode<FlutterCallInfo, FlutterCallInfo>,
        CallLeaf<FlutterCallInfo, FlutterCallInfo>,
        CallNode<FlutterCallInfo, FlutterCallInfo> {
  @override
  InvokerStrategy<FlutterCallInfo> invokerStrategy =
      SingleInvokerStrategy(SingleInvokerConflictType.exception);
  @override
  HandlerStrategy<FlutterCallInfo> handlerStrategy =
      SingleHandlerStrategy(SingleHandlerConflictType.exception);

  @override
  String name = Constant.typeFunctionName;

  @override
  FlutterCallInfo encodeData(FlutterCallInfo data) {
    data.methodInfo.type = name;
    return data;
  }

  @override
  StrategyData<FlutterCallInfo> decodeData(FlutterCallInfo data) =>
      StrategyData(data.methodInfo.name, false, data);

  FunctionCallNode._();

  static final FunctionCallNode _instance = FunctionCallNode._();

  factory FunctionCallNode.instance() {
    return _instance;
  }
}
