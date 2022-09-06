//
//  GYEFlutterMethodName.m
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/4.
//  

#import "GYEFlutterMethodName.h"
#import "MJExtension.h"

@implementation GYEFlutterMethodName

/// 监听所有 method
NSString *const GYEFlutterMethodName_All = @"All_Method";



/// name 组装 json 字符串
+(NSString*)initMethodName:(NSString*)name type:(GYEFlutterMethodNameType)type {
    NSDictionary *dict = [self methodNameToMap:name type:type];
    NSString *jsonStr = [dict mj_JSONString];
    return jsonStr;
}

+(NSDictionary*)methodNameToMap:(NSString*)name type:(GYEFlutterMethodNameType)type {
    NSString *typeStr = @"data";
    if (type == GYEFlutterMethodNameType_data) {
        typeStr = @"data";
    } else {
        typeStr = @"function";
    }
    
    if (name == nil) {
        name = @"";
        NSLog(@"方法名不能为空");
    }
    
    NSDictionary *dict = [NSDictionary dictionaryWithObjectsAndKeys:
                          name, @"name",
                          typeStr, @"type",
                          nil];
    
    return dict;
}

/// json 字符串转字典
+(NSDictionary*)decodeMethodNameJson:(NSString*)jsonStr {
    if (!jsonStr || jsonStr.length == 0) {
        return nil;
    }
    return [jsonStr mj_JSONObject];
}

@end
