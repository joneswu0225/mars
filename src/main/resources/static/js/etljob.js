var var_map = {}, init_flag=false, table_info = {}, init_data = {};
/**
 * 加载目标数据库表
 * @param dbId_dest
 * @param defaultValue
 */
function refreshDestTableSelector(dbId_dest, defaultValue){
    doGet("/etljob/dbs/" + dbId_dest + "/tables", {}, function (data) {
        data = eval("(" + data + ")");
        var selector = $("#dest-table-selector");
        var dbType = $("#dest-db-selector option:selected").attr("dbtype")
        selector.html("");
        for(var index in data){
            selector.append("<option value='"+data[index]+"'>" + data[index] + "</option>")
        }
        selector.attr("dbid", dbId_dest);
        $("#table-selector").html(selector);
        initSelector("#table-selector",defaultValue,{needblank:1, searchBox:1});
        $("#dest-table-selector").on("change",function(){
            refreshDestTableInfos($(this).attr("dbid"), $(this).val(), dbType)
        });
        if(init_flag){
            refreshDestTableInfos(init_data['destDbId'], init_data['destTableName'], dbType)
        }else{
            $("#dest-table-selector").trigger("change")
        }
    })
}

/**
 * 加载目标数据库表信息
 * @param dbId_dest
 * @param tableName
 */
function refreshDestTableInfos(dbId_dest, tableName, dbType){
    doGet("/etljob/dbs/" + dbId_dest + "/tables/" + tableName, {}, function (data) {
        table_info = eval("(" + data + ")");
        initDestTableInfos(table_info);
    })
}

function initDestTableInfos(data){
    $("#table-dest-structure").html("<table class='am-table am-table-bordered am-table-striped am-table-hover am-text-xs'><tr><th>名称</th><th>类型</th><th>允许null</th><th>是否主键</th></tr></table>");
    $("#table-dest-uniquekeys").html("<table class='am-table am-table-bordered am-table-striped am-table-hover'><tr class='am-text-xs'><th>业务主键名称</th><th>主键字段</th><th>选择</th></tr></table>");
    $("#etljob-frames .dest-table-columns select option").remove();
    if(data){
        var columns = data['columns'];
        for(var i in columns){
            $("#table-dest-structure table").append("<tr class='am-text-xs'><td>" + columns[i]['name'] + "</td><td>" + columns[i]['type'] + "</td><td>" + columns[i]['nullable'] + "</td><td>" + columns[i]['pK'] + "</td></tr>" );
            var optStr = "<option value='" + columns[i]['name'] + "'>" + columns[i]['name'] + "</option>";
            $("#etljob-frames .dest-table-columns select").append(optStr);
        }
        var uniquekeys = data['bizKeys'];
        for(var keyname in uniquekeys){
            $("#table-dest-uniquekeys table").append("<tr class='am-text-xs'><td>" + keyname + "</td><td>" + uniquekeys[keyname].join(", ") + "</td><td><input name='destDb.uniqueKeyName' value='" + keyname + "' type='radio'/></tr>")
        }
    }
    if($("#table-dest-uniquekeys table :radio").size() > 0){
        $("#table-dest-uniquekeys table :radio").on("change", function(){
            $(":radio[name='joinInfo.joinMode'][value='LEFT_PK']").removeAttr("disabled");
            $(":radio[name='joinInfo.joinMode'][value=" + init_data['joinMode'] +"]").prop("checked",true).trigger("change");
            $(":radio[name='joinInfo.compareMode'][value=" + init_data['compareMode'] + "]").prop("checked",true).trigger("change")
        })
    } else {
        $(":radio[name='joinInfo.joinMode'][value='LEFT_PK']").attr("disabled", true);
        $(":radio[name='joinInfo.joinMode'][value='LEFT_FIELD']").prop("checked",true).trigger("change");
        $(":radio[name='joinInfo.compareMode'][value=" + init_data['compareMode'] + "]").prop("checked",true).trigger("change");
        $("#table-dest-uniquekeys table").append("<tr class='am-text-xs'><td align='center' colspan='3'>无业务主键</td></tr>")
    }
    $("#table-dest-uniquekeys table :radio:last").prop("checked","true").trigger("change");
    initUpsertPannel();
}

/**
 * 动态生成表格行（源/目标对应字段）
 * @param $table
 * @param source_col
 * @param target_col
 */
