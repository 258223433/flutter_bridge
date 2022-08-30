import 'package:flutter_bridge/src/call/call_leaf.dart';
import 'package:flutter_bridge/src/call/call_root.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : 可以向上传递并且可以向下传递的双向节点
///   version: 1.0
mixin  CallNode<S, P> on CallRoot<S,P>,CallLeaf<S,P> {
}
