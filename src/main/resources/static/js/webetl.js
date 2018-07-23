/**
 * 清空所有本地缓存
 * @param type
 */
function clearLocalStorage(){
    sessionStorage.clear();
}
/**
 * 清除指定类型的本地缓存
 * @param type
 */
function clearLocalStorage(type){
    sessionStorage.removeItem(type)
}
/**
 * 从缓存中获取值
 * @param type
 * @param key
 */
function getValueFromStorage(type, key, field){
    var option = $(eval($(sessionStorage.getItem(type))[0])).find("option[value='"+ key +"']");
    var result = option.text();
    if(field){
        result = option.attr(field);
    }
    return result;
}

function initStorage(){
    setStorage("sty");
    setStorage("dataStoreType");
    setStorage("webSiteOps");
}
function setStorage(type){
    $.get(project_path + "/util/selector",{"type":type},function(data) {
        sessionStorage.setItem(type, data);
    });
}
/**
 *
 * @param type： select类型，用于请求和请求结果的缓存
 * @param containerId： select外部容器的选择器
 * @param selectedvalue： select的选中值
 * @param needUI： 是否需要UI渲染 1：需要；0：不需要
 * @param extra： UI渲染的额外参数
 * @param callback： 回调函数（作为异步获取后的回调）
 */
function initSearchBarSelector(type,containerId,selectedvalue,needUI,extra,callback){
    var option = {type:type,needblank:1,multiple:0}
    if(extra != undefined)
        $.extend(option,extra);
    html = sessionStorage.getItem(type);
    if(containerId.substring(0,1)!="#" && containerId.substring(0,1)!=".")
        containerId="#"+containerId;
    if(html!=null){
        fillContent(containerId,html);
        if(needUI!=0)
            initSelector(containerId,selectedvalue,option);
        if(callback!=undefined){
            callback.call(this);
        }
    }else{
        $.get(project_path + "/utils/selector",{"type":type},function(data){
            sessionStorage.setItem(type,data);
            fillContent(containerId,data);
            if(needUI!=0)
                initSelector(containerId,selectedvalue,option);
            if(callback!=undefined){
                callback.call(this,data);
            }
            //$("#"+containerId+" .am-selected-hint").hide()
        })
    }
}
function fillContent(containerId,html){
    var prefix = containerId.trim().substring(0,1);
    if(prefix=="#"){
        $(containerId).html(html);
    }else if(prefix=="."){
        $(containerId).each(function(){
            $(this).html(html);
        })
    }
}
/*var option_selector = {
    btnWidth: '260px',
    btnSize: 'sm',//xl|sm|lg|xl
    btnStyle: 'default',//primary|secondary|success|warning|danger
    maxHeight: '360px',
    searchBox: 1,
    multiple:0,
    placeholder:'请选择...'
};*/
var option_selector = {
    width: '100%',
    searchBox: 1,
    dropUp: 1,
    multiple:0,
    disable_search_threshold:10,
    placeholder_text_multiple:'请选择...',
    placeholder_text_single:'请选择...',
    search_contains:true
};

/**
 * select的UI渲染
 * @param $ele： select的jquery对象
 * @param extra: UI渲染参数
 */
function defaultSelected($ele,extra){
    var option = $.extend(true,extra,option_selector);
    $ele.chosen(option);
}

function updateSelected($ele){
    $ele.trigger("chosen:updated");
    $ele.parent().children("input").val($ele.val());
}

/**
 * select设值，并且UI渲染
 * @param containerId： select外层div的选择器
 * @param selectedvalue： select的初始值
 * @param extra： UI渲染参数
 */
function initSelector(containerId, selectedvalue, extra){
    if(containerId.substring(0,1)!="#" && containerId.substring(0,1)!=".")
        containerId="#"+containerId;
    initContainerSelector($(containerId), selectedvalue, extra)
}

