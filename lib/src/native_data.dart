import 'dart:async';
import 'dart:ffi';

import 'package:flutter/material.dart';
import 'package:flutter_bridge/src/flutter_context.dart';
import 'package:flutter_bridge/src/flutter_method_call_handler.dart';
import 'package:flutter_bridge/src/type_on_call_observer.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/20
///   desc   : 和原生交互的数据
///   version: 1.0
///todo 数据粘性
class NativeData<T> extends ValueNotifier<T> with TypeOnCallObserver<T, void> {
  String name;
  final _channel = FlutterContext
      .instance()
      .globalChannel;
  List<Completer<T>> completerList = [];
  final FromJson<T>? _fromJson;

  NativeData(this.name, super.value, [this._fromJson]) {
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
  notifyListeners() {
    super.notifyListeners();
    for (var element in completerList) {
      element.complete(value);
    }
    completerList.clear();
  }

  ///todo 多个数据requireValue存在的顺序问题
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

  @override
  void onCallOfType(T data) {
    print("flutter_bridge NativeData onCall");
    super.value = data;
  }

  @override
  T fromJsom(Map<String, dynamic> json) {
    if (_fromJson == null) {
      throw Exception("NativeData 请通过构造方法传递fromJson");
    }
    return _fromJson!.call(json);
  }
}

typedef FromJson<T> = T Function(Map<String, dynamic> json);
