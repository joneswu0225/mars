<#assign project_path="">
<!DOCTYPE html>
<html>
<head>
    <title>BS_CMSA_VR_Training</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, viewport-fit=cover" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta http-equiv="x-ua-compatible" content="IE=edge" />
    <script src='${project_path}/pano/skin/tourloading.js'></script>
    <script data-main="${project_path}/pano/js/init" src="${project_path}/pano/js/lib/require.js"></script>
    <script src='${project_path}/pano/js/lib/jquery.js'></script>
    <script src='${project_path}/pano/js/readscene.js'></script>
    <script src='${project_path}/pano/js/hsoutline.js'></script>
    <script src='${project_path}/pano/js/hsgallery.js'></script>
    <script src='${project_path}/pano/js/addhs.js'></script>
    <script src='${project_path}/pano/js/addcomment.js'></script>
    <script src='${project_path}/pano/js/app_share_screen.js'></script>
    <script src='${project_path}/pano/js/acc_share_screen.js'></script>
    <script src='${project_path}/pano/js/release_share_screen.js'></script>
    <script src='${project_path}/pano/js/selecthslist.js'></script>
    <script src='${project_path}/pano/js/selectscenelist.js'></script>
    <script src='${project_path}/pano/js/hsscene.js'></script>
    <script src="${project_path}/js/stomp.js"></script>
    <script src="${project_path}/js/sockjs.min.js"></script>
    <script src="${project_path}/pano/tour.js"></script>
    <script>
        var project_path = "${project_path}";
    </script>
    <script src="${project_path}/js/webetl.js"></script>
    <script src="${project_path}/js/pano.js"></script>
</head>
<body>
<div id="pano" class="pano" style="width:100%;">
    <noscript><table style="width:100%;height:100%"><tr style="vertical-align:middle;text-align:center;"><td>提示:<br><br>浏览器禁用了JavaScript功能<br>解决：工具-〉internet选项-〉安全 选中“internet” 在点击“默认级别”<br></td></tr></table></noscript>
    <script>
        embedpano({
            swf:"tour.swf",
            id:"tourSWFObject",
            xml:"${project_path}/pano/tour.xml",
            target:"pano",
            html5:"prefer",
            passQueryParameters:true,
            onerror:embedpanoerror,
            localfallback:"flash"
        });
        var hs_list = {};
        var scene_list = {};
        getAllScenes(function(data){scene_list = data;})
        getAllHotspots(function(data){hs_list = data;})
        function embedpanoerror()
        {
            deleteWaitInfoDiv();
            document.write("<div class='centerdiv'><div class='divError'><h1>该浏览器功能有限</h1><h2>请使用其它浏览器观看</h2></div></div>");
        }
    </script>
</div>
</body>
</html>
