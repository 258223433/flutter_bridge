import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_bridge/src/flutter_context.dart';
import 'package:flutter_bridge/src/flutter_method_call_handler.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/20
///   desc   : 和原生交互的数据
///   version: 1.0
///todo 数据粘性
class NativeData<T> extends ValueNotifier<T> implements OnCallObserver {
  String name;
  final _channel = FlutterContext.instance().globalChannel;
  List<Completer<T>> completerList = [];

  NativeData(this.name, super.value) {
    if (value != null) {
      _setNativeValue(value);
    }
    _channel.addObserver(name, this);
  }

  @override
  set value(T newValue) {
    _setNativeValue(newValue);
    super.value = newValue;
  }

  @override
  void dispose() {
    _channel.removeObserver(name, this);
    completerList.clear();
    super.dispose();
    print("flutter_bridge NativeData dispose");
  }

  void _setNativeValue(T value) {
    _channel.invokeMethod(name, value);
  }

  @override
  onCall(data) {
    print("flutter_bridge NativeData onCall");
    super.value = data;
  }

  @override
  notifyListeners(){
    super.notifyListeners();
    for (var element in completerList) {
      element.complete(value);
    }
    completerList.clear();
  }

  ///获取一个非空的value
  ///如果没有则等待一个新value的到来
  Future<T> requireValue() async {
    if (value == null) {
      Completer<T> completer = Completer();
      completerList.add(completer);
      return completer.future;
    } else {
      return Future.value(value);
    }
  }
}
