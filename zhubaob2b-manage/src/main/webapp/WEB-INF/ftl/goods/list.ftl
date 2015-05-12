<html>
<head>
    <title>查看商品列表</title>
</head>

<body>
<div class="crumb">
    <div class="crumb-text">
        <div class="crumb-m fl">你现在的位置：查看商品列表</div>
    </div>
</div>

<div class="r_list">
    <div class="r_list01">
        <form action="${managePath}/manage/goods/list.do" mehthod="get">
            <table cellpadding="0" cellspacing="0">
                <tr>
                    <td>
                    材质: <select name="materialId" class="select01" style="width:150px;">
                        <option value="">全部</option>
                    <#if materials?exists>
                        <#list materials as material>
                            <option <#if materialId?exists&&(materialId=material.id)>selected</#if>
                                    value="${material.id?if_exists}">${material.name?if_exists}</option>
                        </#list>
                    </#if>
                    </select>
                        款式: <select name="styleId" class="select01" style="width:150px;">
                        <option value="">全部</option>
                    <#if styles?exists>
                        <#list styles as style>
                            <option <#if styleId?exists&&(styleId=style.id)>selected</#if>
                                    value="${style.id?if_exists}">${style.name?if_exists}</option>
                        </#list>
                    </#if>
                    </select>
                        用途: <select name="useId" class="select01" style="width:150px;">
                        <option value="">全部</option>
                    <#if uses?exists>
                        <#list uses as use>
                            <option <#if useId?exists&&(useId=use.id)>selected</#if>
                                    value="${use.id?if_exists}">${use.name?if_exists}</option>
                        </#list>
                    </#if>
                    </select>
                        <input type="text" class="user_text" maxlength="20" name="keywords"/>
                        <input type="submit" class="button_table" value="查  询"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div class="r_list">
    <div class="r_list02">
        <table border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td width="100" align="center" valign="middle" class="table_tit">商品编码</td>
                <td width="400" align="center" valign="middle" class="table_tit">名称</td>
                <td width="180" align="center" valign="middle" class="table_tit">主图</td>
                <td align="center" valign="middle" class="table_tit">管理</td>
            </tr>
        <#if pagination.data?exists>
            <#list pagination.data as goods>
                <tr <#if goods_index%2==0>class="tr-abg"</#if>>
                    <td align="center" valign="middle">${goods.code?if_exists}</td>
                    <td align="left" valign="middle">${goods.name?if_exists}</td>
                    <td align="center" valign="middle"><img src="${goods.img?if_exists}" width="90px" height="90px"/>
                    </td>
                    <td align="center" valign="middle" class="list_td3">
                        <a href="${managePath}/manage/goods/delete.do?goodsId=${goods.id?if_exists}" onclick='return confirm("确认删除此记录吗?");'>删除</a>
                        <#-- <a href="${managePath}/manage/goods/edit.do?goodsId=${goods.id?if_exists}">修改</a> -->
                    </td>
                </tr>
            </#list>
        </#if>
        </table>
    </div>
</div>

<div style="width:540px; margin:10px auto;">
    <#assign pageLink='${managePath}/manage/goods/list.do?materialId=${materialId?default("")}&styleId=${styleId?default("")}&useId=${userId?default("")}&keywords=${keywords?default("")}' />
	<@c.page pagination=pagination pageLink=pageLink></@c.page>
</div>
</body>
</html>