function appendCompareModeTableRow($table, source_col, target_col, dbTypeSrc, dbTypeDest){
    $table.append("<tr><td class='cmp-source-col'></td><td class='cmp-target-col'></td><td class='td-normal'><a class='row-delete'>删除</a></td></tr>");
    var source_col_lower = source_col ? source_col.toLowerCase() : target_col.toLowerCase();
    var found_source_match = false;
    if(dbTypeSrc == "ES"){
        source_col = source_col ? source_col : target_col ;
        $table.find("tr:last td.cmp-source-col").append("<div class='source-table-columns'><input type='text' value='"+ source_col +"'/></div>");
    } else {
        $table.find("tr:last td.cmp-source-col").append($("#etljob-frames").find(".source-table-columns").clone(true));
        $table.find("tr:last td.cmp-source-col select option").each(function () {
            if ($(this).val().toLowerCase() == source_col_lower) {
                $(this).parent("select").val($(this).val());
                found_source_match = true;
            }
        });
        if (!found_source_match) {
            $table.find("tr:last td.join-source-col select").val("");
        }
    }

    if(dbTypeDest.toLowerCase() == "mongodb"){
        target_col = target_col ? target_col : source_col;
        $table.find("tr:last td.cmp-target-col").append("<div class='dest-table-columns'><input type='text' value='"+ target_col +"'/></div>");
    } else {
        $table.find("tr:last td.cmp-target-col").append($("#etljob-frames .dest-table-columns").clone(true));
        $table.find("tr:last td.cmp-target-col select").val(target_col.toUpperCase());
        if(!$table.find("tr:last td.cmp-target-col select").val()){
            $table.find("tr:last td.cmp-target-col select").val(target_col.toLowerCase());
        }
    }

    $(".row-delete").on("click", function(){
        $(this).parents("tr").remove()
    })
}

/**
 * 动态生成表格行（源/目标对应字段）
 * @param $table
 * @param source_col
 * @param target_col
 */
function appendJoinTableRow($table, source_col, target_col, dbTypeSrc, dbTypeDest){
    $table.append("<tr><td class='join-source-col'></td><td class='join-target-col'></td><td class='td-normal'><a class='row-delete'>删除</a></td></tr>");
    var source_col_lower = source_col ? source_col.toLowerCase() : target_col.toLowerCase();
    var found_source_match = false;
    if(dbTypeSrc == "ES"){
        source_col = source_col ? source_col : target_col ;
        $table.find("tr:last td.join-source-col").append("<div class='source-table-columns'><input type='text' value='"+ source_col +"'/></div>");
    } else {
        $table.find("tr:last td.join-source-col").append($("#etljob-frames").find(".source-table-columns").clone(true));
        $table.find("tr:last td.join-source-col select option").each(function () {
            if ($(this).val().toLowerCase() == source_col_lower) {
                $(this).parent("select").val($(this).val());
                found_source_match = true;
            }
        });
        if (!found_source_match) {
            $table.find("tr:last td.join-source-col select").val("");
        }
    }

    if(dbTypeDest.toLowerCase() == "mongodb"){
        target_col = target_col ? target_col : source_col;
        $table.find("tr:last td.join-target-col").append("<div class='dest-table-columns'><input type='text' value='"+ target_col +"'/></div>");
    } else {
        $table.find("tr:last td.join-target-col").append($("#etljob-frames").find(".dest-table-columns").clone(true)
            .on("change", function () {
                markUnnecessaryUpdates()
            }));
        $table.find("tr:last td.join-target-col select").val(target_col.toUpperCase());
        if(!$table.find("tr:last td.join-target-col select").val()){
            $table.find("tr:last td.join-target-col select").val(target_col.toLowerCase());
        }
    }
    $(".row-delete").on("click", function () {
            $(this).parents("tr").remove();
            markUnnecessaryUpdates()
        });
}

function getDefaultValForUpsertRow(source_table_name, target_col) {
    var columns = table_info['columns'];
    var value = source_table_name + "." + target_col;
    for (var index in columns) {
        if (columns[index]['name'] == target_col) {
            if ("defaultVal" in columns[index] && columns[index]['defaultVal'] != undefined) {
                value = columns[index]['defaultVal'];
                for (var k in var_map) {
                    if (value.includes(k)) {
                        value = value.replace(k, var_map[k]);
                    }
                }
            }

            break;
        }
    }
    return value;
}

