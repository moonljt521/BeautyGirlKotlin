# BeautyGirl API 测试工具

用于在不运行 Android APP 的情况下测试网络请求，方便调试和开发。

## 📋 API 列表

### 1. Gank API (萌妹子)
- **Base URL**: `http://gank.io/api/`
- **接口**: `data/福利/{number}/{page}`
- **参数**:
  - `number`: 每页数量
  - `page`: 页码
- **示例**: `http://gank.io/api/data/福利/10/1`

### 2. 豆瓣 API
- **Base URL**: `http://www.buxiuse.com/`
- **参数**:
  - `cid`: 分类 ID (例如: 27)
  - `page`: 页码
- **示例**: `http://www.buxiuse.com/?cid=27&page=1`

### 3. 优美图库 API
- **Base URL**: `http://www.umei.cc/bizhitupian/meinvbizhi/`

## 🚀 使用方法

### 方法 1: Python 脚本 (推荐)

#### 安装依赖
```bash
pip3 install requests
```

#### 运行测试
```bash
# 测试所有 API
python3 api-test.py

# 交互模式 - 可以自定义参数
python3 -i api-test.py
>>> test_gank_api(10, 1)      # 测试 Gank API，每页10条，第1页
>>> test_gank_api(20, 2)      # 测试 Gank API，每页20条，第2页
>>> test_douban_api(27, 1)    # 测试豆瓣 API
>>> test_youmei_api()         # 测试优美图库 API
```

### 方法 2: Kotlin 脚本

#### 安装 Kotlin
```bash
# macOS
brew install kotlin

# 或下载: https://kotlinlang.org/docs/command-line.html
```

#### 运行测试
```bash
kotlin api-test.kts
```

### 方法 3: cURL 命令

```bash
# 测试 Gank API
curl "http://gank.io/api/data/福利/10/1"

# 测试豆瓣 API
curl "http://www.buxiuse.com/?cid=27&page=1"

# 测试优美图库 API
curl "http://www.umei.cc/bizhitupian/meinvbizhi/"
```

### 方法 4: 使用 Postman 或 Insomnia

1. 导入以下 URL 到 Postman/Insomnia
2. 发送 GET 请求测试

## 🔧 自定义测试

### Python 示例

```python
# 在交互模式下
python3 -i api-test.py

# 测试不同参数
>>> test_gank_api(number=20, page=3)  # 第3页，每页20条
>>> test_douban_api(cid=28, page=2)   # 不同的分类ID
```

### 修改脚本

你可以直接编辑 `api-test.py` 文件来：
- 添加新的 API 测试
- 修改请求头
- 添加认证信息
- 保存响应到文件

## 📊 输出示例

```
==================== 测试 Gank API ====================
URL: http://gank.io/api/data/福利/10/1
✅ 请求成功!
状态码: 200

响应数据:
{
  "error": false,
  "results": [
    {
      "_id": "xxx",
      "url": "http://...",
      "desc": "美女图片",
      ...
    }
  ]
}

📊 数据统计:
  - 返回数量: 10
  - 第一条数据:
    - ID: xxx
    - URL: http://...
    - 描述: 美女图片
```

## 🎯 使用场景

1. **快速验证 API 是否可用**
   - 不需要启动 Android 模拟器
   - 不需要编译 APP

2. **调试网络请求**
   - 查看实际返回的数据结构
   - 验证参数是否正确

3. **测试不同参数组合**
   - 快速测试不同的页码
   - 测试不同的分类 ID

4. **开发新功能前的 API 探索**
   - 了解 API 返回的数据格式
   - 规划数据模型

## 💡 提示

- 如果 API 请求失败，检查网络连接
- 某些 API 可能需要特定的 User-Agent 或 Headers
- 可以使用 `--verbose` 参数查看详细的请求信息（需要修改脚本）

## 🔗 相关文件

- `api-test.py` - Python 测试脚本
- `api-test.kts` - Kotlin 测试脚本
- `app/src/main/java/com/moon/beautygirlkotlin/common/network/api/` - APP 中的 API 定义

## 📝 添加新的 API 测试

在 `api-test.py` 中添加新函数：

```python
def test_new_api(param1: str, param2: int):
    """测试新的 API"""
    print_separator("测试新 API")
    url = f"http://example.com/api?param1={param1}&param2={param2}"
    print(f"URL: {url}")
    
    try:
        response = requests.get(url, timeout=TIMEOUT)
        if response.status_code == 200:
            print("✅ 请求成功!")
            print(json.dumps(response.json(), indent=2, ensure_ascii=False))
        else:
            print(f"❌ 请求失败! 状态码: {response.status_code}")
    except Exception as e:
        print(f"❌ 异常: {e}")
```

然后在 `test_all()` 函数中调用它。
