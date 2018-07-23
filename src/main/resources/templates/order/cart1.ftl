<#assign page_title="铂悦滨江",page_type="",nosidebar=true>
<#include "../common/header.ftl"/>

<link type="text/css" rel="stylesheet" href="${project_path}/css/base.css" />
<link type="text/css" rel="stylesheet" href="${project_path}/css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="${project_path}/css/common.css" />
<link type="text/css" rel="stylesheet" href="${project_path}/css/selfpub.css" />
<style>
    .container {
        width: 1170px;
    }
    .imgdiv {
        border: 1px solid #ddd;
        border-radius: 5px;
        height: 300px;
        overflow: hidden;
        font-family: "微软雅黑";
    }
    .person2 {
        width: 1140px;
    }
    .person3 {
        width: 554px;
    }
    .dl-horizontal dt {
        width: 100px;
        line-height: 40px;
    }
    .dl-horizontal dd {
        margin-left: 120px;
        line-height: 40px;
    }
    label {
        font-size: 12px;
        color: #000;
    }
</style>

<div class="container">
    <div class="row" style="height: 30px;"> </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="person2">
                <div class="person3 yaHei center fLeft  personcolor personfcolor">
                    确认支持信息及收件地址
                </div>
                <img src="../images/person1.png" class="fLeft" />
                <div class="person3 yaHei center fLeft">
                    支付
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="height: 30px;"> </div>
    <div class="row">
        <div class="col-lg-8">
            <div class="imgdiv" style="height: auto;">
                <div class="row">
                    <div class="col-lg-12" style="padding-left: 30px;">
                        <h4> 欧式沙发 </h4>
                    </div>
                </div>
                <div class="row" style="border-bottom: 1px dashed #ddd;"> </div>
                <div class="row">
                    <div class="col-lg-12" style="padding: 30px;">
                        <dl class="dl-horizontal">
                            <dt>金额：</dt>
                            <dd>￥12660</dd>
                            <dt>配送费用：</dt>
                            <dd style="margin-top:14px">￥0</dd>
                            <dt>简介：</dt>
                            <dd style="margin-top:14px">
                                欧式沙发的“豪华”主要是通过体积庞大的真皮沙发体现出来，这些沙发款式多样，线条圆滑，整个造型上体现着现代家具制造工业的先进性。欧式沙发富于现代气息，沙发大多色彩清雅、线条简洁，适合一般家庭选用，同时也能够满足豪宅、别墅业主的使用需求，适用范围很广。
                            </dd>
                        </dl>
                    </div>
                </div>
                <div class="row" style="border-bottom: 1px dashed #ddd;"> </div>
                <div class="row">
                    <div class="col-lg-12" style="padding: 30px;">
                        <p>收件地址：<a href="javascript:void(0);" class="fRight text-danger">更改地址</a></p>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox">上海市 浦东新区 崮山路 763 号
                            </label>
                            <#--<a href="javascript:void(0);" class="fRight text-danger margL20">删除</a>-->
                            <#--<a href="javascript:void(0);" class="fRight text-danger">修改</a>-->
                        </div>
                    </div>
                </div>
                <div class="row" style="border-bottom: 1px dashed #ddd;"> </div>
                <div class="row">
                    <div class="col-lg-12" style="padding: 30px;">
                        <h5>备注：</h5>
                        <form>
                            <div class="form-group">
                                <textarea class="form-control" rows="3" style="width: 100%;resize: none;"> </textarea>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-4"> </div>
                    <div class="col-lg-4 col-lg-offset-1">
                        <a href="/order/pay" class="btn btn-danger">下一步</a>
                    </div>
                </div>
                <div class="row" style="height: 40px;"> </div>
            </div>
        </div>
        <div class="col-lg-4">
            <div class="imgdiv">
                <img src="${project_path}/pano/files/sofa.jpg" style="width: 350px;">
                <div style="padding: 5px;">
                    <p>
                        欧式沙发， 整个造型上体现着现代家具制造工业的先进性。
                    </p>
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="height: 30px;"> </div>
</div>

<#include "../common/footer.ftl"/>
