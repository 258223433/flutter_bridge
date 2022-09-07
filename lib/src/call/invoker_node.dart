import 'package:flutter_bridge/src/call/invoke_attachable.dart';
import 'package:flutter_bridge/src/call/invoker.dart';
import 'package:flutter_bridge/src/call/strategy/invoker_strategy.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/18
///   desc   : 可以向上传递的invoker节点
///   version: 1.0
mixin InvokerNode<S, P> implements InvokeAttachable<P>, Invoker<S> {
  ///invoker策略
  abstract InvokerStrategy<P> invokerStrategy;

  ///重写成用策略来调用
  @override
  Future<dynamic> invoke(S data) {
    return invokerStrategy.invokeStrategy(encodeData(data));
  }

  @override
  void attachInvoker(Invoker<P> invoker) {
    invokerStrategy.attachInvoker(invoker);
  }

  @override
  detachInvoker(Invoker<P> invoker) {
    invokerStrategy.detachInvoker(invoker);
  }

  ///编码为传递到父节点的数据
  P encodeData(S data);
}
