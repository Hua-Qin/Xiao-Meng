# Tasks

- [x] Task 1: 分析当前项目结构并规划模块映射
  - [x] SubTask 1.1: 识别当前目录结构（00_CORE_REQUIRED、01_个性化与界面设置等）与Gradle模块（app、dragonbones、terminal等）的对应关系
  - [x] SubTask 1.2: 确定每个模块需要包含的源代码和资源文件
  - [x] SubTask 1.3: 识别共享依赖和模块间依赖关系

- [x] Task 2: 为app模块创建build.gradle.kts
  - [x] SubTask 2.1: 创建app/build.gradle.kts文件
  - [x] SubTask 2.2: 配置Android插件和基本构建参数（compileSdk、applicationId、minSdk、targetSdk）
  - [x] SubTask 2.3: 配置Kotlin编译选项和Compose插件
  - [x] SubTask 2.4: 添加项目依赖（Compose、Material3、ObjectBox、网络库等）
  - [x] SubTask 2.5: 配置资源文件和Manifest路径
  - [x] SubTask 2.6: 配置ProGuard规则（如果需要）

- [x] Task 3: 为dragonbones模块创建build.gradle.kts
  - [x] SubTask 3.1: 创建dragonbones/build.gradle.kts文件
  - [x] SubTask 3.2: 配置为Android Library模块
  - [x] SubTask 3.3: 添加DragonBones相关依赖和资源

- [x] Task 4: 为terminal模块创建build.gradle.kts
  - [x] SubTask 4.1: 创建terminal/build.gradle.kts文件
  - [x] SubTask 4.2: 配置为Android Library模块
  - [x] SubTask 4.3: 添加终端相关依赖（JS执行引擎等）

- [x] Task 5: 为mnn模块创建build.gradle.kts
  - [x] SubTask 5.1: 创建mnn/build.gradle.kts文件
  - [x] SubTask 5.2: 配置为Android Library模块
  - [x] SubTask 5.3: 配置JNI库路径和Native构建

- [x] Task 6: 为llama模块创建build.gradle.kts
  - [x] SubTask 6.1: 创建llama/build.gradle.kts文件
  - [x] SubTask 6.2: 配置为Android Library模块
  - [x] SubTask 6.3: 添加LLaMA模型相关依赖

- [x] Task 7: 为mmd模块创建build.gradle.kts
  - [x] SubTask 7.1: 创建mmd/build.gradle.kts文件
  - [x] SubTask 7.2: 配置为Android Library模块
  - [x] SubTask 7.3: 添加MMD模型相关依赖

- [x] Task 8: 为showerclient模块创建build.gradle.kts
  - [x] SubTask 8.1: 创建showerclient/build.gradle.kts文件
  - [x] SubTask 8.2: 配置为Android Library模块
  - [x] SubTask 8.3: 添加客户端相关依赖

- [x] Task 9: 调整项目目录结构以匹配Gradle模块配置
  - [x] SubTask 9.1: 为每个模块创建标准Android目录结构（src/main/java、src/main/res等）
  - [x] SubTask 9.2: 将源代码文件从功能目录移动到对应的模块目录
  - [x] SubTask 9.3: 移动资源文件到对应的res目录
  - [x] SubTask 9.4: 移动JNI库文件到jniLibs目录
  - [x] SubTask 9.5: 移动Assets资源到assets目录
  - [x] SubTask 9.6: 创建或更新AndroidManifest.xml文件

- [x] Task 10: 验证并修复资源文件引用
  - [x] SubTask 10.1: 检查所有R资源引用的准确性
  - [x] SubTask 10.2: 验证drawable、string、layout资源完整性
  - [x] SubTask 10.3: 修复任何缺失或错误的资源引用

- [x] Task 11: 验证并修复依赖项配置
  - [x] SubTask 11.1: 检查所有依赖项版本兼容性
  - [x] SubTask 11.2: 验证Maven仓库配置
  - [x] SubTask 11.3: 修复任何版本冲突或缺失依赖

- [ ] Task 12: 创建GitHub Actions工作流配置
  - [ ] SubTask 12.1: 创建.github/workflows/android-build.yml文件
  - [ ] SubTask 12.2: 配置构建触发条件（push、pull_request）
  - [ ] SubTask 12.3: 设置Java JDK环境
  - [ ] SubTask 12.4: 配置Gradle缓存和构建命令
  - [ ] SubTask 12.5: 配置APK上传到Artifacts
  - [ ] SubTask 12.6: 配置Release APK上传到Releases（可选）

- [ ] Task 13: 本地测试构建（可选）
  - [ ] SubTask 13.1: 执行gradlew clean验证配置
  - [ ] SubTask 13.2: 执行gradlew assembleDebug测试构建
  - [ ] SubTask 13.3: 检查构建日志并修复错误
  - [ ] SubTask 13.4: 验证生成的APK文件

- [ ] Task 14: 推送代码触发GitHub Actions构建
  - [ ] SubTask 14.1: 提交所有构建配置文件
  - [ ] SubTask 14.2: 推送到GitHub触发构建
  - [ ] SubTask 14.3: 监控GitHub Actions构建状态
  - [ ] SubTask 14.4: 下载并测试生成的APK

# Task Dependencies
- [Task 2-8] 依赖于 [Task 1] - 需要先完成模块映射规划
- [Task 9] 依赖于 [Task 2-8] - 需要先创建所有模块的build.gradle.kts
- [Task 10-11] 依赖于 [Task 9] - 需要先完成目录结构调整
- [Task 12] 依赖于 [Task 10-11] - 需要先验证资源文件和依赖项
- [Task 13] 依赖于 [Task 12] - 可选的本地测试，依赖于GitHub Actions配置
- [Task 14] 依赖于 [Task 12] 或 [Task 13] - 推送触发构建
