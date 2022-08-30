import 'package:flutter_bridge/src/call/call_root.dart';
import 'package:flutter_bridge/src/call/handler.dart';
import 'package:flutter_bridge/src/call/invoker_node.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : 可以向上传递并且可以接收数据的叶子节点
///   version: 1.0
mixin CallLeaf<S, P> on InvokerNode<S, P> implements Handler<P> {
  ///和父节点连接
  void linkParent(CallRoot<P, Object> callRoot) {
    callRoot.addHandler(this);
    attachInvoker(callRoot);
  }

  ///和父节点断开连接
  void unlinkParent(CallRoot<P, Object> callRoot) {
    callRoot.removeHandler(this);
    detachInvoker(callRoot);
  }
}
