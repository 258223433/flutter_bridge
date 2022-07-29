import 'package:flutter/services.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/21
///   desc   :
///   version: 1.0
class FlutterMethodCallHandler {
  final Map<String, List<OnCallObserver>> observers = {};

  Future<dynamic> methodCallHandler(MethodCall call) {
    print("flutter_bridge methodCallHandler");
    return handle(call);
  }

  void addObserver(String name, OnCallObserver observer) {
    var list = observers[name];
    if (list == null) {
      list = [];
      observers[name] = list;
    }

    list.add(observer);
    print("flutter_bridge keys${observers.keys}");
  }

  void removeObserver(String name, OnCallObserver observer) {
    var list = observers[name];
    if (list != null) {
      list.remove(observer);
    }
  }

  dynamic handle(MethodCall call) async {
    print("flutter_bridge handle ${call.method}->${call.arguments}");
    var list = observers[call.method];
    print("flutter_bridge find ${list}");
    if (list == null || list.isEmpty) {
      throw Exception(
          'FlutterMethodCallHandler not implemented ${call.method}');
    } else if (list.length == 1) {
      return list[0].onCall(call.arguments);
    } else {
      for (var element in list) {
        element.onCall(call.arguments);
      }
      return null;
    }
  }
}

abstract class OnCallObserver {
  dynamic onCall(dynamic data);
}
