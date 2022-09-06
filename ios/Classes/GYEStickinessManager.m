//
//  GYEStickinessManager.m
//  iOSFlutterBridgeDemo
//
//  Created by arrfu on 2022/9/1.
//  处理粘性数据

#import "GYEStickinessManager.h"

@interface GYEStickinessManager()

@property(nonatomic,strong)NSMutableDictionary *stickData;


@end

@implementation GYEStickinessManager

+(instancetype)share {
    static dispatch_once_t once_t;
    static GYEStickinessManager *manager;
    dispatch_once(&once_t, ^{
        manager = [[GYEStickinessManager alloc] init];
    });
    return manager;
}

// 添加粘性数据
-(void)addStickDataWithName:(NSString*)name data:(id)data {
    if (name == nil || name.length == 0) {
        return;
    }
    
    GYEMethodCallNotify *noti = [[GYEMethodCallNotify alloc] initWithMethodName:name arguments:data];
    noti.stick = true;
    [self.stickData setObject:noti forKey:name];
    
}

// 根据名字获取粘性数据
-(GYEMethodCallNotify*)getStickDataWithName:(NSString*)name {
    if (name == nil || name.length == 0) {
        return nil;
    }
    
    GYEMethodCallNotify *noti = [self.stickData objectForKey:name];
    if (noti && noti.arguments) {
        return noti;
    }
    return nil;
}

// 移除所有历史粘性数据
-(void)removeAllStickData {
    [self.stickData removeAllObjects];
}

-(NSMutableDictionary *)stickData {
    if (!_stickData) {
        _stickData = [[NSMutableDictionary alloc] init];
    }
    return _stickData;
}

@end
