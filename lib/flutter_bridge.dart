library flutter_bridge;

import 'package:flutter/material.dart';
import 'package:flutter_boost/flutter_boost.dart';
import 'package:flutter/widgets.dart';

export 'src/main.dart';
export 'src/native_data.dart';

class _FlutterBridgeBinding extends WidgetsFlutterBinding with BoostFlutterBinding {}

class FlutterBridge {
  static void init() {
    _FlutterBridgeBinding();
  }
}
