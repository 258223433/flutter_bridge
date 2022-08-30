import 'package:flutter_bridge/src/call/exception/invoker_not_found_exception.dart';
import 'package:flutter_bridge/src/call/invoker.dart';
import 'package:flutter_bridge/src/call/strategy/invoker_strategy.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/24
///   desc   : 只保存单个invoker,可设置冲突处理方式
///   version: 1.0
class SingleInvokerStrategy<A> implements InvokerStrategy<A> {
  Invoker<A>? _invoker;
  final SingleInvokerConflictType _conflictType;

  SingleInvokerStrategy(this._conflictType);

  @override
  void attachInvoker(Invoker<A> invoker) {
    if (_invoker == null) {
      _invoker = invoker;
    } else {
      _conflictType.attachInvoker(this, invoker);
    }
  }

  @override
  void detachInvoker(Invoker<A> invoker) {
    _invoker = null;
  }

  @override
  Future invokeStrategy(A data) {
    var invoke = _invoker?.invoke(data);
    if (invoke != null) {
      return invoke;
    }
    throw InvokerNotFoundException();
  }
}

enum SingleInvokerConflictType {
  /// 替换
  replace,

  /// 忽略
  ignore,

  /// 异常
  exception;

  void attachInvoker<A>(SingleInvokerStrategy<A> owner, Invoker<A> invoker) {
    switch (this) {
      case SingleInvokerConflictType.replace:
        owner._invoker = invoker;
        break;
      case SingleInvokerConflictType.ignore:
        break;
      case SingleInvokerConflictType.exception:
        throw Exception("invoker已存在");
    }
  }
}
