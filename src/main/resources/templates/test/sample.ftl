<#assign page_title="功能测试",page_type="",nosidebar=true>
<#include "../common/header.ftl"/>
<script>
    $(function(){
        $("#submitThumbUp").on("click", function(){
            var hotspotCode = $("#form-comment-edit input[name='hotspotCode']").val();
            var thumbUp = $("#form-comment-edit input[name='thumbUp']:checked").val();
            submitThumbUp({"hostspotCode": hotspotCode, "thumbUp": thumbUp}, function(data){
                console.log(data);
                alert("点赞成功！")
            })
        })
    })
</script>
<div class="am-tabs">
    <div class="am-tab-panel am-fade am-in am-active">
        <#-- 添加评论 -->
        <#include "./comment_add.ftl"/>

        <#-- 评论列表 -->
        <#include "./comment_list.ftl"/>

        <#-- 点赞信息 -->
        <#include "./thumbup.ftl"/>

    </div>
    <!--基本信息结束-->
</div>
