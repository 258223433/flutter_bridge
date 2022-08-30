import 'package:flutter_bridge/src/call/call_leaf.dart';
import 'package:flutter_bridge/src/call/call_node.dart';
import 'package:flutter_bridge/src/call/call_root.dart';
import 'package:flutter_bridge/src/call/handler_node.dart';
import 'package:flutter_bridge/src/call/invoker_node.dart';
import 'package:flutter_bridge/src/call/strategy/handler_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/invoker_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/single_handler_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/single_invoker_strategy.dart';
import 'package:flutter_bridge/src/common/constant.dart';
import 'package:flutter_bridge/src/model/flutter_call_info.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : data节点,处理type为data的逻辑
///   version: 1.0
class DataCallNode
    with
        HandlerNode<FlutterCallInfo, FlutterCallInfo>,
        CallRoot<FlutterCallInfo, FlutterCallInfo>,
        InvokerNode<FlutterCallInfo, FlutterCallInfo>,
        CallLeaf<FlutterCallInfo, FlutterCallInfo>,
        CallNode<FlutterCallInfo, FlutterCallInfo> {
  @override
  HandlerStrategy<FlutterCallInfo> handlerStrategy =
      SingleHandlerStrategy(SingleHandlerConflictType.exception);

  @override
  InvokerStrategy<FlutterCallInfo> invokerStrategy =
      SingleInvokerStrategy(SingleInvokerConflictType.exception);

  @override
  String name = Constant.typeDataName;

  @override
  StrategyData<FlutterCallInfo> decodeData(FlutterCallInfo data) =>
      StrategyData(data.methodInfo.name, true, data);

  @override
  FlutterCallInfo encodeData(FlutterCallInfo data) {
    data.methodInfo.type = name;
    return data;
  }

  DataCallNode._();

  static final DataCallNode _instance = DataCallNode._();

  factory DataCallNode.instance() {
    return _instance;
  }
}
