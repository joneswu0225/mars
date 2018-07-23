<#assign page_title="场景",page_type="列表",nosidebar=false>
<#assign config_json>
{
    "needCheckBox":"false",
    "primarykey":"hsHsId",
    "params": [
        {"name": "父热点","attrName":"pHotspotCode","type": "map","content":"hotspotMap"},
        {"name": "父热点编号","attrName":"pHotspotCode"},
        {"name": "热点","attrName":"hotspotCode","type": "map","content":"hotspotMap"},
        {"name": "热点编号","attrName":"hotspotCode"},
        {"name": "ath","attrName":"ath"},
        {"name": "atv","attrName":"atv"}
        {"name": "操作","type": "text", "isExtra": "true",
            "content":"<a href='javascript:void(0)' class='edit'>修改</a> <a href='javascript:void(0)' class='delete'>删除</a>"
        }
    ]
}
</#assign>
<#include "../../common/header.ftl"/>
<script>
    function openModal(id){
        if(!id){
            $("#form-hshs")[0].reset();
        }
        clearReminderHTML('form-hshs');
        $('#basicSet').modal({
            relatedTarget: this,
            onConfirm: function (e) {
                var checkResult = checkForm('form-hshs');
                if(checkResult){
                    var request_param = $("#form-hshs").serializeArray();
                    console.log(request_param)
                    var url = "/pano/hshs/save";
                    doSyncPost(url, request_param, function(data){
                        if(data['suc']){
                            windowLocate(location.href)
                        } else {
                            showReminder('form-hshs', data["msg"])
                        }
                    })
                }
            },
            closeOnConfirm: false,
        });
    }
    $(function(){
        initSearchBarSelector("hotspot", "#search-photspot", "${pHotspotCode!}", 1, {multiple:0, searchBox:1},function(data){
            $("#search-photspot").find("select").attr("name", "pHotspotCode")
        })
        initSearchBarSelector("hotspot", "#search-hotspot", "${hotspotCode!}", 1, {multiple:0, searchBox:1},function(data){
            $("#search-hotspot").find("select").attr("name", "hotspotCode")
        })
        initSearchBarSelector("hotspot", "#selector-photspot", "", 1, {multiple:0, searchBox:1},function(data){
            $("#selector-photspot").find("select").attr("name", "pHotspotCode")
        })
        initSearchBarSelector("hotspot", "#selector-hotspot", "", 1, {multiple:0, searchBox:1},function(data){
            $("#selector-hotspot").find("select").attr("name", "hotspotCode")
        })
        $("#addHssc").on("click", function(){
            openModal();
        })
        $("table .delete").on("click",function(){
            var id = $(this).parents("tr").attr("dataid");
            doPost("/pano/hshs/delete", {"hsHsId": id}, function(data){
                if(data['suc']){
                    windowLocate(location.href)
                } else {
                    alert(data["msg"])
                }
            })
        })
        $("table .edit").on("click",function(){
            var id = $(this).parents("tr").attr("dataid");
            doGet("/pano/hshs/" + id,{}, function(data){
                $("#selector-photspot select").val(data.pHotspotCode)
                updateSelected($("#selector-photspot select"))
                $("#selector-hotspot select").val(data.hotspotCode)
                updateSelected($("#selector-hotspot select"))
                $("#form-hshs [name='hsHsId']").val(data.hsHsId);
                $("#form-hshs [name='ath']").val(data.ath);
                $("#form-hshs [name='scScId']").val(data.scScId);
                $("#form-hshs [name='atv']").val(data.atv);
                openModal(id);
            })
        });
    })
</script>
<form id="form_list" class="am-form">
    <div class="am-g search-bar">
        <div class="am-g am-margin-top">
            <div class="am-u-md-2 am-text-right">
                父热点
            </div>
            <div class="am-u-md-3" id="search-photspot"></div>
            <div class="am-u-md-2 am-text-right">
                热点
            </div>
            <div class="am-u-md-3" id="search-hotspot"></div>
            <div class="am-u-md-1"><button class="am-btn am-btn-success" type="button" id="search_list">搜索</button></div>
            <#--<div class="am-u-md-1 am-u-end"><button class="am-btn am-btn-warning" type="button" id="addHssc">添加</button></div>-->
        </div>
    </div>
<#include "../../common/table.ftl"/>
</form>

<#include "../../common/footer.ftl"/>

<div class="am-modal am-modal-prompt" tabindex="-1" id="basicSet">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">热点间关联信息</div>
        <form id="form-hshs">
            <input name="hsHsId" type="hidden">
            <span class="reminder"></span>
            <div class="am-modal-bd">
                <input name="hsHsId" type="hidden">
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">父热点</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end" id="selector-photspot">
                        <input name="pHotspotCode" class="input-middle">
                    </div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">热点</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end" id="selector-hotspot">
                        <input name="hotspotCode" class="input-middle">
                    </div>
                </div>
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">坐标</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end">
                        <input name="ath" class="input-sm" placeholder="ath">
                        <input name="atv" class="input-sm" placeholder="atv">
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