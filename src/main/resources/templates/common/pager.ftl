<#assign page_gap=2, page_page_start=1/><#-- 页标从第一页开始 -->
<div class="am-cf pager">
     ${page.totalElements} 条记录 共${page.totalPages}页
    <div class="am-fr">
        <ul class="am-pagination">
        <#if (page.number > page_page_start)>
            <li class="page_goto" pageno="${page_page_start+page.number-1}"><a href="javascript:void(0)">«</a></li>
        </#if>
        <#list page_gap..1 as gap>
            <#if ((page.number-gap)>=(page_page_start-1))>
                <li class="page_goto" pageno="${page.number-gap+1}"><a href="javascript:void(0)">${page.number-gap+1}</a></li>
            </#if>
        </#list>
            <li class="am-disabled"><a href="javascript:void(0)">${page.number+1}</a></li>
        <#list 1..page_gap as gap>
            <#if ((page.number+gap)<page.totalPages)>
                <li class="page_goto" pageno="${page.number+gap+page_page_start}"><a href="javascript:void(0)">${page.number+gap+page_page_start}</a></li>
            </#if>
        </#list>
        <#if ((page.number+1)<page.totalPages)>
            <li class="page_goto" pageno="${page.number+1+page_page_start}"><a href="javascript:void(0)">»</a></li>
        </#if>
            <li><input class="pager_goto_pageno" type="text" placeholder="页码" /></li>
            <li><a class="page_goto_point" href="javascript:void(0)">跳转</a></li>
        </ul>
    </div>
</div>
    <input type="hidden" id="aimpage" name="page"/>
    <#--<input type="hidden" id="aimsize" name="size" value="5"/>-->
<script>
    $(".page_goto").on("click",function(){
        gotoPage($(this).attr("pageno"));
    })
    $(".page_goto_point").on("click",function(){
        var pageno = $(".pager_goto_pageno").val();
        if(pageno<${page_page_start}) pageno=${page_page_start};
        if(pageno <= ${page.totalPages}){
            $("#aimpage").val(pageno);
            gotoPage(pageno);
        }else{
            alert("页码格式不正确或超过总页数！")
        }

    })
    $(".table-main th.checkbox :checkbox").on("change", function(){
        $(this).parents("table").find("td.checkbox :checkbox").prop("checked", $(this).is(":checked"))
    })
<#if ajax_submit?? && ajax_submit>
<#else>
    function gotoPage(pageno){
        $("#aimpage").val(pageno);
        $("#form_list").attr("action",location.pathname);
        $("#form_list").submit();
    }
</#if>

</script>