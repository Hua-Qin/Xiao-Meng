# Checklist

## 模块映射规划
- [ ] 项目结构分析文档完整记录各功能目录与Gradle模块的对应关系
- [ ] 模块间依赖关系图清晰标注
- [ ] 共享依赖列表已识别并记录

## Gradle构建脚本
- [ ] app模块build.gradle.kts已创建并配置正确
  - [ ] 包含Android插件声明
  - [ ] compileSdk、applicationId、minSdk、targetSdk配置正确
  - [ ] Kotlin编译选项配置正确
  - [ ] Compose插件已启用
  - [ ] 所有必需依赖已添加（Compose、Material3、ObjectBox等）
  - [ ] 资源文件和Manifest路径配置正确
- [ ] dragonbones模块build.gradle.kts已创建并配置正确
  - [ ] 配置为Android Library模块
  - [ ] DragonBones相关依赖已添加
- [ ] terminal模块build.gradle.kts已创建并配置正确
  - [ ] 配置为Android Library模块
  - [ ] 终端相关依赖已添加
- [ ] mnn模块build.gradle.kts已创建并配置正确
  - [ ] 配置为Android Library模块
  - [ ] JNI库路径配置正确
  - [ ] Native构建配置正确
- [ ] llama模块build.gradle.kts已创建并配置正确
  - [ ] 配置为Android Library模块
  - [ ] LLaMA模型相关依赖已添加
- [ ] mmd模块build.gradle.kts已创建并配置正确
  - [ ] 配置为Android Library模块
  - [ ] MMD模型相关依赖已添加
- [ ] showerclient模块build.gradle.kts已创建并配置正确
  - [ ] 配置为Android Library模块
  - [ ] 客户端相关依赖已添加

## 项目目录结构
- [ ] 所有模块具有标准的Android目录结构
  - [ ] src/main/java目录存在
  - [ ] src/main/res目录存在
  - [ ] src/main/AndroidManifest.xml文件存在
- [ ] 源代码文件已移动到对应模块的java目录
- [ ] 资源文件已移动到对应模块的res目录
  - [ ] drawable资源已移动
  - [ ] layout资源已移动
  - [ ] string资源已移动
  - [ ] style资源已移动
- [ ] JNI库文件已移动到对应模块的jniLibs目录
- [ ] Assets资源已移动到对应模块的assets目录
- [ ] AndroidManifest.xml文件已创建或更新并配置正确

## 资源文件验证
- [ ] 所有R资源引用准确无误
  - [ ] drawable资源引用正确
  - [ ] string资源引用正确
  - [ ] layout资源引用正确
  - [ ] style资源引用正确
- [ ] 所有必需的资源文件存在
  - [ ] 布局文件完整
  - [ ] 图片资源完整
  - [ ] 字符串资源完整
  - [ ] 样式资源完整
- [ ] 无缺失或错误的资源引用

## 依赖项验证
- [ ] 所有依赖项版本兼容
  - [ ] Kotlin版本兼容
  - [ ] Compose版本兼容
  - [ ] Material3版本兼容
  - [ ] ObjectBox版本兼容
  - [ ] 其他第三方库版本兼容
- [ ] Maven仓库配置正确
  - [ ] Google仓库已配置
  - [ ] Maven Central仓库已配置
  - [ ] JitPack仓库已配置
  - [ ] 其他必需仓库已配置
- [ ] 无版本冲突
- [ ] 无缺失依赖

## GitHub Actions配置
- [ ] .github/workflows/android-build.yml文件已创建
- [ ] 构建触发条件配置正确
  - [ ] push事件已配置
  - [ ] pull_request事件已配置
- [ ] Java JDK环境配置正确
- [ ] Gradle缓存已配置
- [ ] 构建命令配置正确
  - [ ] gradlew clean命令已配置
  - [ ] gradlew assembleDebug命令已配置
- [ ] APK上传到Artifacts已配置
- [ ] Release APK上传到Releases已配置（可选）

## 构建验证
- [ ] GitHub Actions构建成功
  - [ ] 构建过程无错误
  - [ ] 构建日志正常
- [ ] APK文件生成
  - [ ] Debug APK已生成
  - [ ] Release APK已生成（如配置）
- [ ] APK文件可正常安装
  - [ ] APK签名正确
  - [ ] APK大小合理
- [ ] 应用可正常启动
  - [ ] 应用启动无崩溃
  - [ ] 主界面正常显示
  - [ ] AI聊天UI功能正常
- [ ] 所有预期功能正常执行
  - [ ] 导航栏功能正常
  - [ ] 聊天功能正常
  - [ ] 输入功能正常
  - [ ] 弹窗功能正常
  - [ ] 侧边栏功能正常
