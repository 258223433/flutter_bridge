import 'dart:convert';
import 'package:flutter_bridge/src/call/handler_node.dart';
import 'package:flutter_bridge/src/call/strategy/handler_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/single_handler_strategy.dart';
import 'package:flutter_bridge/src/function/function_call_node.dart';
import 'package:flutter_bridge/src/json_message_codec.dart';
import 'package:flutter_bridge/src/model/flutter_call_info.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : function的name节点,处理name的逻辑
///   version: 1.0
class FunctionNamedHandlerNode<T> with HandlerNode<T, FlutterCallInfo> {
  ///缓存同名字的FunctionNamedHandlerNode
  static Map<String, FunctionNamedHandlerNode> namedGroups = {};

  ///创建或者复用一个FunctionNamedHandlerNode
  factory FunctionNamedHandlerNode.create(String name, FromJson? fromJson) {
    if (namedGroups[name] == null) {
      namedGroups[name] = FunctionNamedHandlerNode<T>._(name, fromJson);
    }
    return namedGroups[name] as FunctionNamedHandlerNode<T>;
  }

  FunctionNamedHandlerNode._(this.name, this._fromJson) {
    FunctionCallNode.instance().addHandler(this);
  }

  @override
  HandlerStrategy<T> handlerStrategy =
      SingleHandlerStrategy(SingleHandlerConflictType.replace);

  @override
  String name;

  FromJson? _fromJson;

  @override
  StrategyData<T> decodeData(FlutterCallInfo data) {
    var rawData = data.data;
    if (rawData is JsonString) {
      rawData = _fromJson!(json.decode(rawData.jsonString));
    }
    return StrategyData(data.methodInfo.name, false, rawData as T);
  }
}
