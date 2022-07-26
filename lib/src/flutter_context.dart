import 'package:flutter/services.dart';
import 'package:flutter_bridge/src/flutter_channel.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/21
///   desc   :
///   version: 1.0
class FlutterContext{
  static const globalFlutterChannelName = "global_flutter_channel";
  late final FlutterChannle globalChannel;

  FlutterContext._() ;

  static final FlutterContext _instance = FlutterContext._();

  factory FlutterContext.instace() {
    return _instance;
  }
}