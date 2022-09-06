//
//  GYEBaseLiveData.h
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/9/5.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface GYEBaseLiveData : NSObject

/// 数据改变，广播通知两端
/// @param name 广播名称
-(void)postValueWithName:(NSString*)name;

/// 数据改变，广播通知两端
///  广播名称默认为类名
-(void)postValue;

@end

NS_ASSUME_NONNULL_END
