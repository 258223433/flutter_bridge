import 'package:flutter_bridge/src/call/invoke_attachable.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/18
///   desc   : invoker的策略
///   version: 1.0
abstract class InvokerStrategy<P> implements InvokeAttachable<P> {
  ///invoker的策略
  Future<dynamic> invokeStrategy(P data);
}
