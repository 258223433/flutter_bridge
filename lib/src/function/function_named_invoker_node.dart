import 'package:flutter_bridge/src/call/invoker_node.dart';
import 'package:flutter_bridge/src/call/strategy/invoker_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/single_invoker_strategy.dart';
import 'package:flutter_bridge/src/function/function_call_node.dart';
import 'package:flutter_bridge/src/model/flutter_call_info.dart';
import 'package:flutter_bridge/src/model/flutter_method_info.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : function的name节点,处理name的逻辑
///   version: 1.0
class FunctionNamedInvokerNode<T> with InvokerNode<T, FlutterCallInfo> {

  ///缓存同名字的FunctionNamedInvokerNode
  static Map<String, FunctionNamedInvokerNode> namedGroups = {};

  ///创建或者复用一个FunctionNamedInvokerNode
  factory FunctionNamedInvokerNode.create(String name) {

    if (namedGroups[name] == null) {
      namedGroups[name] = FunctionNamedInvokerNode<T>._(name);
    }
    return namedGroups[name] as FunctionNamedInvokerNode<T>;
  }

  FunctionNamedInvokerNode._(this.name){
    attachInvoker(FunctionCallNode.instance());
  }

  @override
  InvokerStrategy<FlutterCallInfo> invokerStrategy =
      SingleInvokerStrategy(SingleInvokerConflictType.exception);

  @override
  String name;

  @override
  FlutterCallInfo encodeData(T data) =>
      FlutterCallInfo(FlutterMethodInfo(name), data);
}
