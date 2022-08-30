import 'package:flutter_bridge/src/call/handler.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : 可以添加和移除handler的容器
///   version: 1.0
abstract class Handleable<S> {
  ///添加handler
  void addHandler(Handler<S> handler);

  ///移除handler
  void removeHandler(Handler<S> handler);
}
