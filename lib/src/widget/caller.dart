import 'package:flutter/widgets.dart';
import 'package:flutter_bridge/src/call/disposable.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/09/30
///   desc   :
///   version: 1.0
class Caller<T extends Disposable> extends StatefulWidget {
  const Caller({
    super.key,
    required this.caller,
    required this.builder,
  });

  final T caller;
  final Widget Function(
    BuildContext context,
    T caller,
  ) builder;

  @override
  _CallerWidgetState<T> createState() => _CallerWidgetState();
}

class _CallerWidgetState<T extends Disposable> extends State<Caller<T>> {

  @override
  Widget build(BuildContext context) {
    debugPrint("build");
    var widget2 = widget.builder(context,widget.caller);
    debugPrint("build end");
    return widget2;
  }

  @override
  void dispose() {
    widget.caller.dispose();
    super.dispose();
    debugPrint("dispose");
  }
}
