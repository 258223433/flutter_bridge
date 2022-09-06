//
//  GYEFlutterCodec.h
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/2.
//  编解码器

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>

NS_ASSUME_NONNULL_BEGIN

NSObject<FlutterMessageCodec> *GYEFlutterUserInfoApiGetCodec(void) ;

@interface GYEFlutterApiCodecReaderWriter : FlutterStandardReaderWriter

@end

NS_ASSUME_NONNULL_END
