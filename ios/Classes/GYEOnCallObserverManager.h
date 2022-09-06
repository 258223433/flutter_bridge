//
//  GYEOnCallObserverManager.h
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/5.
//

#import <Foundation/Foundation.h>
#import "GYEOnCallObserverWrapper.h"
#import "GYEFlutterMethodName.h"
#import <Flutter/Flutter.h>

NS_ASSUME_NONNULL_BEGIN

@interface GYEOnCallObserverManager : NSObject

+(instancetype)share;

/// 添加 data 回调监听
-(void)addObserver:(NSString*)name observer:(id<GYEOnCallObserver>)observer ;

/// 添加 data 监听 block 回调
-(void)addObserver:(NSString*)name observer:(id<GYEOnCallObserver>)observer callback:(GYELiveDataResult)callback;

/// 添加 method 回调监听
-(void)addMethodObserver:(NSString*)name observer:(id<GYEOnCallObserver>)observer handle:(FlutterResult _Nullable)callback;

/// 移除监听
-(void)removeObserver:(NSString*)name observer:(id<GYEOnCallObserver>)observer ;

-(void)removeObserver:(id<GYEOnCallObserver>)observer;

/// 通知所有 native 和 flutter
+(void)postNativeFlutterWithName:(NSString*)name data:(id)data;

/// data 方法名是否已注册
-(BOOL)isContainMethodName:(NSString*)name;

/// method 方法名是否已注册
-(BOOL)isContainMethodName:(NSString*)name type:(NSString*)type;

/// 根据方法名获取值
-(GYEOnCallObserverWrapper*)getValueWithMethodName:(NSString*)name;

/// 移除方法监听
-(void)removeMethodObserver:(NSString*)name observer:(id<GYEOnCallObserver>)observer;

@end

NS_ASSUME_NONNULL_END
