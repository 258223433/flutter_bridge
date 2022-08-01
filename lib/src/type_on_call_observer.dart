import 'dart:convert';

import 'package:flutter/foundation.dart';
import 'package:flutter_bridge/src/flutter_method_call_handler.dart';
import 'package:flutter_bridge/src/json_message_codec.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/01
///   desc   : 带泛型的OnCall
///   version: 1.0
abstract class TypeOnCallObserver<A, R> implements OnCallObserver {
  @override
  onCall(data) {
    if (data is JsonString) {
      return onCallOfType(fromJsom(json.decode(data.jsonString)));
    }
    return onCallOfType(data as A);
  }

  R onCallOfType(A data);

  A fromJsom(Map<String, dynamic> json) => throw Exception("TypeOnCallObserver 类型A为对象时需要重写此方法");

}
