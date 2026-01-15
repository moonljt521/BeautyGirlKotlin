# BeautyGirl 项目文档

## 📚 文档目录

### API 测试工具
位置：`docs/api-testing/`

- **[API-TESTING-TOOLS.md](API-TESTING-TOOLS.md)** - 快速开始指南 ⭐
- **[api-testing/API-TOOLS-SUMMARY.md](api-testing/API-TOOLS-SUMMARY.md)** - 工具完整总结
- **[api-testing/API-DEBUG-GUIDE.md](api-testing/API-DEBUG-GUIDE.md)** - 详细调试指南
- **[api-testing/API-TEST-README.md](api-testing/API-TEST-README.md)** - 使用说明

### Apifox MCP 集成 ⭐ 新增
位置：`docs/`

- **[APIFOX-MCP-SETUP.md](APIFOX-MCP-SETUP.md)** - Apifox MCP 配置指南
- **[api-testing/APIFOX-USAGE-EXAMPLES.md](api-testing/APIFOX-USAGE-EXAMPLES.md)** - 使用示例

### 相关脚本
位置：`scripts/`

- **[scripts/setup-apifox-mcp.sh](../scripts/setup-apifox-mcp.sh)** - Apifox MCP 快速配置脚本
- **[scripts/api-testing/README.md](../scripts/api-testing/README.md)** - API 测试脚本说明

## 🚀 快速开始

### Apifox MCP 配置（推荐）⭐

通过 Apifox MCP，你可以直接在聊天窗口中调试 API：

```bash
# 运行配置脚本
bash scripts/setup-apifox-mcp.sh

# 或手动配置
# 1. 编辑 .kiro/settings/mcp.json
# 2. 填入你的 Apifox Access Token
# 3. 重启 Kiro IDE
```

详细说明请查看 [APIFOX-MCP-SETUP.md](APIFOX-MCP-SETUP.md)

### 传统 API 测试

```bash
# 使用交互式菜单
bash scripts/api-testing/quick-api-test.sh

# 或直接查看 API 信息
python3 scripts/api-testing/show-api-info.py
```

详细说明请查看 [API-TESTING-TOOLS.md](API-TESTING-TOOLS.md)

## 📁 项目结构

```
BeautyGirlKotlin/
├── app/                          # Android 应用源码
├── docs/                         # 📚 文档目录
│   ├── README.md                 # 文档索引（本文件）
│   ├── APIFOX-MCP-SETUP.md       # Apifox MCP 配置指南 ⭐
│   ├── API-TESTING-TOOLS.md      # API 测试工具快速指南
│   └── api-testing/              # API 测试相关文档
│       ├── API-TOOLS-SUMMARY.md
│       ├── API-DEBUG-GUIDE.md
│       ├── API-TEST-README.md
│       ├── APIFOX-USAGE-EXAMPLES.md  # Apifox 使用示例 ⭐
│       └── BeautyGirl.postman_collection.json
├── scripts/                      # 🔧 脚本目录
│   ├── setup-apifox-mcp.sh       # Apifox MCP 配置脚本 ⭐
│   └── api-testing/              # API 测试脚本
│       ├── README.md
│       ├── quick-api-test.sh     # 快速启动脚本
│       ├── show-api-info.py      # API 信息提取
│       └── ...
└── README.md                     # 项目主 README
```

## 🔗 相关链接

- [项目主 README](../README.md)
- [Apifox MCP 配置](APIFOX-MCP-SETUP.md) ⭐
- [API 测试工具文档](API-TESTING-TOOLS.md)
- [API 测试脚本](../scripts/api-testing/)

## 📝 添加新文档

当添加新文档时，请：
1. 将文档放在相应的子目录中
2. 更新本 README 的目录
3. 在文档中添加清晰的标题和说明
