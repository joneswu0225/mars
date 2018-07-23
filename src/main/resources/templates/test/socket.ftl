<#assign page_title="铂悦滨江",page_type="",nosidebar=true>
<#include "../common/header.ftl"/>
<#--<script src='skin/tourloading.js'></script>-->
<#--<script data-main="js/init" src="js/lib/require.js"></script>-->
<script src="${project_path}/js/tour.js"></script>
<script src="${project_path}/js/stomp.js"></script>
<script src="${project_path}/js/sockjs.min.js"></script>
<div>返回编码：<span id="code"></span></div>
<div>控制编码：<input id="controlcode"></div>
<div><input id="message" value="hello"></div>
<div><input type="button" id="sendMsg" value="发送"/></div>
<div><input type="button" id="control" value="启动控制"/><input type="button" id="becontrolled" value="申请被控制"/><input type="button" id="releasecontroll" value="释放控制"/></div>
<script>
    $("#control").on("click", function() {
        syncScreen.initServer("tourSWFObject", function (code) {
            $("#code").html(code);
        });
    })

    $("#sendMsg").on("click", function(){
        syncScreen.syncCommonMsg($("#message").val());
    })

    $("#becontrolled").on("click", function(){
        var code = $("#controlcode").val()
        syncScreen.initClient(code, "tourSWFObject")
    })

    $("#releasecontroll").on("click", function(){
        syncScreen.disconnect();
    })
</script>
<#include "../common/footer.ftl"/>
