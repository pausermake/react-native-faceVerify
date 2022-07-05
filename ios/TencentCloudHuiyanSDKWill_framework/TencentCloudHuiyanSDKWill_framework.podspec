Pod::Spec.new do |s|
  s.name = "TencentCloudHuiyanSDKWill_framework"
  s.version = "1.0.0"
  s.summary = "A short description of TencentCloudHuiyanSDKWill_framework."
  s.homepage         = 'https://github.com/brownfeng/TencentCloudHuiyanSDKWill_framework'
  s.license          =  "MIT"
  s.authors = {"brownfeng"=>"brownfeng@github.com"}
  s.description = "TODO: Add long description of the pod here."
  s.frameworks = ["UIKit", "AVFoundation", "CoreVideo", "Security", "SystemConfiguration", "CoreMedia", "VideoToolbox", "CoreTelephony", "ImageIO", "MediaPlayer"]
  s.libraries = ["c++","z"]
  s.source = { :path => '.' }

  s.ios.deployment_target    = '9.0'
  s.ios.vendored_framework   = 'Libs/*.framework'
  s.ios.resource = 'Resources/*.bundle'
end