function appendUpsertTableRow($table, target_col, source_col){
    var source_table_name = $("form input[name='tmpTableName']").val();
    $table.append("<tr><td class='upsert-target-col'></td><td class='upsert-source-col'></td><td class='td-normal'><a class='row-delete'>删除</a></td></tr>");
    var dbType = $("#dest-db-selector option:selected").attr("dbtype")
    if(dbType == "MONGODB") {
        $table.find("tr:last td.upsert-target-col").append("<div class='dest-table-columns'><input class='upsert-row' value='" + target_col + "'/></div>");
        markUnnecessaryUpdates();
    } else {
        var cloned_dest_cols = $("#etljob-frames").find(".dest-table-columns").clone(true);
        cloned_dest_cols.find("select").on("change", function () {
            var v = $(this).val();
            var input_field = $(this).parents("tr").first().find("input.upsert-row");
            var value = getDefaultValForUpsertRow(source_table_name, v);
            input_field.val(value);
            markUnnecessaryUpdates();
        });
        $table.find("tr:last td.upsert-target-col").append(cloned_dest_cols);
    }
    $table.find("tr:last td.upsert-source-col").append("<input class='upsert-row'/>");
    if (source_col || init_flag) {
        $table.find("tr:last td.upsert-source-col input").val(source_col)
    }
    else {
        var value = getDefaultValForUpsertRow(source_table_name, target_col);
        $table.find("tr:last td.upsert-source-col input").val(value);
    }
    $table.find("tr:last td.upsert-target-col select").val(target_col.toUpperCase());
    if(!$table.find("tr:last td.upsert-target-col select").val()){
        $table.find("tr:last td.upsert-target-col select").val(target_col.toLowerCase());
    }
    $table.find("tr:last td.upsert-target-col").append('<div class="am-text-warning hide"><i class="am-icon-warning"></i>这个字段是用来作表join的，确定要更新？</div>');
    $(".row-delete").on("click", function(){
        $(this).parents("tr").remove()
    })
}

/**
 * 加载连接方式
 * @param joinMode
 */
function initJoinModePannel(joinMode){
    var dbTypeDest = $("#dest-db-selector option:selected").attr("dbtype")
    var dbTypeSrc = $("#source-db-selector option:selected").attr("dbtype")
    var panel = $("#pannel-joinMode");
    panel.html("<table class='am-table am-table-bordered am-table-striped am-table-hover'><tr><th>" + $("form input[name='tmpTableName']").val() + "</th><th>" + $("#dest-table-selector").val() + "</th><th><a class='row-add'>添加</a> | <a class='row-clear'>清空</a></th></tr></table>");
    panel.find(".row-add").on("click", function(){
        appendJoinTableRow($(this).parents("table"), "", "", dbTypeSrc, dbTypeDest)
    });
    panel.find(".row-clear").on("click", function(){
        panel.find("table tr:gt(0)").remove();
        appendJoinTableRow($(this).parents("table"), "", "", dbTypeSrc, dbTypeDest)
    });
    if(init_flag){
        var init_join_map = init_data['joinMap'];
        for (var key in init_join_map){
            appendJoinTableRow(panel.find("table"), key, init_join_map[key], dbTypeSrc, dbTypeDest)
        }
    } else {
        if(joinMode == 'LEFT_PK'){
            var uniquekeyName = $("#table-dest-uniquekeys").find(":radio:checked").parents("tr").children("td:first").html();
            var uniqueKeys = table_info['uniqueKeys'][uniquekeyName];
            for(var index in uniqueKeys){
                appendJoinTableRow(panel.find("table"), "", uniqueKeys[index], dbTypeSrc, dbTypeDest)
            }
        } else if(joinMode == 'LEFT_FIELD'){
            if(dbTypeDest == "MONGODB"){
                $("#table-source-result").find("th").each(function(i, data){
                    appendJoinTableRow(panel.find("table"), data.innerText, data.innerText, dbTypeSrc, dbTypeDest)
                })
            } else {
                if(table_info){
                    var columns = table_info['columns'];
                    for(var index in columns){
                        if (columns[index]['candFor']['leftJoin']) {
                            appendJoinTableRow(panel.find("table"), "", columns[index]['name'], dbTypeSrc, dbTypeDest)
                        }
                    }
                }
            }
        }
    }
}

/**
 * 加载数据变动判断
 * @param compareMode
 */
