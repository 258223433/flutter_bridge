import 'package:flutter/material.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/07/20
///   desc   : 和原生交互的数据
///   version: 1.0
class NativeData<T> extends ChangeNotifier {
  T data;

  String name;

  NativeData(this.name, this.data);
}
