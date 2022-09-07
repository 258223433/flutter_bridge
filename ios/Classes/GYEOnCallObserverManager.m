//
//  GYEOnCallObserverManager.m
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/5.
//  服务监听分发管理器

#import "GYEOnCallObserverManager.h"
#import "MJExtension.h"
#import "GYEStickinessManager.h"

@interface GYEOnCallObserverManager()

/// 数据 data
@property(nonatomic,strong)NSMutableArray<GYEOnCallObserverWrapper *> *observers;

/// 方法 method
@property(nonatomic,strong)NSMutableDictionary *methodObservers;

@end

@implementation GYEOnCallObserverManager

+(instancetype)share {
    static dispatch_once_t once_t;
    static GYEOnCallObserverManager *manager;
    dispatch_once(&once_t, ^{
        manager = [[GYEOnCallObserverManager alloc] init];
    });
    return manager;
}

/// 通知所有 native 和 flutter
+(void)postNativeFlutterWithName:(NSString*)name data:(id)data {
    [[self share] postNativeFlutterWithName:name data:data];
}

/// 通知所有 native 和 flutter
-(void)postNativeFlutterWithName:(NSString*)name data:(id)data {
    if (name == nil || name.length == 0) {
        return;
    }

    // 存储粘性数据
    [[GYEStickinessManager share] addStickDataWithName:name data:data];

    [self.observers enumerateObjectsUsingBlock:^(GYEOnCallObserverWrapper * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
            if (obj.wrapped && ([obj.name isEqualToString:name] || [obj.name isEqualToString:GYEFlutterMethodName_All])) {

                GYEMethodCallNotify *noti = [[GYEMethodCallNotify alloc] initWithMethodName:name arguments:data];
                if ([obj.wrapped respondsToSelector:@selector(onCallNotify:)]) {
                    [obj.wrapped onCallNotify:noti];
                }
                // todo 这里可以先转换成字典，再分发给原生；而 分发给 flutter 需要以model形式分发，才能进入自定义编码
    //            id newData = [data mj_keyValues];

                // block 回调
                if (obj.wrapped && obj.liveDataCallback) {
                    obj.liveDataCallback(noti);
                }
            }
    }];

}

/// 添加监听
-(void)addObserver:(NSString*)name observer:(id<GYEOnCallObserver>)observer {
    if (observer == nil) {
        return;
    }
    if (name == nil || ![name isKindOfClass:[NSString class]] ||  name.length == 0) {
        NSLog(@"name 不能为空！");
        return;
    }
    GYEOnCallObserverWrapper *obWrapper = [[GYEOnCallObserverWrapper alloc] initWithName:name wrapped:observer];
    [self.observers addObject:obWrapper];
    
    // 获取粘性数据
    GYEMethodCallNotify *stickNoti = [[GYEStickinessManager share] getStickDataWithName:name];
    if (stickNoti && [observer respondsToSelector:@selector(onCallNotify:)]) {
        [observer onCallNotify:stickNoti];
    }
}

/// 添加 data 监听 block 回调
-(void)addObserver:(NSString*)name observer:(id<GYEOnCallObserver>)observer callback:(GYELiveDataResult)callback {
//    (GYEMethodCallNotify *)notify
    if (observer == nil) {
        return;
    }
    if (name == nil || ![name isKindOfClass:[NSString class]] ||  name.length == 0) {
        NSLog(@"name 不能为空！");
        return;
    }
    GYEOnCallObserverWrapper *obWrapper = [[GYEOnCallObserverWrapper alloc] initWithName:name wrapped:observer];
    obWrapper.liveDataCallback = callback;
    [self.observers addObject:obWrapper];
    
    // 获取粘性数据
    GYEMethodCallNotify *stickNoti = [[GYEStickinessManager share] getStickDataWithName:name];
    if (stickNoti && [observer respondsToSelector:@selector(onCallNotify:)]) {
        [observer onCallNotify:stickNoti];
    }
    
    if (stickNoti && callback) {
        callback(stickNoti);
    }
}

