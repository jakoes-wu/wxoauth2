# 微信网页授权项目 (WxOAuth2)

这是一个基于Spring Boot的微信网页授权示例项目，实现了完整的微信OAuth2.0授权流程，可用于微信公众号网页授权登录。

## 功能特点

- 微信网页授权登录
- 支持静默授权(snsapi_base)和用户信息授权(snsapi_userinfo)两种模式
- 获取用户基本信息(openid)
- 获取用户详细信息(昵称、头像等)
- 展示授权信息和用户信息

## 技术栈

- Spring Boot 2.7.x
- FreeMarker 模板引擎
- HttpClient
- FastJSON
- JDK 1.8

## 系统要求

- JDK 1.8+
- Maven 3.6+
- 微信公众平台测试/服务号

## 构建与运行

### 1. 克隆代码

```bash
git clone https://your-repository-url/wxoauth2.git
cd wxoauth2
```

### 2. 配置参数

修改 `src/main/resources/application.properties` 文件：

```properties
# 微信公众号配置
oauth.wx.appid=你的公众号APPID
oauth.wx.appsecret=你的公众号APPSECRET
oauth.callback=你的回调地址
oauth.user.confirm=1 # 1:获取用户信息 0:静默授权
```

### 3. 构建项目

```bash
# 使用Maven构建
./mvnw clean package -DskipTests

# 或者使用自定义Maven配置
./mvnw -s /path/to/your/settings.xml clean package -DskipTests
```

### 4. 运行项目

```bash
java -jar target/wxoauth2-0.0.1-SNAPSHOT.jar
```

默认访问地址: http://localhost:8080

## 配置说明

| 配置项 | 说明 |
| ----- | ----- |
| oauth.wx.appid | 微信公众号的AppID |
| oauth.wx.appsecret | 微信公众号的AppSecret |
| oauth.callback | 授权成功后的回调地址，需要与微信公众平台配置一致 |
| oauth.user.confirm | 授权模式：0-静默授权，1-用户信息授权 |
| server.port | 服务器端口，默认8080 |

## 使用方法

1. 确保微信公众号已配置授权回调域名（微信公众平台->设置->公众号设置->功能设置->网页授权域名）
2. 访问微信登录接口: `/wxlogin`
3. 用户在微信中完成授权
4. 系统获取授权码(code)并自动换取access_token
5. 根据配置决定是否获取用户信息
6. 授权结果展示在回调页面

## 授权流程

1. 用户访问 `/wxlogin` 接口
2. 系统重定向到微信授权页面
3. 用户在微信中确认授权
4. 微信重定向回 `/wxcallback` 并附带授权码(code)
5. 系统使用code换取access_token
6. 根据授权模式，可选地获取用户详细信息
7. 展示授权结果

## 提示

- 本地开发时，需要使用内网穿透工具(如ngrok, natapp等)将本地服务器暴露到公网
- 确保回调地址与微信公众平台配置的授权回调域名一致
- 授权回调域名不支持IP地址，必须使用域名 