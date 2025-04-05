<!DOCTYPE html>
<html>
<head>
    <title>微信授权结果</title>
    <style>
        .auth-info {
            margin: 20px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }
        .info-item {
            margin: 10px 0;
        }
        .label {
            font-weight: bold;
            color: #333;
        }
        .value {
            color: #666;
            word-break: break-all;
        }
        .user-avatar {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            margin: 10px 0;
        }
        .success {
            color: green;
        }
        .error {
            color: red;
        }
    </style>
</head>
<body>
    <div class="auth-info">
        <#if result??>
            <h1 class="${(result.code == 200)?string('success', 'error')}">${result.message!''}</h1>
            
            <#if result.data?? && result.code == 200>
                <#if result.data.headimgurl??>
                    <div class="info-item">
                        <img src="${result.data.headimgurl}" alt="头像" class="user-avatar">
                    </div>
                </#if>
                
                <#if result.data.nickname??>
                    <div class="info-item">
                        <span class="label">用户昵称:</span>
                        <span class="value">${result.data.nickname}</span>
                    </div>
                </#if>
                
                <#if result.data.code??>
                    <div class="info-item">
                        <span class="label">授权码(code):</span>
                        <span class="value">${result.data.code}</span>
                    </div>
                </#if>
                
                <#if result.data.accessToken??>
                    <div class="info-item">
                        <span class="label">访问令牌(access_token):</span>
                        <span class="value">${result.data.accessToken}</span>
                    </div>
                </#if>
                
                <#if result.data.openid??>
                    <div class="info-item">
                        <span class="label">用户标识(openid):</span>
                        <span class="value">${result.data.openid}</span>
                    </div>
                </#if>
            </#if>
        <#else>
            <h1>欢迎使用微信授权</h1>
        </#if>
    </div>
</body>
</html>
