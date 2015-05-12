<html>
    <head>
        <title>订单列表</title>
    </head>

<body>

	<div class="fr w750 bo1 pd10 mb20">
		<div class="t1w">
				<div class="div_title">我的订单</div>
				<div class="cont_choice">
					<form action="${managePath}/manage/order/list.do" mehthod="get">
						<input type="text" name="k" placeholder="订单号或商品名称" />
						<input type="submit" class="bt3" value="查  询"/>
					</form>
				</div>
			<div class="clear"></div>
		</div>
		<div class="pd10">
				<div class="per_cont">
						<span>订单状态：</span>
						<a href="${managePath}/manage/order/list.do?k=${k?if_exists}&ct=${ct?if_exists}&o=${o?if_exists}" <#if s?if_exists=="all" || !s?exists> class="ss1"</#if>>全部</a>
						<a href="${managePath}/manage/order/list.do?k=${k?if_exists}&ct=${ct?if_exists}&o=${o?if_exists}&s=WAIT_WEIGHT" <#if orderMap["WAIT_WEIGHT"]?default(0)<1> class="c99"</#if> <#if s?if_exists=="WAIT_WEIGHT"> class="ss1"</#if>>待称重(${orderMap["WAIT_WEIGHT"]?default("0")})</a>
                        <a href="${managePath}/manage/order/list.do?k=${k?if_exists}&ct=${ct?if_exists}&o=${o?if_exists}&s=WEIGHTING" <#if orderMap["WEIGHTING"]?default(0)<1> class="c99"</#if> <#if s?if_exists=="WEIGHTING"> class="ss1"</#if>>称重中(${orderMap["WEIGHTING"]?default("0")})</a>
						<a href="${managePath}/manage/order/list.do?k=${k?if_exists}&ct=${ct?if_exists}&o=${o?if_exists}&s=WEIGHTED" <#if orderMap["WEIGHTED"]?default(0)<1> class="c99"</#if> <#if s?if_exists=="WEIGHTED"> class="ss1"</#if>>待买家付款(${orderMap["WEIGHTED"]?default("0")})</a>
						<a href="${managePath}/manage/order/list.do?k=${k?if_exists}&ct=${ct?if_exists}&o=${o?if_exists}&s=WAIT_DEVELIVERY" <#if orderMap["WAIT_DEVELIVERY"]?default(0)<1> class="c99"</#if> <#if s?if_exists=="WAIT_DEVELIVERY"> class="ss1"</#if>>待发货(${orderMap["WAIT_DEVELIVERY"]?default("0")})</a>
						<a href="${managePath}/manage/order/list.do?k=${k?if_exists}&ct=${ct?if_exists}&o=${o?if_exists}&s=WAIT_CONFIRM" <#if orderMap["WAIT_CONFIRM"]?default(0)<1> class="c99"</#if> <#if s?if_exists=="WAIT_CONFIRM"> class="ss1"</#if>>已发货(${orderMap["WAIT_CONFIRM"]?default("0")})</a>
						<a href="${managePath}/manage/order/list.do?k=${k?if_exists}&ct=${ct?if_exists}&o=${o?if_exists}&s=ENDED" <#if orderMap["ENDED"]?default(0)<1> class="c99"</#if>  <#if s?if_exists=="ENDED"> class="ss1"</#if>>已完成(${orderMap["ENDED"]?default("0")})</a>
						<a href="${managePath}/manage/order/list.do?k=${k?if_exists}&ct=${ct?if_exists}&o=${o?if_exists}&s=CANCELED" <#if orderMap["CANCELED"]?default(0)<1> class="c99"</#if> <#if s?if_exists=="CANCELED"> class="ss1"</#if>>已取消(${orderMap["CANCELED"]?default("0")})</a>
				</div>
				<div class="per_cont">
						<span>订单时间：</span>
						<a href="${managePath}/manage/order/list.do?k=${k?if_exists}&o=${o?if_exists}&s=${s?if_exists}" <#if ct?if_exists == "all" || !ct?exists> class="ss1"</#if>>全部</a>
                        <a href="${managePath}/manage/order/list.do?k=${k?if_exists}&o=${o?if_exists}&s=${s?if_exists}&ct=1" <#if ct?if_exists == "1"> class="ss1"</#if>>最近1个周</a>
						<a href="${managePath}/manage/order/list.do?k=${k?if_exists}&o=${o?if_exists}&s=${s?if_exists}&ct=2" <#if ct?if_exists == "2"> class="ss1"</#if>>最近3个月</a>
						<a href="${managePath}/manage/order/list.do?k=${k?if_exists}&o=${o?if_exists}&s=${s?if_exists}&ct=3" <#if ct?if_exists == "3"> class="ss1"</#if>>最近1年</a>
				</div>
		</div>
	</div>
	<div class="fy">
			<a href="${managePath}/manage/order/list.do?k=${k?if_exists}&s=${s?if_exists}&ct=${ct?if_exists}" title="按下单时间倒序排列" <#if o?if_exists != "-payTime" || !o?exists> class="ss1"</#if>>下单时间</a>
			<a href="${managePath}/manage/order/list.do?k=${k?if_exists}&o=-payTime&s=${s?if_exists}&ct=${ct?if_exists}" title="按支付时间倒序排列" <#if o?if_exists == "-payTime"> class="ss1"</#if>>支付时间</a>
	</div>
	<div class="fr w750 bo1 pd10 mb20">
			
