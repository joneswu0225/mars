<#assign page_title="铂悦滨江",page_type="",nosidebar=true>
<#include "../common/header.ftl"/>
<link type="text/css" rel="stylesheet" href="${project_path}/css/selfpub.css" />

<style>
    @media only screen and (min-width: 1200px) {
        .blog-g-fixed {
            max-width: 1200px;
        }
    }

    @media only screen and (min-width: 641px) {
        .blog-sidebar {
            font-size: 1.4rem;
        }
    }

    .blog-main {
        padding: 20px 0;
    }

    .blog-title {
        margin: 10px 0 20px 0;
    }

    .blog-meta {
        font-size: 14px;
        margin: 10px 0 20px 0;
        color: #222;
    }

    .blog-meta a {
        color: #27ae60;
    }

    .blog-pagination a {
        font-size: 1.4rem;
    }

    .blog-team li {
        padding: 4px;
    }

    .blog-team img {
        margin-bottom: 0;
    }

    .blog-content img,
    .blog-team img {
        max-width: 100%;
        height: auto;
    }
    p{
        margin: 0 0 1.2rem;
    }
    .blog-footer {
        padding: 10px 0;
        text-align: center;
    }
    table{
        width:60%
    }
    table td{
        padding-left:10%
    }
</style>

<div class="am-g am-g-fixed blog-g-fixed">
    <div class="am-u-md-12">
        <article class="blog-main">
            <h3 class="am-article-title blog-title">
                确认订单信息
            </h3>
            <hr class="blog-hr">
            <div class="am-g blog-content">
                <div class="am-u-lg-5">
                    <p><img src="${project_path}/pano/files/sofa.jpg"></p>
                </div>
                <div class="am-u-lg-7">
                    <p><span class="bold">商品：</span> 欧式沙发</p>
                    <p><span class="bold">金额：</span> ￥12660</p>
                    <p><span class="bold">配送费用：</span>免运费</p>
                    <p><span class="bold">收货地址：</span>上海市 浦东新区 崮山路 763 号 <br/><a href="javascript:void(0)">管理地址</a></p>
                    <p><span class="bold">简介：</span>“豪华”主要是通过体积庞大的真皮沙发体现出来，这些沙发款式多样，线条圆滑，整个造型上体现着现代家具制造工业的先进性。</p>
                    <p><span class="bold">备注：</span><textarea class="form-control" rows="3" style="width: 100%;resize: none;"> </textarea></p>

                </div>
            </div>
        </article>
        <div>
            <p style="float:left"><span class="bold">支付方式：</span></p>
            <table style="float:left">
                <tr>
                    <td>
                        <div class="bank1"> </div>
                    </td>
                </tr>
            </table>
        </div>
        <div><a class="am-btn am-btn-success am-btn-sm" style="margin:0 42%" href="#">提交</a></div>
    </div>
</div>

<#include "../common/footer.ftl"/>
