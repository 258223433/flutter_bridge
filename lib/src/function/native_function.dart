import 'package:flutter/widgets.dart';
import 'package:flutter_bridge/src/call/disposable.dart';
import 'package:flutter_bridge/src/call/invoker_node.dart';
import 'package:flutter_bridge/src/call/strategy/invoker_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/single_invoker_strategy.dart';
import 'package:flutter_bridge/src/function/function_named_invoker_node.dart';
import 'package:flutter_bridge/src/json_message_codec.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/18
///   desc   : 可以和native交互的function
///   version: 1.0
class NativeFunction<T,R> with InvokerNode<T, T> implements Disposable {
  @override
  InvokerStrategy<T> invokerStrategy =
      SingleInvokerStrategy(SingleInvokerConflictType.replace);

  late FunctionNamedInvokerNode<T> _parent;

  NativeFunction(this.name, [this._fromJson]) {
    _parent = FunctionNamedInvokerNode<T>.create(name);
    attachInvoker(_parent);
  }

  @override
  String name;

  final FromJson<R>? _fromJson;

  @override
  T encodeData(T data) => data;

  @override
  void dispose() {
    detachInvoker(_parent);
    debugPrint('NativeFunction dispose');
  }

  @override
  Future<R> invoke([T? data]) async {
    var res = await super.invoke(data as T);
    return (res as Object?).convertFromJson(_fromJson) as R;
  }
}
