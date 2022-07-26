import 'package:flutter/services.dart';
import 'package:flutter_bridge/src/flutter_method_call_handler.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/21
///   desc   :
///   version: 1.0
class FlutterChannle {
  late MethodChannel delegate;
  late FlutterMethodCallHandler flutterMethodCallHandler;

  FlutterChannle(String channelName) {
    delegate = MethodChannel(channelName);
    flutterMethodCallHandler = FlutterMethodCallHandler();
    delegate.setMethodCallHandler(flutterMethodCallHandler.methodCallHandler);
    print("zzyy FlutterChannle init");
  }

  Future<T?> invokeMethod<T>(String method, [dynamic arguments]) {
    return delegate.invokeMethod<T>(method, arguments);
  }

  void addObserver(String name, OnCallObserver observer) {
    flutterMethodCallHandler.addObserver(name, observer);
  }

  void removeObserver(String name) {
    flutterMethodCallHandler.removeObserver(name);
  }
}
