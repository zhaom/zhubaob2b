<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${title}-供货商管理平台</title>
    <link href="${imageFilePath}/manage/css/common.css" rel="stylesheet" type="text/css"/>
    <link href="${imageFilePath}/manage/css/in.css" rel="stylesheet" type="text/css"/>
${head}
</head>

<body>
<div class="top">
	<div class="ntop clearfix">
		<div class="top_left"><img src="${imageFilePath}/manage/img/logo.jpg"/></div>
		<div class="top_mid">商家后台管理</div>
		<div class="top_right">${vender.kshopAgencyName?if_exists}</div>
	</div>
	<div class="nav">
		<div class="n_nav">
			<ul>
				<li><a href="${managePath}/manage/main/main.do" target="_blank">首页</a></li>
				<li><a href="#" class="act">商品管理</a></li>
				<li><a href="${managePath}/manage/order/list.do" target="_blank">订单管理</a></li>
				<li><a href="${managePath}/manage/settle/list.do" target="_blank">结算管理</a></li>
				<li><a href="${managePath}/manage/member/list.do" target="_blank">会员管理</a></li>
				<li><a href="${managePath}/manage/shop/profile.do" target="_blank">商铺设置</a></li>
			</ul>
		</div>
	</div>
</div>
<div class="breakout">
		<span>您当前所在位置</span>><span>商家后台管理</span>><span>商品管理</span>
</div>

<div class="w960 ce clearfix">
	<div class="w170 fl bo1">
		<ul class="ul1">
			<li><a href="${managePath}/manage/goods/onshelf.do">在售商品</a></li>
			<li><a href="${managePath}/manage/goods/offshelf.do">已下架商品</a></li>
            <li><a href="${managePath}/manage/goods/newSelectBasic.do">添加商品</a></li>
			<li><a href="${managePath}/manage/goods/waitonshelf.do">待上架商品</a></li>
			<li><a href="${managePath}/manage/goods/cate.do">商品类别管理</a></li>
		</ul>
	</div>
	<div class="w770 fr">
	${body}
	</div>
</div>
<div class="w960 ce footer">
	 <div class="copyright">珠宝网 版权所有</div>
</div>
</body>
</html>
