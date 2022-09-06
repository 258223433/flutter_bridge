//
//  GYEBaseLiveData.m
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/9/5.
//

#import "GYEBaseLiveData.h"
#import "GYEOnCallObserverManager.h"

@implementation GYEBaseLiveData

/// 数据改变，广播通知两端
/// @param name 广播名称
-(void)postValueWithName:(NSString*)name {
    if (name == nil || name.length == 0) {
        return;
    }
    // 原生的数据改变，需要分发通知两端
    [GYEOnCallObserverManager postNativeFlutterWithName:name data:self];
}

/// 数据改变，广播通知两端
///  广播名称默认为类名
-(void)postValue {
    NSString *className = NSStringFromClass(self.class);
    [self postValueWithName:className];
}

@end
