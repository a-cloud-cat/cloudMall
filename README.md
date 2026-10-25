# cloudMall

微服务电商后台。后端 Spring Cloud 多模块，前端 Vue 3 独立仓库。

## 技术栈

- Java 17
- Spring Boot 4.0.6
- Spring Cloud 2025.1.0
- MyBatis 3.5.19（手动装配，不用 starter）
- PageHelper 6.1.0（核心包）
- Druid 1.2.25 连接池
- MyBatis Generator 1.4.2 生成 Model / Mapper
- MySQL 5.7

## 模块

| 模块 | 端口 | 作用 |
| --- | --- | --- |
| cloud-common | — | 公共工具：统一返回体、分页、异常处理 |
| cloud-mbg | — | 数据库实体与 Mapper 代码生成 |
| cloud-auth | 8081 | 登录认证，查 ums_admin 表校验密码 |
| cloud-admin | 8082 | 后台业务，目前是品牌 CRUD |
| cloud-gateway | 8080 | 网关，按前缀转发到 auth / admin |

cloud-common 和 cloud-mbg 不启动，被其他模块依赖。

## 网关路由

```
/auth/**  ->  localhost:8081  （StripPrefix=1，去掉 /auth）
/admin/** ->  localhost:8082  （StripPrefix=1，去掉 /admin）
```

前端走网关 8080，例如登录调 `/auth/login`，品牌列表调 `/admin/brand/list`。

## 环境

- JDK 17
- Maven（项目自带 mvnw，也可用本地 Maven）
- MySQL 5.7，库名 `mall`，账号 `root` / `123456`
- 前端仓库在 `d:\A-learn\Blearn\cloud-admin-web`，独立 git 仓库

MySQL 的 `pms_brand` 和 `ums_admin` 表需要已建好。登录管理员由 cloud-auth 启动时自动播种：`admin1` / `123`。

## 一键启动

1. 打开 Docker，让 mysql 容器跑起来
2. 在 cloudMall 根目录执行：

```powershell
.\start-all.ps1
```

一条命令拉起所有服务。脚本会先检查端口（8080 / 8081 / 8082 / 5173），再 `mvnw install` 把 common / mbg 装进本地仓库，最后分别启动 gateway、auth、admin 和前端。三个后端各开一个窗口，前端在当前窗口跑，日志里点 `http://localhost:5173/` 打开。

停止：各窗口 `Ctrl+C`。

## 已有接口

登录：
- `POST /auth/login`，参数 `{ username, password }`，返回 `{ code, message, data: { token } }`。当前 token 是 UUID 占位，网关尚未校验（Sa-Token 待接入）。

品牌（经网关前缀 `/admin`）：
- `GET /admin/brand/list?keyword=&pageNum=1&pageSize=5` 分页查询
- `GET /admin/brand/listAll` 全量
- `GET /admin/brand/{id}` 详情
- `POST /admin/brand/create` 新增
- `POST /admin/brand/update/{id}` 修改
- `GET /admin/brand/delete/{id}` 删除

返回结构统一为 `CommonResult`：`{ code: 200, message: "操作成功", data: ... }`。

## 前端

前端在独立仓库 `cloud-admin-web`，Vue 3 + Vite + Element Plus。Vite 代理把 `/api` 转到 `http://localhost:8080`。目前已做登录页与后台布局。
