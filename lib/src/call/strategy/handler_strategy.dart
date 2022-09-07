import 'package:flutter_bridge/src/call/handleable.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : 保存和调用handler的策略
///   version: 1.0
abstract class HandlerStrategy<S> implements Handleable<S> {
  ///handler的策略
  Future<dynamic> onCallStrategy(String name, bool sticky, S data);
}
