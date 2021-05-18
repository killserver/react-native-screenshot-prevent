# RNScreenshotPrevent

require 'json'
package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|
  s.name         = "RNScreenshotPrevent"
  s.version      = package['version']
  s.summary      = package['description']
  s.license      = package['license']
  s.authors      = package['author']
  s.homepage     = package['repository']['url']
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.platform     = :ios, "9.0"
  s.source       = { :git => "https://github.com/killserver/react-native-screenshot-prevent.git", :tag => "master" }
  s.source_files  = "ios/**/*.{h,m}"
  s.requires_arc = true


  s.dependency "React"
  #s.dependency "others"

end

  