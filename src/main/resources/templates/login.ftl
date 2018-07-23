<#assign page_title="登录",page_type="",nosidebar=true>
<!doctype html>
<html class="no-js">
<head>
    <title>${page_title!"全景展示"}<#if page_type??>/${page_type}</#if></title>
<#include "common/head_meta.ftl"/>
</head>
<body class="loginPage-body">
<div>
<script>
    $(function(){
        clearLocalStorage()
        $('#btn-login').on('click', function(){
            if(checkBlank("form-login", $("#username"), "用户名") && checkBlank("form-login", $("#password"), "密码")){
                doPost("/user/auth", $("#form-login").serializeArray(), function(result){
                    if(result['suc']){
                        if(location.search.indexOf("callback")>=0){
                            location.replace(location.search.split("callback=")[1]);
                        }else{
                            windowLocate("/pano/scene/list");
                        }
                    } else{
                        showReminder("form-login", result['msg'])
                    }

                });
            }
        });
        $("body").on("keyup",function(e){
            if(e.keyCode == 13)
            {
                $("#btn-login").trigger('click');
            }
        });
    });
</script>
    <div id="loginPage-title" class="am-u-lg-4 am-u-md-6 am-u-sm-centered">
        <div class="am-text-primary am-text-xxl am-kai">
        全景展示
        </div>
        <div>
            <img src="${project_path}/images/etl-logo.png"/>
        </div>
    </div>
    <div class="am-u-lg-4 am-u-md-6 am-u-sm-centered login">
    <div class="login-right-border"></div>
    <div class="login-bottom-border"></div>
    <div class="login-left">
        <div class="login-header">登录</div>
        <form id="form-login" class="am-form">
            <div style="min-height:1.5em">
                <span class="reminder login-reminder"></span>
            </div>
        <div class="login-input-wrapper" id="username" >
            <span class="am-icon-user login-icon"></span>
            <input type="text" name="username" class="login-input username" placeholder="用户名" value="">
        </div>
        <div class="login-input-wrapper" id="password">
            <span class="am-icon-eye login-icon"></span>
            <input type="password" name="password" class="login-input password" placeholder="密码" value="">
        </div>
        <div class="am-cf">
            <input id="btn-login" type="button" value="登 录" class="am-btn am-btn-primary am-btn-sm btn-login">
        </div>
        </form>
    </div>
    <div class="login-right"></div>
    <div class="triangle-with-shadow"></div>
    </div>

</div>

</body>
</html>