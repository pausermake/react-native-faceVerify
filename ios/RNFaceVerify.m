
#import "RNFaceVerify.h"



@implementation RNFaceVerify

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(init:(NSDictionary*)data :(RCTResponseSenderBlock)callback){
    
    self.appid = data[@"appId"];
    self.keyLicence = data[@"keyLicence"];
    
    [WBFaceVerifyCustomerService sharedInstance].delegate = self;
    
    callback("111");
}

RCT_EXPORT_METHOD(startVerify: (NSDictionary*)data :(RCTResponseSenderBlock)callback) {
    dispatch_async(dispatch_get_main_queue(), ^{
        [[WBFaceVerifyCustomerService sharedInstance] initWillSDKWithUserId:self.userID nonce:weakSelf.nonce sign:sign appid:weakSelf.appid orderNo:weakSelf.orderNO apiVersion:@"1.0.0" licence:LICENCE faceId:faceId sdkConfig:config success:^{
            [[WBFaceVerifyCustomerService sharedInstance] startWbFaceVeirifySdk];
            [weakSelf hideLoading];

        } failure:^(WBFaceError * _Nonnull error) {
            [weakSelf hideLoading];
            if (iOS8Later) {
                NSString *message = [NSString stringWithFormat:@"%@", error.desc];
                NSLog(@"error: %@", error);
                [ISMessages showCardAlertWithTitle:@"登录时候出错" message:message duration:10 hideOnSwipe:YES hideOnTap:YES alertType:ISAlertTypeInfo alertPosition:0 didHide:nil];
            }
        }];
    });
    
}

-(void)wbfaceVerifyCustomerServiceDidFinishedWithFaceVerifyResult:(WBFaceVerifyResult *)faceVerifyResult{
    NSString *userImageString = faceVerifyResult.userImageString;
    if (userImageString) {

    }
    if (faceVerifyResult.willModeResult.videoURL)
    {
        ALAssetsLibrary *library = [[ALAssetsLibrary alloc] init];
        [library writeVideoAtPathToSavedPhotosAlbum:faceVerifyResult.willModeResult.videoURL

                                    completionBlock:^(NSURL *assetURL, NSError *error) {
            if (error)
            {
                [self showCardAlertWithTitle:@"失败" message:[error description]
                                      duration:3 hideOnSwipe:YES hideOnTap:YES alertType:ISAlertTypeInfo alertPosition:0 didHide:nil];
            }
                                    }];
    }
    if (faceVerifyResult.isSuccess) {
        //刷脸成功，可以到后台查询结果
        NSString *message = [NSString stringWithFormat:@"liveRate: %@, similarity: %@", faceVerifyResult.liveRate, faceVerifyResult.similarity];
        [ISMessages showCardAlertWithTitle:@"成功" message:message
                                  duration:3 hideOnSwipe:YES hideOnTap:YES alertType:ISAlertTypeSuccess alertPosition:0 didHide:nil];
    }else {
        if ([faceVerifyResult.error.domain isEqualToString:WBFaceErrorDomainCompareServer])
        {
            //虽然比对失败，但这个domain也可以到后台查询刷脸结果
            NSString *message = [NSString stringWithFormat:@"%@, liveRate:%@, similarity:%@, sign: %@", faceVerifyResult.error.desc, faceVerifyResult.liveRate, faceVerifyResult.similarity, faceVerifyResult.sign];
            NSLog(@"error: %@", faceVerifyResult.error);
            [ISMessages showCardAlertWithTitle:@"比对失败" message:message
                                      duration:3 hideOnSwipe:YES hideOnTap:YES alertType:ISAlertTypeInfo alertPosition:0 didHide:nil];
        } else {
            NSString *message = [NSString stringWithFormat:@"%@, liveRate:%@, similarity:%@, sign: %@", faceVerifyResult.error.desc, faceVerifyResult.liveRate, faceVerifyResult.similarity, faceVerifyResult.sign];
            NSLog(@"error: %@", faceVerifyResult.error);
            [ISMessages showCardAlertWithTitle:@"刷脸失败" message:message
                                      duration:3 hideOnSwipe:YES hideOnTap:YES alertType:ISAlertTypeInfo alertPosition:0 didHide:nil];
        }
    }
}

@end
