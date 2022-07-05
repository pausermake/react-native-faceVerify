//
//  WBWillConfig.h
//  TencentCloudHuiyanSDKWill
//
//  Created by wakeenzhou on 2022/5/17.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface WBWillConfig : NSObject

@property (nonatomic, assign) CGRect faceRect;

@property (nonatomic, strong) NSString *bundlePath;

@property (nonatomic, strong) NSString *answer;

@property (nonatomic, strong) NSString *questionAudio;

@property (nonatomic, strong) NSString *question;

@property (nonatomic, assign) BOOL recordWillVideo;

@property (nonatomic, assign) CGFloat will_service_volume_turnup_level;

@property (nonatomic, assign) CGFloat will_service_volume_detect_minlevel_ios;

@property (nonatomic, assign) CGFloat will_service_volume_detect_waittime;

@property (nonatomic, assign) CGFloat will_service_mute_timeout;

@end

NS_ASSUME_NONNULL_END
