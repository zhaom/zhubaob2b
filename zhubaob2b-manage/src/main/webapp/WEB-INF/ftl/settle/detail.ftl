<html>
<head>
    <title>结算单详情</title>
</head>

<body>

    <div class="fr w750 bo1 pd10 mb20">
        <div class="t1w">
            <div class="div_title">结价单详情</div>
            <div class="cont_choice">
                <a href="${managePath}/manage/settle/list.do">返回列表》</a>
            </div>
            <div class="clear"></div>
        </div>
        <div class="pd10">
            <h3 class="bianhao">结价单编号：${settle.id[16..31]}<span>起止时间：${timer("yyyy-MM-dd H:mm:ss", settle.beginTime?if_exists)}-${timer("yyyy-MM-dd H:mm:ss", settle.endTime?if_exists)}</span></h3>
            <p class="he_25">商家应收：<span class="red">${settle.receivables?if_exists}元</span>=${settle.tradeVolume?if_exists}元(交易额)-${settle.commission?if_exists}元(交易佣金)-${settle.serviceFee?if_exists}元(风控服务费)</p>
        </div>
    </div>

    <div class="fr w750 bo1 pd10 mb20">
        <div class="t1w">
            <div class="div_title">订单明细</div>
            <div class="clear"></div>
        </div>
        <div class="pd10">
            <table class="myTable" width="100%">
                <thead class="mythead">
                <tr>
                    <td align="center">订单号</td>
                    <td align="center">付费时间</td>
                    <td align="center">订单金额</td>
                    <td align="center">交易佣金</td>
                    <td align="center">风控服务费</td>
                    <td align="center">订单状态</td>
                </tr>
                </thead>
        <#if settle.items?exists>
            <#list settle.items as item>
                <tr>
                    <td align="center">
                        ${item.id?if_exists}
                    </td>
                    <td align="center">
                        ${timer("yyyy-MM-dd H:mm:ss", item.payTime?if_exists)}
                    </td>
                    <td align="center"  valign="middle" class="red add_weight">
                        ${item.orderAmount?if_exists}元
                    </td>
                    <td align="center" valign="middle" class="red add_weight">
                        -${item.commission?if_exists}元
                    </td>
                    <td align="center" valign="middle" class="red add_weight">
                        -${item.serviceFee?if_exists}元
                    </td>
                    <td>
                        ${item.orderStatusDesc?if_exists}
                    </td>
                </tr>
            </#list>
        </#if>
                <tr>
                    <td colspan="6" class="add_weight">
                        本期合计：<span class="red">订单金额：${settle.tradeVolume?if_exists}元-交易佣金：${settle.commission?if_exists}元-风控服务费：${settle.serviceFee?if_exists}元=商家应收：${settle.receivables?if_exists}元</span>
                    </td>
                </tr>
                <tr>
                    <td class="add_weight">操作</td>
                    <td colspan="5" class="add_weight">
                    <#if settle.status == 0>
                        <a href="${managePath}/manage/settle/confirm.do?id=${settle.id?if_exists}">确认</a>&nbsp;
                    <#elseif settle.status == 5>
                        <a href="${managePath}/manage/settle/check.do?id=${settle.id?if_exists}">确认支付</a>&nbsp;
                    </#if>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <div class="clear"></div>
</body>
</html>