<#if pagination.data?exists>
	<#list pagination.data as order>	
	
		<div class="t1w">
				<div class="div_title">订单号:${order.id?if_exists}&nbsp;</div>
				<div class="cont_choice">
					<span style="margin-right:15px;">下单时间：${timer("yyyy-MM-dd H:mm:ss", order.createTime?if_exists)}</span><span style="margin-right:15px;">支付时间：${timer("yyyy-MM-dd H:mm:ss", order.payTime?if_exists)}</span>  <span>买家：${order.customer.kshopUserNickName?if_exists}</span>
				</div>
			<div class="clear"></div>
		</div>
		<div class="pd10">
			<table class="myTable" width="100%">   
				<thead class="mythead">
					<tr>
						  <td align="center" nowrap>商品图片</td>
						  <td align="center" width="200px">商品信息</td>
						  <td align="center" nowrap>估重/实重</td>
						  <td align="center" nowrap>金额</td>
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
				  <td>
                      <#if order.isFixedPrice ==1>
				     定价商品
                        <#elseif order.status == "WAIT_WEIGHT" || order.status == "WEIGHTING">
                            估重：${goods.valuedWeight?if_exists}
                        <#else>
                      估重：${goods.valuedWeight?if_exists}</br>实重：${goods.materialWeight?if_exists}
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
					<td colspan="4" nowrap><span class="add_weight">状态与操作</span>
                        <span style="margin-right:15px;">状态：${order.statusInfo.desc?if_exists}</span>
                        <form action="${managePath}/manage/order/detail.do" mehthod="get">
                            <input type="hidden" name="oid" value="${order.id?if_exists}">
                    <#if order.status == "WAIT_WEIGHT" || order.status == "WEIGHTING">
                        &nbsp;<input type="submit" class="bt1"  value="去称重"/>
                    <#elseif order.status == "WAIT_DEVELIVERY">
                        &nbsp;<input type="submit" class="bt1"  value="去发货"/>
                    <#else>
                        &nbsp;<input type="submit" class="bt1"  value="查看详情"/>
                    </#if>
                       </form>
                    </td>
				</tr>
				<tr>
					<td colspan="4"><span class="add_weight">订单总金额：</span><span class="red add_weight">${order.orderSumPrice?if_exists}元</span></td>
				</tr>
			</table>
			<div class="clear"></div>
        </div>
    </#list>
</#if>

			
			<div class="fy">
				<#assign pageLink='${managePath}/manage/order/list.do?k=${k?if_exists}&o=${o?if_exists}&s=${s?if_exists}&ct=${ct?if_exists}' />
					<@c.page pagination=pagination pageLink=pageLink></@c.page>
				<div class="clear"></div>
			</div>

			<div class="clear"></div>

	</div>
	<div class="clear"></div>


</body>
</html>
