<form id="form-comment-edit">
<div class="am-panel am-panel-default">
    <div class="am-panel-hd am-panel-title">发表评论</div>
    <div class="am-panel-collapse am-collapse am-in">
        <div class="am-g am-margin-top">
            <div class="am-u-sm-6 am-u-md-2 am-text-right">热点编号</div>
            <div class="am-u-sm-6 am-u-md-3">
                <input type="text" name="code" value="" placeholder="为空则系统自动生成"/>
            </div>
            <div class="am-u-sm-6 am-u-md-2 am-text-right">热点类型</div>
            <div class="am-u-sm-6 am-u-md-3 am-u-end">
                <input type="text" name="catalogName" value="comment" placeholder="catalogName"/>
            </div>
        </div>
        <div class="am-g am-margin-top">
            <div class="am-u-sm-6 am-u-md-2 am-text-right">场景编号</div>
            <div class="am-u-sm-6 am-u-md-3">
                <input type="text" name="sceneCode" value="" placeholder="scene_code"/>
            </div>
            <div class="am-u-sm-6 am-u-md-2 am-text-right">样式名称</div>
            <div class="am-u-sm-6 am-u-md-3 am-u-end">
                <input type="text" name="styleName" value="" placeholder="style_name"/>
            </div>
        </div>
        <div class="am-g am-margin-top">
            <div class="am-u-sm-6 am-u-md-2 am-text-right">标题</div>
            <div class="am-u-sm-6 am-u-md-3">
                <input type="text" name="title" value="" placeholder="title"/>
            </div>
            <div class="am-u-sm-6 am-u-md-2 am-text-right">位置信息</div>
            <div class="am-u-sm-6 am-u-md-3 am-u-end">
                <input type="text" name="ath" value="" placeholder="ath"/>
                <input type="text" name="atv" value="" placeholder="atv"/>
            </div>
        </div>
        <div class="am-g am-margin-top">
            <div class="am-u-sm-6 am-u-md-2 am-text-right">评论内容</div>
            <div class="am-u-sm-12 am-u-md-10"><textarea name="content" class="textarea-default" placeholder="content"></textarea></div>
        </div>
        <div class="am-g am-margin-top">
            <div class="am-u-md-4 am-u-end">
                <input class="am-btn am-btn-success" id="submitComment" type="button" value="提交"/>
            </div>
        </div>
    </div>
</div>
</form>
<script>
    $(function(){
        $("#submitComment").on("click", function(){
            var hotspotCode = $("#form-comment-edit input[name='hotspotCode']").val();
            var thumbUp = $("#form-comment-edit input[name='thumbUp']").val();
            var content = $("#form-comment-edit textarea[name='content']").val();
            var data = $("#form-comment-edit").serializeArray()
            console.log(data)
            addHotspot(data, function(data){
                console.log(data);
                alert("添加评论成功！")
            })
        })
    })
</script>