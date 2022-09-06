//
//  GYEFlutterCodec.m
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/2.
//

#import "GYEFlutterCodec.h"
#import "MJExtension.h"

int const k_FlutterJsonType = 64;

/// 读
@interface GYEFlutterApiCodecReader : FlutterStandardReader
@end

@implementation GYEFlutterApiCodecReader
- (nullable id)readValueOfType:(UInt8)type {
  switch (type) {
  case k_FlutterJsonType:
      {
         NSString *dataStrUtf8 = [self readUTF8];
          return [GYEFlutterApiCodecReader decodeCustomData:dataStrUtf8];
      }

  default:
    return [super readValueOfType:type];
  }
}

+(NSDictionary*)decodeCustomData:(NSString*)content {
    if (content == nil) {
        return nil;
    }
    NSDictionary *dic = [content mj_JSONObject];
    return dic;
}

@end

/// 写
@interface GYEFlutterApiCodecWriter : FlutterStandardWriter
@end

@implementation GYEFlutterApiCodecWriter
- (void)writeValue:(id)value {
    @try {
        [super writeValue:value];
    }
    @catch (NSException *exception) {
        [self writeByte:k_FlutterJsonType];
        NSString *jsonStr = [value mj_JSONString];
        [self writeUTF8:jsonStr];
    }
}

@end

// 读写
@interface GYEFlutterApiCodecReaderWriter()
@end

@implementation GYEFlutterApiCodecReaderWriter
- (FlutterStandardWriter *)writerWithData:(NSMutableData *)data {
  return [[GYEFlutterApiCodecWriter alloc] initWithData:data];
}
- (FlutterStandardReader *)readerWithData:(NSData *)data {
  return [[GYEFlutterApiCodecReader alloc] initWithData:data];
}
@end

NSObject<FlutterMessageCodec> *GYEFlutterUserInfoApiGetCodec() {
  static dispatch_once_t s_pred = 0;
  static FlutterStandardMessageCodec *s_sharedObject = nil;
  dispatch_once(&s_pred, ^{
      GYEFlutterApiCodecReaderWriter *readerWriter =
        [[GYEFlutterApiCodecReaderWriter alloc] init];
    s_sharedObject =
        [FlutterStandardMessageCodec codecWithReaderWriter:readerWriter];
  });
  return s_sharedObject;
}
