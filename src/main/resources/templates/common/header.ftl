<!doctype html>
<html class="no-js">
<head>
    <title>${page_title!"WebETL"}<#if page_type??>/${page_type}</#if></title>
<#include "./head_meta.ftl"/>
</head>
<body>

<header class="am-topbar admin-header">
    <div class="am-topbar-brand">
        <strong><a href="${project_path}">全景船福</a></strong> <span class="page_title">${page_title!"全景船福管理"}</span><#if page_type??><span class="page_type"> / ${page_type}</span></#if>
    </div>
    <div class="am-collapse am-topbar-collapse" id="topbar-collapse">
        <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
            <#--<li><a href="javascript:void(0)" id="updatePassword" userId="${(Session["cur_user"].userId)!}">修改密码</a></li>-->
            <li><a href="javascript:void(0)" id="logout">&nbsp;注销</a></li>
        </ul>
    </div>
</header>
<div class="am-cf admin-main">
<#assign user_role=(Session["cur_user"].role)!>
<#if !nosidebar?? || (nosidebar?? && nosidebar==false)>
    <#include "./sidebar.ftl"/>
</#if>
    <div class="admin-content">
