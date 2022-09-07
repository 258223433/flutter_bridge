import 'package:flutter_bridge/src/call/disposable.dart';
import 'package:flutter_bridge/src/call/invoker_node.dart';
import 'package:flutter_bridge/src/call/strategy/invoker_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/single_invoker_strategy.dart';
import 'package:flutter_bridge/src/function/function_named_invoker_node.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/18
///   desc   : 可以和native交互的function
///   version: 1.0
class NativeFunction<T> with InvokerNode<T, T> implements Disposable {
  @override
  InvokerStrategy<T> invokerStrategy =
      SingleInvokerStrategy(SingleInvokerConflictType.replace);

  late FunctionNamedInvokerNode<T> _parent;

  NativeFunction(this.name) {
    _parent = FunctionNamedInvokerNode<T>.create(name);
    attachInvoker(_parent);
  }

  @override
  String name;

  @override
  T encodeData(T data) => data;

  @override
  void dispose() {
    detachInvoker(_parent);
  }
}