function initCompareModePannel(compareMode){
    var dbTypeDest = $("#dest-db-selector option:selected").attr("dbtype")
    var dbTypeSrc = $("#source-db-selector option:selected").attr("dbtype")
    $("#pannel-compareMode").html("");
    if(compareMode == 'FIELD'){
        $("#pannel-compareMode").append("<table class='am-table am-table-bordered am-table-striped am-table-hover'><tr><th>" + $("form input[name='tmpTableName']").val() + "</th><th>" + $("#dest-table-selector").val() + "</th><th><a class='row-add'>添加</a></th></tr></table>");
        $("#pannel-compareMode .row-add").on("click", function(){
            appendCompareModeTableRow($(this).parents("table"), "", "", dbTypeSrc, dbTypeDest)
        });
        if(init_flag){
            var init_field_map = init_data['fieldMap'];
            for (var key in init_field_map){
                appendCompareModeTableRow($("#pannel-compareMode table"), key, init_field_map[key], dbTypeSrc, dbTypeDest)
            }
        }else{
            if(dbTypeDest == "MONGODB"){
                $("#table-source-result").find("th").each(function(i, data){
                    appendCompareModeTableRow($("#pannel-compareMode table"), data.innerText, data.innerText, dbTypeSrc, dbTypeDest)
                })
            } else {
                var columns = table_info['columns'];
                for (var index in columns) {
                    if (columns[index]['candFor']['fieldComp']) {
                        appendCompareModeTableRow($("#pannel-compareMode table"), "", columns[index]['name'], dbTypeSrc, dbTypeDest)
                    }
                }
            }
        }
    } else if(compareMode == 'CRC'){
        $("#pannel-compareMode").append("<div class='crc-source-pannel'><div><span class='crc-pannel-title'>源数据 [" + $("form input[name='tmpTableName']").val() + "] CRC 计算列 （注意顺序）</span></div></div>");
        $("#pannel-compareMode").append("<div class='crc-dest-pannel'><div><span class='crc-pannel-title'>目标表 [" + $("#dest-table-selector").val() + "] CRC 字段</span></div></div>");
        $(".crc-source-pannel").append($("#etljob-frames .source-table-columns").clone(true));
        $(".crc-source-pannel select").attr('name', 'joinInfo.compareCRCCols');
        $(".crc-dest-pannel").append($("#etljob-frames .dest-table-columns").clone(true));
        $(".crc-dest-pannel select").attr('name', 'joinInfo.compareCRCDestCol');
        if(init_flag){
            initContainerSelector($("#pannel-compareMode .source-table-columns"), init_data['compareCRCCols'], {searchBox:0, needblank:0, multiple:1});
            initContainerSelector($("#pannel-compareMode .dest-table-columns"), init_data['compareCRCDestCol'], {searchBox:0, width:'60%'})
        }else{
            initContainerSelector($("#pannel-compareMode .source-table-columns"),undefined, {searchBox:0, needblank:0, multiple:1});
            initContainerSelector($("#pannel-compareMode .dest-table-columns"),"ETL_CRC",{searchBox:0, width:'60%'})
        }

    }
}

/**
 * 加载更新插入方式
 * @param joinMode
 */
function initUpsertPannel(){
    var dbType = $("#dest-db-selector option:selected").attr("dbtype")
    $(".upsert-pannel#upsert-insert-pannel").html("<table class='am-table am-table-bordered am-table-striped am-table-hover'><tr><th colspan='3'>插入</th></tr><tr><th>" + $("#dest-table-selector").val() + "</th><th>" + $("form input[name='tmpTableName']").val() + "</th><th><a class='row-add'>添加</a></th></tr></table>");
    $(".upsert-pannel#upsert-update-pannel").html("<table class='am-table am-table-bordered am-table-striped am-table-hover'><tr><th colspan='3'>更新</th></tr><tr><th>" + $("#dest-table-selector").val() + "</th><th>" + $("form input[name='tmpTableName']").val() + "</th><th><a class='row-add'>添加</a></th></tr></table>");
    $(".upsert-pannel .row-add").on("click", function() {
        appendUpsertTableRow($(this).parents("table"), "", "")
    });
    if (init_flag) {
        var insert_map = init_data['insertMap'];
        var update_map = init_data['updateMap'];
        for (var key in insert_map){
            appendUpsertTableRow($("#upsert-insert-pannel table"), key, insert_map[key], "insert")
        }
        for (var key in update_map){
            appendUpsertTableRow($("#upsert-update-pannel table"), key, update_map[key], "update")
        }
    }
    else if(dbType == "MONGODB") {
        var tmpTableName = $("form input[name='tmpTableName']").val();
        $("#table-source-result").find("th").each(function(i, data){
            appendUpsertTableRow($("#upsert-insert-pannel").find("table"), data.innerText, tmpTableName + "." + data.innerText, dbType)
            appendUpsertTableRow($("#upsert-update-pannel").find("table"), data.innerText, tmpTableName + "." + data.innerText, dbType);
        })
    } else {
        var target_table_columns = table_info['columns'];
        for(var index in target_table_columns){
            if (target_table_columns[index]['candFor']['insert']) {
                appendUpsertTableRow($("#upsert-insert-pannel").find("table"), target_table_columns[index]['name'], "", "insert");
            }
            if (target_table_columns[index]['candFor']['update']) {
                appendUpsertTableRow($("#upsert-update-pannel").find("table"), target_table_columns[index]['name'], "", "update");
            }
        }
    }
    markUnnecessaryUpdates();
    init_flag = false;
}

