# BeautyGirl API 调试指南

## 📚 概述

本指南提供了多种方式来调试和测试 BeautyGirl APP 的网络请求，无需在 Android 设备上运行应用。

## 🎯 已创建的工具

### 1. **show-api-info.py** - API 信息提取工具 ⭐ 推荐
自动从源代码中提取所有 API 信息，包括：
- Base URLs
- API 接口定义
- 数据模型
- 生成 curl 测试命令
- 生成 Postman Collection

```bash
python3 show-api-info.py
```

**输出：**
- 所有 API 的详细信息
- 可直接使用的 curl 命令
- `BeautyGirl.postman_collection.json` 文件

### 2. **api-test-simple.py** - 简单 API 测试工具
使用 Python 标准库，无需额外依赖

```bash
python3 api-test-simple.py

# 或交互模式
python3 -i api-test-simple.py
>>> test_gank_api(10, 1)
>>> test_douban_api(27, 1)
```

### 3. **api-test.py** - 完整 API 测试工具
功能更强大，需要 requests 库

```bash
pip3 install requests
python3 api-test.py
```

### 4. **api-test-curl.sh** - Shell 脚本测试
使用 curl 命令测试所有 API

```bash
bash api-test-curl.sh
```

### 5. **api-test.kts** - Kotlin 脚本
使用 Kotlin 编写的测试脚本

```bash
kotlin api-test.kts
```

## 🔧 推荐的调试流程

### 方案 1: 快速查看 API 信息（最简单）

```bash
# 1. 提取所有 API 信息
python3 show-api-info.py

# 2. 使用生成的 curl 命令测试
curl "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"
```

### 方案 2: 使用 Postman（最专业）

```bash
# 1. 生成 Postman Collection
python3 show-api-info.py

# 2. 在 Postman 中导入 BeautyGirl.postman_collection.json

# 3. 在 Postman 中测试和调试 API
```

### 方案 3: 使用 Python 脚本（最灵活）

```bash
# 交互式测试
python3 -i api-test-simple.py

# 在 Python 中测试不同参数
>>> test_gank_api(20, 2)  # 第2页，每页20条
>>> test_gank_api(10, 3)  # 第3页，每页10条
```

## 📋 当前 APP 使用的 API

### 1. Gank API (萌妹子)
- **Base URL**: `http://gank.io/api/`
- **接口**: `data/福利/{number}/{page}`
- **方法**: GET
- **参数**:
  - `number`: 每页数量
  - `page`: 页码

**测试命令：**
```bash
curl "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"
```

### 2. 豆瓣 API
- **Base URL**: `http://www.buxiuse.com/`
- **接口**: `/`
- **方法**: GET
- **参数**:
  - `cid`: 分类 ID
  - `page`: 页码

**测试命令：**
```bash
curl "http://www.buxiuse.com/?cid=27&page=1"
```

### 3. 优美图库 API
- **Base URL**: `http://www.umei.cc/bizhitupian/meinvbizhi/`
- **方法**: GET (HTML 解析)

**测试命令：**
```bash
curl "http://www.umei.cc/bizhitupian/meinvbizhi/"
```

## 🛠️ 高级调试技巧

### 使用 curl 的高级选项

```bash
# 查看完整的请求和响应头
curl -v "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"

# 只查看响应头
curl -I "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"

# 设置超时时间
curl --max-time 10 "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"

# 保存响应到文件
curl "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1" > response.json

# 使用 jq 格式化 JSON
curl "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1" | jq

# 添加自定义 Header
curl -H "User-Agent: BeautyGirl/1.0" "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"
```

### 使用 Python 进行复杂测试

```python
# 在交互模式下
python3 -i api-test-simple.py

# 测试多个页面
for page in range(1, 6):
    print(f"\n=== 测试第 {page} 页 ===")
    test_gank_api(10, page)

# 测试不同的数量
for number in [5, 10, 20]:
    print(f"\n=== 测试每页 {number} 条 ===")
    test_gank_api(number, 1)
```

## 🔍 调试常见问题

### 问题 1: API 请求超时
**原因**: 网络问题或 API 服务器不可用

**解决方案**:
```bash
# 增加超时时间
curl --max-time 30 "URL"

# 或在 Python 中
make_request(url, timeout=60)
```

### 问题 2: API 返回 404
**原因**: API 已失效或 URL 错误

**解决方案**:
1. 检查 URL 是否正确
2. 查看 APP 源代码中是否有更新的 URL
3. 寻找替代 API

### 问题 3: 中文 URL 编码问题
**原因**: URL 中包含中文字符

**解决方案**:
```bash
# 使用 URL 编码
# "福利" -> "%E7%A6%8F%E5%88%A9"
curl "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"
```

## 📊 监控和分析

### 使用 Charles Proxy 或 Fiddler
1. 在电脑上安装 Charles Proxy
2. 配置 Android 设备使用代理
3. 在 Charles 中查看所有网络请求

### 使用 Android Studio 的 Network Profiler
1. 在 Android Studio 中运行 APP
2. 打开 Profiler 窗口
3. 选择 Network 标签
4. 查看所有网络请求的详细信息

## 🎨 使用 MCP (Model Context Protocol)

如果你想使用 AI 辅助调试，可以：

1. **创建 MCP Server** 来包装这些 API
2. **使用 AI 助手** 自动测试和分析 API 响应
3. **生成测试用例** 和文档

示例 MCP Server 配置（可以添加到 `.kiro/settings/mcp.json`）:

```json
{
  "mcpServers": {
    "beautygirl-api": {
      "command": "python3",
      "args": ["-m", "http.server", "8000"],
      "env": {},
      "disabled": false
    }
  }
}
```

## 📝 总结

**最简单的方式：**
```bash
python3 show-api-info.py
# 然后使用生成的 curl 命令
```

**最专业的方式：**
```bash
python3 show-api-info.py
# 导入 BeautyGirl.postman_collection.json 到 Postman
```

**最灵活的方式：**
```bash
python3 -i api-test-simple.py
# 在 Python 交互模式中测试
```

## 🔗 相关文件

- `show-api-info.py` - API 信息提取工具 ⭐
- `api-test-simple.py` - 简单测试工具（无依赖）
- `api-test.py` - 完整测试工具（需要 requests）
- `api-test-curl.sh` - Shell 脚本测试
- `api-test.kts` - Kotlin 脚本测试
- `BeautyGirl.postman_collection.json` - Postman Collection
- `API-TEST-README.md` - 详细使用说明

## 💡 下一步

1. 如果 API 失效，考虑寻找替代 API
2. 可以添加缓存机制减少 API 调用
3. 考虑添加错误重试机制
4. 实现 API 响应的本地 Mock 用于开发测试
