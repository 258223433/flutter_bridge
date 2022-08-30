import 'package:flutter_bridge/src/call/named.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : method信息
///   version: 1.0
class FlutterMethodInfo implements Named {
  ///method类型
  late String type;

  ///method名字
  @override
  String name;

  FlutterMethodInfo(this.name);

  factory FlutterMethodInfo.fromJson(Map<String, dynamic> json) {
    var info = FlutterMethodInfo(
      json['name'] as String,
    );
    info.type =  json['type'] as String;
    return info;
  }

  Map<String, dynamic> toJson() => <String, dynamic>{
    'name': name,
    'type': type,
  };

  @override
  String toString() {
    return 'FlutterMethodInfo{type: $type, name: $name}';
  }
}
