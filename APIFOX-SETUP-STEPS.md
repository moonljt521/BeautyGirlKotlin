# 🚀 Apifox MCP 使用步骤（完整版）

## 📋 前置准备

### 1. 注册 Apifox 账号（如果还没有）

1. 访问 https://apifox.com
2. 点击右上角 "注册"
3. 使用邮箱或手机号注册
4. 验证并登录

### 2. 创建项目（可选）

1. 登录后，点击 "新建项目"
2. 输入项目名称，例如 "BeautyGirl API"
3. 选择项目类型（Web 项目）
4. 点击创建

### 3. 导入现有 API（可选）

如果你想在 Apifox 中管理 BeautyGirl 的 API：

**方式 1: 导入 Postman Collection**
1. 在 Apifox 项目中，点击 "导入"
2. 选择 "Postman"
3. 上传 `docs/api-testing/BeautyGirl.postman_collection.json`
4. 点击导入

**方式 2: 手动添加 API**
1. 点击 "新建接口"
2. 填写接口信息：
   - 名称: 获取 Gank 福利图片
   - 方法: GET
   - URL: http://gank.io/api/data/福利/{number}/{page}
   - 路径参数: number, page
3. 保存

## 🔑 第一步：获取 Access Token

### 详细步骤：

1. **登录 Apifox**
   - 访问 https://apifox.com
   - 使用你的账号登录

2. **进入个人设置**
   - 点击右上角的头像
   - 选择 "个人设置"

3. **生成 Access Token**
   - 在左侧菜单中找到 "Access Token"
   - 点击 "生成新 Token"
   - 输入 Token 名称（例如：Kiro IDE）
   - 选择权限（建议全选）
   - 点击 "生成"

4. **复制 Token**
   - ⚠️ **重要**: Token 只会显示一次！
   - 立即复制并保存到安全的地方
   - 格式类似：`apifox_xxxxxxxxxxxxxxxxxxxxx`

## ⚙️ 第二步：配置 Kiro MCP

### 方式 1: 使用自动配置脚本（推荐）⭐

```bash
# 在项目根目录运行
bash scripts/setup-apifox-mcp.sh
```

**脚本会提示你：**
```
请输入你的 Apifox Access Token: 
```

**粘贴你刚才复制的 Token，按回车**

脚本会自动：
- ✅ 创建配置文件
- ✅ 填入 Token
- ✅ 测试连接
- ✅ 更新 .gitignore

### 方式 2: 手动配置

1. **创建配置文件**
   ```bash
   mkdir -p .kiro/settings
   ```

2. **编辑配置文件**
   ```bash
   # 使用你喜欢的编辑器
   nano .kiro/settings/mcp.json
   # 或
   code .kiro/settings/mcp.json
   ```

3. **填入以下内容**（替换 YOUR_TOKEN_HERE）
   ```json
   {
     "mcpServers": {
       "apifox": {
         "command": "npx",
         "args": ["-y", "apifox-mcp-server"],
         "env": {
           "APIFOX_API_BASE_URL": "https://api.apifox.com",
           "APIFOX_ACCESS_TOKEN": "YOUR_TOKEN_HERE"
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
   ```

4. **保存文件**

## 🔄 第三步：重启 Kiro IDE

**重要**: 必须重启 Kiro 才能加载新的 MCP 配置

1. 保存所有文件
2. 退出 Kiro IDE
3. 重新打开 Kiro IDE
4. 等待 MCP Server 连接（可能需要几秒钟）

## ✅ 第四步：验证配置

### 检查 MCP 连接状态

1. **打开 MCP 面板**
   - 在 Kiro 中按 `Cmd+Shift+P` (Mac) 或 `Ctrl+Shift+P` (Windows)
   - 输入 "MCP"
   - 选择 "Open Kiro MCP UI" 或 "View MCP Servers"

2. **查看 Apifox 状态**
   - 应该看到 "apifox" 服务器
   - 状态应该是 "Connected" 或 "Running"
   - 如果是红色或错误状态，查看下面的故障排查

### 测试基本功能

在 Kiro 聊天窗口中输入：

```
列出我的 Apifox 项目
```

**预期结果：**
- AI 会调用 Apifox API
- 显示你的项目列表
- 如果没有项目，会提示创建

## 🎯 第五步：开始使用

### 基础命令

```
# 1. 列出项目
列出我的 Apifox 项目

# 2. 列出接口（替换为你的项目名）
列出 BeautyGirl API 项目的所有接口

# 3. 查看接口详情
查看 "获取 Gank 福利图片" 接口的详细信息

# 4. 调用接口测试
调用 Gank API，获取 10 条数据
```

