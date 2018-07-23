<#assign page_title="场景",page_type="列表",nosidebar=false>
<#assign config_json>
{
    "needCheckBox":"false",
    "primarykey":"sceneId",
    "params": [
        {"name": "编号", "attrName": "code"},
        {"name": "标题","attrName":"title"},
        {"name": "描述","attrName":"detail",
            "wrapper":{
                "before":"<div class='text-overflow' style='max-width:540px'>",
                "after":"</div>"
            },
            "extras": [
                {"isAttr": "true","type": "self", "name": "title", "attrName": "detail"}
            ]
        },
        {"name": "项目编码","attrName":"projectCode"},
        {"name": "操作","type": "text", "isExtra": "true",
            "content":"<a href='javascript:void(0)' class='edit'>修改</a>"
        }
    ]
}
</#assign>
<#include "../../common/header.ftl"/>
<script>
    function openModal(sceneId){
        clearReminderHTML('form-scene');
        $('#basicSet').modal({
            relatedTarget: this,
            onConfirm: function (e) {
                var checkResult = checkForm('form-scene');
                if(checkResult){
                    var request_param = {"title": $("#sceneTitle").val(), "detail": $("#sceneDetail").val(), "sceneId":sceneId}
                    var url = "/pano/scene/save";
                    doSyncPost(url, request_param, function(data){
                        if(data['suc']){
                            windowLocate(location.href)
                        } else {
                            showReminder('form-scene', data["msg"])
                        }
                    })
                }
            },
            closeOnConfirm: false,
        });
    }
    $(function(){
        clearLocalStorage("scene")
        $("table .edit").on("click",function(){
            var id = $(this).parents("tr").attr("dataid");
            var title = $.trim($(this).parents("tr").find(".title").html());
            var detail = $.trim($(this).parents("tr").find(".detail").text());
            $("#sceneTitle").val(title);
            $("#sceneId").val(id);
            $("#sceneDetail").val(detail);
            openModal(id);
        });
    })
</script>
<form id="form_list" class="am-form">
    <div class="am-g search-bar">
        <div class="am-g am-margin-top">
            <div class="am-u-md-2 am-text-right">
                场景标题
            </div>
            <div class="am-u-md-3">
                <input class="input-plain" name="title" value="${title!}">
            </div>
            <div class="am-u-md-2 am-u-end"><button class="am-btn am-btn-success" type="button" id="search_list">搜索</button></div>
        </div>
    </div>
<#include "../../common/table.ftl"/>
</form>

<#include "../../common/footer.ftl"/>

<div class="am-modal am-modal-prompt" tabindex="-1" id="basicSet">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">场景信息</div>
        <form id="form-scene">
            <input id="sceneId" name="sceneId" type="hidden">
            <span class="reminder"></span>
            <div class="am-modal-bd">
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">场景标题</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end">
                        <input id="sceneTitle" name="title" class="input-middle">
                    </div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">场景描述</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end">
                        <textarea id="sceneDetail" name="detail" class="input-middle"></textarea>
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