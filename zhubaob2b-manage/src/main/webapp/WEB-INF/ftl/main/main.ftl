<html>
<head>
    <title>首页</title>
</head>
<body>
<div class="fr w750 bo1 pd10 mb20">
		<div class="t1w">
			<div class="t1 pd10">
			我的订单
			</div>
			<div class="clear"></div>
		</div>
		<div class="pd10">
			<div class="box1">
				<span class="boxs1">${orderMap["WAIT_WEIGHT"]?default("0")}</span>
				<br />
				等待称重
			</div>
			<div class="box1">
				<span class="boxs1">${orderMap["WEIGHTING"]?default("0")}</span>
				<br />
				称重中
			</div>
			<div class="box1">
				<span class="boxs1">${orderMap["WEIGHTED"]?default("0")}</span>
				<br />
				待付款
			</div>
			<div class="box1">
				<span class="boxs1">${orderMap["WAIT_DEVELIVERY"]?default("0")}</span>
				<br />
				待发货
			</div>
            <div class="box1">
                <span class="boxs1">${orderMap["WAIT_CONFIRM"]?default("0")}</span>
                <br />
                待收货
            </div>
            <div class="box1">
                <span class="boxs1">${orderMap["ENDED"]?default("0")}</span>
                <br />
                已完成
            </div>
            <div class="box1">
                <span class="boxs1">${orderMap["CANCELED"]?default("0")}</span>
                <br />
                已取消
            </div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="fr w750 bo1 pd10 mb20">
		<div class="t1w">
			<div class="t1 pd10">
			我的商品
			</div>
			<div class="clear"></div>
		</div>
		<div class="pd10">
			<div class="box1">
				<span class="boxs1">${goodsMap["AU"]?if_exists}</span>
				<br />
				黄金
			</div>
			<div class="box1">
				<span class="boxs1">${goodsMap["AG"]?if_exists}</span>
				<br />
				白银
			</div>
			<div class="box1">
				<span class="boxs1">${goodsMap["PT"]?if_exists}</span>
				<br />
				铂金
			</div>
			<div class="box1" style="background:#f6f6f6; margin:0; text-align:center; line-height:100px;">
				<a href="${managePath}/manage/goods/newSelectBasic.do">增加商品</a>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="fr w750 bo1 pd10 mb20">
			<div class="t1w">
				<div class="t1 pd10">
				我的会员
				</div>
				<div class="clear"></div>
			</div>
			<div class="pd10">
  <#if members?exists>
    <#list members as member>
		<span>单位：${member.kshopAgencyName?if_exists}</span>&nbsp;<span>用户：${member.kshopUserNickName?if_exists}</span>		<br />
    </#list>
  </#if>
				<div class="clear"></div>
			</div>
	</div>
	<div class="clear"></div>
</body>
</html>