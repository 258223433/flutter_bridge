import 'package:flutter/widgets.dart';
import 'package:flutter_bridge/src/call/disposable.dart';
import 'package:provider/provider.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/09/29
///   desc   : 可以自动Dispose的Provider
///   version: 1.0
class CallProvider<T extends Disposable> extends InheritedProvider<T> {
  CallProvider({
    Key? key,
    required Create<T> create,
    TransitionBuilder? builder,
    Widget? child,
  }) : super(
          key: key,
          create: create,
          dispose: _dispose,
          lazy: false,
          builder: builder,
          child: child,
        );

  static void _dispose(BuildContext context, Disposable disposable) {
    disposable.dispose();
  }
}
