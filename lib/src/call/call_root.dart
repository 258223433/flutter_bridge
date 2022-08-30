import 'package:flutter_bridge/src/call/call_leaf.dart';
import 'package:flutter_bridge/src/call/handler_node.dart';
import 'package:flutter_bridge/src/call/invoker.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : 可以向下传递并且可以调用的根节点
///   version: 1.0
mixin CallRoot<S, P> on HandlerNode<S, P> implements Invoker<S> {
  ///和子节点连接
  void linkChild(CallLeaf<Object, S> callLeaf) {
    callLeaf.attachInvoker(this);
    addHandler(callLeaf);
  }

  ///和子节点断开连接
  void unlinkChild(CallLeaf<Object, S> callLeaf) {
    callLeaf.detachInvoker(this);
    removeHandler(callLeaf);
  }
}
