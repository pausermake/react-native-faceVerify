//
//  WBWillResult.h
//  TencentCloudHuiyanSDKWill
//
//  Created by wakeenzhou on 2022/5/17.
//

#import <Foundation/Foundation.h>
#import "WBWillError.h"
NS_ASSUME_NONNULL_BEGIN

@interface WBWillResult : NSObject

@property (nonatomic, assign) BOOL success;

@property (nonatomic, strong) NSData *audioData;

@property (nonatomic, strong) WBWillError *error;

@property (nonatomic, assign) BOOL retry;

@property (nonatomic, strong) NSURL *videoURL;

@end

NS_ASSUME_NONNULL_END
