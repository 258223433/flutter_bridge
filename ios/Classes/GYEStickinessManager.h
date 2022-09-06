//
//  GYEStickinessManager.h
//  iOSFlutterBridgeDemo
//
//  Created by arrfu on 2022/9/1.
//  处理粘性数据

#import <Foundation/Foundation.h>
#import "GYEMethodCallNotify.h"

NS_ASSUME_NONNULL_BEGIN

@interface GYEStickinessManager : NSObject

+(instancetype)share;

/// 添加粘性数据
-(void)addStickDataWithName:(NSString*)name data:(id)data;

/// 根据名字获取粘性数据
-(GYEMethodCallNotify*)getStickDataWithName:(NSString*)name;

/// 移除所有历史粘性数据
-(void)removeAllStickData;

@end

NS_ASSUME_NONNULL_END
