import 'package:flutter_bridge/src/call/exception/handler_not_found_exception.dart';
import 'package:flutter_bridge/src/call/exception/mutable_handler_exception.dart';
import 'package:flutter_bridge/src/call/handler.dart';
import 'package:flutter_bridge/src/call/strategy/sticky_handler_strategy.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : 同一个名字保存多个handler
///   version: 1.0
class MutableHandlerStrategy<A> extends StickyHandlerStrategy<A> {
  final Map<String, List<Handler<A>>> _handlers = {};


  @override
  void addHandler(Handler<A> handler) {
    super.addHandler(handler);
    var name = handler.name;
    var list = _handlers[name];
    if (list == null) {
      list = [];
      _handlers[name] = list;
    }
    list.add(handler);
  }

  @override
  void removeHandler(Handler<A> handler) {
    var list = _handlers[handler.name];
    list?.removeWhere((element) => element == handler);
  }

  @override
  onCallNoSticky(String name, A data) {
    var list = _handlers[name];
    if (list == null || list.isEmpty) {
      throw HandlerNotFoundException();
    } else {
      for (var element in list) {
        element.onCall(data);
      }
      throw MutableHandlerException();
    }
  }
}
