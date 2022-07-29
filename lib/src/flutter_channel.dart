import 'package:flutter/services.dart';
import 'package:flutter_bridge/src/flutter_method_call_handler.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/21
///   desc   :
///   version: 1.0
class FlutterChannel {
  late MethodChannel delegate;
  late FlutterMethodCallHandler flutterMethodCallHandler;

  FlutterChannel(String channelName) {
    delegate = MethodChannel(channelName);
    flutterMethodCallHandler = FlutterMethodCallHandler();
    delegate.setMethodCallHandler(flutterMethodCallHandler.methodCallHandler);
    print("flutter_bridge FlutterChannel init");
  }

  Future<T?> invokeMethod<T>(String method, [dynamic arguments]) {
    return delegate.invokeMethod<T>(method, arguments);
  }

  void addObserver(String name, OnCallObserver observer) {
    flutterMethodCallHandler.addObserver(name, observer);
  }

  void removeObserver(String name, OnCallObserver observer) {
    flutterMethodCallHandler.removeObserver(name,observer);
  }
}
