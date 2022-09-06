//
//  GYEMethodCallNotify.h
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/4.
//  原生和flutter消息体

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@class GYEMethodCallNotify;

/// 共享data 回调结果
typedef  void (^GYELiveDataResult)(GYEMethodCallNotify *notify);

@interface GYEMethodCallNotify : NSObject


- (instancetype)initWithMethodName:(NSString*)method arguments:(id _Nullable)arguments;

@property(copy, nonatomic) NSString* name;

//@property(copy, nonatomic, nullable) id arguments;
@property(nonatomic, nullable) id arguments;

/// 是否来自粘性数据
@property(nonatomic, assign) Boolean stick;

@end

NS_ASSUME_NONNULL_END
