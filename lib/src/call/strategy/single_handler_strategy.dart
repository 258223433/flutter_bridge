import 'package:flutter_bridge/src/call/exception/handler_not_found_exception.dart';
import 'package:flutter_bridge/src/call/handler.dart';
import 'package:flutter_bridge/src/call/strategy/sticky_handler_strategy.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : 同一个名字只保存单个handler,可设置冲突处理方式
///   version: 1.0
class SingleHandlerStrategy<A> extends StickyHandlerStrategy<A> {
  final Map<String, Handler<A>> _handlers = {};
  SingleHandlerConflictType conflictType;

  SingleHandlerStrategy(this.conflictType);

  @override
  onCallNoSticky(String name, A data) {
    var handler = _handlers[name];
    if (handler == null) {
      throw HandlerNotFoundException();
    }
    return handler.onCall(data);
  }

  @override
  void addHandler(Handler<A> handler) {
    super.addHandler(handler);
    if (_handlers[handler.name] == null) {
      _handlers[handler.name] = handler;
    } else {
      conflictType.addHandler(_handlers, handler);
    }
  }

  @override
  void removeHandler(Handler<A> handler) {
    _handlers[handler.name] = handler;
  }
}

enum SingleHandlerConflictType {
  /// 替换
  replace,

  /// 忽略
  ignore,

  /// 异常
  exception;

  void addHandler<A>(Map<String, Handler<A>> handlers, Handler<A> handler) {
    switch (this) {
      case SingleHandlerConflictType.replace:
        handlers[handler.name] = handler;
        break;
      case SingleHandlerConflictType.ignore:
        break;
      case SingleHandlerConflictType.exception:
        throw Exception("同名字的handler已存在");
    }
  }
}
