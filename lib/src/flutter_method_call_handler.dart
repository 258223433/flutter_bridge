import 'package:flutter/services.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/21
///   desc   :
///   version: 1.0
class FlutterMethodCallHandler {
  final Map<String, OnCallObserver> observers = {};

  Future<dynamic> methodCallHandler(MethodCall call) {
    print("zzyy methodCallHandler");
    return handle(call);
  }

  void addObserver(String name, OnCallObserver observer) {
    observers[name] = observer;
    print("zzyy keys${observers.keys}");
  }

  void removeObserver(String name) {
    observers.remove(name);
  }

  dynamic handle(MethodCall call) async {
    print("zzyy handle ${call.method}->${call.arguments}");
    var observer = observers[call.method];
    print("zzyy find${observer}");
    if (observer != null) {
      return observer.onCall(call.arguments);
    } else {
      throw Exception('FlutterMethodCallHandler not implemented ${call.method}');
    }
  }
}

abstract class OnCallObserver {
  dynamic onCall(dynamic data);
}
