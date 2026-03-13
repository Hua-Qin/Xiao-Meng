# GitHub Actions 构建使用文档

## 目录
- [概述](#概述)
- [前置准备](#前置准备)
- [配置 GitHub Secrets](#配置-github-secrets)
- [上传代码到 GitHub](#上传代码到-github)
- [触发构建](#触发构建)
- [下载构建产物](#下载构建产物)
- [常见问题](#常见问题)

---

## 概述

本文档详细介绍如何使用 GitHub Actions 在云端自动构建 Operit Android 项目。构建系统会自动编译 Debug 和 Release 两个版本的 APK 文件。

### 构建环境
- **操作系统**: Ubuntu Latest
- **Java 版本**: JDK 17 (Temurin)
- **Android NDK**: 27.0.12077973
- **超时设置**: Debug 90 分钟, Release 120 分钟

### 构建产物
- **Debug APK**: `app-debug.apk` (调试版本)
- **Release APK**: `app-release.apk` (发布版本)

---

## 前置准备

### 1. 准备 GitHub 账户
确保你有一个 GitHub 账户，并且已经登录。

### 2. 准备本地项目
确保本地项目已完整初始化，包括所有子模块。

```bash
cd c:\Users\Administrator\Desktop\Operit-1.9.1
git submodule update --init --recursive
```

### 3. 准备 GitHub Token (可选)
如果你需要访问私有仓库或执行特殊操作，需要创建 Personal Access Token。

---

## 配置 GitHub Secrets

### Secrets 说明
项目需要配置以下两个 Secrets 才能正常构建：

| Secret 名称 | 说明 | 必需 |
|------------|------|------|
| `GITHUB_CLIENT_ID` | GitHub OAuth 客户端 ID | 是 |
| `GITHUB_CLIENT_SECRET` | GitHub OAuth 客户端密钥 | 是 |

### 配置步骤

#### 步骤 1: 进入仓库设置
1. 打开浏览器，访问你的仓库页面：`https://github.com/Hua-Qin/Xiao-Meng`
2. 点击仓库页面右上角的 **Settings** (设置) 按钮
3. 在左侧菜单中找到 **Secrets and variables** → **Actions**

#### 步骤 2: 添加 Secrets
1. 点击 **New repository secret** 按钮
2. 在 **Name** 字段输入: `GITHUB_CLIENT_ID`
3. 在 **Value** 字段输入你的 GitHub Client ID
4. 点击 **Add secret** 保存
5. 重复上述步骤，添加 `GITHUB_CLIENT_SECRET`

**注意**: 如果没有 GitHub OAuth 配置，可以暂时使用空字符串或测试值。

---

## 上传代码到 GitHub

### 方式一：使用 Git 命令行 (推荐)

#### 初始化本地仓库 (首次)
```bash
cd c:\Users\Administrator\Desktop\Operit-1.9.1

# 初始化 Git 仓库
git init

# 添加所有文件
git add .

# 提交更改
git commit -m "Initial commit"

# 添加远程仓库
git remote add origin https://github.com/Hua-Qin/Xiao-Meng.git

# 推送到 GitHub
git push -u origin main
```

#### 推送更新 (后续)
```bash
cd c:\Users\Administrator\Desktop\Operit-1.9.1

# 查看状态
git status

# 添加修改的文件
git add .

# 提交更改
git commit -m "描述你的修改"

# 推送到 GitHub
git push origin main
```

### 方式二：使用 GitHub Desktop

#### 首次上传
1. 打开 GitHub Desktop
2. 点击 **File** → **Add Local Repository**
3. 选择项目文件夹: `c:\Users\Administrator\Desktop\Operit-1.9.1`
4. 点击 **Publish repository** 按钮
5. 填写仓库名称: `Xiao-Meng`
6. 选择可见性: Public (公开) 或 Private (私有)
7. 点击 **Publish** 按钮

#### 后续更新
1. 在 GitHub Desktop 中修改文件
2. 在左侧填写提交信息
3. 点击 **Commit to main** 按钮
4. 点击 **Push origin** 按钮

### 方式三：直接在 GitHub 上传文件

#### 适合场景
- 修改少量文件
- 快速测试

#### 步骤
1. 访问仓库页面: `https://github.com/Hua-Qin/Xiao-Meng`
2. 点击 **Add file** → **Upload files**
3. 拖拽文件到上传区域
4. 在底部填写提交信息
5. 点击 **Commit changes** 按钮

---

## 触发构建

### 方式一：自动触发 (代码推送)

#### 触发条件
当以下操作发生时，自动触发构建：
- 推送代码到 `main` 分支
- 推送代码到 `master` 分支
- 创建针对 `main` 或 `master` 的 Pull Request

#### 操作步骤
```bash
# 推送代码到 main 分支，自动触发构建
cd c:\Users\Administrator\Desktop\Operit-1.9.1
git add .
git commit -m "更新代码"
git push origin main
```

推送完成后，GitHub Actions 会自动开始构建。

### 方式二：手动触发 (Workflow Dispatch)

#### 触发条件
- 不需要修改代码
- 可以在任何时候手动触发构建

#### 操作步骤
1. 访问仓库页面: `https://github.com/Hua-Qin/Xiao-Meng`
2. 点击 **Actions** 标签页
3. 在左侧选择 **Android Build** 工作流
4. 点击右侧的 **Run workflow** 按钮
5. 在弹出框中选择分支: `main`
6. 点击绿色的 **Run workflow** 按钮

---

## 查看构建状态

### 查看 Actions 页面
1. 访问: `https://github.com/Hua-Qin/Xiao-Meng/actions`
2. 查看最近的构建列表
3. 点击具体的构建记录查看详情

### 构建状态说明
| 图标 | 状态 | 说明 |
|------|------|------|
| ⏳ | Queued | 构建排队中 |
| 🔄 | In progress | 构建进行中 |
| ✅ | Success | 构建成功 |
| ❌ | Failure | 构建失败 |
| 🟡 | Cancelled | 构建被取消 |

### 查看构建日志
1. 点击具体的构建记录
2. 选择 **build-debug** 或 **build-release** job
3. 展开每个步骤查看详细日志
4. 点击错误信息查看详细堆栈

---

## 下载构建产物

### 构建成功后下载

#### 方式一：从 Actions 页面下载
1. 访问: `https://github.com/Hua-Qin/Xiao-Meng/actions`
2. 找到成功的构建记录
3. 滚动到页面底部 **Artifacts** 区域
4. 点击 **app-debug** 或 **app-release** 下载

#### 方式二：从构建详情页下载
1. 点击具体的构建记录
2. 在底部找到 **Artifacts** 区域
3. 点击对应的 artifact 名称下载

### 产物说明
| 文件名 | 说明 | 大小 |
|---------|------|------|
| `app-debug.apk` | 调试版本，包含调试信息 | 较大 |
| `app-release.apk` | 发布版本，已优化 | 较小 |

---

## 工作流文件详解

### 文件位置
`.github/workflows/build.yml`

### 构建流程

#### Debug 构建流程
1. **Checkout code**: 拉取代码和子模块
2. **Set up JDK 17**: 安装 Java 17
3. **Setup Android SDK**: 安装 Android SDK
4. **Install NDK**: 安装 NDK 27.0.12077973
5. **Initialize missing submodules**: 初始化缺失的子模块
6. **Create local.properties**: 创建本地配置文件
7. **Setup Gradle**: 配置 Gradle 缓存
8. **Grant execute permission**: 添加执行权限
9. **Build Debug APK**: 编译 Debug APK
10. **Upload Debug APK**: 上传构建产物

#### Release 构建流程
1. 等待 Debug 构建成功
2. 执行与 Debug 相同的步骤 1-8
3. **Build Release APK**: 编译 Release APK
4. **Upload Release APK**: 上传构建产物

### 子模块自动初始化
工作流会自动克隆以下子模块:
- `quickjs/thirdparty/quickjs`: JavaScript 引擎
- `app/src/main/cpp/thirdparty/webrtc_aec3`: 音频回声消除
- `app/src/main/cpp/thirdparty/ncnn`: 深度学习推理框架
- `app/src/main/cpp/thirdparty/sherpa-ncnn`: 语音识别库

---

## 常见问题

### Q1: 构建失败，提示 Secrets 未配置
**解决方法**: 按照 [配置 GitHub Secrets](#配置-github-secrets) 章节添加必需的 Secrets。

### Q2: 构建超时
**原因**: 
- Debug 构建超过 90 分钟
- Release 构建超过 120 分钟

**解决方法**:
1. 检查网络连接
2. 优化代码减少编译时间
3. 在 `.github/workflows/build.yml` 中调整 `timeout-minutes`

### Q3: 子模块初始化失败
**原因**: 子模块地址失效或网络问题

**解决方法**:
1. 检查 `.gitmodules` 文件中的子模块地址
2. 手动在工作流中更新子模块克隆命令
3. 使用镜像地址

### Q4: NDK 版本不匹配
**错误信息**: `NDK from ndk.dir had version [X.X.X] which disagrees with android.ndkVersion [Y.Y.Y]`

**解决方法**:
1. 检查项目 `build.gradle` 中的 `android.ndkVersion`
2. 在工作流中安装对应的 NDK 版本
3. 当前项目使用 NDK 27.0.12077973

### Q5: 无法下载构建产物
**原因**:
- 构建未成功完成
- 产物过期 (90 天后自动删除)

**解决方法**:
1. 等待构建完全成功
2. 及时下载产物
3. 或重新触发构建

### Q6: libsudo.so 无法 strip 警告
**现象**: 构建日志中出现 `libsudo.so: The file was not recognized as a valid object file`

**说明**: 这是正常警告，不影响构建成功。`libsudo.so` 是预编译库，与 NDK 27 的 strip 工具不兼容。

### Q7: 推送代码后构建未触发
**原因**:
- 推送到了非 `main` 或 `master` 分支
- GitHub Actions 未启用

**解决方法**:
1. 推送到 `main` 或 `master` 分支
2. 检查仓库 Settings → Actions → General，确保 Actions 已启用

### Q8: 如何查看构建日志
**步骤**:
1. 访问 `https://github.com/Hua-Qin/Xiao-Meng/actions`
2. 点击构建记录
3. 点击 job 名称 (build-debug 或 build-release)
4. 展开步骤查看日志

### Q9: 构建产物在哪里
**位置**: 
- 构建过程中: `/home/runner/work/Xiao-Meng/Xiao-Meng/app/build/outputs/apk/`
- 下载后: 本地下载文件夹

### Q10: 如何修改构建配置
**步骤**:
1. 编辑 `.github/workflows/build.yml` 文件
2. 提交并推送更改
3. 下次构建会使用新配置

---

## 技术参考

### Git 常用命令
```bash
# 查看远程仓库
git remote -v

# 拉取最新代码
git pull origin main

# 查看分支
git branch -a

# 创建新分支
git branch feature-name

# 切换分支
git checkout feature-name

# 删除分支
git branch -d feature-name
```

### GitHub Actions 工作流语法
```yaml
# 触发条件
on:
  push:
    branches: [ main, master ]
  pull_request:
    branches: [ main, master ]
  workflow_dispatch:

# 环境变量
env:
  GRADLE_OPTS: "-Dorg.gradle.daemon=false"

# Job 依赖
needs: build-debug

# 超时设置
timeout-minutes: 90
```

---

## 附录

### 相关链接
- GitHub Actions 官方文档: https://docs.github.com/en/actions
- Gradle 官方文档: https://docs.gradle.org/
- Android NDK 下载: https://developer.android.com/ndk/downloads

### 联系方式
- 项目仓库: https://github.com/Hua-Qin/Xiao-Meng
- 问题反馈: 在仓库中创建 Issue

---

## 快速开始检查清单

- [ ] GitHub 账户已准备
- [ ] Secrets 已配置 (GITHUB_CLIENT_ID, GITHUB_CLIENT_SECRET)
- [ ] 代码已推送到 GitHub
- [ ] Actions 已启用
- [ ] 工作流文件已提交
- [ ] 已触发首次构建
- [ ] 构建成功完成
- [ ] 已下载构建产物

---

**文档版本**: 1.0  
**最后更新**: 2026-03-13  
**维护者**: Hua-Qin