function initContainerSelector($container,selectedvalue,extra){
    var option = $.extend(true,{},option_selector);
    var $select = $container.find("select");
    var placeholder = option.placeholder_text_single;
    if(extra != undefined){
        $.extend(option,extra);
        if(option.multiple==0){
            $select.removeAttr("multiple");
            placeholder = option.placeholder_text_single;
        }else{
            $select.attr("multiple","multiple");
            placeholder = option.placeholder_text_multiple;
        }
        hasBlank = $select.children("option[value='']").size()>0;
        if(option.needblank==0 && hasBlank){
            $select.children("option[value='']").remove();
        }else if(option.needblank==1 && !hasBlank){
            $select.find("option:selected").removeAttr("selected");
            if(selectedvalue!=undefined && $.trim(selectedvalue).length>0){
                $select.prepend("<option value=''>"+placeholder+"</option>")
            }else{
                if(option.multiple!=1){
                    $select.prepend("<option selected value=''>"+placeholder+"</option>")
                }
            }
        }
    }
    //如果是id设置初始值（可以是多选的初始值）
    if(selectedvalue!=undefined && $.trim(selectedvalue).length>0 && $container.size() == 1){
        selectedvalue = $.trim(selectedvalue);
        var values = [];
        values[0]=selectedvalue;
        if(selectedvalue.indexOf(",")>0)
            values = selectedvalue.split(",");
        $container.find("select option:selected").removeAttr("selected");
        for(var i in values){
            $container.find("select option[value='"+values[i]+"']").prop("selected",true);
        }
        // $select.val(selectedvalue);
        // $container.find("input").val(selectedvalue);
    }
    //渲染UI
    if($container.size() > 1){
        $container.each(function () {
            $select = $(this).find("select")
            $select.chosen(option);
        })
    }else{
        $select.chosen(option);
    }
}

/**
 * 公用表格数据转移方法
 * @param formId 目标表单ID
 * @param attrName 字段属性名称
 * @param $src 表格内触发事件的dom
 * @param isAttr true：从td的某个属性获取内容； false：从表格的class获取内容
 */
function dataShift(formId,attrName,$src,isAttr){
    var value = $.trim(isAttr ? $src.closest("td").attr(attrName.toLowerCase()) : $src.closest("tr").children("." + attrName).html());
    var aim = $("#" + formId + " [name='"+ attrName + "']");
    if(aim.length === 0)
        aim = $("#" + formId + " [attr='"+ attrName + "']");
    switch (aim[0].tagName.toLowerCase()){
        case "select":
            aim.children("option:selected").attr("selected",false);
            aim.val(value);
            value.split(",").forEach(function(e){
                aim.children("option[value='"+e+"']").prop("selected",true);
            })
            break;
        default:
            if(aim.attr("type")=="radio" || aim.attr("type")=="checkbox"){
                aim.prop("checked",false);
                value.split(",").forEach(function(e){
                    aim = $(aim.selector+"[value='"+e+"']")
                    aim.prop("checked",true);
                })
            }else{
                aim.val($.trim(value));
            }
            break;
    }
}

function resetForm(formId){
    var $form = $("#"+formId);
    $form.find(":text[name],[type='hidden'][name]").val("");
    $form.find("textarea[name]").val("");
    $form.find(":radio,:checkbox").prop("checked",false);
    $form.find(":radio[default='1'],:checkbox[default='1']").prop("checked",true);
    $form.find("select option:selected").removeAttr("selected");
    $form.find("select").each(function(){
        updateSelected($(this));
    })
}

function getRequestUrl(url){
    return project_path + url;
}

function doPost(url,data,callback){
    $.ajax({
        url: getRequestUrl(url),
        async: true,
        type: "POST",
        data: data,//$("#form-operation").serializeArray(),
        success: callback
    });
}
function doPostJson(url,data,callback){
    $.ajax({
        url: getRequestUrl(url),
        async: true,
        contentType: 'application/json',
        type: "POST",
        data: data,//$("#form-operation").serializeArray(),
        success: callback
    });
}
function doSyncPost(url,data,callback){
    $.ajax({
        url: getRequestUrl(url),
        async: false,
        type: "POST",
        data: data,//$("#form-operation").serializeArray(),
        success: callback
    });
}

