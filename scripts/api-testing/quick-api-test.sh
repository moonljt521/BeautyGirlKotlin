#!/bin/bash

# BeautyGirl API 快速测试脚本

echo "🚀 BeautyGirl API 快速测试工具"
echo ""
echo "请选择测试方式:"
echo "  1) 查看 API 信息（推荐）"
echo "  2) Python 交互式测试"
echo "  3) 单次测试所有 API"
echo "  4) 使用 curl 测试"
echo "  5) 生成 Postman Collection"
echo ""
read -p "请输入选项 (1-5): " choice

case $choice in
    1)
        echo ""
        echo "📋 提取 API 信息..."
        python3 show-api-info.py
        ;;
    2)
        echo ""
        echo "🐍 启动 Python 交互模式..."
        echo "可用命令:"
        echo "  test_gank_api(10, 1)"
        echo "  test_douban_api(27, 1)"
        echo "  test_youmei_api()"
        echo ""
        python3 -i api-test-simple.py
        ;;
    3)
        echo ""
        echo "🧪 测试所有 API..."
        python3 api-test-simple.py
        ;;
    4)
        echo ""
        echo "📡 使用 curl 测试..."
        echo ""
        echo "=== Gank API ==="
        curl -s "http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1" | head -20
        echo ""
        echo ""
        echo "=== 豆瓣 API ==="
        curl -s "http://www.buxiuse.com/?cid=27&page=1" | head -20
        echo ""
        ;;
    5)
        echo ""
        echo "📦 生成 Postman Collection..."
        python3 show-api-info.py > /dev/null 2>&1
        if [ -f "BeautyGirl.postman_collection.json" ]; then
            echo "✅ 已生成: BeautyGirl.postman_collection.json"
            echo ""
            echo "导入步骤:"
            echo "  1. 打开 Postman"
            echo "  2. 点击 Import"
            echo "  3. 选择 BeautyGirl.postman_collection.json"
        else
            echo "❌ 生成失败"
        fi
        ;;
    *)
        echo "❌ 无效选项"
        exit 1
        ;;
esac

echo ""
echo "✨ 完成!"
