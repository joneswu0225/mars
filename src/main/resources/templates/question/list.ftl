<#assign page_title="用户",page_type="列表",nosidebar=false>
<#assign config_json>
{
    "needCheckBox":"false",
    "primarykey":"questionId",
    "params": [
        {"name": "id", "attrName": "questionId"},
        {"name": "热点","attrName":"hotspotCode","type": "map","content":"hotspotMap"},
        {"name": "热点编号","attrName":"hotspotCode"},
        {"name": "图片路径","attrName":"imgUrl"},
        {"name": "问题内容","attrName":"content"},
        {"name": "操作"," type": "text", "isExtra": "true",
            "content":"<a href='javascript:void(0)' class='edit'>修改</a> <a href='javascript:void(0)' class='delete'>删除</a> ",
            "extras":[{"isAttr":"true", "name":"questionId","attrName":"id","type":"self"}]}
    ]
}
</#assign>
<#include "../common/header.ftl"/>
<script>
    function openModal(id){
        if(!id){
            $("#form-question")[0].reset();
        }
        clearReminderHTML('form-question');
        $('#basicSet').modal({
            relatedTarget: this,
            onConfirm: function (e) {
                var checkResult = checkForm('form-question');
                if(checkResult){
                    var request_param = $("#form-question").serializeArray();
                    console.log(request_param)
                    var url = "/question/save";
                    doSyncPost(url, request_param, function(data){
                        if(data['suc']){
                            windowLocate(location.href)
                        } else {
                            showReminder('form-question', data["msg"])
                        }
                    })
                }
            },
            closeOnConfirm: false,
        });
    }
    $(function(){
        initSearchBarSelector("innerHotspot", "#selector-innerHotspot", "${sceneCode!}", 1, {multiple:0, searchBox:1},function(data){
            $("#selector-innerHotspot").find("select").attr("name", "hotspotCode")
        })
        clearLocalStorage("tag")
        $('#jobtag-add').on("click",function(){
            resetForm("form-jobtag");
            openModal();
        })
        $("table .delete").on("click",function(){
            var id = $(this).parents("tr").attr("dataid");
            doSyncPost("/question/delete", {"questionId":id}, function(data){
                if(data['suc']){
                    windowLocate(location.href)
                } else {
                    alert(data["msg"])
                }
            })
        });
        $("table .edit").on("click",function(){
            var id = $(this).parents("tr").attr("dataid");
            doGet("/question/" + id,{}, function(data){
                $("#selector-innerHotspot select").val(data.hotspotCode)
                updateSelected($("#selector-innerHotspot select"))
                $("#form-question [name='imgUrl']").val(data.imgUrl);
                $("#form-question [name='questionId']").val(data.questionId);
                $("#form-question [name='content']").val(data.content);
                openModal(id);
            })
        });
    })
</script>
<#--<div class="am-cf am-padding list-title"><div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">操作类型</strong> / <small>列表</small></div></div>-->
<form id="form_list" class="am-form">
<div class="am-g search-bar">
    <#--<div class="am-u-md-1"><button class="am-btn am-btn-warning" type="button" id="jobtag-add">添加标签</button></div>-->
</div>
<#include "../common/table.ftl"/>
</form>

<#include "../common/footer.ftl"/>

<div class="am-modal am-modal-prompt" tabindex="-1" id="basicSet">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">问题信息</div>
        <form id="form-question">
            <span class="reminder"></span>
            <div class="am-modal-bd">
                <input name="questionId" type="hidden">
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">热点</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end" id="selector-innerHotspot"></div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">图片路径</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end">
                        <input name="imgUrl" class="input-middle">
                    </div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">问题内容</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end">
                        <textarea name="content" class="input-middle"></textarea>
                    </div>
                </div>
            </div>
            <div class="am-modal-footer">
                <span class="am-modal-btn" data-am-modal-confirm>确定</span>
                <span class="am-modal-btn" data-am-modal-cancel>取消</span>
            </div>
        </form>
    </div>
</div>