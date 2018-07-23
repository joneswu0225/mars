<#assign page_title="场景",page_type="列表",nosidebar=false>
<#assign config_json>
{
    "needCheckBox":"false",
    "primarykey":"hsScId",
    "params": [
        {"name": "场景","attrName":"sceneCode","type": "map","content":"sceneMap"},
        {"name": "场景编号","attrName":"sceneCode"},
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
            $("#form-hssc")[0].reset();
        }
        clearReminderHTML('form-hssc');
        $('#basicSet').modal({
            relatedTarget: this,
            onConfirm: function (e) {
                var checkResult = checkForm('form-hssc');
                if(checkResult){
                    var request_param = $("#form-hssc").serializeArray();
                    console.log(request_param)
                    var url = "/pano/hssc/save";
                    doSyncPost(url, request_param, function(data){
                        if(data['suc']){
                            windowLocate(location.href)
                        } else {
                            showReminder('form-hssc', data["msg"])
                        }
                    })
                }
            },
            closeOnConfirm: false,
        });
    }
    $(function(){
        initSearchBarSelector("scene", "#search-scene", "${sceneCode!}", 1, {multiple:0, searchBox:1},function(data){
            $("#search-scene").find("select").attr("name", "sceneCode")
        })
        initSearchBarSelector("hotspot", "#search-hotspot", "${hotspotCode!}", 1, {multiple:0, searchBox:1},function(data){
            $("#search-hotspot").find("select").attr("name", "hotspotCode")
        })
        initSearchBarSelector("scene", "#selector-scene", "", 1, {multiple:0, searchBox:1},function(data){
            $("#selector-scene").find("select").attr("name", "sceneCode")
        })
        initSearchBarSelector("hotspot", "#selector-hotspot", "", 1, {multiple:0, searchBox:1},function(data){
            $("#selector-hotspot").find("select").attr("name", "hotspotCode")
        })
        $("#addHssc").on("click", function(){
            openModal();
        })
        $("table .delete").on("click",function(){
            var id = $(this).parents("tr").attr("dataid");
            doPost("/pano/hssc/delete", {"hsScId": id}, function(data){
                if(data['suc']){
                    windowLocate(location.href)
                } else {
                    alert(data["msg"])
                }
            })
        })
        $("table .edit").on("click",function(){
            var id = $(this).parents("tr").attr("dataid");
            doGet("/pano/hssc/" + id,{}, function(data){
                $("#selector-scene select").val(data.sceneCode)
                updateSelected($("#selector-scene select"))
                $("#selector-hotspot select").val(data.hotspotCode)
                updateSelected($("#selector-hotspot select"))
                $("#form-hssc [name='ath']").val(data.ath);
                $("#form-hssc [name='hsScId']").val(data.hsScId);
                $("#form-hssc [name='atv']").val(data.atv);
                openModal(id);
            })
        });
    })
</script>
<form id="form_list" class="am-form">
    <div class="am-g search-bar">
        <div class="am-g am-margin-top">
            <div class="am-u-md-2 am-text-right">
                场景
            </div>
            <div class="am-u-md-3" id="search-scene"></div>
            <div class="am-u-md-2 am-text-right">
                热点
            </div>
            <div class="am-u-md-3" id="search-hotspot"></div>
            <div class="am-u-md-1"><button class="am-btn am-btn-success" type="button" id="search_list">搜索</button></div>
            <div class="am-u-md-1 am-u-end"><button class="am-btn am-btn-warning" type="button" id="addHssc">添加</button></div>
        </div>
    </div>
<#include "../../common/table.ftl"/>
</form>

<#include "../../common/footer.ftl"/>

<div class="am-modal am-modal-prompt" tabindex="-1" id="basicSet">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">热点/场景关联信息</div>
        <form id="form-hssc">
            <span class="reminder"></span>
            <div class="am-modal-bd">
                <input name="hsScId" type="hidden">
                <div class="am-g am-margin-top">
                    <div class="am-u-sm-6 am-u-md-4 am-text-right not-null">场景</div>
                    <div class="am-u-sm-6 am-u-md-7 am-text-left am-u-end" id="selector-scene">
                        <input name="sceneCode" class="input-middle">
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