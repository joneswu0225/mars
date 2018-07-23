<form id="form-thumbup-add">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-panel-title">点赞</div>
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
                <div class="am-u-sm-6 am-u-md-2 am-text-right">用户ID</div>
                <div class="am-u-sm-6 am-u-md-2 am-u-end">
                    <input type="text" name="userId" value="" placeholder="user_id"/>
                </div>
            </div>
            <div class="am-g am-margin-top">
                <div class="am-u-sm-6 am-u-md-2 am-text-right">点赞数</div>
                <div class="am-u-sm-6 am-u-md-1 am-u-end" id="thumbupCount"></div>
            </div>
            <div class="am-g am-margin-top">
                <div class="am-u-md-4 am-u-end">
                    <input class="am-btn am-btn-success" id="addThumbup" type="button" value="点赞"/>
                    <input class="am-btn am-btn-success" id="getThumbupCount" type="button" value="点赞数"/>
                </div>
            </div>
        </div>
    </div>
</form>
<form id="form-thumbup-cancel">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-panel-title">取消点赞</div>
        <div class="am-panel-collapse am-collapse am-in">
            <div class="am-g am-margin-top">
                <div class="am-u-sm-6 am-u-md-2 am-text-right">场景编号</div>
                <div class="am-u-sm-6 am-u-md-1">
                    <input type="text" name="sceneCode" value="" placeholder="scene_code"/>
                </div>
                <div class="am-u-sm-6 am-u-md-1 am-text-right">用户ID</div>
                <div class="am-u-sm-6 am-u-md-2 am-u-end">
                    <input type="text" name="userId" value="" placeholder="user_id"/>
                </div>
            </div>
            <div class="am-g am-margin-top">
                <div class="am-u-md-4 am-u-end">
                    <input class="am-btn am-btn-success" id="cancelThumbup" type="button" value="取消点赞"/>
                </div>
            </div>
        </div>
    </div>
</form>
<script>
    $(function(){
        $("#addThumbup").on("click", function(){
            var params = $("#form-thumbup-add").serializeArray();
            console.log(params)
            addThumbup(params, function(data){
                console.log(data);
                alert("点赞成功！")
            })
        })
        $("#cancelThumbup").on("click", function(){
            var userId = $("#form-thumbup-cancel input[name='userId']").val();
            var sceneCode = $("#form-thumbup-cancel input[name='sceneCode']").val();
            var params = {"userId": userId, "sceneCode": sceneCode};
            console.log(params)
            cancelThumbup(sceneCode, userId, function(data){
                console.log(data);
                alert("取消点赞成功！")
            })
        })
        $("#getThumbupCount").on("click", function(){
            var hotspotCode = $("#form-thumbup-add input[name='hotspotCode']").val();
            var sceneCode = $("#form-thumbup-add input[name='sceneCode']").val();
            var params = {"hotspotCode": hotspotCode, "sceneCode": sceneCode};
            console.log(params)
            getThumbupCount(params, function(data){
                console.log(data)
                $("#thumbupCount").html(data)
            })
        })
    })
</script>