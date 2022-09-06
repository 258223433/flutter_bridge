//
//  GYEMethodCallNotify.m
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/4.
//  原生和flutter消息体

#import "GYEMethodCallNotify.h"

@implementation GYEMethodCallNotify

-(instancetype)initWithMethodName:(NSString*)method arguments:(id _Nullable)arguments {
    if (self = [super init]) {
        self.name = method;
        self.arguments = arguments;
    }
    return self;
}

@end
