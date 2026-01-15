#!/usr/bin/env python3
"""
显示 BeautyGirl APP 中的 API 信息
从源代码中提取 API 定义和 URL
"""

import os
import re
from pathlib import Path

def print_separator(title: str = ""):
    """打印分隔线"""
    if title:
        print(f"\n{'=' * 20} {title} {'=' * 20}")
    else:
        print("=" * 60)


def find_api_files():
    """查找所有 API 相关文件"""
    api_dir = Path("app/src/main/java/com/moon/beautygirlkotlin/common/network/api")
    if api_dir.exists():
        return list(api_dir.glob("*.kt"))
    return []


def extract_api_info(file_path: Path):
    """从 Kotlin 文件中提取 API 信息"""
    print_separator(f"API 文件: {file_path.name}")
    
    with open(file_path, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # 提取接口名
    interface_match = re.search(r'interface\s+(\w+)', content)
    if interface_match:
        print(f"📝 接口名: {interface_match.group(1)}")
    
    # 提取所有方法
    methods = re.findall(
        r'@(GET|POST|PUT|DELETE)\("([^"]+)"\)\s+suspend\s+fun\s+(\w+)\(([^)]*)\)',
        content
    )
    
    if methods:
        print(f"\n🔗 API 方法:")
        for http_method, path, func_name, params in methods:
            print(f"\n  方法: {func_name}")
            print(f"  HTTP: {http_method}")
            print(f"  路径: {path}")
            if params:
                print(f"  参数: {params}")
    
    # 显示完整代码
    print(f"\n📄 完整代码:")
    print("-" * 60)
    print(content)
    print("-" * 60)


def find_base_urls():
    """查找 Base URL 配置"""
    print_separator("Base URL 配置")
    
    retrofit_file = Path("app/src/main/java/com/moon/beautygirlkotlin/common/network/RetrofitHelper.kt")
    
    if retrofit_file.exists():
        with open(retrofit_file, 'r', encoding='utf-8') as f:
            content = f.read()
        
        # 提取所有 URL
        urls = re.findall(r'(BASE_\w+|base\w*[Uu]rl)\s*=\s*"([^"]+)"', content)
        
        if urls:
            print("\n🌐 发现的 Base URLs:")
            for var_name, url in urls:
                print(f"  {var_name}: {url}")
        
        print(f"\n📄 RetrofitHelper.kt 相关代码:")
        print("-" * 60)
        # 只显示 URL 相关的行
        for line in content.split('\n'):
            if 'BASE_' in line or 'baseUrl' in line or 'http' in line:
                print(line)
        print("-" * 60)


def find_data_models():
    """查找数据模型"""
    print_separator("数据模型")
    
    model_dirs = [
        "app/src/main/java/com/moon/beautygirlkotlin/common/data/service/gank/model",
        "app/src/main/java/com/moon/beautygirlkotlin/common/data/service/douban/model",
    ]
    
    for model_dir in model_dirs:
        model_path = Path(model_dir)
        if model_path.exists():
            print(f"\n📁 目录: {model_dir}")
            for kt_file in model_path.glob("*.kt"):
                print(f"  - {kt_file.name}")


def generate_curl_commands():
    """生成 curl 测试命令"""
    print_separator("生成 curl 测试命令")
    
    print("\n📋 可以使用以下 curl 命令测试 API:\n")
    
    print("# Gank API (萌妹子)")
    print('curl "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"')
    print()
    
    print("# 豆瓣 API")
    print('curl "http://www.buxiuse.com/?cid=27&page=1"')
    print()
    
    print("# 优美图库 API")
    print('curl "http://www.umei.cc/bizhitupian/meinvbizhi/"')
    print()
    
    print("# 使用 jq 格式化 JSON 输出:")
    print('curl "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1" | jq')
    print()


def generate_postman_collection():
    """生成 Postman Collection"""
    print_separator("Postman Collection")
    
    collection = {
        "info": {
            "name": "BeautyGirl API",
            "description": "BeautyGirl Android APP API Collection",
            "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
        },
        "item": [
            {
                "name": "Gank API - 获取福利图片",
                "request": {
                    "method": "GET",
                    "header": [],
                    "url": {
                        "raw": "http://gank.io/api/data/福利/10/1",
                        "protocol": "http",
                        "host": ["gank", "io"],
                        "path": ["api", "data", "福利", "10", "1"]
                    }
                }
            },
            {
                "name": "豆瓣 API - 获取图片",
                "request": {
                    "method": "GET",
                    "header": [],
                    "url": {
                        "raw": "http://www.buxiuse.com/?cid=27&page=1",
                        "protocol": "http",
                        "host": ["www", "buxiuse", "com"],
                        "query": [
                            {"key": "cid", "value": "27"},
                            {"key": "page", "value": "1"}
                        ]
                    }
                }
            }
        ]
    }
    
    import json
    collection_json = json.dumps(collection, indent=2, ensure_ascii=False)
    
    print("\n💾 Postman Collection JSON:")
    print("可以将以下内容保存为 BeautyGirl.postman_collection.json")
    print("然后在 Postman 中导入\n")
    print(collection_json)
    
    # 保存到文件
    with open("BeautyGirl.postman_collection.json", "w", encoding="utf-8") as f:
        f.write(collection_json)
    print("\n✅ 已保存到: BeautyGirl.postman_collection.json")


def main():
    print_separator("BeautyGirl API 信息提取工具")
    
    # 查找并显示 Base URLs
    find_base_urls()
    
    # 查找并显示所有 API 文件
    api_files = find_api_files()
    if api_files:
        for api_file in api_files:
            extract_api_info(api_file)
    else:
        print("\n❌ 未找到 API 文件")
    
    # 查找数据模型
    find_data_models()
    
    # 生成测试命令
    generate_curl_commands()
    
    # 生成 Postman Collection
    generate_postman_collection()
    
    print_separator("完成")
    
    print("\n💡 使用建议:")
    print("  1. 使用 curl 命令快速测试 API")
    print("  2. 导入 Postman Collection 到 Postman 进行详细测试")
    print("  3. 查看源代码了解 API 的详细实现")
    print("  4. 如果 API 失效，可能需要更新 URL 或寻找替代 API")


if __name__ == "__main__":
    main()
