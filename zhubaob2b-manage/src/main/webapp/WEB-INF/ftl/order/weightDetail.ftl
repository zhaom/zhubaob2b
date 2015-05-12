<html>
<head>
    <title>订单称重</title>
</head>

<body>

<div class="fr w750 bo1 pd10 mb20">
    <div class="small_title">
        <span class="left_margin">订单号：${order.id?if_exists}</span>
        <span class="right_margin">下单时间：${timer("yyyy-MM-dd H:mm:ss", order.createTime?if_exists)}  支付时间：${timer("yyyy-MM-dd H:mm:ss", order.payTime?if_exists)}    状态：${order.statusInfo.desc?if_exists}</span>
    </div>
</div>
<div class="fr w750 bo1 pd10 mb20">
    <div class="t1w">
        <div class="div_title">收货人信息</div>
        <div class="clear"></div>
    </div>
    <div class="div_ul pd10">
        <ul>
            <li>收货人：${order.address.contactUser?if_exists}</li>
            <li>联系方式：${order.address.mobile?if_exists}</li>
            <li>收货地址：${order.address.address?if_exists}</li>
        </ul>
    </div>
</div>
<div class="fr w750 bo1 pd10 mb20">
    <div class="t1w">
        <div class="div_title">订单跟踪</div>
        <div class="clear"></div>
    </div>
    <div class="div_ul pd10">
        <ul>
        <#if statusList?exists>
            <#list statusList as statusItem>
                <li>${statusItem.createdDate?if_exists}&nbsp;  ${statusItem.desc?if_exists}</li>
            </#list>
        </#if>
        </ul>
    </div>
</div>
<!--  表格 样式示例-->
<div class="fr w750 bo1 pd10 mb20">
    <div class="t1w">
        <div class="div_title">商品清单</div>
        <div class="clear"></div>
    </div>

    <div class="pd10">
        <table class="myTable" width="100%">
            <thead class="mythead">
            <tr>
                <td align="center" width="90">商品图片</td>
                <td align="center" width="200">商品信息</td>
                <td align="center" nowrap>估重/实重</td>
                <td align="center">金额</td>
            </tr>
            </thead>
        <#if order.goods?exists>
            <#list order.goods as goods>
                <tr>
                    <td>
                        <img src="${goods.coverImg?if_exists}_125x125.jpg" class="bo2" />
                    </td>
                    <td width="200">
                        <div class="bo" style=" padding-bottom:5px;">${goods.name?if_exists}</div>

						<span class="c6">
						<#if goods.goodsSku.skuAttrSpecs?exists>
                            <#list goods.goodsSku.skuAttrSpecs as spec>
                            ${spec.attributeName?if_exists}：${spec.value?if_exists}<br />
                            </#list>
                        </#if>
                            购买数量：${goods.amount?if_exists}<br />
                        </span>
                    </td>
                    <td><#if (order.status == "WAIT_WEIGHT" || order.status == "WEIGHTING") && goods.isFixedPrice != 1>
                        估重：${goods.valuedWeight?if_exists}<br>
                        <form action="${managePath}/manage/order/modifyWeight.do" method="post">
                            <input type="hidden" name="oid" value="${order.id?if_exists}">
                            <input type="hidden" name="gid" value="${goods.id?if_exists}">
                            <#if (goods.materialWeight>0.01)>
                                实重：<input type="text" name="weight" value="${goods.materialWeight?if_exists}">
                                <input type="submit" class="bt3" value="修改"/>
                            <#else>
                                实重：<input type="text" name="weight" placeholder="请录入">
                                <input type="submit" class="bt3" value="确定"/>
                            </#if>
                        </form>
                    <#else>
                        实重：${goods.materialWeight?if_exists}
                    </#if>
                    </td>
                    <td align="center" valign="middle">
                        <span class="add_weight">金额小计：</span><span class="red add_weight">${goods.subSumPrice?if_exists}元</span></br>
                        <#if goods.orderGoodsPrice?exists>
                            <#list goods.orderGoodsPrice as priceItem>
                            ${priceItem.desc?if_exists}：${priceItem.price?if_exists}  </br>
                            </#list>
                        </#if>
                    </td>
                </tr>
            </#list>
        </#if>
        <#if order.payway?exists>
            <#if order.payway.type?if_exists == "hedging">
                <tr>
                    <td colspan="4"><span class="add_weight">服务</span>使用风控套保服务</td>
                </tr>
            </#if>
        </#if>
            <tr>
                <td colspan="4">
                    <span class="add_weight">订单总金额：</span><span class="red add_weight">${order.orderSumPrice?if_exists}元</span><br>
                <#if order.orderPrice?exists>
                    <#list order.orderPrice as orderPriceItem>
                        <span>${orderPriceItem.desc?if_exists}:${orderPriceItem.price?if_exists}</span></br>
                    </#list>
                </#if>
                    <form action="${managePath}/manage/order/weightedOrder.do" mehthod="post">
                        <input type="hidden" name="oid" value="${order.id?if_exists}">
                        <input type="submit" class="bt1"  value="称重完成"/>
                    </form>
                </td>
            </tr>
        </table>
        <div class="clear"></div>
    </div>

</div>

<div class="clear"></div>

</body>
</html>