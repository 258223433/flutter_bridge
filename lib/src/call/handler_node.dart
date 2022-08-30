import 'package:flutter_bridge/src/call/handleable.dart';
import 'package:flutter_bridge/src/call/handler.dart';
import 'package:flutter_bridge/src/call/strategy/handler_strategy.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/27
///   desc   : 可以向下传递的Handler节点
///   version: 1.0
mixin HandlerNode<S, P> implements Handleable<S>, Handler<P> {
  ///Handler策略
  abstract HandlerStrategy<S> handlerStrategy;

  ///解析收到的数据,返回一组策略信息
  StrategyData<S> decodeData(P data);

  @override
  Future<dynamic> onCall(data) {
    var decode = decodeData(data);
    return handlerStrategy.onCallStrategy(
        decode.name, decode.sticky, decode.data);
  }

  @override
  void addHandler(Handler<S> handler) {
    handlerStrategy.addHandler(handler);
  }

  @override
  void removeHandler(Handler<S> handler) {
    handlerStrategy.removeHandler(handler);
  }
}

///策略信息
class StrategyData<D> {
  String name;
  bool sticky;
  D data;

  StrategyData(this.name, this.sticky, this.data);
}
