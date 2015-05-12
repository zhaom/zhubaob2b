<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>我的结算</title>
</head>

<body>

    <div class="fr w750 bo1 pd10 mb20">
        <div class="t1w">
            <div class="div_title">结算单</div>
            <div class="clear"></div>
        </div>
        <div class="pd10">
            <table class="myTable" width="100%">
                <thead class="mythead">
                <tr>
                    <td align="center">结算单号</td>
                    <td align="center">起止时间</td>
                    <td align="center">交易额</td>
                    <td align="center">应收</td>
                    <td align="center">交易手续费</td>
                    <td align="center">服务费</td>
                    <td align="center">结算状态</td>
                    <td align="center">付款日期</td>
                    <td align="center">操作</td>
                </tr>
                </thead>
        <#if pagination.data?exists>
            <#list pagination.data as settle>
                <tr>
                    <td>${settle.id[16..31]}</td>
                    <td>${timer("yyyy-MM-dd H:mm:ss", settle.beginTime?if_exists)}-</br>${timer("yyyy-MM-dd H:mm:ss", settle.endTime?if_exists)}</td>
                    <td>${settle.tradeVolume?if_exists}</td>
                    <td>${settle.receivables?if_exists}</td>
                    <td>${settle.commission?if_exists}</td>
                    <td>${settle.serviceFee?if_exists}</td>
                    <td>${settle.statusDesc?if_exists}</td>
                    <td>${timer("yyyy-MM-dd H:mm:ss", settle.checkTime?if_exists)}</td>
                    <td>
                        <#if settle.status == 0>
                            <a href="${managePath}/manage/settle/confirm.do?id=${settle.id?if_exists}">确认</a>&nbsp;
                        <#elseif settle.status == 5>
                            <a href="${managePath}/manage/settle/check.do?id=${settle.id?if_exists}">确认支付</a>&nbsp;
                        </#if>
                        <a href="${managePath}/manage/settle/detail.do?id=${settle.id?if_exists}">详细信息</a>&nbsp;
                    </td>
                </tr>
            </#list>
        </#if>

            </table>
            <div class="clear"></div>
            <div class="clear"></div>
        </div>
        <div class="fy">
        <#assign pageLink='${managePath}/manage/settle/list.do' />
        <@c.page pagination=pagination pageLink=pageLink></@c.page>
            <div class="clear"></div>
        </div>
    </div>
    <div class="clear"></div>
</body>
</html>