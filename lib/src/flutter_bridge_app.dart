import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter_bridge/flutter_bridge.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/20
///   desc   : Bridge的app，把路由的注册放到初始化里[FlutterBridge]
///   version: 1.0

class FlutterBridgeApp extends FlutterBoostApp {
  static Map<String, FlutterBoostRouteFactory> routerMap = {};

  static Route<dynamic>? _routeFactory(
      RouteSettings settings, String? uniqueId) {
    FlutterBoostRouteFactory? func = routerMap[settings.name];
    return func != null ? func(settings, uniqueId) : null;
  }

  FlutterBridgeApp({
    super.key,
    super.appBuilder,
    super.initialRoute,
    super.interceptors,
    FlutterBoostRouteFactory? routeFactory,
  }) : super(routeFactory??_routeFactory);
}
