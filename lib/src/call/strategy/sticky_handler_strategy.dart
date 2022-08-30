import 'package:flutter/material.dart';
import 'package:flutter_bridge/src/call/handler.dart';
import 'package:flutter_bridge/src/call/strategy/handler_strategy.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : 处理粘性数据
///   version: 1.0
abstract class StickyHandlerStrategy<A> implements HandlerStrategy<A> {
  final Map<String, A> _stickyData = {};

  @mustCallSuper
  @override
  void addHandler(Handler<A> handler) {
    var data = _stickyData[handler.name];
    print("flutter_bridge StickyHandlerStrategy addHandler ${handler.name}  $data");
    if (data != null) {
      try {
        handler.onCall(data);
      } on Exception {}
    }
  }

  @mustCallSuper
  @override
  Future<dynamic> onCallStrategy(String name, bool sticky, A data) {
    print("flutter_bridge StickyHandlerStrategy onCallStrategy $name $sticky $data");
    if (sticky) {
      _stickyData[name] = data;
    }
    return onCallNoSticky(name, data);
  }

  dynamic onCallNoSticky(String name, A data);
}
