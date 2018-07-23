<#--
<#assign config_json>
{
    "needCheckBox":"true",
    "primarykey":"machineId",
    "webSiteOpsName": {
        "attrName":"webSiteOpsName", //当type不是text时必填
        "name":"操作类型名称", //显示的名称
        "type":"text", //必填，类型：text,child,list,deepChild
        "content":"123", //可填，内容，由type指定,当type为list或者deepChild时，此处为一个对象
        "wrapper":{"before":"B","after":"B"}, //可选，添加前缀和后缀
        "attrs":[ //可选，新增额外属性
            {"name":"操作类型名称","type":"self","content":"webSiteOpsName"},
            {"name":"服务器角色名称1","type":"child","content":"machineRole.machineRoleName"},
            {"name":"服务器角色名称2","type":"deepChild","content":{"name":"machineRole","node":{"type":"self","content":"machineRoleName","wrapper":{"before":"T</br>","after":"T</br>"}}}},
            {"name":"服务器角色名称3","type":"list","content":{"name":"webSiteOpsParams","node":{"name":"抓取参数的网站名称","type":"child","content":"webSiteOps.dataStoreType.collectionName","wrapper":{"before":"T</br>","after":"T</br>"}}}}
        ]
    }，
    "webSiteOpsParams": {
        "name":"操作类型参数", //显示的名称
        "type":"list", //必填，类型：text,child,list,deepChild
        "content":{ //必填，内容，由type指定,当type为list或者deepChild时，此处为一个对象
            "attrName":"webSiteOpsParams", //必填，list对应的属性名
            "type":"self", //必填，类型：text,child,list,deepChild
            "content":"fieldCnName"  //必填
        },
        "wrapper":{"before":"B","after":"B"}, //可选，添加前缀和后缀
        "attrs":[ //可选，新增额外属性
            {"name":"操作类型名称","type":"self","content":"webSiteOpsName"},
            {"name":"服务器角色名称1","type":"child","content":"machineRole.machineRoleName"},
            {"name":"服务器角色名称2","type":"deepChild","content":{"name":"machineRole","node":{"type":"self","content":"machineRoleName","wrapper":{"before":"T</br>","after":"T</br>"}}}},
            {"name":"服务器角色名称3","type":"list","content":{"name":"webSiteOpsParams","node":{"name":"抓取参数的网站名称","type":"child","content":"webSiteOps.dataStoreType.collectionName","wrapper":{"before":"T</br>","after":"T</br>"}}}}
        ]
    }
}
</#assign>

<#assign config_json>
{
"needCheckBox":"true",
"primarykey":"machineId",
"host": {
"name":"机器",
"type":"text",
"content":"123",
"wrapper":{"before":"B","after":"B"},
"attrs":[
{"name":"操作类型名称","type":"self","content":"webSiteOpsName"},
{"name":"服务器角色名称1","type":"child","content":"machineRole.machineRoleName"},
{"name":"服务器角色名称2","type":"deepChild","content":{"attrName":"machineRole","type":"self","content":"machineRoleName","wrapper":{"before":"Tb","after":"TA"}},"wrapper":{"before":"TCC","after":"TDD"}},
{"name":"服务器角色名称3","type":"list","content":{"attrName":"webSiteOpsParams","type":"child","content":"webSiteOps.dataStoreType.collectionName","wrapper":{"before":"TBB","after":"TAA"}},"wrapper":{"before":"TCC","after":"TDD"}}
]
}
}
</#assign>
-->
<#setting number_format="##.####">
<#assign config=config_json?eval>
<#function getContent obj data isLast>
    <#local result="", before="", after=""/>
    <#if obj.wrapper??><#local before=obj.wrapper.before!"", after=obj.wrapper.after!""></#if>
    <#if data?? && data != "" >

        <#if !obj.type?? || obj.type=="self" || obj.type=="child">
            <#if obj.content??>
                <#local result=("(data."+obj.content+")!")?eval>
            <#elseif obj.attrName?? && obj.attrName!="">
                <#local result=("(data."+obj.attrName+")!")?eval>
            <#else>
                <#local result=data>
            </#if>
        <#elseif obj.type=="map">
            <#if obj.attrName??>
                <#local tmp=("(data."+obj.attrName+")!")?eval?string>
                <#if obj.content?? && tmp !=""><#local result=(obj.content?eval[tmp])!''>
                </#if>
            <#else>
                <#local result=(obj.content?eval[data?string])!>
            </#if>
        <#elseif obj.type=="list">
            <#list ("data."+obj.attrName)?eval as child>
                <#if child?is_last><#local isLast="true"></#if>
                <#local result=result+getContent(obj.content,child,isLast)>
            </#list>
        <#elseif obj.type=="deepChild">
            <#local result=getContent(obj.content,("(data."+obj.attrName+")!")?eval,isLast)>
        <#elseif obj.type=="text">
            <#local result=obj.content!>
        </#if>
        <#if obj.attrType?? && obj.attrType!="">
            <#local result=("result!?"+obj.attrType)?eval>
        </#if>
    <#else>
        <#local result=obj.content!>
    </#if>
    <#if isLast??&&isLast!=""&&isLast=="true"&&obj.wrapper??&&obj.wrapper.last??><#local after=obj.wrapper.last?string></#if>
    <#return before+result+after>
</#function>
<#assign plainAttrs=["needCheckBox","primarykey"]/>