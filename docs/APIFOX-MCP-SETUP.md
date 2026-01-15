# Apifox MCP Server 配置指南

## 📋 概述

通过配置 Apifox MCP Server，你可以直接在 Kiro 聊天窗口中：
- ✅ 列出所有 API 接口
- ✅ 查看接口详情
- ✅ 自动调用接口进行调试
- ✅ 无需离开编辑器

## 🚀 快速配置

### 步骤 1: 获取 Apifox Access Token

1. 访问 [Apifox](https://apifox.com)
2. 登录你的账号
3. 进入 **个人设置** → **Access Token**
4. 点击 **生成新 Token**
5. 复制生成的 Token

### 步骤 2: 配置 MCP Server

编辑 `.kiro/settings/mcp.json` 文件，填入你的 Access Token：

```json
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
        "APIFOX_ACCESS_TOKEN": "你的_ACCESS_TOKEN_在这里"
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

### 步骤 3: 重启 Kiro

配置完成后，重启 Kiro IDE 使配置生效。

## 💡 使用方法

### 基本命令

配置完成后，你可以在聊天窗口中直接使用：

```
# 列出所有项目
列出我的 Apifox 项目

# 列出项目中的所有 API
列出项目 [项目ID] 的所有接口

# 查看接口详情
查看接口 [接口ID] 的详细信息

# 调用接口
调用接口 [接口ID]，参数是 {...}

# 测试 Gank API
帮我测试 Gank API 的福利接口
```

### 实际使用示例

**示例 1: 列出所有接口**
```
你: 列出 BeautyGirl 项目的所有 API 接口

AI: 正在查询...
找到以下接口：
1. GET /api/data/福利/{number}/{page} - 获取 Gank 福利图片
2. GET /?cid={cid}&page={page} - 获取豆瓣图片
3. ...
```

**示例 2: 调用接口测试**
```
你: 调用 Gank API 获取 10 条数据

AI: 正在调用接口...
请求: GET http://gank.io/api/data/福利/10/1
响应: 
{
  "error": false,
  "results": [...]
}
```

**示例 3: 批量测试**
```
你: 测试所有 API 接口是否正常

AI: 正在测试...
✅ Gank API - 正常
❌ 豆瓣 API - 404 错误
✅ 优美图库 API - 正常
```

## 🔧 高级配置

### 配置多个环境

```json
{
  "mcpServers": {
    "apifox-dev": {
      "command": "npx",
      "args": ["-y", "apifox-mcp-server"],
      "env": {
        "APIFOX_API_BASE_URL": "https://api.apifox.com",
        "APIFOX_ACCESS_TOKEN": "开发环境_TOKEN",
        "APIFOX_PROJECT_ID": "开发项目ID"
      },
      "disabled": false
    },
    "apifox-prod": {
      "command": "npx",
      "args": ["-y", "apifox-mcp-server"],
      "env": {
        "APIFOX_API_BASE_URL": "https://api.apifox.com",
        "APIFOX_ACCESS_TOKEN": "生产环境_TOKEN",
        "APIFOX_PROJECT_ID": "生产项目ID"
      },
      "disabled": false
    }
  }
}
```

### 自定义自动批准的操作

```json
{
  "autoApprove": [
    "list_projects",      // 列出项目
    "list_apis",          // 列出接口
    "get_api_detail",     // 获取接口详情
    "run_api",            // 运行接口
    "get_api_schema",     // 获取接口 Schema
    "list_environments"   // 列出环境
  ]
}
```

## 📚 可用的 MCP 工具

Apifox MCP Server 提供以下工具：

| 工具名称 | 说明 | 示例 |
|---------|------|------|
| `list_projects` | 列出所有项目 | "列出我的项目" |
| `list_apis` | 列出项目的所有 API | "列出项目的接口" |
| `get_api_detail` | 获取接口详情 | "查看接口详情" |
| `run_api` | 运行接口测试 | "调用这个接口" |
| `get_api_schema` | 获取接口 Schema | "查看接口定义" |
| `list_environments` | 列出环境配置 | "列出所有环境" |

## 🎯 实际应用场景

### 场景 1: 快速验证 API

```
你: 帮我验证 Gank API 是否还能用

AI: 正在测试...
调用 GET http://gank.io/api/data/福利/10/1
✅ 接口正常，返回 10 条数据
```

### 场景 2: 对比不同参数

```
你: 分别测试 number=10 和 number=20 的返回结果

AI: 正在测试...
number=10: 返回 10 条数据，耗时 234ms
number=20: 返回 20 条数据，耗时 456ms
```

### 场景 3: 批量测试

```
你: 测试第 1-5 页的数据是否都能正常返回

AI: 正在批量测试...
第 1 页: ✅ 正常
第 2 页: ✅ 正常
第 3 页: ✅ 正常
第 4 页: ✅ 正常
第 5 页: ✅ 正常
```

### 场景 4: 自动生成测试报告

```
你: 生成所有 API 的测试报告

AI: 正在生成报告...
[生成详细的测试报告，包括响应时间、成功率等]
```

## 🔍 故障排查

### 问题 1: MCP Server 无法启动

**症状**: 聊天窗口提示 "Apifox MCP Server 未连接"

**解决方案**:
1. 检查 Node.js 是否已安装: `node --version`
2. 检查 npx 是否可用: `npx --version`
3. 手动安装: `npm install -g apifox-mcp-server`
4. 查看 MCP 日志: Kiro → View → MCP Servers

### 问题 2: Access Token 无效

**症状**: 提示 "认证失败" 或 "Token 无效"

**解决方案**:
1. 重新生成 Access Token
2. 确保 Token 没有过期
3. 检查 Token 是否正确复制（没有多余空格）

### 问题 3: 找不到项目或接口

**症状**: 提示 "项目不存在" 或 "接口未找到"

**解决方案**:
1. 确认项目 ID 是否正确
2. 检查 Token 是否有访问该项目的权限
3. 在 Apifox 网页端确认项目和接口存在

## 📝 配置文件位置

```
.kiro/settings/mcp.json
```

## 🔗 相关链接

- [Apifox 官网](https://apifox.com)
- [Apifox MCP Server 文档](https://github.com/apifox/apifox-mcp-server)
- [MCP 协议文档](https://modelcontextprotocol.io)

## 💡 提示

1. **安全性**: 不要将包含 Access Token 的配置文件提交到 Git
2. **性能**: 使用 `autoApprove` 可以减少手动确认，提高效率
3. **调试**: 如果遇到问题，可以先禁用 MCP Server (`"disabled": true`)
4. **更新**: 定期更新 apifox-mcp-server: `npm update -g apifox-mcp-server`

## 🎊 下一步

配置完成后，你可以：
1. 在聊天窗口中尝试 "列出我的 Apifox 项目"
2. 导入现有的 API 到 Apifox
3. 使用 AI 自动化测试所有接口
4. 生成 API 文档和测试报告

---

**配置完成后，重启 Kiro IDE 即可使用！** 🚀