function doGet(url,data,callback){
    $.ajax({
        url: getRequestUrl(url),
        async: true,
        type: "GET",
        data: data,//$("#form-operation").serializeArray(),
        success: callback
    });
}

function windowOpen(url){
    window.open(getRequestUrl(url));
}

function windowLocate(url){
    location.replace(getRequestUrl(url));
}

/**
 * 检查表单必填项是否已填写
 * @param formId 表单id
 */
function checkForm(formId){
    var inputs = $('#' + formId).find(".not-null").next().find("input[type!='radio'][name!='hidden'][name!=''], textarea,select");
    for(var i = 0; i < inputs.length; i++){
        if(inputs[i].value === "" && inputs[i].name !== ""){
            triggerReminder(formId, inputs[i]);
            return false;
        }
    }
    return true;
}

function checkJson(formId, $dom, content){
    var $input = $dom;
    if(!isJSON($input.val())){
        triggerReminder(formId, $input[0], content, "必须为Json！");
        return false;
    }
    clearReminderHTML(formId);
    return true;
}

function checkBlank(formId, $dom, content){
    var inputs = $dom.find("input[type!='radio'], textarea,select");
    for(var i = 0; i < inputs.length; i++){
        if(inputs[i].value === "" && inputs[i].autocomplete != 'off'){
            triggerReminder(formId, inputs[i], content, "不能为空值！");
            return false;
        }
    }
    clearReminderHTML(formId);
    return true;
}

function triggerReminder(formId, dom_input, pannel_title, warn_msg){
    warn_msg = warn_msg ? warn_msg : "不能为空值！";
    var title = pannel_title ? pannel_title : $(dom_input).parent().prev().html();
    var reminder = showReminder(formId, title + warn_msg);
    if($(dom_input).closest('.am-modal-dialog').length === 0){
        var scrollValue = $(dom_input).offset().top - $(window).height()/2.4;
        $('html, body').delay(1200).animate({
            scrollTop: scrollValue
        }, 700 + scrollValue/4);
        setTimeout(function(){
            $(dom_input).focus();
            flicker($(dom_input).parent(), 2, 800);
        }, 1500 + scrollValue/4)
    } else{
        flicker($(dom_input).parent(), 2, 800);
        $(dom_input).focus();
    }
    if(dom_input.type === "hidden" || dom_input.hidden || dom_input.tagName === "SELECT"){
        $(dom_input).parent().find("select").on("change", function(){
            reminder.fadeOut("slow");
        });
    } else{
        $(dom_input).on("keydown" ,function () {
            reminder.fadeOut("slow");
        });
    }
}

function clearReminderHTMLBase($reminder){
    var reminder = $reminder;
    reminder.html('');
    reminder.css('display', 'none');
    reminder.removeClass('am-text-danger am-text-success');
}
function clearReminderHTML(formId){
    var reminder = $('#' + formId).children('.reminder');
    clearReminderHTMLBase(reminder)
}
function clearReminderAll(){
    var reminder = $('#reminder-all');
    clearReminderHTMLBase(reminder)
}

/**
 * 检查表单中日期输入是否符合格式
 * @param formId 表单id
 */
function checkDateFormat(formId){
    var dateFormat = /^\d{4}-(0\d|1[012])-(0[1-9]|[12]\d|3[01]) ([01]\d|2[0-3]):[0-5]\d:[0-5]\d$/g;
    var inputs =  $('#' + formId).find("input[name*='Datetime']")
    for(var i = 0; i < inputs.length; i++){
        if($.trim(inputs[i].value) != '' && !inputs[i].value.match(dateFormat)){
            if($('#' + formId).children('.reminder').length === 0)
                $('#' + formId).prepend('<span class="reminder"></span>')
            var reminder = showReminder(formId, '时间格式应为：yyyy-mm-dd hh:mm:ss');
            flicker($(inputs[i]), 2, 800);

            $(inputs[i]).on('keydown' ,function () {
                reminder.fadeOut("slow");
            });
            return false;
        }
    }
    return true;
}


