import 'package:flutter/material.dart';
import 'package:flutter_bridge/src/call/disposable.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/10/19
///   desc   : Caller混合State,用来释放资源
///   version: 1.0
mixin CallState on State {
  late final List<Disposable> _disposables;

  @override
  void initState() {
    _disposables = initCaller();
    super.initState();
  }

  @override
  void dispose() {
    for (var element in _disposables) {
      element.dispose();
    }
    super.dispose();
  }

  List<Disposable> initCaller();
}
