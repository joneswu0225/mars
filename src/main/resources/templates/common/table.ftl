<hr data-am-widget="divider" style="" class="am-divider am-divider-dashed" />
<#include "./rooter.ftl"/>
<div class="am-g">
    <div class="am-u-sm-12" style="overflow-x: auto">
        <table class="am-table am-table-striped am-table-hover table-main">
            <tr>
                <#if config.needCheckBox?? && config.needCheckBox=="true">
                    <th class="checkbox"><input type="checkbox"/></th>
                </#if>
                <th class="t-index"></th>
                <#list config.params as param>
                    <th class="${param.attrName!}">${param.name!}</th>
                </#list>
            </tr>
          <#list page.content as item>
            <tr dataid="<#if config.primarykey??>${item[config.primarykey]!}</#if>">
                <#if config.needCheckBox?? && config.needCheckBox=="true"><td class="checkbox"><input type="checkbox"/></td></#if>
                <td>${item_index+1}</td>
              <#list config.params as param>
              <#-- 跳过非属性参数 -->
              <td class="${param.attrName!'extra'}"
                  <#-- 添加额外隐藏内容 -->
                  <#if param.extras??>
                      <#list param.extras as extra>
                      <#if extra.isAttr?? && extra.isAttr=="true">
                      ${extra.name}="${getContent(extra,item,"")}"
                      </#if>
                      </#list>
                  </#if>
              >
              <#-- 判断结果中的key有没有配置中的key，配置中可以使用结果集中不包含的key做自定义处理-->
              <#if param.attrName?? && ("(item."+param.attrName+")??")?eval>
                ${getContent(param,item,"")}
              <#elseif param.isExtra?? && param.isExtra=="true">
                ${getContent(param,"","")}
              </#if>
              <#if param.extras??>
                  <#list param.extras as extra>
                      <#if extra.isAttr?? && extra.isAttr=="false" >
                      <#if !extra.condition?? || (extra.condition?? && ("item."+extra.condition)?eval)>
                      ${getContent(extra,item,"")}
                      </#if>
                      </#if>
                  </#list>
              </#if>
                </td>
              </#list>
            </tr>
          </#list>
        </table>
        <#--<#if pageable??>-->
            <#include "./pager.ftl"/>
        <#--</#if>-->
    </div>
</div>
