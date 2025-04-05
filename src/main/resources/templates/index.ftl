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
    </style>
</head>
<body>
    <div class="auth-info">
        <h1>${message!''}</h1>
        
        <#if headimgurl??>
            <div class="info-item">
                <img src="${headimgurl}" alt="头像" class="user-avatar">
            </div>
        </#if>
        
        <#if nickname??>
            <div class="info-item">
                <span class="label">用户昵称:</span>
                <span class="value">${nickname}</span>
            </div>
        </#if>
        
        <#if code??>
            <div class="info-item">
                <span class="label">授权码(code):</span>
                <span class="value">${code}</span>
            </div>
        </#if>
        
        <#if accessToken??>
            <div class="info-item">
                <span class="label">访问令牌(access_token):</span>
                <span class="value">${accessToken}</span>
            </div>
        </#if>
        
        <#if openid??>
            <div class="info-item">
                <span class="label">用户标识(openid):</span>
                <span class="value">${openid}</span>
            </div>
        </#if>
    </div>
</body>
</html>
