//
//  GYEFlutterBridge.m
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/5.
//

#import "GYEFlutterBridge.h"
#import "GYEFlutterChannelManager.h"

@interface GYEFlutterBridge ()<FlutterBoostDelegate>

@property (strong, nonatomic) FlutterEngine *flutterEngine;

@end

@implementation GYEFlutterBridge

+(instancetype)instance {
    static dispatch_once_t once_t;
    static GYEFlutterBridge *manager;
    dispatch_once(&once_t, ^{
        manager = [[GYEFlutterBridge alloc] init];
    });
    return manager;
}

/// 初始化
/// @param application 全局Application实例，如未设置engine参数，则默认从Application做engine的绑定
/// @param callback 初始化完成以后的回调，
+ (void)setup:(UIApplication*)application callback:(void (^)(BOOL isFinish))callback {
    [[GYEFlutterBridge instance] setup:application callback:callback];
}

- (void)setup:(UIApplication*)application callback:(void (^)(BOOL isFinish))callback{
    
    __weak typeof(self) weakSelf = self;
    // 初始化引擎
    [[FlutterBoost instance] setup:application delegate:self callback:^(FlutterEngine *engine) {
        NSLog(@"----engine = %@",engine);
        weakSelf.flutterEngine = engine;
        [[GYEFlutterChannelManager share] setupChannelWithEngine:engine];
        if (callback) {
            callback(true);
        }
    } ];
    
}


#pragma mark - boost

/// 打开 flutter 页面
/// @param options 配置参数
- (void)open:(GYEFlutterBoostRouteOptions* )options {
    [[FlutterBoost instance] open:options];
}

/// 将原生页面的数据回传到flutter侧的页面的的方法
/// @param pageName 这个页面在路由表中的名字，和flutter侧BoostNavigator.push(name)中的name一样
/// @param arguments 你想传的参数
- (void)sendResultToFlutterWithPageName:(NSString*)pageName arguments:(NSDictionary*) arguments {
    // 注意这句话并不会退出页面
    [[FlutterBoost instance] sendResultToFlutterWithPageName:pageName arguments:arguments];
}

#pragma mark - boost FlutterBoostDelegate

/// flutter 调用原生页面
- (void)pushNativeRoute:(NSString *) pageName arguments:(NSDictionary *) arguments {
    if (self.delegate && [self.delegate respondsToSelector:@selector(pushNativeRoute:arguments:)]) {
        [self.delegate pushNativeRoute:pageName arguments:arguments];
    }
}

/// 原生调用 flutter 页面
- (void)pushFlutterRoute:(FlutterBoostRouteOptions *)options {
    if (self.delegate && [self.delegate respondsToSelector:@selector(pushFlutterRoute:)]) {
        [self.delegate pushFlutterRoute:(GYEFlutterBoostRouteOptions*)options];
    }
}

- (void) popRoute:(FlutterBoostRouteOptions *)options {
    if (self.delegate && [self.delegate respondsToSelector:@selector(popRoute:)]) {
        [self.delegate popRoute:(GYEFlutterBoostRouteOptions*)options];
    }
}

@end
