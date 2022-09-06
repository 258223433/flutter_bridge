//
//  GYEFlutterMethodName.h
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/4.
//  自定义方法名

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/// 方法类型
typedef enum : NSUInteger {
    /// data数据类型
    GYEFlutterMethodNameType_data,
    /// 方法类型
    GYEFlutterMethodNameType_function,
} GYEFlutterMethodNameType;

@interface GYEFlutterMethodName : NSObject

/// 监听所有 method
extern NSString *const GYEFlutterMethodName_All;


/// name 组装 json 字符串
+(NSString*)initMethodName:(NSString*)name type:(GYEFlutterMethodNameType)type;

/// json 字符串转字典
+(NSDictionary*)decodeMethodNameJson:(NSString*)jsonStr;

@end

NS_ASSUME_NONNULL_END
