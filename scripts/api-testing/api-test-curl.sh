#!/bin/bash

# BeautyGirl API 测试工具 (使用 curl)
# 使用方法: bash api-test-curl.sh

echo "========================================"
echo "   BeautyGirl API 测试工具 (curl)"
echo "========================================"

# 测试 Gank API
echo ""
echo "==================== 测试 Gank API ===================="
GANK_URL="http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1"
echo "URL: $GANK_URL"
echo ""
curl -s -w "\n状态码: %{http_code}\n" "$GANK_URL" | head -50
echo ""

# 测试豆瓣 API
echo ""
echo "==================== 测试豆瓣 API ===================="
DOUBAN_URL="http://www.buxiuse.com/?cid=27&page=1"
echo "URL: $DOUBAN_URL"
echo ""
curl -s -w "\n状态码: %{http_code}\n" "$DOUBAN_URL" | head -50
echo ""

# 测试优美图库 API
echo ""
echo "==================== 测试优美图库 API ===================="
YOUMEI_URL="http://www.umei.cc/bizhitupian/meinvbizhi/"
echo "URL: $YOUMEI_URL"
echo ""
curl -s -w "\n状态码: %{http_code}\n" --max-time 10 "$YOUMEI_URL" | head -50
echo ""

echo "========================================"
echo "   测试完成!"
echo "========================================"
echo ""
echo "💡 提示:"
echo "  - 使用 jq 格式化 JSON: curl URL | jq"
echo "  - 查看完整响应: curl -v URL"
echo "  - 保存到文件: curl URL > output.json"
