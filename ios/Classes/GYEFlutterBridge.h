//
//  GYEFlutterBridge.h
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/5.
//

#import <Foundation/Foundation.h>
#import <flutter_boost/FlutterBoost.h>
#import "GYEFlutterBoostRouteOptions.h"

NS_ASSUME_NONNULL_BEGIN

@protocol GYEFlutterBridgeDelegate <NSObject>

/// 如果框架发现您输入的路由表在flutter里面注册的路由表中找不到，那么就会调用此方法来push一个纯原生页面
- (void) pushNativeRoute:(NSString *) pageName arguments:(NSDictionary *) arguments;

/// 当框架的withContainer为true的时候，会调用此方法来做原生的push
- (void) pushFlutterRoute:(GYEFlutterBoostRouteOptions *)options;

/// 当pop调用涉及到原生容器的时候，此方法将会被调用
- (void) popRoute:(GYEFlutterBoostRouteOptions *)options;

@end

@interface GYEFlutterBridge : NSObject

@property(nonatomic, weak, nullable) id<GYEFlutterBridgeDelegate> delegate;

/// FlutterBridge 全局单例
+(instancetype)instance;

/// 初始化
/// @param application 全局Application实例，如未设置engine参数，则默认从Application做engine的绑定
/// @param callback 初始化完成以后的回调，
+ (void)setup:(UIApplication*)application callback:(void (^)(BOOL isFinish))callback ;

#pragma mark - boost

/// 利用启动参数配置开启新页面
/// @param options 配置参数
- (void)open:(GYEFlutterBoostRouteOptions* )options;

/// 将原生页面的数据回传到flutter侧的页面的的方法
/// @param pageName 这个页面在路由表中的名字，和flutter侧BoostNavigator.push(name)中的name一样
/// @param arguments 你想传的参数
- (void)sendResultToFlutterWithPageName:(NSString*)pageName arguments:(NSDictionary*) arguments;

@end

NS_ASSUME_NONNULL_END
