import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_bridge/src/call/call_leaf.dart';
import 'package:flutter_bridge/src/call/disposable.dart';
import 'package:flutter_bridge/src/call/invoker_node.dart';
import 'package:flutter_bridge/src/call/strategy/invoker_strategy.dart';
import 'package:flutter_bridge/src/call/strategy/single_invoker_strategy.dart';
import 'package:flutter_bridge/src/data/data_named_call_node.dart';
import 'package:flutter_bridge/src/json_message_codec.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/20
///   desc   : 和原生交互的数据
///   version: 1.0
class NativeData<T> extends ValueNotifier<T>
    with InvokerNode<T, T>, CallLeaf<T, T>
    implements Disposable {
  @override
  String name;

  @override
  InvokerStrategy<T> invokerStrategy =
      SingleInvokerStrategy(SingleInvokerConflictType.replace);

  List<Completer<T>> completerList = [];
  final FromJson<T>? _fromJson;

  late DataNamedCallNode<T> _parent;

  NativeData(this.name, super.value, [this._fromJson]) {
    _parent = DataNamedCallNode<T>.create(name, _fromJson);
    linkParent(_parent);
    if (value != null) {
      _setNativeValue(value);
    }
  }

  @override
  set value(T newValue) {
    _setNativeValue(newValue);
  }

  @override
  void dispose() {
    unlinkParent(_parent);
    completerList.clear();
    super.dispose();
    print("flutter_bridge NativeData dispose");
  }

  void _setNativeValue(T value) {
    invoke(value);
  }

  @override
  notifyListeners() {
    super.notifyListeners();
    for (var element in completerList) {
      element.complete(value);
    }
    completerList.clear();
  }

  ///todo 多个数据requireValue存在的顺序问题
  ///获取一个非空的value
  ///如果没有则等待一个新value的到来
  Future<T> requireValue() async {
    if (value == null) {
      Completer<T> completer = Completer();
      completerList.add(completer);
      return completer.future;
    } else {
      return Future.value(value);
    }
  }

  @override
  T encodeData(T data) => data;

  @override
  Future<dynamic> onCall(T data) {
    print("flutter_bridge NativeData onCall $data");
    super.value = data;
    return Future.value(null);
  }
}
