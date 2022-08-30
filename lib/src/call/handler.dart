import 'package:flutter_bridge/src/call/named.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/18
///   desc   : 可以接收数据并返回结果的handler
///   version: 1.0
abstract class Handler<P> implements Named {

  ///接收数据并返回结果
  Future<dynamic> onCall(P data);
}
