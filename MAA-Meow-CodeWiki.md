# MAA-Meow 项目代码文档

> **项目地址**: https://github.com/Aliothmoon/MAA-Meow
>
> **版本**: v0.7.1
>
> **最后更新**: 2026-05-09

---

## 目录

1. [项目概述](#1-项目概述)
2. [技术栈与语言分布](#2-技术栈与语言分布)
3. [目录结构](#3-目录结构)
4. [核心模块详解](#4-核心模块详解)
5. [后台运行原理](#5-后台运行原理)
6. [屏幕捕获技术](#6-屏幕捕获技术)
7. [输入控制机制](#7-输入控制机制)
8. [依赖关系](#8-依赖关系)
9. [关键类与函数](#9-关键类与函数)
10. [运行流程](#10-运行流程)
11. [构建与运行](#11-构建与运行)

---

## 1. 项目概述

MAA-Meow 是一款在 Android 设备上原生运行的《明日方舟》游戏小助手，基于 MAA (MaaAssistantArknights) 核心实现自动化任务。核心特性包括：

- **无需 Root 权限**: 通过 Shizuku 实现后台运行
- **双模式运行**: 前台悬浮控制面板 / 后台虚拟显示器无界面运行
- **完整任务支持**: 理智作战、公招识别、基建托管、抄作业、自动肉鸽等
- **定时任务**: 按预设时间自动启动任务

---

## 2. 技术栈与语言分布

| 语言 | 占比 | 主要用途 |
|------|------|----------|
| **Kotlin** | 90.8% | 应用层主要开发语言，UI、业务逻辑、数据层 |
| **Java** | 5.6% | JNI 桥接层、系统兼容代码 |
| **C++** | 2.0% | Native 屏幕捕获、帧缓冲处理、性能关键代码 |
| **C** | 0.8% | Native 启动器 (launcher.c) |
| **Python** | 0.5% | MAA Core 资源下载脚本 |
| **AIDL** | 0.2% | 进程间通信接口定义 |
| **CMake** | 0.1% | Native 代码构建配置 |

---

## 3. 目录结构

```
MAA-Meow/
├── app/                          # 主应用模块
│   └── src/main/
│       ├── java/com/aliothmoon/maameow/
│       │   ├── bridge/           # JNI 桥接层
│       │   │   └── NativeBridgeLib.java
│       │   ├── constant/         # 常量定义
│       │   ├── data/            # 数据层
│       │   ├── domain/          # 领域层
│       │   │   └── service/      # 任务执行服务
│       │   ├── koin/            # 依赖注入配置
│       │   ├── maa/             # MAA 核心封装
│       │   ├── manager/         # 权限与服务管理
│       │   ├── overlay/         # 悬浮窗相关
│       │   ├── presentation/    # 表现层 (Compose UI)
│       │   │   ├── components/   # UI 组件
│       │   │   ├── navigation/   # 导航
│       │   │   ├── state/        # 状态管理
│       │   │   ├── view/         # 页面视图
│       │   │   └── viewmodel/    # 视图模型
│       │   ├── remote/          # 远程服务连接
│       │   ├── root/            # Root 服务实现
│       │   ├── schedule/        # 定时任务
│       │   │   ├── receiver/     # 广播接收器
│       │   │   └── service/      # 调度执行服务
│       │   ├── service/         # 无障碍服务
│       │   ├── theme/           # 主题配置
│       │   ├── third/          # 第三方集成
│       │   ├── utils/          # 工具类
│       │   ├── MaaApplication.kt
│       │   └── MainActivity.kt
│       ├── native/              # C/C++ Native 代码
│       │   ├── bridge.cpp/h      # 核心桥接
│       │   ├── bridge_capture.cpp/h # 屏幕捕获
│       │   ├── bridge_frame_buffer.cpp/h # 帧缓冲处理
│       │   ├── bridge_input.cpp/h # 输入控制
│       │   ├── bridge_preview.cpp/h # 预览帧处理
│       │   ├── launcher.c        # Native 启动器
│       │   ├── misc.cpp           # 杂项工具
│       │   └── CMakeLists.txt
│       ├── aidl/                # AIDL 接口定义
│       ├── assets/              # 资源文件
│       └── res/                 # Android 资源
├── hidden-api/                   # Android 隐藏 API 访问模块
├── annotation-api/              # 注解 API 定义
├── ksp-processor/               # KSP 注解处理器
├── gradle/                      # Gradle 配置
├── docs/                        # 文档
├── scripts/                     # 辅助脚本
└── build.gradle.kts            # 根级构建配置
```

---

## 4. 核心模块详解

### 4.1 应用层 (app)

#### 4.1.1 bridge - JNI 桥接层

**职责**: 连接 Kotlin/Java 层与 Native C++ 层

**核心文件**:
- `NativeBridgeLib.java` - Native 库加载与调用入口

**关键方法**:
```java
public static native int nativeInit(String corePath, String resourcePath);
public static native int nativeRelease();
public static native int nativeConnect(long callbackPtr);
public static native int nativeDisconnect();
public static native int nativeScreencapStart(int width, int height);
public static native int nativeScreencapStop();
```

#### 4.1.2 manager - 权限与服务管理

**职责**: 管理 Shizuku、Root 权限及远程服务连接

**核心文件**:
- `ShizukuManager.kt` - Shizuku 服务管理
- `ShizukuRemoteServiceConnector.kt` - Shizuku 远程服务连接器
- `RootManager.kt` - Root 权限管理
- `RootRemoteServiceConnector.kt` - Root 远程服务连接器
- `PermissionManager.kt` - 运行时权限管理
- `RemoteServiceManager.kt` - 远程服务统一管理

#### 4.1.3 service - 无障碍服务

**职责**: 实现无障碍服务用于系统级操作

**核心文件**:
- `AccessibilityHelperService.kt` - 无障碍服务实现

#### 4.1.4 presentation - 表现层 (Jetpack Compose)

**职责**: 用户界面展示

**核心组件**:
- `components/` - 可复用 UI 组件
- `navigation/` - 导航配置
- `state/` - UI 状态定义
- `view/` - 页面视图
- `viewmodel/` - MVVM 视图模型

#### 4.1.5 schedule - 定时任务

**职责**: 实现定时任务调度

**核心组件**:
- `receiver/ScheduleReceiver.kt` - 闹钟触发接收
- `receiver/BootReceiver.kt` - 开机/更新恢复接收
- `service/ScheduleExecutionService.kt` - 定时执行服务

#### 4.1.6 root - Root 服务

**职责**: 在 Root 模式下启动特权服务

**核心文件**:
- `RootServiceStarter.java` - 服务启动器
- `RootUserService.java` - 用户服务实现
- `RootIContentProviderCompat.java` - IContentProvider 兼容
- `RootServiceBootstrapProvider.kt` - 引导提供者
- `RootServiceBootstrapRegistry.kt` - 引导注册表

### 4.2 Native 层 (native)

**职责**: 高性能屏幕捕获、帧缓冲处理、输入控制

**核心模块**:

| 文件 | 职责 |
|------|------|
| `bridge.cpp` | 核心桥接逻辑 |
| `bridge_capture.cpp` | 屏幕捕获实现 (AImageReader) |
| `bridge_frame_buffer.cpp` | 帧缓冲管理、BGR 转换 |
| `bridge_input.cpp` | 输入事件分发 |
| `bridge_preview.cpp` | 预览帧处理 |
| `launcher.c` | Native 进程启动 |

### 4.3 hidden-api 模块

**职责**: 访问 Android 隐藏的系统 API

**用途**:
- 绕过 Android 公开 API 限制
- 访问系统级功能
- 实现后台屏幕捕获

### 4.4 ksp-processor 模块

**职责**: 注解处理器，用于代码生成

**用途**:
- 处理自定义注解
- 生成辅助代码
- 减少样板代码

---

## 5. 后台运行原理

### 5.1 整体架构

MAA-Meow 的后台运行依赖于 **Shizuku** 或 **Root** 权限，通过以下机制实现：

```
┌─────────────────────────────────────────────────────────┐
│                    MAA-Meow 应用                         │
├─────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────┐  │
│  │   UI 层     │  │  业务逻辑层  │  │   Native 层     │  │
│  │ (Compose)   │  │  (Kotlin)   │  │   (C/C++)       │  │
│  └─────────────┘  └─────────────┘  └─────────────────┘  │
├─────────────────────────────────────────────────────────┤
│                    权限层                                │
│  ┌─────────────────────┐  ┌─────────────────────────┐  │
│  │   Shizuku Manager    │  │    Root Manager          │  │
│  │   (ADB 权限)         │  │    (Root 权限)           │  │
│  └─────────────────────┘  └─────────────────────────┘  │
├─────────────────────────────────────────────────────────┤
│                  系统 API 层                             │
│  ┌─────────────────────┐  ┌─────────────────────────┐  │
│  │  Accessibility API   │  │  Hidden API (Shizuku)    │  │
│  │  (无障碍服务)         │  │  (隐藏系统 API)          │  │
│  └─────────────────────┘  └─────────────────────────┘  │
├─────────────────────────────────────────────────────────┤
│                  Native 服务层                           │
│  ┌─────────────────────┐  ┌─────────────────────────┐  │
│  │  Virtual Display     │  │  Input Control           │  │
│  │  (虚拟显示器)         │  │  (输入控制)              │  │
│  └─────────────────────┘  └─────────────────────────┘  │
└─────────────────────────────────────────────────────────┘
```

### 5.2 核心机制

#### 5.2.1 Shizuku 方案

Shizuku 通过 ADB 启动一个具有系统权限的 Java 进程 (app_process)，应用通过 IPC 与该进程通信。

**流程**:
1. 用户通过 ADB 授权 Shizuku
2. Shizuku 服务在后台运行
3. MAA-Meow 通过 Shizuku API 调用系统隐藏 API
4. 实现屏幕捕获和输入控制

#### 5.2.2 Root 方案

对于已 Root 的设备，直接启动特权服务。

**流程**:
1. 检测 Root 权限
2. 通过 `su` 启动 Root 服务
3. 服务在 root 用户下运行
4. 提供系统级功能访问

#### 5.2.3 虚拟显示器 (Virtual Display)

后台运行的关键是创建虚拟显示器：

```kotlin
// 虚拟显示器用于无界面屏幕捕获
val virtualDisplay = windowManager.createVirtualDisplay(
    name = "MAA-Meow",
    width = screenWidth,
    height = screenHeight,
    densityDpi = screenDensity,
    surface = captureSurface,
    flags = VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR
)
```

**特点**:
- 不需要可见的窗口
- 可以配置任意分辨率
- 目标应用运行在虚拟显示器上
- 支持后台自动化操作

#### 5.2.4 前台服务

对于需要长时间运行的任务，使用前台服务：

```xml
<service
    android:name=".domain.service.TaskExecutionService"
    android:foregroundServiceType="specialUse">
    <property
        android:name="android.app.PROPERTY_SPECIAL_USE_FGS_SUBTYPE"
        android:value="Background MAA task execution progress notification" />
</service>
```

---

## 6. 屏幕捕获技术

### 6.1 捕获原理

MAA-Meow 采用 **AImageReader + AHardwareBuffer** 实现高效的屏幕捕获：

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│  Virtual Display │───│   AImageReader   │───│  Frame Buffer   │
│  (Surface)       │    │  (RGBA_8888)    │    │  (BGR 格式)     │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                      │
                                                      ▼
                                              ┌─────────────────┐
                                              │   MAA Core      │
                                              │  (图像识别)      │
                                              └─────────────────┘
```

### 6.2 核心实现 (bridge_capture.cpp)

```cpp
// AImageReader 回调处理
static void onImageAvailable(void* context, AImageReader* reader) {
    AImage* image = nullptr;
    if (AImageReader_acquireLatestImage(reader, &image) != AMEDIA_OK) {
        return;
    }
    
    AHardwareBuffer* hb = nullptr;
    if (AImage_getHardwareBuffer(image, &hb) == AMEDIA_OK && hb) {
        // 写入帧缓冲
        WriteHardwareBufferToFrame(hb);
    }
    
    // 如果启用了预览，分发预览帧
    if (IsPreviewEnabled()) {
        DispatchPreview(image);
    }
}
```

### 6.3 帧缓冲管理 (bridge_frame_buffer.cpp)

采用三缓冲机制优化性能：

```cpp
// 三缓冲状态机
enum FrameState {
    FRAME_STATE_FREE = 0,      // 空闲可用
    FRAME_STATE_WRITING = 1,   // 写入中
    FRAME_STATE_READING = 2    // 读取中
};

// 帧缓冲数组
static FrameBuffer g_buffers[FRAME_BUFFER_COUNT] = {};
```

### 6.4 ARM NEON 优化

BGR 转换使用 NEON 指令集加速：

```cpp
#if defined(__ARM_NEON)
for (; x <= width - 16; x += 16) {
    uint8x16x4_t rgba = vld4q_u8(s);
    uint8x16x3_t bgr;
    bgr.val[0] = rgba.val[2];  // B = R
    bgr.val[1] = rgba.val[1];  // G = G
    bgr.val[2] = rgba.val[0];  // R = B
    vst3q_u8(d3, bgr);
}
#endif
```

---

## 7. 输入控制机制

### 7.1 架构

输入控制通过 JNI 调用 Java 层的 InputDevice Driver：

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│    MAA Core     │───▶│   Input Bridge   │───▶│  InputDriver    │
│  (C++)          │    │  (bridge_input)  │    │  (Java)         │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                                      │
                                                      ▼
                                              ┌─────────────────┐
                                              │  系统 InputManager │
                                              │  或 Accessibility  │
                                              └─────────────────┘
```

### 7.2 核心实现 (bridge_input.cpp)

```cpp
BRIDGE_API int DispatchInputMessage(MethodParam param) {
    switch (param.method) {
        case TOUCH_DOWN:
            return UpcallInputControl(env, TOUCH_DOWN, x, y, 0, displayId);
        case TOUCH_MOVE:
            return UpcallInputControl(env, TOUCH_MOVE, x, y, 0, displayId);
        case TOUCH_UP:
            return UpcallInputControl(env, TOUCH_UP, x, y, 0, displayId);
        case KEY_DOWN:
            return UpcallInputControl(env, KEY_DOWN, 0, 0, keyCode, displayId);
        case KEY_UP:
            return UpcallInputControl(env, KEY_UP, 0, 0, keyCode, displayId);
        case START_GAME:
            return UpcallStartApp(env, packageName, displayId, forceStop);
    }
}
```

### 7.3 输入方法

| 方法 | 描述 | 使用场景 |
|------|------|----------|
| `touchDown` | 触摸按下 | 点击、拖拽开始 |
| `touchMove` | 触摸移动 | 拖拽操作 |
| `touchUp` | 触摸释放 | 点击结束 |
| `keyDown` | 按键按下 | 按键操作 |
| `keyUp` | 按键释放 | 按键操作 |
| `startApp` | 启动应用 | 打开游戏 |

---

## 8. 依赖关系

### 8.1 主要依赖

| 依赖 | 版本 | 用途 |
|------|------|------|
| **Kotlin** | 2.0+ | 开发语言 |
| **Jetpack Compose** | BOM 2024+ | UI 框架 |
| **Koin** | 3.5+ | 依赖注入 |
| **AndroidX Lifecycle** | 2.7+ | 生命周期管理 |
| **Shizuku** | 13+ | 无 Root 权限系统 API |
| **libsu** | 5.0+ | Root 权限封装 |
| **JNA** | 5.14+ | Native 调用 |
| **Fastjson2** | 2.0+ | JSON 解析 |
| **OkHttp** | 4.12+ | 网络请求 |
| **Kotlinx Serialization** | 1.6+ | 序列化 |

### 8.2 模块依赖图

```
┌────────────────────────────────────────────────────────────────┐
│                        app 模块                                 │
│  ┌──────────────────────────────────────────────────────────┐   │
│  │  presentation ──▶ domain ──▶ data                         │   │
│  │       │              │         │                          │   │
│  │       │              │         ▼                          │   │
│  │       │              │    bridge ◀──▶ Native (C++)        │   │
│  │       │              │         │                          │   │
│  │       │              ▼         ▼                          │   │
│  │       │         manager ◀──▶ root                         │   │
│  │       │              │                                    │   │
│  │       │              ▼                                    │   │
│  │       │         hidden-api (compileOnly)                 │   │
│  └───────┼──────────────────────────────────────────────────┘   │
│          │                                                      │
│          └───▶ annotation-api                                  │
│                                                                   │
│          └───▶ ksp-processor                                    │
└──────────────────────────────────────────────────────────────────┘
```

### 8.3 第三方库集成

| 库 | 集成方式 | 功能 |
|---|---------|------|
| **scrcpy** | 代码参考 | 屏幕镜像和控制技术 |
| **MAA Core** | 外部依赖 | 游戏自动化核心 |
| **Shizuku** | API 集成 | 系统 API 访问 |

---

## 9. 关键类与函数

### 9.1 Java/Kotlin 层

#### MaaApplication.kt
```kotlin
class MaaApplication : Application() {
    // 应用初始化
    // Koin 依赖注入配置
    // 全局状态管理
}
```

#### MainActivity.kt
```kotlin
class MainActivity : ComponentActivity() {
    // 主界面入口
    // Compose UI 渲染
    // 生命周期管理
}
```

#### ShizukuManager.kt
```kotlin
class ShizukuManager {
    fun checkPermission(): Boolean      // 检查 Shizuku 权限
    fun requestPermission()             // 请求授权
    fun startService()                  // 启动 Shizuku 服务
    fun stopService()                  // 停止服务
    fun isRunning(): Boolean            // 服务运行状态
}
```

#### NativeBridgeLib.java
```java
public class NativeBridgeLib {
    // 加载 Native 库
    // 初始化 MAA Core
    // 连接回调
    // 屏幕捕获控制
    // 输入分发
}
```

### 9.2 Native 层

#### bridge.h - 核心接口
```cpp
// 初始化/释放
int Init(const char* core_path, const char* resource_path);
void Release();

// 连接管理
int Connect(Callbacks* callbacks);
void Disconnect();

// 屏幕捕获
int ScreencapStart(int width, int height);
int ScreencapStop();

// 输入控制
int DispatchInput(MethodParam* param);
```

#### bridge_capture.h - 捕获接口
```cpp
// 捕获器管理
jobject SetupNativeCapturer(JNIEnv* env, int width, int height);
void ReleaseNativeCapturer();
```

#### bridge_frame_buffer.h - 帧缓冲接口
```cpp
// 缓冲管理
void InitFrameBuffers(int width, int height);
void ReleaseFrameBuffers();

// 帧操作
FrameInfo GetLockedPixels();
int UnlockPixels(FrameInfo info);
int64_t GetFrameCount();
```

#### bridge_input.h - 输入接口
```cpp
// 初始化
bool InitInputBridge(JavaVM* vm, JNIEnv* env, const char* driverClassName);
void ReleaseInputBridge(JNIEnv* env);

// 消息分发
int DispatchInputMessage(MethodParam param);
```

---

## 10. 运行流程

### 10.1 应用启动流程

```
┌─────────────────────────────────────────────────────────────┐
│                    1. 应用启动                               │
│  MainActivity.onCreate()                                     │
│        │                                                    │
│        ▼                                                    │
│  ┌─────────────────────────────────────────────────────┐     │
│  │ 2. 权限检查                                          │     │
│  │   - Shizuku 权限                                    │     │
│  │   - Storage 权限                                    │     │
│  │   - Notification 权限                               │     │
│  └─────────────────────────────────────────────────────┘     │
│        │                                                    │
│        ▼                                                    │
│  ┌─────────────────────────────────────────────────────┐     │
│  │ 3. 初始化 Native 层                                  │     │
│  │   - System.loadLibrary("bridge")                   │     │
│  │   - Init(MAA_CORE_PATH, RESOURCE_PATH)              │     │
│  └─────────────────────────────────────────────────────┘     │
│        │                                                    │
│        ▼                                                    │
│  ┌─────────────────────────────────────────────────────┐     │
│  │ 4. 初始化 Koin DI                                   │     │
│  │   - moduleList = appModules + platformModules       │     │
│  └─────────────────────────────────────────────────────┘     │
│        │                                                    │
│        ▼                                                    │
│  ┌─────────────────────────────────────────────────────┐     │
│  │ 5. 渲染 Compose UI                                  │     │
│  │   - MaterialTheme                                  │     │
│  │   - Navigation                                     │     │
│  └─────────────────────────────────────────────────────┘     │
└─────────────────────────────────────────────────────────────┘
```

### 10.2 任务执行流程

```
┌─────────────────────────────────────────────────────────────┐
│                    任务执行流程                               │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐    │
│  │ 1. 启动后台服务 (Foreground Service)                  │    │
│  └─────────────────────────────────────────────────────┘    │
│        │                                                    │
│        ▼                                                    │
│  ┌─────────────────────────────────────────────────────┐    │
│  │ 2. 启动 MAA Core                                     │    │
│  │   - NativeBridgeLib.nativeInit()                    │    │
│  │   - 设置回调函数                                     │    │
│  │   - 加载资源文件                                     │    │
│  └─────────────────────────────────────────────────────┘    │
│        │                                                    │
│        ▼                                                    │
│  ┌─────────────────────────────────────────────────────┐    │
│  │ 3. 启动屏幕捕获                                       │    │
│  │   - NativeBridgeLib.nativeScreencapStart()          │    │
│  │   - 创建 AImageReader                               │    │
│  │   - 开始帧捕获循环                                   │    │
│  └─────────────────────────────────────────────────────┘    │
│        │                                                    │
│        ▼                                                    │
│  ┌─────────────────────────────────────────────────────┐    │
│  │ 4. MAA Core 循环                                     │    │
│  │   while (running) {                                 │    │
│  │     frame = GetLockedPixels();  // 获取当前帧        │    │
│  │     action = analyze(frame);       // 图像识别      │    │
│  │     if (action) {                                    │    │
│  │       DispatchInput(action);     // 执行动作        │    │
│  │     }                                                │    │
│  │     UnlockPixels(frame);                             │    │
│  │   }                                                 │    │
│  └─────────────────────────────────────────────────────┘    │
│        │                                                    │
│        ▼                                                    │
│  ┌─────────────────────────────────────────────────────┐    │
│  │ 5. 任务完成/异常退出                                  │    │
│  │   - 停止屏幕捕获                                     │    │
│  │   - 断开连接                                         │    │
│  │   - 清理资源                                         │    │
│  └─────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
```

### 10.3 定时任务流程

```
┌─────────────────────────────────────────────────────────────┐
│                    定时任务流程                               │
│                                                             │
│  ┌─────────────────────────────────────────────────────┐    │
│  │ 1. 用户配置定时任务                                   │    │
│  │   - 选择任务类型                                     │    │
│  │   - 设置执行时间                                     │    │
│  │   - 保存到 DataStore                                │    │
│  └─────────────────────────────────────────────────────┘    │
│        │                                                    │
│        ▼                                                    │
│  ┌─────────────────────────────────────────────────────┐    │
│  │ 2. 调度器注册 Alarm                                  │    │
│  │   - AlarmManager.setExactAndAllowWhileIdle()       │    │
│  └─────────────────────────────────────────────────────┘    │
│        │                                                    │
│        ▼                                                    │
│  ┌─────────────────────────────────────────────────────┐    │
│  │ 3. 等待触发 / 设备重启恢复                             │    │
│  │   - BootReceiver 恢复定时任务                        │    │
│  │   - PackageUpdateReceiver 恢复                       │    │
│  └─────────────────────────────────────────────────────┘    │
│        │                                                    │
│        ▼                                                    │
│  ┌─────────────────────────────────────────────────────┐    │
│  │ 4. Alarm 触发                                        │    │
│  │   - ScheduleReceiver 接收广播                        │    │
│  │   - 启动 ScheduleExecutionService                   │    │
│  └─────────────────────────────────────────────────────┘    │
│        │                                                    │
│        ▼                                                    │
│  ┌─────────────────────────────────────────────────────┐    │
│  │ 5. 执行任务 (同任务执行流程)                          │    │
│  └─────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
```

---

## 11. 构建与运行

### 11.1 环境要求

| 要求 | 版本 |
|------|------|
| JDK | Temurin JDK 21 |
| Android Studio | 最新版 |
| Android SDK | API 28+ |
| Gradle | 8.x |
| NDK | 29.0.13113456 |

### 11.2 构建步骤

```bash
# 1. 克隆项目
git clone https://github.com/Aliothmoon/MAA-Meow.git

# 2. 下载 MAA Core
python scripts/setup_maa_core.py

# 3. 使用 Android Studio 打开
# Settings -> Build, Execution, Deployment -> Build Tools -> Gradle
# 选择 Temurin 21

# 4. Sync Project with Gradle Files

# 5. 构建 Debug APK
./gradlew assembleDebug
```

### 11.3 权限清单

```xml
<!-- 核心权限 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_SPECIAL_USE" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

<!-- Shizuku -->
<uses-permission android:name="moe.shizuku.manager.permission.API_V23" />
<uses-permission android:name="moe.shizuku.manager.permission.API_V11" />

<!-- 无障碍服务 -->
<uses-permission android:name="android.permission.BIND_ACCESSIBILITY_SERVICE" />

<!-- 悬浮窗 -->
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
<uses-permission android:name="android.permission.SYSTEM_OVERLAY_WINDOW" />

<!-- 存储 -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />

<!-- 其他 -->
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
```

---

## 附录

### A. 参考资料

- [MaaAssistantArknights](https://github.com/MaaAssistantArknights/MaaAssistantArknights)
- [Shizuku](https://github.com/RikkaApps/Shizuku)
- [Genymobile/scrcpy](https://github.com/Genymobile/scrcpy)
- [Android NDK 文档](https://developer.android.com/ndk)

### B. 相关文档

- [构建指南](./docs/BUILDING.md)
- [Roadmap](./docs/ROADMAP.md)
- [第三方代码声明](./docs/THIRD_PARTY_NOTICES.md)

### C. 许可证

本项目采用 AGPL-3.0 许可证开源。

---

*本文档由代码分析自动生成，如有问题请提交 Issue。*
