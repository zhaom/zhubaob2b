<html>
<head>
    <title>在售商品</title>
</head>

<body>

<div class="fr w750 bo1 pd10 mb20">
    <div class="t1w">
        <div class="div_title">商品检索</div>
        <div class="cont_choice">
            <form action="${managePath}/manage/goods/onshelf.do" mehthod="get">
                <input type="text" name="k" placeholder="商品名称" />
                <input type="submit" class="bt3" value="查  询"/>
            </form>
        </div>
        <div class="clear"></div>
    </div>
    <div class="pd10">
        <div class="per_cont">
            <span>材质：</span>
            <a href="${managePath}/manage/goods/onshelf.do?k=${k?if_exists}&s=${s?if_exists}&u=${u?if_exists}" <#if m?if_exists=="all" || !m?has_content> class="ss1"</#if>>全部</a>
        <#if materials?exists>
            <#list materials as material>
                <a href="${managePath}/manage/goods/onshelf.do?k=${k?if_exists}&s=${s?if_exists}&u=${u?if_exists}&m=${material.id?if_exists}" <#if m?exists&&(m==material.id)> class="ss1"</#if>>${material.name?if_exists}</a>
            </#list>
        </#if>
        </div>
        <div class="per_cont">
            <span>款式：</span>
            <a href="${managePath}/manage/goods/onshelf.do?k=${k?if_exists}&m=${m?if_exists}&u=${u?if_exists}" <#if s?if_exists=="all" || !s?has_content> class="ss1"</#if>>全部</a>
        <#if styles?exists>
            <#list styles as style>
                <a href="${managePath}/manage/goods/onshelf.do?k=${k?if_exists}&s=${style.id?if_exists}&u=${u?if_exists}&m=${m?if_exists}" <#if s?exists&&(s==style.id)> class="ss1"</#if>>${style.name?if_exists}</a>
            </#list>
        </#if>
        </div>
        <div class="per_cont">
            <span>用途：</span>
            <a href="${managePath}/manage/goods/onshelf.do?k=${k?if_exists}&m=${m?if_exists}&s=${s?if_exists}" <#if u?if_exists=="all" || !u?has_content> class="ss1"</#if>>全部</a>
        <#if uses?exists>
            <#list uses as use>
                <a href="${managePath}/manage/goods/onshelf.do?k=${k?if_exists}&s=${style.id?if_exists}&u=${use.id?if_exists}&m=${m?if_exists}" <#if u?exists&&(u==use.id)> class="ss1"</#if>>${use.name?if_exists}</a>
            </#list>
        </#if>
        </div>
    </div>
</div>

<div class="fr w750 bo1 pd10 mb20">

    <div class="t1w">
        <div class="div_title">在售商品列表</div>
        <div class="clear"></div>
    </div>

    <div class="pd10">
        <table class="myTable" width="100%">
            <thead class="mythead">
            <tr>
                <td align="center" width="90">商品图片</td>
                <td align="center" width="200">商品信息</td>
                <td align="center">规格/库存</td>
                <td align="center">价格</td>
                <td align="center">操作</td>
            </tr>
            </thead>
        <#if pagination.data?exists>
            <#list pagination.data as goods>
                <tr>
                    <td>
                        <img src="${goods.img?if_exists}_125x125.jpg" class="bo2" />
                    </td>
                    <td width="200">
                        <div class="bo" style=" padding-bottom:5px;">${goods.name?if_exists}</div>
					<span class="c6">
					  商品编码：${goods.id?if_exists}<br />
					  商家货号：${goods.code?if_exists}<br />
					  材质：${goods.material.name?default("无")}&nbsp;款式: ${goods.style.name?default("无")}&nbsp;用途：<#if goods.uses?exists><#list goods.uses as use>${use.name?if_exists}&nbsp;</#list></#if></span>
                    </td>
                    <td>
                        <#if goods.skus?exists>
                            <#list goods.skus as sku>
                                <#if sku.skuAttrSpecs?exists>
                                    <#list sku.skuAttrSpecs as spec>
                                    ${spec.attributeName?if_exists}:${spec.value?if_exists}&nbsp;
                                    </#list>
                                </#if>
                                可卖数量：${sku.curCount?default("0")}&nbsp;冻结数量：${sku.freezeCount?default("0")}&nbsp;累计卖出：${sku.sellCount?default("0")}
                                </br>
                            </#list>
                        </#if>
                    </td>
                    <td align="center" valign="middle">
                        <#if goods.prices?exists>
                            <#list goods.prices as price>
                            ${price.desc?if_exists}:${price.price?if_exists}</br>
                            </#list>
                        </#if>
                    </td>
                    <td align="center" valign="middle">
                        <form action="${managePath}/manage/goods/edit.do" mehthod="post">
                            <input type="hidden" name="goodsId" value="${goods.id?if_exists}">
                            <input type="submit" class="bt1"  value="修改"/>
                        </form>
                        <form action="${managePath}/manage/goods/doOffShelf.do" mehthod="post">
                            <input type="hidden" name="goodsId" value="${goods.id?if_exists}">
                            <input type="submit" class="bt1"  value="下架"/>
                        </form>
                    </td>
                </tr>
            </#list>
        </#if>
        </table>
        <div class="clear"></div>

        <div class="fy">
        <#assign pageLink='${managePath}/manage/goods/onshelf.do?k=${k?if_exists}&s=${s?if_exists}&u=${u?if_exists}&m=${m?if_exists}' />
        <@c.page pagination=pagination pageLink=pageLink></@c.page>
            <div class="clear"></div>
        </div>

        <div class="clear"></div>
    </div>
</div>

<div class="clear"></div>
</div>
</body>
</html>