### 实际使用示例

**示例 1: 测试单个接口**
```
你: 帮我测试 Gank API 是否正常

AI: 正在调用接口...
GET http://gank.io/api/data/福利/10/1
✅ 接口正常，返回 10 条数据
响应时间: 234ms
```

**示例 2: 批量测试**
```
你: 测试所有 API 接口

AI: 正在测试...
✅ Gank API - 正常
❌ 豆瓣 API - 404 错误
⚠️  优美图库 API - 超时
```

**示例 3: 生成报告**
```
你: 生成 API 测试报告

AI: [生成详细的测试报告]
```

## 🔧 故障排查

### 问题 1: MCP Server 无法连接

**症状**: 聊天窗口提示 "Apifox MCP Server 未连接"

**解决方案**:

1. **检查 Node.js**
   ```bash
   node --version
   # 应该显示版本号，如 v18.x.x
   ```
   如果没有，安装 Node.js: https://nodejs.org/

2. **检查配置文件**
   ```bash
   cat .kiro/settings/mcp.json
   # 确认 Token 已正确填入
   ```

3. **查看 MCP 日志**
   - 在 Kiro 中打开 MCP 面板
   - 查看 apifox 服务器的日志
   - 查找错误信息

4. **手动测试 MCP Server**
   ```bash
   npx -y apifox-mcp-server --version
   ```

### 问题 2: Token 无效

**症状**: 提示 "认证失败" 或 "401 Unauthorized"

**解决方案**:

1. **重新生成 Token**
   - 回到 Apifox 网站
   - 删除旧 Token
   - 生成新 Token
   - 更新配置文件

2. **检查 Token 格式**
   - Token 应该以 `apifox_` 开头
   - 没有多余的空格或换行

### 问题 3: 找不到项目

**症状**: 提示 "没有找到项目"

**解决方案**:

1. **在 Apifox 网站创建项目**
   - 访问 https://apifox.com
   - 创建一个新项目

2. **检查 Token 权限**
   - 确保 Token 有访问项目的权限

### 问题 4: npx 命令失败

**症状**: 提示 "command not found: npx"

**解决方案**:

```bash
# 安装 Node.js（包含 npx）
# macOS
brew install node

# 或访问 https://nodejs.org/ 下载安装
```

## 📚 进阶使用

### 1. 配置多个环境

编辑 `.kiro/settings/mcp.json`:

```json
{
  "mcpServers": {
    "apifox-dev": {
      "command": "npx",
      "args": ["-y", "apifox-mcp-server"],
      "env": {
        "APIFOX_ACCESS_TOKEN": "开发环境_TOKEN"
      }
    },
    "apifox-prod": {
      "command": "npx",
      "args": ["-y", "apifox-mcp-server"],
      "env": {
        "APIFOX_ACCESS_TOKEN": "生产环境_TOKEN"
      }
    }
  }
}
```

### 2. 自定义自动批准

```json
{
  "autoApprove": [
    "list_projects",
    "list_apis",
    "get_api_detail",
    "run_api",
    "create_api",
    "update_api"
  ]
}
```

### 3. 设置项目 ID

如果你只使用一个项目，可以设置默认项目 ID：

```json
{
  "env": {
    "APIFOX_ACCESS_TOKEN": "your_token",
    "APIFOX_PROJECT_ID": "your_project_id"
  }
}
```

## 🎓 学习资源

- **Apifox 官方文档**: https://apifox.com/help/
- **MCP 协议文档**: https://modelcontextprotocol.io
- **本项目文档**:
  - 快速开始: `APIFOX-QUICK-START.md`
  - 详细配置: `docs/APIFOX-MCP-SETUP.md`
  - 使用示例: `docs/api-testing/APIFOX-USAGE-EXAMPLES.md`

## ✨ 使用技巧

1. **清晰描述**: 说明你想做什么，AI 会自动选择合适的接口
2. **批量操作**: 可以一次测试多个接口
3. **保存结果**: 可以要求 AI 保存测试结果
4. **生成代码**: 可以根据 API 响应生成数据模型

## 🎉 完成！

现在你可以在 Kiro 聊天窗口中直接调试 API 了！

**试试这些命令：**
```
列出我的项目
测试 Gank API
生成测试报告
```

---

**需要帮助？** 查看 `docs/APIFOX-MCP-SETUP.md` 获取更多信息
