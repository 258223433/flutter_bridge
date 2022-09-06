//
//  GYEFlutterChannelManager.h
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/6.
//  

#import <Foundation/Foundation.h>
#import <Flutter/Flutter.h>
#import "GYEStickinessManager.h"

NS_ASSUME_NONNULL_BEGIN

@interface GYEFlutterChannelManager : NSObject

@property(nonatomic,strong)FlutterMethodChannel *methodChannel;

+(instancetype)share;

/// 添加 channel （ channel 必须在 boost 初始化成功后）
-(void)setupChannelWithEngine:(FlutterEngine*)engine;

/// 发送消息（ 通知flutter ）
-(void)sendMethod:(NSString*)method arguments:(id)arguments;

/// 发送消息（ 通知flutter ）并接收回调
/// @param method 方法名
/// @param arguments 入参
/// @param callback 异步回调。若错误时，返回 FlutterError 实例;
/// 若方法未实现，则返回 FlutterMethodNotImplemented；
/// 若返回其他值，包括 nil ，都表示成功
- (void)invokeMethod:(NSString*)method
           arguments:(id _Nullable)arguments
              result:(FlutterResult _Nullable)callback ;

@end

NS_ASSUME_NONNULL_END
