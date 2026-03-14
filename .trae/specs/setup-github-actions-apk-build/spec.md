# GitHub Actions APK构建配置 Spec

## Why
当前项目缺少Gradle构建配置，无法执行APK构建。需要为重组后的功能模块创建完整的构建脚本，并配置GitHub Actions实现自动化构建，避免本地环境限制。

## What Changes
- 为各功能模块创建build.gradle.kts构建脚本
- 配置GitHub Actions工作流实现自动化APK构建
- 验证并调整项目结构以符合Android标准
- 确保所有资源文件和依赖项正确配置
- 生成可正常安装运行的APK文件

## Impact
- Affected specs: 构建系统、CI/CD流程
- Affected code: 所有模块的build.gradle.kts、GitHub Actions配置文件、项目结构

## ADDED Requirements
### Requirement: Gradle构建脚本配置
系统应为各功能模块创建完整的build.gradle.kts文件：
- **WHEN** 创建模块构建脚本
- **THEN** 脚本应包含正确的插件声明、依赖管理、编译选项和资源配置

### Requirement: 模块结构配置
系统应配置以下模块：
- app模块（主应用模块）
- dragonbones模块（骨骼动画）
- terminal模块（终端功能）
- mnn模块（MNN推理引擎）
- llama模块（LLaMA模型）
- mmd模块（MMD模型）
- showerclient模块（客户端）

### Requirement: 项目结构调整
系统应调整项目结构以匹配Gradle配置：
- **WHEN** 检查项目结构
- **THEN** 源代码、资源文件、库文件位置符合Android标准目录结构规范

### Requirement: 资源文件验证
系统应验证所有资源文件的完整性和正确性：
- 布局文件（layout）
- 图片资源（drawable）
- 字符串资源（string）
- 样式资源（style）
- JNI库文件（jniLibs）
- Assets资源文件

### Requirement: 依赖项验证
系统应验证所有依赖项：
- **WHEN** 检查依赖项配置
- **THEN** 所有本地库和远程库的版本兼容且引用正确
- 包括：Jetpack Compose、Material3、ObjectBox、Kotlin协程、网络库等

### Requirement: GitHub Actions工作流
系统应配置GitHub Actions实现自动化构建：
- **WHEN** 推送代码到GitHub
- **THEN** 自动触发APK构建流程
- 构建完成后自动上传APK到GitHub Releases或Artifacts

### Requirement: APK构建验证
系统应执行完整的项目构建流程：
- **WHEN** 执行构建命令
- **THEN** 构建过程无任何报错信息
- 生成的APK文件可直接安装并正常运行

#### Scenario: GitHub Actions构建APK
- **WHEN** 开发者推送代码到main分支或创建Pull Request
- **THEN** GitHub Actions自动触发构建流程
- 构建完成后生成Debug或Release APK
- APK可正常安装到Android设备或模拟器
- 应用启动后所有预期功能正常执行

#### Scenario: 本地验证构建
- **WHEN** 开发者本地执行构建命令（可选）
- **THEN** 构建成功生成APK文件
- APK签名信息正确
- APK大小合理

### Requirement: 构建产物管理
系统应管理构建产物：
- **WHEN** 构建完成
- **THEN** APK文件命名规范（包含版本号、构建类型）
- Debug APK上传到GitHub Actions Artifacts
- Release APK上传到GitHub Releases（可选）

## MODIFIED Requirements
无

## REMOVED Requirements
无
