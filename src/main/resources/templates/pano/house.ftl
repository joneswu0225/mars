<#assign page_title="铂悦滨江",page_type="",nosidebar=true>
<#include "../common/header.ftl"/>
<#--<script src='skin/tourloading.js'></script>-->
<#--<script data-main="js/init" src="js/lib/require.js"></script>-->
<script src="${project_path}/js/tour.js"></script>
<script src="${project_path}/js/stomp.js"></script>
<script src="${project_path}/js/sockjs.min.js"></script>
<div id="pano" class="pano" style="width:100%;">
    <noscript><table style="width:100%;height:100%"><tr style="vertical-align:middle;text-align:center;"><td>提示:<br><br>浏览器禁用了JavaScript功能<br>解决：工具-〉internet选项-〉安全 选中“internet” 在点击“默认级别”<br></td></tr></table></noscript>
</div>
<div><input type="button" id="auto_pause" value="暂停漫游"/><input type="button" id="auto_resume" value="继续漫游"/></div>
<div><input type="button" id="control" value="启动控制"/><input type="button" id="becontrolled" value="申请被控制"/><input type="button" id="releasecontroll" value="释放控制"/></div>
<script>
    function addToCart(){
        alert("添加成功！")
    }
    function addToOrder(){
        window.open("/order/cart")
    }
    function selectmobilescaleusage(){
        var ratio = document.body.scrollHeight/ 900;
        return ratio > 1 ? 1 : ratio
    }
    $("#pano").css("height", (document.body.scrollHeight - 65) + "px");
    embedpano({
        id:"tourSWFObject",
        xml:"${project_path}/pano/tour.xml",
        target:"pano",
        consolelog : true,
        html5:"only",
        fakeDevice:"mobile",
        passQueryParameters:true,
        mobilescale: selectmobilescaleusage(),
        onerror:embedpanoerror,
        localfallback:"flash",
        onready: krpanoReady
    });
    function krpanoReady(pano) {
        krpano = pano;
        krpano.call("trace(krpano is ready...)");
    }
    function embedpanoerror(){
        deleteWaitInfoDiv();
        document.write("<div class='centerdiv'><div class='divError'><h1>该浏览器功能有限</h1><h2>请使用其它浏览器观看</h2></div></div>");
    }
    var krpano = null;

    $("#auto_pause").on("click", function(){
        var krpano = document.getElementById("tourSWFObject");
        krpano.call("autorotate.pause()")
    })

    $("#auto_resume").on("click", function(){
        var krpano = document.getElementById("tourSWFObject");
        krpano.call("autorotate.resume()")
    })

    $("#control").on("click", function(){
        syncScreen.initServer("tourSWFObject", function(){
            setInterval('syncScreen.syncMsg("tourSWFObject")', 50);
        });
    })

    $("#becontrolled").on("click", function(){
        syncScreen.initClient("tourSWFObject")
    })

    $("#releasecontroll").on("click", function(){
        syncScreen.disconnect();
    })
</script>
<#include "../common/footer.ftl"/>
