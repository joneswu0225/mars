<script>
    $(function(){
        /*$("#myreviewcount").on("click", function(e){
            e.stopPropagation()
            windowLocate("/etljob/myReviews")
        })
        doGet("/etljob/myReviewCount",{},function(data){
            $("#myreviewcount").html(data);
        })*/
        $("#admin-offcanvas a[href]").on("click",function(){
            windowLocate($(this).attr("link"));
        })
        $("#sidebar_fade").on("click",function(){
            $("#admin-offcanvas").toggle("normal");
        })
    })
//    function autoHeight(){
//        var ifr = document.getElementById('page_content');
//        ifr.height = ( ifr.contentDocument && ifr.contentDocument.body.offsetHeight ) ||
//                ( ifr.contentWindow.document.body.scrollHeight );
//    }
</script>
<!-- sidebar start -->
<div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
    <div class="am-offcanvas-bar admin-offcanvas-bar">
        <ul class="am-list admin-sidebar-list">
            <li class="admin-parent">
                <a href="javascript:void(0);" link="/etljob/list" class="am-cf"><span class="am-icon-pencil-square-o"></span> Krpano <span class="am-fr am-margin-right"></span></a>
                <ul class="am-list am-collapse admin-sidebar-sub am-in" id="collapse-etljob">
                    <li><a href="javascript:void(0);" link="/pano/scene/list" class="am-cf"><span class="am-icon-check"></span> 场景 </a></li>
                    <li><a href="javascript:void(0);" link="/pano/hotspot/list" class="am-cf"><span class="am-icon-check"></span> 热点 </a></li>
                    <li><a href="javascript:void(0);" link="/pano/hssc/list" class="am-cf"><span class="am-icon-check"></span> 场景/热点 </a></li>
                    <li><a href="javascript:void(0);" link="/pano/hshs/list" class="am-cf"><span class="am-icon-check"></span> 热点/热点 </a></li>
                    <li><a href="javascript:void(0);" link="/question/list" class="am-cf"><span class="am-icon-check"></span> 问题 </a></li>
                </ul>
            </li>
            <#--<li class="admin-parent">
                <a href="javascript:void(0);" link="/jobtag/list" class="am-cf"><span class="am-icon-pencil-square-o"></span> 标签设置 <span class="am-fr am-margin-right"></span></a>
            </li>
            <li class="admin-parent">
                <a href="javascript:void(0);" link="/cron/list" class="am-cf"><span class="am-icon-pencil-square-o"></span> cron配置 <span class="am-fr am-margin-right"></span></a>
            </li>-->
            <#--<#if Session["cur_user"]?? && Session["cur_user"].role=="MASTER">-->
            <#--<li class="admin-parent">
                <a href="javascript:void(0);" link="/dbinfo/list" class="am-cf"><span class="am-icon-pencil-square-o"></span> 数据库配置 <span class="am-fr am-margin-right"></span></a>
            </li>-->
            <#--<#if user_role != 'DEVELOPER'>-->
            <#--<li class="admin-parent">-->
                <#--<a href="javascript:void(0);" link="/user/list" class="am-cf"><span class="am-icon-pencil-square-o"></span> 环境对应设置 <span class="am-fr am-margin-right"></span></a>-->
            <#--</li>-->
            <#--</#if>-->
            <#--<#if user_role != 'DEVELOPER'>
            <li class="admin-parent">
                <a href="javascript:void(0);" link="/user/list" class="am-cf"><span class="am-icon-pencil-square-o"></span> 用户管理 <span class="am-fr am-margin-right"></span></a>
            </li>
            </#if>-->
            <#--</#if>-->
            <#--<li class="admin-parent"><a href="#"><span class="am-icon-sign-out"></span> 注销</a></li>-->
        </ul>
    </div>
</div>
<a href="javascript:void(0);" class="admin-menu" id="sidebar_fade">
    <span class="am-icon-btn am-icon-th-list"></span>
</a>
<!-- sidebar end -->