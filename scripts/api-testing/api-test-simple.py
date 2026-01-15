#!/usr/bin/env python3
"""
BeautyGirl API 简单测试工具 (无需额外依赖)
使用 Python 标准库的 urllib

使用方法：
    python3 api-test-simple.py
"""

import urllib.request
import urllib.parse
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


def make_request(url: str) -> tuple:
    """
    发送 HTTP GET 请求
    
    Returns:
        (success: bool, status_code: int, data: str, error: str)
    """
    try:
        req = urllib.request.Request(
            url,
            headers={
                'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36'
            }
        )
        
        with urllib.request.urlopen(req, timeout=TIMEOUT) as response:
            status_code = response.getcode()
            data = response.read().decode('utf-8')
            return (True, status_code, data, None)
            
    except urllib.error.HTTPError as e:
        return (False, e.code, None, f"HTTP Error: {e.code} - {e.reason}")
    except urllib.error.URLError as e:
        return (False, 0, None, f"URL Error: {e.reason}")
    except Exception as e:
        return (False, 0, None, f"Exception: {str(e)}")


def test_gank_api(number: int = 10, page: int = 1):
    """测试 Gank API"""
    print_separator("测试 Gank API")
    url = f"{GANK_BASE_URL}data/福利/{number}/{page}"
    print(f"URL: {url}")
    
    success, status_code, data, error = make_request(url)
    
    if success:
        print("✅ 请求成功!")
        print(f"状态码: {status_code}")
        print("\n响应数据:")
        
        try:
            # 格式化 JSON 输出
            json_data = json.loads(data)
            print(json.dumps(json_data, indent=2, ensure_ascii=False))
            
            # 提取关键信息
            if 'results' in json_data:
                print(f"\n📊 数据统计:")
                print(f"  - 返回数量: {len(json_data['results'])}")
                if json_data['results']:
                    first_item = json_data['results'][0]
                    print(f"  - 第一条数据:")
                    print(f"    - ID: {first_item.get('_id', 'N/A')}")
                    print(f"    - URL: {first_item.get('url', 'N/A')}")
                    print(f"    - 描述: {first_item.get('desc', 'N/A')}")
        except json.JSONDecodeError as e:
            print(f"❌ JSON 解析失败: {e}")
            print(f"原始数据: {data[:500]}")
    else:
        print("❌ 请求失败!")
        print(f"错误: {error}")


def test_douban_api(cid: int = 27, page: int = 1):
    """测试豆瓣 API"""
    print_separator("测试豆瓣 API")
    url = f"{DOUBAN_BASE_URL}?cid={cid}&page={page}"
    print(f"URL: {url}")
    
    success, status_code, data, error = make_request(url)
    
    if success:
        print("✅ 请求成功!")
        print(f"状态码: {status_code}")
        print(f"\n响应数据长度: {len(data)} 字符")
        print("\n响应数据 (前 500 字符):")
        print(data[:500])
        print("...")
    else:
        print("❌ 请求失败!")
        print(f"错误: {error}")


def test_youmei_api():
    """测试优美图库 API"""
    print_separator("测试优美图库 API")
    url = YOUMEI_BASE_URL
    print(f"URL: {url}")
    
    success, status_code, data, error = make_request(url)
    
    if success:
        print("✅ 请求成功!")
        print(f"状态码: {status_code}")
        print(f"\n响应数据长度: {len(data)} 字符")
        print("\n响应数据 (前 500 字符):")
        print(data[:500])
        print("...")
    else:
        print("❌ 请求失败!")
        print(f"错误: {error}")


def test_all():
    """测试所有 API"""
    print_separator("BeautyGirl API 测试工具")
    print("使用 Python 标准库 (无需额外依赖)")
    
    test_gank_api(number=10, page=1)
    test_douban_api(cid=27, page=1)
    test_youmei_api()
    
    print_separator("测试完成")


if __name__ == "__main__":
    test_all()
    
    print("\n💡 提示: 可以使用交互模式进行单独测试:")
    print("   python3 -i api-test-simple.py")
    print("   >>> test_gank_api(10, 2)  # 测试第2页")
    print("   >>> test_douban_api(27, 1)  # 测试豆瓣 API")
