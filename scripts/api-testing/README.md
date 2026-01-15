# API 测试脚本

## 📋 脚本列表

### 🌟 推荐使用

| 脚本 | 说明 | 使用方式 |
|------|------|----------|
| `quick-api-test.sh` | 交互式快速启动 ⭐ | `bash quick-api-test.sh` |
| `show-api-info.py` | API 信息提取 ⭐ | `python3 show-api-info.py` |

### 🧪 测试工具

| 脚本 | 说明 | 依赖 | 使用方式 |
|------|------|------|----------|
| `api-test-simple.py` | 简单测试工具 | 无 | `python3 api-test-simple.py` |
| `api-test.py` | 完整测试工具 | requests | `python3 api-test.py` |
| `api-test-curl.sh` | Shell 脚本测试 | curl | `bash api-test-curl.sh` |
| `api-test.kts` | Kotlin 脚本测试 | kotlin | `kotlin api-test.kts` |

## 🚀 快速开始

### 方式 1: 交互式菜单（最简单）

```bash
cd scripts/api-testing
bash quick-api-test.sh
```

### 方式 2: 直接查看 API 信息

```bash
cd scripts/api-testing
python3 show-api-info.py
```

### 方式 3: 交互式测试

```bash
cd scripts/api-testing
python3 -i api-test-simple.py

# 在 Python 中测试
>>> test_gank_api(10, 1)
>>> test_douban_api(27, 1)
```

## 📝 脚本详细说明

### quick-api-test.sh
交互式菜单，提供以下选项：
1. 查看 API 信息
2. Python 交互式测试
3. 单次测试所有 API
4. 使用 curl 测试
5. 生成 Postman Collection

### show-api-info.py
自动从源代码提取 API 信息，包括：
- Base URLs
- API 接口定义
- 数据模型
- 生成 curl 测试命令
- 生成 Postman Collection

**输出文件：**
- `../../docs/api-testing/BeautyGirl.postman_collection.json`

### api-test-simple.py
使用 Python 标准库的简单测试工具，无需额外依赖。

**功能：**
- 测试 Gank API
- 测试豆瓣 API
- 测试优美图库 API
- 支持交互模式

### api-test.py
功能更强大的测试工具，需要 requests 库。

**安装依赖：**
```bash
pip3 install requests
```

### api-test-curl.sh
使用 curl 命令测试所有 API。

**依赖：**
- curl（通常系统自带）

### api-test.kts
使用 Kotlin 编写的测试脚本。

**依赖：**
```bash
# macOS
brew install kotlin

# 或访问 https://kotlinlang.org/docs/command-line.html
```

## 💡 使用场景

### 场景 1: 快速验证 API
```bash
bash quick-api-test.sh
# 选择选项 3
```

### 场景 2: 调试特定 API
```bash
python3 -i api-test-simple.py
>>> test_gank_api(20, 2)  # 测试不同参数
```

### 场景 3: 生成 Postman Collection
```bash
python3 show-api-info.py
# 然后在 Postman 中导入生成的 JSON 文件
```

### 场景 4: 使用 curl 快速测试
```bash
bash api-test-curl.sh
```

## 🔧 从项目根目录运行

如果你在项目根目录，可以这样运行：

```bash
# 快速启动
bash scripts/api-testing/quick-api-test.sh

# 查看 API 信息
python3 scripts/api-testing/show-api-info.py

# 交互式测试
python3 -i scripts/api-testing/api-test-simple.py
```

## 📚 相关文档

详细文档位于 `docs/api-testing/` 目录：
- [API-TOOLS-SUMMARY.md](../../docs/api-testing/API-TOOLS-SUMMARY.md) - 工具完整总结
- [API-DEBUG-GUIDE.md](../../docs/api-testing/API-DEBUG-GUIDE.md) - 详细调试指南
- [API-TEST-README.md](../../docs/api-testing/API-TEST-README.md) - 使用说明

## 🎯 输出文件

脚本运行后可能生成以下文件：

| 文件 | 位置 | 说明 |
|------|------|------|
| `BeautyGirl.postman_collection.json` | `docs/api-testing/` | Postman Collection |

## ⚠️ 注意事项

1. **网络连接**：某些 API 可能需要特定的网络环境
2. **API 失效**：如果 API 返回错误，可能是服务已失效
3. **中文编码**：URL 中的中文会自动进行 URL 编码
4. **超时设置**：默认超时时间为 30 秒

## 🔗 快速链接

- [返回文档目录](../../docs/README.md)
- [API 测试工具快速指南](../../docs/API-TESTING-TOOLS.md)
- [项目主 README](../../README.md)
