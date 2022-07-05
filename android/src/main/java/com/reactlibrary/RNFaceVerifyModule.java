
package com.reactlibrary;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson2.JSON;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.tencent.cloud.huiyansdkface.facelight.api.WbCloudFaceContant;
import com.tencent.cloud.huiyansdkface.facelight.api.WbCloudFaceVerifySdk;
import com.tencent.cloud.huiyansdkface.facelight.api.listeners.WbCloudFaceVerifyLoginListener;
import com.tencent.cloud.huiyansdkface.facelight.api.listeners.WbCloudFaceVerifyResultListener;
import com.tencent.cloud.huiyansdkface.facelight.api.result.WbFaceError;
import com.tencent.cloud.huiyansdkface.facelight.api.result.WbFaceVerifyResult;
import com.tencent.cloud.huiyansdkface.facelight.process.FaceVerifyStatus;

import java.util.HashMap;

public class RNFaceVerifyModule extends ReactContextBaseJavaModule {



  private static final String TAG = "FaceVerify";

  public static Callback callback;

  /**
   * 发送消息回复到前端
   * @param status
   * @param message
   */
  public static void invokeCallback(String status,String message){
    HashMap<String,String> result = new HashMap<String,String>();
    result.put("status",status);
    result.put("message",message);
    String resJson = JSON.toJSONString(result);
    RNFaceVerifyModule.callback.invoke(resJson);
  }

  private boolean isShowSuccess;
  private boolean isShowFail;
  private boolean isRecordVideo;
  private boolean isPlayVoice;
  //意愿性
  private boolean isRecordWillVideo;
  private boolean isCheckWillVideo;

  private String appId;
  private String keyLicence;

  public RNFaceVerifyModule(ReactApplicationContext reactContext) {
    super(reactContext);
    //默认不展示成功/失败页面
    isShowSuccess = false;
    isShowFail = false;
    //默认不录制视频
    isRecordVideo = false;
    //默认不播放提示语
    isPlayVoice = false;
    //默认不录制意愿性视频
    isRecordWillVideo = false;
    isCheckWillVideo = false;
  }

  @Override
  public String getName() {
    return "RNFaceVerify";
  }


  @ReactMethod
  public void startVerify(ReadableMap params, Callback callback){
    String faceId = params.getString("faceId");
    String orderNo = params.getString("orderNo");
    String nonce = params.getString("nonce");
    String userId = params.getString("userId");
    String sign = params.getString("sign");

    RNFaceVerifyModule.callback = callback;
    this.openCloudFaceService(faceId,orderNo,nonce,this.appId,userId,sign,this.keyLicence);
  }

  @ReactMethod
  public void init(ReadableMap params, Callback callback){
    this.appId = params.getString("appId");
    this.keyLicence = params.getString("keyLicence");

    HashMap<String,String> result = new HashMap<String,String>();
    result.put("status","true");
    result.put("message","success");
    String resJson = JSON.toJSONString(result);
    callback.invoke(resJson);
  }

