import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter/widgets.dart';
///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/20
///   desc   :
///   version: 1.0

class FlutterBridgeApp extends FlutterBoostApp{

  static Map<String, FlutterBoostRouteFactory> routerMap = {};

  static Route<dynamic>? _routeFactory(RouteSettings settings, String? uniqueId) {
    FlutterBoostRouteFactory? func = routerMap[settings.name];
    return func!=null? func(settings, uniqueId):null;
  }

  FlutterBridgeApp({super.key,super.appBuilder,super.initialRoute,super.interceptors}) : super(_routeFactory);
}