import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bridge/src/global_call_root.dart';
import 'package:flutter_bridge/src/json_message_codec.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/21
///   desc   :
///   version: 1.0
class FlutterChannel {
  late MethodChannel delegate;
  late GlobalCallRoot flutterMethodCallHandler;

  FlutterChannel(String channelName) {
    delegate = MethodChannel(channelName,StandardMethodCodec(JsonMessageCodec()));
    flutterMethodCallHandler = GlobalCallRoot();
    delegate.setMethodCallHandler(flutterMethodCallHandler.methodCallHandler);
    debugPrint("flutter_bridge FlutterChannel init");
  }

  Future<T?> invokeMethod<T>(String method, [dynamic arguments]) {
    return delegate.invokeMethod<T>(method, arguments);
  }

}
