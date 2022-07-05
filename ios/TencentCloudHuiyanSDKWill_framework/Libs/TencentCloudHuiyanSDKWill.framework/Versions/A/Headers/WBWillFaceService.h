//
//  WBWillFaceService.h
//  TencentCloudHuiyanSDKWill
//
//  Created by wakeenzhou on 2022/5/7.
//

#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>
#import "WBWillResult.h"
#import "WBWillConfig.h"

NS_ASSUME_NONNULL_BEGIN
#define TencentCloudHuiyanSDKWillVersion @"1.0.0"

UIKIT_EXTERN NSString *const TencentCloudWillSDKVersion;

typedef NS_ENUM(NSInteger, WBWillMediaType) {
    WBWillMediaTypeVideo = 0,
    WBWillMediaTypeAudio = 1,
};

@protocol WBWillFaceServiceDelegate <NSObject>

- (void)serviceWillFinished:(BOOL)success;

- (void)serviceDidFinished:(WBWillResult *)result;

@end

@interface WBWillFaceService : NSObject

@property (nonatomic, weak) id<WBWillFaceServiceDelegate> delegate;

+ (void)checkAudioPermission:(void(^)(BOOL hasRight))block;

- (instancetype)initWithController:(UIViewController *)controller config:(WBWillConfig *)config;

- (void)updateConfig:(WBWillConfig *)config;

- (BOOL)startService;

- (void)stopService;

- (void)captureDidOutputSampleBuffer:(CMSampleBufferRef)sampleBuffer mediaType:(WBWillMediaType)mediaType;

@end

NS_ASSUME_NONNULL_END
