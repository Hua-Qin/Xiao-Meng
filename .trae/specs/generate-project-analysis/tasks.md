# Tasks
- [x] Task 1: 分析项目整体结构和技术栈
  - [x] SubTask 1.1: 读取并分析根目录配置文件（build.gradle.kts, package.json等）
  - [x] SubTask 1.2: 分析app模块的build.gradle.kts了解依赖和配置
  - [x] SubTask 1.3: 分析原生模块（mnn, mmd, llama）的配置
  - [x] SubTask 1.4: 识别项目使用的主要技术栈

- [x] Task 2: 深度分析核心模块文件
  - [x] SubTask 2.1: 分析app/src/main/java目录下的核心Kotlin文件
  - [x] SubTask 2.2: 分析app/src/main/cpp目录下的C++文件
  - [x] SubTask 2.3: 分析app/src/main/assets目录下的JavaScript包和资源
  - [x] SubTask 2.4: 分析tools目录下的工具脚本

- [x] Task 3: 生成文件分析.md文档
  - [x] SubTask 3.1: 编写项目整体架构说明
  - [x] SubTask 3.2: 编写各模块功能描述
  - [x] SubTask 3.3: 编写关键文件作用说明
  - [x] SubTask 3.4: 编写技术栈列表和依赖关系

- [x] Task 4: 生成项目文件目录树.md文档
  - [x] SubTask 4.1: 生成根目录及所有子目录的树形结构
  - [x] SubTask 4.2: 为每个文件/目录添加详细注释
  - [x] SubTask 4.3: 标注文件类型和作用
  - [x] SubTask 4.4: 确保无遗漏任何文件

- [x] Task 5: 验证文档完整性
  - [x] SubTask 5.1: 检查文件分析.md内容完整性
  - [x] SubTask 5.2: 检查目录树.md内容完整性
  - [x] SubTask 5.3: 确保无遗漏文件

# Task Dependencies
- [Task 2] depends on [Task 1]
- [Task 3] depends on [Task 2]
- [Task 4] depends on [Task 2]
- [Task 5] depends on [Task 3], [Task 4]
