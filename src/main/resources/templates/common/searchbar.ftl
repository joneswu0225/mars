<select>
<#if type="style">
    <#list list as item>
        <option value="${item.code!}">${item.text!}</option>
    </#list>
<#elseif type="catalog">
    <#list list as item>
        <option value="${item.name!}">${item.detail!}</option>
    </#list>
<#elseif type="scene">
    <#list list as item>
        <option value="${item.code!}">${item.title!}</option>
    </#list>
<#elseif type="hotspot">
    <#list list as item>
        <option value="${item.code!}">${item.title!}</option>
    </#list>
<#elseif type="innerHotspot">
    <#list list as item>
        <option value="${item.code!}">${item.title!}</option>
    </#list>
<#else>
</#if>
</select>