function validateEtlJobForm(){
    if(checkForm("form-jobinfo")
        && checkBlank("form-jobinfo", $("#table-selector"), "目标数据库表")
        && checkBlank("form-jobinfo", $("#jobtag-selector"), "任务标签")
        && checkBlank("form-jobinfo", $("#pannel-joinMode"), $("[name='joinInfo.joinMode']:checked").parent().text())
        && checkBlank("form-jobinfo", $("#pannel-compareMode"), $("[name='joinInfo.compareMode']:checked").parent().text())
        && checkBlank("form-jobinfo", $("#upsert-insert-pannel"), "插入字段对应关系")
        && checkBlank("form-jobinfo", $("#upsert-update-pannel"), "更新字段对应关系")){
        return true;
    }
    return false;
}

function updateWorkFlow(type){
    var jobId = $("#jobId").val();
    // if(validateEtlJobForm()){
        doPost("/etljob/" + type, {"jobId": jobId}, function(data){
            if(data['suc']){
                alert("提交成功！");
                if(data['msg'] != ""){
                    windowLocate("/etljob/detail/" + data['msg']);
                } else {
                    windowLocate(location.href);
                }
            } else {
                alert(data['msg'])
            }

        })
    // }
}

function startTaskStatusInterval(startTime){
    var checkTaskStatusInterval;
    checkTaskStatusInterval = window.setInterval(function(){
        $.get("/etljob/testResult?jobId=" + $("#jobId").val() + "&startTime=" + startTime, {}, function (data) {
            $("#pannel-test-result").html(data);
            var result = $("#pannel-test-result").find("input.task-status").val();
            if(result === 'SUCCESS' || result === "ERROR"){
                window.clearInterval(checkTaskStatusInterval);
                $("#submitTest").removeAttr("disabled");
            }
        })
    }, 2000);
}

