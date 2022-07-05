
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif

#import <TencentCloudHuiyanSDKFace/WBFaceVerifyCustomerService.h>

@interface RNFaceVerify : NSObject <RCTBridgeModule,WBFaceVerifyCustomerServiceDelegate>


@property NSString* appid;
@property NSString* keyLicence;

@end
