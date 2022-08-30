import 'package:flutter_bridge/src/model/flutter_method_info.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : call信息
///   version: 1.0
class FlutterCallInfo {
  ///method信息
  FlutterMethodInfo methodInfo;

  ///数据
  dynamic data;

  FlutterCallInfo(this.methodInfo, this.data);

  @override
  String toString() {
    return 'FlutterCallInfo{methodInfo: $methodInfo, data: $data}';
  }
}
