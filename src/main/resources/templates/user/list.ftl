<#assign page_title="用户",page_type="列表",nosidebar=false>
<#assign config_json>
{
    "needCheckBox":"false",
    "primarykey":"userId",
    "params": [
        {"name": "id", "attrName": "userId"},
        {"name": "用户名", "attrName": "username"},
        {"name": "角色","attrName":"role.roleName"},
        {"name": "角色名","attrName":"role.roleTitle"},
        {"name": "项目编码","attrName":"role.projectCode"},
        {"name": "操作"," type": "text", "isExtra": "true",
            "content":"<a href='javascript:void(0)' class='edit'>修改</a> <a href='javascript:void(0)' class='delete'>删除</a> ",
            "extras":[{"isAttr":"true", "name":"dbinfoId","attrName":"id","type":"self"}]}
    ]
}
</#assign>
<#include "../common/header.ftl"/>
<script>
    function openModal(tagId){
        clearReminderHTML('form-jobtag');
        $('#basicSet').modal({
            relatedTarget: this,
            onConfirm: function (e) {
                var checkResult = checkForm('form-jobtag');
                if(checkResult){
                    var request_param = {"tagName": $("#newTagName").val(), "tagId":tagId}
                    var url = "/jobtag/add";
                    doSyncPost(url, request_param, function(data){
                        if(data['suc']){
                            windowLocate(location.href)
                        } else {
                            showReminder('form-jobtag', data["msg"])
                        }
                    })
                }
            },
            closeOnConfirm: false,
        });
    }
    $(function(){
        clearLocalStorage("tag")
        $('#jobtag-add').on("click",function(){
            resetForm("form-jobtag");
            openModal();
        })
        $("table .delete").on("click",function(){
            var id = $(this).parents("tr").attr("dataid");
            doSyncPost("/jobtag/delete", {"tagId":id}, function(data){
                if(data['suc']){
                    windowLocate(location.href)
                } else {
                    alert(data["msg"])
                }
            })
        });
        $("table .edit").on("click",function(){
            var id = $(this).parents("tr").attr("dataid");
            openModal(id);
        });
    })
</script>
<#--<div class="am-cf am-padding list-title"><div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">操作类型</strong> / <small>列表</small></div></div>-->
<form id="form_list" class="am-form">
<div class="am-g search-bar">
    <div class="am-u-md-1"><button class="am-btn am-btn-warning" type="button" id="jobtag-add">添加标签</button></div>
</div>
<#include "../common/table.ftl"/>
</form>

<#include "../common/footer.ftl"/>

<div class="am-modal am-modal-prompt" tabindex="-1" id="basicSet">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">标签信息</div>
        <form id="form-jobtag">
            <span class="reminder"></span>
            <div class="am-modal-bd">
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">标签名称</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end">
                        <input id="newTagName" class="input-middle">
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