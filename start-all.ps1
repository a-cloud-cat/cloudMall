# 一键拉起 cloud-gateway(8080)、cloud-auth(8081)、cloud-admin(8082) 与前端(5173)。
# 用法：.\start-all.ps1（需先让 Docker 里的 mysql 跑起来）
$ErrorActionPreference = 'Stop'
$root = $PSScriptRoot
$web = Join-Path $root '..\cloud-admin-web'

# 避免重复拉起
foreach ($port in 8080, 8081, 8082, 5173) {
    if (Get-NetTCPConnection -LocalPort $port -State Listen -ErrorAction SilentlyContinue) {
        Write-Host "端口 $port 已被占用，服务可能已在运行，先关闭再执行。" -ForegroundColor Red
        exit 1
    }
}

if (-not (Test-Path (Join-Path $web 'package.json'))) {
    Write-Host "未找到前端目录：$web" -ForegroundColor Red
    exit 1
}

# 先 install：保证 .m2 里的 cloud-mbg 为最新，并提前暴露编译错误
Write-Host '==> 构建并安装后端模块到本地仓库（首次较慢）...' -ForegroundColor Cyan
Push-Location $root
.\mvnw.cmd -DskipTests install -pl cloud-gateway,cloud-auth,cloud-admin -am
$buildOk = $LASTEXITCODE -eq 0
Pop-Location
if (-not $buildOk) {
    Write-Host '后端构建失败，已中止，未启动任何服务。' -ForegroundColor Red
    exit 1
}

# 各服务开独立窗口，便于分别查看日志
$gatewayCmd = '$Host.UI.RawUI.WindowTitle=''cloud-gateway-8080''; .\mvnw.cmd -pl cloud-gateway spring-boot:run'
$authCmd = '$Host.UI.RawUI.WindowTitle=''cloud-auth-8081''; .\mvnw.cmd -pl cloud-auth spring-boot:run'
$adminCmd = '$Host.UI.RawUI.WindowTitle=''cloud-admin-8082''; .\mvnw.cmd -pl cloud-admin spring-boot:run'
Start-Process powershell -WorkingDirectory $root -ArgumentList @('-NoExit', '-Command', $gatewayCmd)
Start-Process powershell -WorkingDirectory $root -ArgumentList @('-NoExit', '-Command', $authCmd)
Start-Process powershell -WorkingDirectory $root -ArgumentList @('-NoExit', '-Command', $adminCmd)

Write-Host ''
Write-Host 'cloud-gateway 已在独立窗口启动：http://localhost:8080  （首次约 30 秒）' -ForegroundColor Green
Write-Host 'cloud-auth    已在独立窗口启动：http://localhost:8081  （首次约 30 秒）' -ForegroundColor Green
Write-Host 'cloud-admin   已在独立窗口启动：http://localhost:8082  （首次约 30 秒）' -ForegroundColor Green
Write-Host '停止后端：到对应窗口按 Ctrl+C；停止前端：本窗口按 Ctrl+C。' -ForegroundColor DarkGray
Write-Host ''
Write-Host '前端地址（Ctrl + 点击打开）： http://localhost:5173/' -ForegroundColor Yellow
Write-Host ''

# 前端在当前窗口前台运行
Set-Location $web
npm run dev
