
Pod::Spec.new do |s|
  s.name         = "RNFaceVerify"
  s.version      = "1.0.0"
  s.summary      = "RNFaceVerify"
  s.description  = <<-DESC
                  RNFaceVerify
                   DESC
  s.homepage     = ""
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "author@domain.cn" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/pausermake/react-native-faceVerify.git", :tag => "master" }
  s.source_files  = "RNFaceVerify/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  