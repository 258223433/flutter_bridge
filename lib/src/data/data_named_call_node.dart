import 'dart:convert';

import 'package:flutter_bridge/src/call/call_leaf.dart';
import 'package:flutter_bridge/src/call/call_node.dart';
import 'package:flutter_bridge/src/call/call_root.dart';
import 'package:flutter_bridge/src/call/handler_node.dart';
import 'package:flutter_bridge/src/call/invoker_node.dart';
import 'package:flutter_bridge/src/call/strategy/handler_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/invoker_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/mutable_handler_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/single_invoker_strategy.dart';
import 'package:flutter_bridge/src/data/data_call_node.dart';
import 'package:flutter_bridge/src/json_message_codec.dart';
import 'package:flutter_bridge/src/model/flutter_call_info.dart';
import 'package:flutter_bridge/src/model/flutter_method_info.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : data的name节点,处理name的逻辑
///   version: 1.0
class DataNamedCallNode<T>
    with
        HandlerNode<T, FlutterCallInfo>,
        CallRoot<T, FlutterCallInfo>,
        InvokerNode<T, FlutterCallInfo>,
        CallLeaf<T, FlutterCallInfo>,
        CallNode<T, FlutterCallInfo> {
  ///缓存同名字的DataNamedCallNode
  static Map<String, DataNamedCallNode> namedGroups = {};

  ///创建或者复用一个DataNamedCallNode
  factory DataNamedCallNode.create(String name, FromJson? fromJson) {
    if (namedGroups[name] == null) {
      namedGroups[name] = DataNamedCallNode<T>._(name, fromJson);
    }
    return namedGroups[name] as DataNamedCallNode<T>;
  }

  DataNamedCallNode._(this.name, this._fromJson) {
    linkParent(DataCallNode.instance());
  }

  @override
  HandlerStrategy<T> handlerStrategy = MutableHandlerStrategy();

  @override
  InvokerStrategy<FlutterCallInfo> invokerStrategy =
  SingleInvokerStrategy(SingleInvokerConflictType.exception);

  @override
  String name;

  FromJson? _fromJson;

  @override
  StrategyData<T> decodeData(FlutterCallInfo data) {
    var rawData = data.data;
    if (rawData is JsonString) {
      rawData = _fromJson!(json.decode(rawData.jsonString));
    }
    return StrategyData(data.methodInfo.name, true, rawData as T);
  }

  @override
  FlutterCallInfo encodeData(T data) =>
      FlutterCallInfo(FlutterMethodInfo(name), data);

  /// 广播给其他监听(模拟收到数据)
  @override
  Future<dynamic> invoke(T data) {
    try {
      handlerStrategy.onCallStrategy(name, true, data);
    }on Exception{}
    return super.invoke(data);
  }
}
