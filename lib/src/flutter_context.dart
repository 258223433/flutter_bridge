import 'package:flutter_bridge/src/flutter_channel.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/21
///   desc   :
///   version: 1.0
class FlutterContext {
  static const globalFlutterChannelName =
      "com.dodo.flutterbridge.global_flutter_channel";
  static const flutterChannelMethodReady =
      "com.dodo.flutterbridge.method_ready";
  late final FlutterChannel globalChannel;

  FlutterContext._();

  static final FlutterContext _instance = FlutterContext._();

  factory FlutterContext.instance() {
    return _instance;
  }
}