function generateJobFormData(){
    $("#source-sql").val($("#source-sql").val().replace(/\\r\\n/g,' ').replace(/NULL/g, 'null'));
    $("#where-sql").val($("#where-sql").val().replace(/\\r\\n/g,' ').replace(/NULL/g, 'null'));
    $("#form-jobinfo [name='sourceDb.type']").remove();
    $("#form-jobinfo [name='destDb.type']").remove();
    $("#form-jobinfo").append("<input type='hidden' name='sourceDb.type' value='" + $("#source-db-selector option:selected").attr("dbtype") + "'/>");
    $("#form-jobinfo").append("<input type='hidden' name='destDb.type' value='" + $("#dest-db-selector option:selected").attr("dbtype") + "'/>");

    $("#form-jobinfo .joinMode-fieldMap").remove();
    $("#form-jobinfo .joinMode-pkMap").remove();
    $("#form-jobinfo .joinInfo-insertMap").remove();
    $("#form-jobinfo .joinInfo-updateMap").remove();
    $("#form-jobinfo .compareMode-fieldMap").remove();
    $("#form-jobinfo [name='jobSetting.schedule.dependNames']").remove();
    $("#job-selector select option:selected").each(function(){
        $("#form-jobinfo").append('<input type="hidden" name="jobSetting.schedule.dependNames" value="' + $.trim($(this).html()) + '"/>')
    })
    var compareMode_value = $("#form-jobinfo [name='joinInfo.compareMode']:radio:checked").val();
    $("#pannel-joinMode tr:gt(0)").each(function(i, tr){
        $("#form-jobinfo").append("<input type='hidden' class='joinMode-joinMap' name='joinInfo.joinMap[" + $(tr).find('td:eq(0) select, td:eq(0) input').val() + "]' value='" + $(tr).find('td:eq(1) select, td:eq(1) input').val() + "'/>")
    });
    if(compareMode_value == 'FIELD'){
        $("#pannel-compareMode tr:gt(0)").each(function(i, tr){
            $("#form-jobinfo").append("<input type='hidden' class='joinMode-pkMap' name='joinInfo.compareFieldMap[" + $(tr).find('td:eq(0) select, td:eq(0) input').val() + "]' value='" + $(tr).find('td:eq(1) select, td:eq(1) input').val() + "'/>")
        })
    }
    $("#upsert-insert-pannel tr:gt(1)").each(function(i, tr){
        $("#form-jobinfo").append('<input type="hidden" class="joinInfo-insertMap" name="joinInfo.insertMap[' + $(tr).find('td:eq(0) select, td:eq(0) input').val() + ']" value="' + $(tr).find('td:eq(1) input').val().replace(/"/g,"'") + '"/>')
    });
    $("#upsert-update-pannel tr:gt(1)").each(function(i, tr){
        $("#form-jobinfo").append('<input type="hidden" class="joinInfo-updateMap" name="joinInfo.updateMap[' + $(tr).find('td:eq(0) select, td:eq(0) input').val() + ']" value="' + $(tr).find('td:eq(1) input').val().replace(/"/g,"'") + '"/>')
    });
    if($.trim($("#input-setting").val()) == ''){
        $("#input-setting").val('{}')
    }
    if($.trim($("#input-schedule").val()) == ''){
        $("#input-schedule").val('{}')
    }
}

function createSqlEditor(sql_default) {
    return getDefaultEditor("sql-editor", "source-sql", "sqlserver", sql_default, 64)
}
function createWhereEditor(sql_default) {
    return getDefaultEditor("where-editor", "where-sql", "sqlserver" , sql_default, 3)
}
function createSettingEditor(defaultValue) {
    return getDefaultEditor("setting-editor", "input-setting", "json" , defaultValue, 10)
}
function getDefaultEditor(pannelId, inputId, mode, defaultValue, maxLines){
    var editor = ace.edit(pannelId);
    editor.setTheme("ace/theme/cobalt");
    editor.getSession().setMode("ace/mode/" + mode);
    var content = "";
    if(defaultValue)
        content = defaultValue;
    editor.setValue(content);
    $("#" + inputId).val(content);
    editor.getSession().on('change', function(){
        $("#" + inputId).val(editor.getValue());
    });
    editor.setHighlightActiveLine(true);
    editor.clearSelection();
    editor.setFontSize(16);
    editor.$blockScrolling = Infinity;
    editor.getSession().setUseWrapMode(true);
    editor.setShowPrintMargin(false);
    editor.renderer.setShowGutter(false);
    editor.setOptions({maxLines: maxLines});
    return editor;
}

function openModal(){
    var width = 1500;
    var modalType = $("#modalType").val();
    // if(modalType == "taskResults"){
    //     width=1500
    // }
    $('#basicSet').modal({
        width:width,
        relatedTarget: this,
        closeOnConfirm: true
    });
}

function markUnnecessaryUpdates() {
    var dest_cmp_cols = $.map(
        $("div#pannel-joinMode").find(".dest-table-columns input, .dest-table-columns select"),
        function (elem) {
            return elem.value;
        }
    );
    $("div#pannel-joinMode").find("div.dest-table-columns").find("input,select").each(function(i, data){
        console.log(data.value)
    })
    $("div#upsert-update-pannel div.dest-table-columns").find("input,select").each(function () {
        var p = $(this).parent("div");
        var w = p.parent("td").find("div.am-text-warning");
        var v = $(this).val();
        if ($.inArray(v, dest_cmp_cols) >= 0) {
            w.show();
        }
        else {
            w.hide();
        }
    })
}

function refreshEtljobHistory(jobName, page) {
    doGet("/etljob/history/list",{"jobName": jobName, "page":page}, function(data){
        $("#task-history-pannel").html(data)
    })
}
function refreshTaskResultHistory(jobId, page) {
    doGet("/etljob/taskResultHistory",{"jobId": jobId, "page":page}, function(data){
        $("#task-history-pannel").html(data)
    })
}
function gotoPage(pageno){
    var modalType = $("#modalType").val();
    if(modalType == "taskResults"){
        refreshTaskResultHistory($("#jobId").val(), pageno)
    } else if(modalType == "jobHis"){
        refreshEtljobHistory($("#jobName").val(), pageno)
    }
}