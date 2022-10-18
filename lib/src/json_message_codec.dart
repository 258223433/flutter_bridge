import 'dart:convert';
import 'dart:typed_data';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

///   author : liuduo
///   e-mail : liuduo@gyenno.com
///   time   : 2022/08/01
///   desc   : JsonMessageCodec
///   version: 1.0
class JsonMessageCodec extends StandardMessageCodec {
  static const int _valueJson = 64;

  @override
  void writeValue(WriteBuffer buffer, Object? value) {
    try {
      super.writeValue(buffer, value);
    } on ArgumentError {
      buffer.putUint8(_valueJson);
      final Uint8List bytes = utf8.encoder.convert(json.encode(value));
      writeSize(buffer, bytes.length);
      buffer.putUint8List(bytes);
    }
  }

  @override
  Object? readValueOfType(int type, ReadBuffer buffer) {
    try {
      return super.readValueOfType(type, buffer);
    } on FormatException {
      switch (type) {
        case _valueJson:
          final int length = readSize(buffer);
          return JsonString(utf8.decoder.convert(buffer.getUint8List(length)));
        default:
          rethrow;
      }
    }
  }
}

class JsonString {
  String jsonString;

  JsonString(this.jsonString);

  @override
  String toString() {
    return "JsonString(jsonString='$jsonString')";
  }
}

typedef FromJson<T> = T Function(Map<String, dynamic> json);

extension ConvertFromJsonObject on Object? {
  Object? convertFromJson(FromJson? fromJson) {
    var rawData = this;
    if (rawData is JsonString) {
      if(fromJson == null){
        throw Exception("类型为JsonString,fromJson却为空");
      }
      rawData = fromJson(json.decode(rawData.jsonString));
    }
    return rawData;
  }
}
