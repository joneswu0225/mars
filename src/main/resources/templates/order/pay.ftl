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
                <div class="person3 yaHei center fLeft">
                    确认支持信息及收件地址
                </div>
                <img src="${project_path}/images/person4.png" class="fLeft" />
                <div class="person3 yaHei center fLeft personcolor personfcolor">
                    支付
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="height: 30px;"> </div>
    <div class="row">
        <div class="col-lg-12">
            <div class="imgdiv" style="height: auto;">
                <div class="row">
                    <div class="col-lg-12" style="padding-left: 30px;padding-right: 30px;">
                        <h4>订单提交成功： 欧式沙发</h4>
                        <div class="row" style="border-bottom: 1px dashed #ddd;"> </div>
                        <dl class="dl-horizontal">
                            <dt>订单号：</dt>
                            <dd>03667428</dd>
                            <dt>金额：</dt>
                            <dd style="margin-top:16px;">￥12660</dd>
                            <dt>下单时间：</dt>
                            <dd style="margin-top:16px;">${.now?string("yyyy-MM-dd HH:mm:ss")}</dd>
                            <dt>收货地址：</dt>
                            <dd style="margin-top:16px;">
                                上海市 浦东新区 崮山路 763 号
                            </dd>
                            <dt>联系人：</dt>
                            <dd style="margin-top:16px;">沈先生 (收)</dd>
                            <dt>联系方式：</dt>
                            <dd style="margin-top:16px;">18862368868</dd>
                            <dt>产品介绍：</dt>
                            <dd>欧式沙发的“豪华”主要是通过体积庞大的真皮沙发体现出来，这些沙发款式多样，线条圆滑，整个造型上体现着现代家具制造工业的先进性。欧式沙发富于现代气息，沙发大多色彩清雅、线条简洁，适合一般家庭选用，同时也能够满足豪宅、别墅业主的使用需求，适用范围很广。</dd>
                            <dt>备注：</dt>
                            <dd style="margin-top:16px;">无</dd>
                        </dl>

                    </div>
                </div>
                <div class="row" style="border-bottom: 1px dashed #ddd;"> </div>
                <div class="row">
                    <div class="col-lg-12" style="padding: 30px;">
                        <div class="zhifu-way">
                            选择在线支付方式：
                        </div>
                        <div class="zhifu-pintai">
                            <table class="zhifu-table" style="margin-left: 30px;">
                                <tr>
                                    <td style="text-align: right;padding-right: 30px;width: 170px;">支付平台支付</td>
                                    <td>
                                        <input class="fLeft" type="radio" name="zhifuspace" checked />
                                        <div class="bank1"> </div></td>
                                    <td>
                                        <input class="fLeft" type="radio" name="zhifuspace" />
                                        <div class="bank2"> </div></td>
                                </tr>
                            </table>
                        </div>
                        <table class="zhifu-table">
                            <tr>
                                <td style="text-align: right;padding-right: 30px;width: 170px;">银行支付</td>
                                <td>
                                    <input class="fLeft" type="radio" name="zhifuspace" />
                                    <div class="bank3"> </div></td>
                                <td>
                                    <input class="fLeft" type="radio" name="zhifuspace" />
                                    <div class="bank4"> </div></td>
                                <td>
                                    <input class="fLeft" type="radio" name="zhifuspace" />
                                    <div class="bank5"> </div></td>
                                <td>
                                    <input class="fLeft" type="radio" name="zhifuspace" />
                                    <div class="bank6"> </div></td>
                            </tr>
                            <tr>
                                <td> </td>
                                <td>
                                    <input class="fLeft" type="radio" name="zhifuspace" />
                                    <div class="bank7"> </div></td>
                                <td>
                                    <input class="fLeft" type="radio" name="zhifuspace" />
                                    <div class="bank8"> </div></td>
                                <td>
                                    <input class="fLeft" type="radio" name="zhifuspace" />
                                    <div class="bank9"> </div></td>
                                <td>
                                    <input class="fLeft" type="radio" name="zhifuspace" />
                                    <div class="bank10"> </div></td>
                            </tr>
                        </table>
                    </div>
                </div>
                <div class="row" style="border-bottom: 1px dashed #ddd;"> </div>
                <div class="row" style="height: 30px;"> </div>
                <div class="row">
                    <div class="col-lg-12" style="margin-left:42%">
                        <button href="javascript: void(0);" style="display: block;" class="btn btn-danger imgcenter">在线支付</button>
                    </div>
                </div>
                <div class="row" style="height: 40px;"> </div>
            </div>
        </div>
    </div>
    <div class="row" style="height: 30px;"> </div>
</div>

<#include "../common/footer.ftl"/>
