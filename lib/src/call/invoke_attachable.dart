import 'package:flutter_bridge/src/call/invoker.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/18
///   desc   : 可以挂载父invoker
///   version: 1.0
abstract class InvokeAttachable<P> {

  ///挂载invoker
  void attachInvoker(Invoker<P> invoker);

  ///卸载invoker
  void detachInvoker(Invoker<P> invoker);
}