  //拉起刷脸sdk
  public void openCloudFaceService(String faceId,String orderNo,String nonce,String appId,String userId,String sign,String keyLicence) {

    Log.d(TAG, "openCloudFaceService");
    Bundle data = new Bundle();
    WbCloudFaceVerifySdk.InputData inputData = new WbCloudFaceVerifySdk.InputData(
            faceId,
            orderNo,
            appId,
            "1.0.0",
            nonce,
            userId,
            sign,
            FaceVerifyStatus.Mode.GRADE,
            keyLicence);

    data.putSerializable(WbCloudFaceContant.INPUT_DATA, inputData);
    //是否展示刷脸成功页面，默认不展示
    data.putBoolean(WbCloudFaceContant.SHOW_SUCCESS_PAGE, isShowSuccess);
    //是否展示刷脸失败页面，默认不展示
    data.putBoolean(WbCloudFaceContant.SHOW_FAIL_PAGE, isShowFail);
    //是否需要录制上传视频 默认不需要
    data.putBoolean(WbCloudFaceContant.VIDEO_UPLOAD, isRecordVideo);
    //是否播放提示音，默认不播放
    data.putBoolean(WbCloudFaceContant.PLAY_VOICE, isPlayVoice);
    //识别阶段合作方定制提示语,可不传，此处为demo演示
    data.putString(WbCloudFaceContant.CUSTOMER_TIPS_LIVE, "仅供体验使用 请勿用于投产!");
    //上传阶段合作方定制提示语,可不传，此处为demo演示
    data.putString(WbCloudFaceContant.CUSTOMER_TIPS_UPLOAD, "仅供体验使用 请勿用于投产!");
    //合作方长定制提示语，可不传，此处为demo演示
    //如果需要展示长提示语，需要邮件申请
    data.putString(WbCloudFaceContant.CUSTOMER_LONG_TIP, "本demo提供的appId仅用于体验，实际生产请使用控制台给您分配的appId！");
    //是否录制意愿性视频，默认不录制
    // 如果设置了录制，视频地址将在刷脸结果中返回
    //【特别注意】拿到录制的视频地址后请及时清理删除
    data.putBoolean(WbCloudFaceContant.RECORD_WILL_VIDEO, isRecordWillVideo);
    //检查录制的意愿性视频大小,默认不检查
    //如果设置了不录制视频，这个设置则默认无效
    data.putBoolean(WbCloudFaceContant.CHECK_WILL_VIDEO, isCheckWillVideo);
    //sdk log开关，默认关闭，debug调试sdk问题的时候可以打开
    //【特别注意】上线前请务必关闭sdk log开关！！！
    data.putBoolean(WbCloudFaceContant.IS_ENABLE_LOG, BuildConfig.DEBUG);

    Log.d(TAG, "WbCloudFaceVerifySdk initWillSdk");
    //【特别注意】请使用activity context拉起sdk
    //【特别注意】请在主线程拉起sdk！
    final Activity that = this.getCurrentActivity();
    WbCloudFaceVerifySdk.getInstance().initWillSdk(that, data, new WbCloudFaceVerifyLoginListener() {
      @Override
      public void onLoginSuccess() {
        //登录sdk成功
        Log.i(TAG, "onLoginSuccess");

        //拉起刷脸页面
        //【特别注意】请使用activity context拉起sdk
        //【特别注意】请在主线程拉起sdk！
        WbCloudFaceVerifySdk.getInstance().startWbFaceVerifySdk(that,
                new WbCloudFaceVerifyResultListener() {
                  @Override
                  public void onFinish(WbFaceVerifyResult result) {
                    String log = "default";
                    //得到刷脸结果
                    if (result != null) {
                      if (result.isSuccess()) {
                        Log.d(TAG, "意愿性刷脸成功!" + result.toString());
                        log = "意愿性成功 :" + result.toString();
                        RNFaceVerifyModule.invokeCallback("true",log);
                      } else {
                        Log.d(TAG, "意愿性刷脸失败!" + result.toString());
                        WbFaceError error = result.getError();
                        log = "意愿性失败:" + result.toString();
                        if (error != null) {
                          Log.d(TAG, "失败详情：domain=" + error.getDomain() + " ;code= " + error.getCode()
                                  + " ;desc=" + error.getDesc() + ";reason=" + error.getReason());
                          RNFaceVerifyModule.invokeCallback("false","失败详情：domain=" + error.getDomain() + " ;code= " + error.getCode()
                                  + " ;desc=" + error.getDesc() + ";reason=" + error.getReason());
                        } else {
                          Log.e(TAG, "sdk返回error为空！");
                          RNFaceVerifyModule.invokeCallback("false","sdk返回error为空！");
                        }
                      }
                    } else {
                      RNFaceVerifyModule.invokeCallback("false","sdk返回结果为空！");
                      Log.e(TAG, "sdk返回结果为空！");
                    }
                    final String finalLog = log;
                    //刷脸结束后，释放资源
                    WbCloudFaceVerifySdk.getInstance().release();
                  }
                });
      }

      @Override
      public void onLoginFailed(WbFaceError error) {
        //登录失败
        Log.i(TAG, "onLoginFailed!");
        if (error != null) {
          Log.d(TAG, "登录失败！domain=" + error.getDomain() + " ;code= " + error.getCode()
                  + " ;desc=" + error.getDesc() + ";reason=" + error.getReason());
          if (error.getDomain().equals(WbFaceError.WBFaceErrorDomainParams)) {
            RNFaceVerifyModule.invokeCallback("false","传入参数有误！");
          } else {
            RNFaceVerifyModule.invokeCallback("false","登录刷脸sdk失败！" + error.getDesc());
          }
        } else {
          Log.e(TAG, "sdk返回error为空！");
        }
      }
    });
  }

}