library flutter_bridge;

import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bridge/src/flutter_bridge_app.dart';

export 'src/main.dart';
export 'src/native_data.dart';
export 'src/flutter_bridge_app.dart';

class _FlutterBridgeBinding extends WidgetsFlutterBinding
    with BoostFlutterBinding {}

class FlutterBridge {
  const FlutterBridge._internal();

  factory FlutterBridge.init() {
    _FlutterBridgeBinding();
    return const FlutterBridge._internal();
  }

  void registerRoute(Map<String, FlutterBoostRouteFactory> route) {
    FlutterBridgeApp.routerMap = route;
  }
}
