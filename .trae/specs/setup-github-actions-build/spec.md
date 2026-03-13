# GitHub Actions 在线构建配置 Spec

## Why
本地编译环境内存不足，导致Gradle构建失败。使用GitHub Actions可以在云端进行构建，避免本地资源限制。

## What Changes
- 创建 `.github/workflows/build.yml` 工作流文件
- 配置Java 17环境
- 配置Android SDK和NDK
- 添加构建缓存优化
- 配置APK上传artifact

## Impact
- Affected specs: 无
- Affected code: 新增GitHub Actions配置文件

## ADDED Requirements
### Requirement: GitHub Actions 工作流
系统SHALL提供GitHub Actions工作流配置，实现自动化APK构建。

#### Scenario: 推送代码触发构建
- **WHEN** 用户推送代码到main分支或创建Pull Request
- **THEN** GitHub Actions自动触发构建流程

#### Scenario: 手动触发构建
- **WHEN** 用户在Actions页面手动触发workflow_dispatch
- **THEN** 系统执行构建并上传APK artifact

### Requirement: 构建环境配置
系统SHALL配置正确的构建环境。

#### Scenario: Java环境
- **GIVEN** GitHub Actions runner
- **WHEN** 构建开始
- **THEN** 使用JDK 17进行构建

#### Scenario: Android SDK配置
- **GIVEN** GitHub Actions runner
- **WHEN** 构建开始
- **THEN** 自动安装Android SDK build-tools 34.0.0和NDK 25.1.8937393

### Requirement: 构建产物上传
系统SHALL上传构建产物供下载。

#### Scenario: Debug APK上传
- **WHEN** 构建成功完成
- **THEN** 上传app-debug.apk到Artifacts

#### Scenario: Release APK上传
- **WHEN** 构建Release版本成功
- **THEN** 上传app-release.apk到Artifacts
