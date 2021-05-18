
Pod::Spec.new do |s|
  s.name         = "RNPreventScreenshot"
  s.version      = "1.0.1"
  s.summary      = "RNPreventScreenshot"
  s.description  = <<-DESC
                  RNPreventScreenshot
                   DESC
  s.homepage     = "https://github.com/killserver/react-native-screenshot-prevent"
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "killserver@gmail.com" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/killserver/react-native-screenshot-prevent.git", :tag => "master" }
  s.source_files  = "RNPreventScreenshot/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  