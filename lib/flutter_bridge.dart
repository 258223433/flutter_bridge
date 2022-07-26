library flutter_bridge;

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bridge/src/flutter_bridge_app.dart';
import 'package:flutter_bridge/src/flutter_channel.dart';
import 'package:flutter_bridge/src/flutter_context.dart';

export 'src/native_data.dart';
export 'src/flutter_bridge_app.dart';

class _FlutterBridgeBinding extends WidgetsFlutterBinding
    with BoostFlutterBinding {}

class FlutterBridge {
  var flutterContext = FlutterContext.instace();

  FlutterBridge._();

  static final FlutterBridge _instance = FlutterBridge._();

  FlutterBridge init() {
    _FlutterBridgeBinding();
    FlutterContext.instace().globalChannel = FlutterChannle(FlutterContext.globalFlutterChannelName);
    return this;
  }

  factory FlutterBridge.instace() {
    return _instance;
  }

  void registerRoute(Map<String, FlutterBoostRouteFactory> route) {
    FlutterBridgeApp.routerMap = route;
  }
}
