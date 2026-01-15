#!/usr/bin/env python3
"""
BeautyGirl API 测试工具
用于在不运行 APP 的情况下测试网络请求

使用方法：
    python3 api-test.py

或者使用交互模式：
    python3 -i api-test.py
    >>> test_gank_api(10, 1)
    >>> test_douban_api(27, 1)
"""

import requests
import json
from typing import Optional

# API URLs
GANK_BASE_URL = "http://gank.io/api/"
DOUBAN_BASE_URL = "http://www.buxiuse.com/"
YOUMEI_BASE_URL = "http://www.umei.cc/bizhitupian/meinvbizhi/"

# 设置超时时间
TIMEOUT = 30


def print_separator(title: str = ""):
    """打印分隔线"""
    if title:
        print(f"\n{'=' * 20} {title} {'=' * 20}")
    else:
        print("=" * 60)


def test_gank_api(number: int = 10, page: int = 1):
    """
    测试 Gank API
    
    Args:
        number: 每页数量
        page: 页码
    """
    print_separator("测试 Gank API")
    url = f"{GANK_BASE_URL}data/福利/{number}/{page}"
    print(f"URL: {url}")
    
    try:
        response = requests.get(url, timeout=TIMEOUT)
        
        if response.status_code == 200:
            print("✅ 请求成功!")
            print(f"状态码: {response.status_code}")
            print("\n响应数据:")
            
            # 格式化 JSON 输出
            data = response.json()
            print(json.dumps(data, indent=2, ensure_ascii=False))
            
            # 提取关键信息
            if 'results' in data:
                print(f"\n📊 数据统计:")
                print(f"  - 返回数量: {len(data['results'])}")
                if data['results']:
                    first_item = data['results'][0]
                    print(f"  - 第一条数据:")
                    print(f"    - ID: {first_item.get('_id', 'N/A')}")
                    print(f"    - URL: {first_item.get('url', 'N/A')}")
                    print(f"    - 描述: {first_item.get('desc', 'N/A')}")
        else:
            print("❌ 请求失败!")
            print(f"状态码: {response.status_code}")
            print(f"错误信息: {response.text[:200]}")
            
    except requests.exceptions.Timeout:
        print("❌ 请求超时!")
    except requests.exceptions.RequestException as e:
        print(f"❌ 请求异常: {e}")
    except json.JSONDecodeError as e:
        print(f"❌ JSON 解析失败: {e}")


def test_douban_api(cid: int = 27, page: int = 1):
    """
    测试豆瓣 API
    
    Args:
        cid: 分类 ID
        page: 页码
    """
    print_separator("测试豆瓣 API")
    url = f"{DOUBAN_BASE_URL}?cid={cid}&page={page}"
    print(f"URL: {url}")
    
    try:
        response = requests.get(url, timeout=TIMEOUT)
        
        if response.status_code == 200:
            print("✅ 请求成功!")
            print(f"状态码: {response.status_code}")
            print(f"Content-Type: {response.headers.get('Content-Type', 'N/A')}")
            print(f"\n响应数据长度: {len(response.text)} 字符")
            print("\n响应数据 (前 500 字符):")
            print(response.text[:500])
            print("...")
        else:
            print("❌ 请求失败!")
            print(f"状态码: {response.status_code}")
            print(f"错误信息: {response.text[:200]}")
            
    except requests.exceptions.Timeout:
        print("❌ 请求超时!")
    except requests.exceptions.RequestException as e:
        print(f"❌ 请求异常: {e}")


def test_youmei_api():
    """测试优美图库 API"""
    print_separator("测试优美图库 API")
    url = YOUMEI_BASE_URL
    print(f"URL: {url}")
    
    try:
        response = requests.get(url, timeout=TIMEOUT)
        
        if response.status_code == 200:
            print("✅ 请求成功!")
            print(f"状态码: {response.status_code}")
            print(f"Content-Type: {response.headers.get('Content-Type', 'N/A')}")
            print(f"\n响应数据长度: {len(response.text)} 字符")
            print("\n响应数据 (前 500 字符):")
            print(response.text[:500])
            print("...")
        else:
            print("❌ 请求失败!")
            print(f"状态码: {response.status_code}")
            print(f"错误信息: {response.text[:200]}")
            
    except requests.exceptions.Timeout:
        print("❌ 请求超时!")
    except requests.exceptions.RequestException as e:
        print(f"❌ 请求异常: {e}")


def test_all():
    """测试所有 API"""
    print_separator("BeautyGirl API 测试工具")
    
    test_gank_api(number=10, page=1)
    test_douban_api(cid=27, page=1)
    test_youmei_api()
    
    print_separator("测试完成")


if __name__ == "__main__":
    # 检查是否安装了 requests
    try:
        import requests
    except ImportError:
        print("❌ 请先安装 requests 库:")
        print("   pip3 install requests")
        exit(1)
    
    # 运行所有测试
    test_all()
    
    print("\n💡 提示: 可以使用交互模式进行单独测试:")
    print("   python3 -i api-test.py")
    print("   >>> test_gank_api(10, 2)  # 测试第2页")
    print("   >>> test_douban_api(27, 1)  # 测试豆瓣 API")
