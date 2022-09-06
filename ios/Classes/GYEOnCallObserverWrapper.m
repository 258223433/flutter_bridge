//
//  GYEOnCallObserverWrapper.m
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/5.
//  统一回调包装器

#import "GYEOnCallObserverWrapper.h"


@implementation GYEOnCallObserverWrapper

-(instancetype)initWithName:(NSString*)name wrapped:(id<GYEOnCallObserver>)wrapped {
    if (self = [super init]) {
        self.wrapped = wrapped;
        self.name = name;
    }
    return self;
}

@end
