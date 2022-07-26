import 'package:flutter/material.dart';
import 'package:flutter_bridge/flutter_bridge.dart';
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
  final channel = FlutterContext.instace().globalChannel;

  NativeData(this.name, super.value) {
    if (value != null) {
      _setNativeValue(value);
    }
    channel.addObserver(name, this);
  }

  @override
  set value(T newValue) {
    _setNativeValue(newValue);
    super.value = newValue;
  }

  @override
  void dispose() {
    super.dispose();
  }

  void _setNativeValue(T value) {
    channel.invokeMethod(name, value);
  }

  @override
  onCall(data) {
    print("zzyy onCall");
    super.value = data;
  }
}