/**
 * 使给定元素闪烁
 * @param $e 目标元素
 * @param times 闪烁次数
 * @param duration 完成一次闪烁的时间
 */
function flicker($e, times, duration){
    var loop = setInterval(function(){
        times--;
        $($e).animate({color: "red"}, duration/2).animate({color: "black"}, duration/2);
        if(times === 0 )
            clearInterval(loop);
    },duration)
}

$(function(){
    $("#search_list").on("click",function(){
        //设置页码默认值为1
        $("#aimpage").val(1);
        $("#form_list").attr("action",location.pathname);
        $("#form_list").submit();
    })
    document.onkeydown = function(e){
        var ev = document.all ? window.event : e;
        if(ev.keyCode===13){
            if($(".search-bar").has($(':focus')).length > 0 &&(location.href.indexOf("list")>0 && location.href.indexOf("fetchItem")<0 || location.href.indexOf("crawlrule")>0)) {
                $("#search_list").click();
            }
            else if($(".am-modal-dialog").has($(":focus")).length > 0){
                $("[data-am-modal-confirm]").trigger("click");
            }
            else if($(".pager").has($(":focus")).length > 0){
                $(".page_goto_point").trigger('click');
            }
        }
    }
    $(".page_title").on("click",function(){
        var href = location.pathname;
        var index = href.lastIndexOf("/") + 1;
        if(href.substring(index) != "list"){
            location.replace(href.substr(0, index) + "list");
        }
    });
    $("#logout").on("click",function(){
        doPost("/user/logout",{},function(){
            location.replace(location.href);
        })
    })
    $("#updatePassword").on("click", function(e){
        $("#currentUserId").val($(this).attr("userId"));
        openPasswordModal();
    });
})

function showReminder(formId,content,isSuccess){
    var reminder = $('#'+formId).find('.reminder');
    if (!isSuccess) {
        reminder.removeClass('am-text-danger');
        reminder.addClass("am-text-success");
    }
    else {
        reminder.removeClass('am-text-success');
        reminder.addClass("am-text-danger");
    }
    reminder.html(content);
    reminder.fadeIn("slow");
    reminder.css("display", "block");
    return reminder;
}

function openPasswordModal(){
    clearReminderHTML('form-password');
    $('#basicSet-password').modal({
        relatedTarget: this,
        onConfirm: function (e) {
            var checkResult = checkForm('form-password');
            if(checkResult){
                if($(".newPassword")[0].value === $(".newPassword")[1].value){
                    doPost("/user/password/update", $("#form-password").serializeArray(), function(){
                        clearLocalStorage("form-user");
                        location.replace(location.href);
                    });
                }
                else{
                    showReminder("form-password", "两次密码输入不一致！");
                }
            }
        },
        closeOnConfirm: false,
    });
}

function copy2Clipboard(content){
    var i = 0 ;
    window.clipboard.clipboardData.setData('text', content);
    if(window.clipboardData.getData('text')==''){
        if(i==1){
            alert("复制失败，请手动Ctrl+C快捷键复制！");
        }else{
            alert("复制失败，请重新复制！");
            i = 1;
        }
    }else{
        alert("内容已经复制到剪贴板！");
    }
}

function isJSON(str) {
    if (typeof str == 'string') {
        try {
            var obj=JSON.parse(str);
            if(str.indexOf('{')>-1){
                return true;
            }else{
                return false;
            }

        } catch(e) {
            console.log(e);
            return false;
        }
    }
    return false;
}