-(void)removeObserver:(NSString*)name observer:(id<GYEOnCallObserver>)observer {
    [self.observers enumerateObjectsUsingBlock:^(GYEOnCallObserverWrapper * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        if (obj && obj.wrapped && (obj.wrapped == observer) && (!name && [name isEqualToString:obj.name])) {
            [self.observers removeObject:obj];
        } else if (obj && !obj.wrapped) {
            // 移除发现已销毁的监听对象
            [self.observers removeObject:obj];
        }
    }];
}

/*
 移除监听
 */
-(void)removeObserver:(id<GYEOnCallObserver>)observer {
    [self.observers enumerateObjectsUsingBlock:^(GYEOnCallObserverWrapper * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        if (obj && obj.wrapped && (obj.wrapped == observer)) {
            [self.observers removeObject:obj];
        } else if (obj && !obj.wrapped) {
            // 移除发现已销毁的监听对象
            [self.observers removeObject:obj];
        }
    }];
}

/// 方法名是否已注册
-(BOOL)isContainMethodName:(NSString*)name {
    if (name == nil) {
        return false;
    }
    
    __block BOOL contain = false;
    [self.observers enumerateObjectsUsingBlock:^(GYEOnCallObserverWrapper * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        if (obj && obj.name && [obj.name isEqualToString:name]) {
            contain = true;
            *stop = true;
        }
    }];
    return contain;
}

/// 设置方法回调监听
-(void)addMethodObserver:(NSString*)name observer:(id<GYEOnCallObserver>)observer handle:(FlutterResult _Nullable)callback {
    if (observer == nil) {
        return;
    }
    if (name == nil || ![name isKindOfClass:[NSString class]] ||  name.length == 0) {
        NSLog(@"name 不能为空！");
        return;
    }
    GYEOnCallObserverWrapper *obWrapper = [[GYEOnCallObserverWrapper alloc] initWithName:name wrapped:observer];
    obWrapper.callbackBlock = callback;
    [self.methodObservers setObject:obWrapper forKey:name];
}

/// 移除方法监听
-(void)removeMethodObserver:(NSString*)name observer:(id<GYEOnCallObserver>)observer {
    if (name == nil || ![name isKindOfClass:[NSString class]] ||  name.length == 0) {
        NSLog(@"name 不能为空！");
        return;
    }
    
    GYEOnCallObserverWrapper *obWrapper = [self.methodObservers valueForKey:name];
    if ( obWrapper && ((obWrapper.wrapped == observer) || !obWrapper.wrapped) ) {
        [self.methodObservers removeObjectForKey:name];
    }
    
}

/// 方法名是否已注册
-(BOOL)isContainMethodName:(NSString*)name type:(NSString*)type {
    if (name == nil) {
        return false;
    }
    
    __block BOOL contain = false;
    
    if ([type isEqualToString:@"function"]) {
        if ([self.methodObservers objectForKey:name]) {
            return true;
        }
        return false;
    }
    
    [self.observers enumerateObjectsUsingBlock:^(GYEOnCallObserverWrapper * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        if (obj && obj.name && [obj.name isEqualToString:name]) {
            contain = true;
            *stop = true;
        }
    }];
    return contain;
}

-(GYEOnCallObserverWrapper*)getValueWithMethodName:(NSString*)name {
    if (name == nil) {
        return nil;
    }
    
    return [self.methodObservers objectForKey:name];
}

-(NSMutableArray<GYEOnCallObserverWrapper *> *)observers {
    if (!_observers) {
        _observers = [[NSMutableArray alloc] init];
    }
    return _observers;
}

-(NSMutableDictionary *)methodObservers {
    if (!_methodObservers) {
        _methodObservers = [[NSMutableDictionary alloc] init];
    }
    return _methodObservers;
}

@end
