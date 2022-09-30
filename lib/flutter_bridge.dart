library flutter_bridge;

import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter_bridge/src/data/native_data.dart';
import 'package:flutter_bridge/src/flutter_bridge_app.dart';
import 'package:flutter_bridge/src/flutter_channel.dart';
import 'package:flutter_bridge/src/flutter_context.dart';

export 'src/flutter_bridge_app.dart';
export 'src/widget/call_provider.dart';
export 'src/widget/caller.dart';
export 'src/data/native_data.dart';
export 'src/function/native_function.dart';
export 'src/function/native_handler.dart';

class FlutterBridge {
  var flutterContext = FlutterContext.instance();

  FlutterBridge._();

  static final FlutterBridge _instance = FlutterBridge._();

  FlutterBridge init() {
    _FlutterBridgeBinding();
    flutterContext.globalChannel =
        FlutterChannel(FlutterContext.globalFlutterChannelName);
    NativeData(FlutterContext.flutterChannelMethodReady, 0);
    return this;
  }

  factory FlutterBridge.instance() {
    return _instance;
  }

  void registerRoute(Map<String, FlutterBoostRouteFactory> route) {
    FlutterBridgeApp.routerMap = route;
  }
}

class _FlutterBridgeBinding extends WidgetsFlutterBinding
    with BoostFlutterBinding {}
