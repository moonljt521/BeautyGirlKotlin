#!/bin/bash

# Apifox MCP Server 配置脚本

echo "🚀 Apifox MCP Server 配置向导"
echo ""

# 检查 Node.js
echo "📋 检查依赖..."
if ! command -v node &> /dev/null; then
    echo "❌ 未找到 Node.js"
    echo "请先安装 Node.js: https://nodejs.org/"
    exit 1
fi

echo "✅ Node.js 版本: $(node --version)"

if ! command -v npx &> /dev/null; then
    echo "❌ 未找到 npx"
    exit 1
fi

echo "✅ npx 可用"
echo ""

# 获取 Access Token
echo "📝 配置 Apifox Access Token"
echo ""
echo "请按照以下步骤获取 Access Token:"
echo "  1. 访问 https://apifox.com"
echo "  2. 登录你的账号"
echo "  3. 进入 个人设置 → Access Token"
echo "  4. 点击 生成新 Token"
echo "  5. 复制生成的 Token"
echo ""
read -p "请输入你的 Apifox Access Token: " ACCESS_TOKEN

if [ -z "$ACCESS_TOKEN" ]; then
    echo "❌ Access Token 不能为空"
    exit 1
fi

# 创建配置目录
mkdir -p .kiro/settings

# 创建配置文件
cat > .kiro/settings/mcp.json << EOF
{
  "mcpServers": {
    "apifox": {
      "command": "npx",
      "args": [
        "-y",
        "apifox-mcp-server"
      ],
      "env": {
        "APIFOX_API_BASE_URL": "https://api.apifox.com",
        "APIFOX_ACCESS_TOKEN": "$ACCESS_TOKEN"
      },
      "disabled": false,
      "autoApprove": [
        "list_projects",
        "list_apis",
        "get_api_detail",
        "run_api"
      ]
    }
  }
}
EOF

echo ""
echo "✅ 配置文件已创建: .kiro/settings/mcp.json"
echo ""

# 测试连接
echo "🧪 测试 Apifox MCP Server..."
echo ""
echo "正在安装 apifox-mcp-server..."
npx -y apifox-mcp-server --version 2>/dev/null

if [ $? -eq 0 ]; then
    echo "✅ apifox-mcp-server 安装成功"
else
    echo "⚠️  apifox-mcp-server 安装可能失败，但会在首次使用时自动安装"
fi

echo ""
echo "🎉 配置完成！"
echo ""
echo "📚 下一步:"
echo "  1. 重启 Kiro IDE"
echo "  2. 在聊天窗口中输入: '列出我的 Apifox 项目'"
echo "  3. 查看详细文档: docs/APIFOX-MCP-SETUP.md"
echo ""
echo "💡 提示:"
echo "  - 不要将 .kiro/settings/mcp.json 提交到 Git"
echo "  - 已自动添加到 .gitignore"
echo ""

# 添加到 .gitignore
if ! grep -q ".kiro/settings/mcp.json" .gitignore 2>/dev/null; then
    echo "" >> .gitignore
    echo "# MCP Configuration (contains sensitive tokens)" >> .gitignore
    echo ".kiro/settings/mcp.json" >> .gitignore
    echo "✅ 已添加到 .gitignore"
fi
