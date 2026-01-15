# 🚀 API 测试工具使用指南

## 快速开始

```bash
# 最简单的方式 - 交互式菜单
bash quick-api-test.sh

# 或者直接查看 API 信息
python3 show-api-info.py
```

## 📁 工具文件列表

| 文件 | 说明 | 使用方式 |
|------|------|----------|
| `quick-api-test.sh` | 快速启动脚本 ⭐ | `bash quick-api-test.sh` |
| `show-api-info.py` | API 信息提取 ⭐ | `python3 show-api-info.py` |
| `api-test-simple.py` | 简单测试工具 | `python3 -i api-test-simple.py` |
| `api-test.py` | 完整测试工具 | `python3 api-test.py` |
| `api-test-curl.sh` | Shell 脚本测试 | `bash api-test-curl.sh` |
| `api-test.kts` | Kotlin 脚本测试 | `kotlin api-test.kts` |
| `BeautyGirl.postman_collection.json` | Postman Collection | 在 Postman 中导入 |

## 📚 文档

- `API-TOOLS-SUMMARY.md` - 工具总结 ⭐
- `API-DEBUG-GUIDE.md` - 完整调试指南
- `API-TEST-README.md` - 详细使用说明

## 💡 常用命令

```bash
# 1. 查看所有 API 信息
python3 show-api-info.py

# 2. 交互式测试
python3 -i api-test-simple.py
>>> test_gank_api(10, 1)

# 3. 使用 curl 快速测试
curl "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"

# 4. 生成 Postman Collection
python3 show-api-info.py  # 自动生成 BeautyGirl.postman_collection.json
```

## 🎯 主要功能

✅ 无需运行 Android APP 即可测试 API  
✅ 自动从源代码提取 API 信息  
✅ 生成可直接使用的 curl 命令  
✅ 生成 Postman Collection  
✅ 支持交互式测试  
✅ 多种工具选择（Python/Shell/Kotlin）  

## 📖 详细文档

查看 `API-TOOLS-SUMMARY.md` 获取完整说明。
