<form id="form-comment-list">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-panel-title">评论列表</div>
        <div class="am-panel-collapse am-collapse am-in">
            <div class="am-g am-margin-top">
                <div class="am-u-sm-6 am-u-md-2 am-text-right">场景编号</div>
                <div class="am-u-sm-6 am-u-md-3">
                    <input type="text" name="sceneCode" value="" placeholder="scene_code"/>
                </div>
                <#--<div class="am-u-sm-6 am-u-md-1 am-text-right">热点编号</div>-->
                <#--<div class="am-u-sm-6 am-u-md-2">-->
                    <#--<input type="text" name="hotspotCode" value="" placeholder="hotspot_code"/>-->
                <#--</div>-->
                <div class="am-u-sm-6 am-u-md-2 am-text-right">分页信息</div>
                <div class="am-u-sm-6 am-u-md-1">
                    <input type="text" name="pageNum" value="1" placeholder="页码"/>
                </div>
                <div class="am-u-sm-6 am-u-md-2 am-u-end">
                    <input type="text" name="pageSize" value="10" placeholder="每页大小"/>
                </div>
            </div>
            <div class="am-g am-margin-top">
                <div class="am-u-md-4 am-u-end">
                    <input class="am-btn am-btn-success" id="getComments" type="button" value="获取"/>
                </div>
            </div>
        </div>
    </div>
</form>
<script>
    $(function(){
        $("#getComments").on("click", function(){
            var sceneCode = $("#form-comment-list input[name='sceneCode']").val();
            var page = $("#form-comment-list input[name='pageNum']").val();
            var size = $("#form-comment-list input[name='pageSize']").val();
            getHotspotList({"sceneCode": sceneCode, "page": page, "size": size}, function(data){
                console.log(data);
                windowOpen("/pano/hotspotList?sceneCode=" + sceneCode + "&page=" + page + "&size=" + size);
            })
        })
    })
</script>