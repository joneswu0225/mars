<#assign page_title="场景",page_type="列表",nosidebar=false>
<#assign config_json>
{
    "needCheckBox":"false",
    "primarykey":"hotspotId",
    "params": [
        {"name": "编号", "attrName": "code"},
        {"name": "类型","attrName":"catalogName","type": "map","content":"catalogMap"},
        {"name": "样式","attrName":"styleName","type": "map","content":"styleMap"},
        {"name": "标题","attrName":"title"},
        {"name": "内容","attrName":"content",
            "wrapper":{
                "before":"<div class='text-overflow' style='max-width:540px'>",
                "after":"</div>"
            },
            "extras": [
                {"isAttr": "true","type": "self", "name": "title", "attrName": "content"}
            ]
        },
        {"name": "操作","type": "text", "isExtra": "true",
            "content":"<a href='javascript:void(0)' class='edit'>修改</a> <a href='javascript:void(0)' class='delete'>删除</a>"
        }
    ]
}
</#assign>
<#include "../../common/header.ftl"/>
<script>
    function openModal(hotspotId){
        if(!hotspotId){
            $("#form-hotspot")[0].reset();
        }
        clearReminderHTML('form-hotspot');
        $('#basicSet').modal({
            relatedTarget: this,
            onConfirm: function (e) {
                var checkResult = checkForm('form-hotspot');
                if(checkResult){
                    var request_param = $("#form-hotspot").serializeArray();
                    var url = "/pano/hotspot/save";
                    doSyncPost(url, request_param, function(data){
                        if(data['suc']){
                            windowLocate(location.href)
                        } else {
                            showReminder('form-hotspot', data["msg"])
                        }
                    })
                }
            },
            closeOnConfirm: false,
        });
    }
    $(function(){
        initSearchBarSelector("style", "#selector-style", "", 1, {multiple:0, searchBox:1},function(data){
            $("#selector-style").find("select").attr("name", "styleName")
        })
        initSearchBarSelector("catalog", "#selector-catalog", "", 1, {multiple:0, searchBox:0},function(data){
            $("#selector-catalog").find("select").attr("name", "catalogName")
        })
        $("#addHotspot").on("click", function(){
            openModal();
        })
        $("table .delete").on("click",function(){
            var id = $(this).parents("tr").attr("dataid");
            doPost("/pano/hotspot/delete", {"hotspotId": id}, function(data){
                if(data['suc']){
                    windowLocate(location.href)
                } else {
                    alert(data["msg"])
                }
            })
        })
        $("table .edit").on("click",function(){
            var id = $(this).parents("tr").attr("dataid");
            doGet("/pano/hotspot/" + id,{}, function(data){
                $("#form-hotspot [name='hotspotId']").val(data.hotspotId);
                $("#form-hotspot [name='code']").val(data.code);
                $("#selector-catalog select").val(data.catalogName)
                updateSelected($("#selector-catalog select"))
                $("#selector-style select").val(data.styleName)
                updateSelected($("#selector-style select"))
//                $("#form-hotspot [name='styleName']").val(data.styleName);
                $("#form-hotspot [name='title']").val(data.title);
                $("#form-hotspot [name='content']").val(data.content);
                $("#form-hotspot [name='onclick']").val(data.onclick);
                $("#form-hotspot [name='onload']").val(data.onload);
                openModal(id);
            })
        });
    })
</script>
<form id="form_list" class="am-form">
    <div class="am-g search-bar">
        <div class="am-g am-margin-top">
            <div class="am-u-md-2 am-text-right">
                标题
            </div>
            <div class="am-u-md-3">
                <input class="input-plain" name="title" value="${title!}">
            </div>
            <div class="am-u-md-1"><button class="am-btn am-btn-success" type="button" id="search_list">搜索</button></div>
            <div class="am-u-md-2 am-u-end"><button class="am-btn am-btn-warning" type="button" id="addHotspot">添加</button></div>
        </div>
    </div>
<#include "../../common/table.ftl"/>
</form>

<#include "../../common/footer.ftl"/>

<div class="am-modal am-modal-prompt" tabindex="-1" id="basicSet">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">场景信息</div>
        <form id="form-hotspot">
            <span class="reminder"></span>
            <div class="am-modal-bd">
                <div class="am-g am-margin-top">
                    <input name="hotspotId" type="hidden">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">编码</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end">
                        <input id="code" name="code" class="input-middle">
                    </div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">分类</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end" id="selector-catalog">
                        <input id="catalogName" name="catalogName" class="input-middle">
                    </div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">样式</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end" id="selector-style">
                        <input id="styleName" name="styleName" class="input-middle">
                    </div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">标题</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end">
                        <input id="hotspotTitle" name="title" class="input-middle">
                    </div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">描述</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end">
                        <textarea id="hotspotContent" name="content" class="input-middle"></textarea>
                    </div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">点击事件</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end">
                        <textarea id="onclick" name="onclick" class="input-middle"></textarea>
                    </div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">加载事件</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end">
                        <textarea id="onload" name="onload" class="input-middle"></textarea>
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