//
//  GYEFlutterChannelManager.m
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/6.
//

#import "GYEFlutterChannelManager.h"
#import "GYEOnCallObserverManager.h"
#import "GYEFlutterCodec.h"
#import "MJExtension.h"
#import "GYEFlutterMethodName.h"
#import "GYEFlutterBridge.h"

NSString *const kGlobbalFlutterChannelName = @"com.dodo.flutterbridge.global_flutter_channel";
//NSString *const kGlobbalFlutterChannelName = @"multiple-flutters";

@interface GYEFlutterChannelManager()<GYEOnCallObserver>

@end

@implementation GYEFlutterChannelManager


+(instancetype)share {
    static dispatch_once_t once_t;
    static GYEFlutterChannelManager *manager;
    dispatch_once(&once_t, ^{
        manager = [[GYEFlutterChannelManager alloc] init];
        
        // 设置所有方法监听
        [manager addObserver];
    });
    return manager;
}

// 设置所有方法监听
-(void)addObserver {
    [[GYEOnCallObserverManager share] addObserver:GYEFlutterMethodName_All observer:self];
}

/// 添加 channel （ channel 必须在 boost 初始化成功后）
-(void)setupChannelWithEngine:(FlutterEngine*)engine {
    
    // 初始化 channel
    if (!engine || !engine.binaryMessenger) {
        return;
    }
    GYEFlutterApiCodecReaderWriter *readerWriter =
      [[GYEFlutterApiCodecReaderWriter alloc] init];
    
    FlutterStandardMethodCodec *methodCodec = [FlutterStandardMethodCodec codecWithReaderWriter:readerWriter];
    FlutterMethodChannel * methodChannel = [FlutterMethodChannel methodChannelWithName:kGlobbalFlutterChannelName binaryMessenger:engine.binaryMessenger codec:methodCodec];

    // 监听flutter回调
    [methodChannel setMethodCallHandler:^(FlutterMethodCall * _Nonnull call, FlutterResult  _Nonnull result) {
        //
        NSLog(@"[global_flutter_channel]收到消息：\nmethod = %@\narguments = %@",call.method,call.arguments);
        NSDictionary *dict = [GYEFlutterMethodName decodeMethodNameJson:call.method];
        NSString *name = dict[@"name"];
        NSString *type = dict[@"type"];

        if ([type isEqualToString:@"data"]) {
            // 数据类型
            NSLog(@"数据类型");
            if ([[GYEOnCallObserverManager share] isContainMethodName:name]) {
                // 通知所有 native 和 flutter
                [GYEOnCallObserverManager postNativeFlutterWithName:name data:call.arguments];
                result(nil);
            } else {
                result(FlutterMethodNotImplemented);
            }

        } else  if ([type isEqualToString:@"function"]) {
            // 方法类型
            NSLog(@"方法类型");
            if ([[GYEOnCallObserverManager share] isContainMethodName:name type:@"function"]) {
                // 方法回调，单次响应
                GYEOnCallObserverWrapper *observerWrapper = [[GYEOnCallObserverManager share] getValueWithMethodName:name];
                if (observerWrapper && observerWrapper.callbackBlock) {
                    //observerWrapper.callbackBlock(call.arguments);
                    GYEMethodCallNotify *noti = [[GYEMethodCallNotify alloc] initWithMethodName:name arguments:call.arguments];
                    noti.result = result;
                    observerWrapper.callbackBlock(noti);
                } else {
                     result(nil);
                 }
                //result(nil);
            } else {
                result(FlutterMethodNotImplemented);
            }
            return;
            
        } else {
            NSLog(@"未实现方法");
            result(FlutterMethodNotImplemented);
        }
        
    }];
    
    self.methodChannel = methodChannel;
    
}


/// [原生数据变化] 通知 flutter
-(void)onCallNotify:(GYEMethodCallNotify *)notify {
    NSLog(@"需要通知flutter:%@ -->%@",notify.name,notify.arguments);
    
    id data = notify.arguments;
    NSString *name = notify.name;

    // 组装name
//    NSDictionary *nameDict = @{
//        name:@"name",
//        @"data":@"type"
//    };
//    NSString *jsonName = [nameDict mj_JSONString];
    NSString *jsonName = [GYEFlutterMethodName initMethodName:name type:GYEFlutterMethodNameType_data];
    [self sendMethod:jsonName arguments:data];
    
}

/// 发送消息（ 通知flutter ）
-(void)sendMethod:(NSString*)method arguments:(id)arguments {
    
    if (self.methodChannel) {
        [self.methodChannel invokeMethod:method arguments:arguments];
    }
}

/// 发送消息（ 通知flutter ）并接收回调
/// @param method 方法名
/// @param arguments 入参
/// @param callback 异步回调。若错误时，返回 FlutterError 实例;
/// 若方法未实现，则返回 FlutterMethodNotImplemented；
/// 若返回其他值，包括 nil ，都表示成功
- (void)invokeMethod:(NSString*)method
           arguments:(id _Nullable)arguments
              result:(FlutterResult _Nullable)callback {
    
    
    if (self.methodChannel) {
        [self.methodChannel invokeMethod:method arguments:arguments result:callback];
    }
}


@end
