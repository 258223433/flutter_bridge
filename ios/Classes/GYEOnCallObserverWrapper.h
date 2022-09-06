//
//  GYEOnCallObserverWrapper.h
//  iOSFlutterBridgeDemo
//
//  Created by gyenno on 2022/8/5.
//

#import <Foundation/Foundation.h>
#import "GYEMethodCallNotify.h"
#import <Flutter/Flutter.h>

NS_ASSUME_NONNULL_BEGIN

@protocol GYEOnCallObserver <NSObject>

@optional
/// 数据变化回调
- (void)onCallNotify:(GYEMethodCallNotify *)notify;

@end

@interface GYEOnCallObserverWrapper : NSObject

@property(nonatomic,weak)id<GYEOnCallObserver> wrapped;
@property(nonatomic,copy)NSString *name;

/// mehtod 回调block
@property(nonatomic,copy)void (^callbackBlock)(id nullable);

/// data 回调block
@property(nonatomic,copy)GYELiveDataResult liveDataCallback;


-(instancetype)initWithName:(NSString*)name wrapped:(id<GYEOnCallObserver>)wrapped;

@end

NS_ASSUME_NONNULL_END
