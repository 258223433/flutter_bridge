import 'package:flutter_bridge/src/call/named.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/18
///   desc   : 调用的接口
///   version: 1.0
abstract class Invoker<S> implements Named{

  ///调用
  Future<dynamic> invoke(S data);
}