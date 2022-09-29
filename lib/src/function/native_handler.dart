import 'package:flutter/widgets.dart';
import 'package:flutter_bridge/src/call/disposable.dart';
import 'package:flutter_bridge/src/call/handler.dart';
import 'package:flutter_bridge/src/function/function_named_handler_node.dart';
import 'package:flutter_bridge/src/json_message_codec.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : 可以和flutter交互的function的Handler
///   version: 1.0
class NativeHandler<T> implements Handler<T>, Disposable {
  @override
  String name;

  OnCallFunction onCallFunction;

  final FromJson? _fromJson;

  @override
  void dispose() {
    _parent.removeHandler(this);
    debugPrint('NativeHandler dispose');
  }

  @override
  Future<dynamic> onCall(T data) {
    return onCallFunction(data);
  }

  late FunctionNamedHandlerNode _parent;

  NativeHandler(this.name, this.onCallFunction, [this._fromJson]) {
    _parent = FunctionNamedHandlerNode<T>.create(name, _fromJson);
    _parent.addHandler(this);
  }
}

typedef OnCallFunction<T> = Future<T>  Function(T data);
