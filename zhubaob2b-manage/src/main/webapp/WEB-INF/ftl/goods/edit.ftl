<html>
<head>
    <title>修改商品信息</title>
    <script src="http://image.zhubao.com/js/jquery.js" type="text/javascript"></script>
    <script src="http://image.zhubao.com/js/jquery.validate.js" type="text/javascript"></script>
    <script src="${imageFilePath}/manage/js/validate.js" type="text/javascript"></script>
    <@c.uploadJS uploadField="uploadField" uploadNotify="uploadNotify" uploadHiddenField="uploadHiddenField" />
    <script src="${imageFilePath}/manage/js/sku.js" type="text/javascript"></script>
    <script type="text/javascript">
        var skuAttrs = [];
        <#if skuAttributes?exists>
            <#assign attrSize=skuAttributes?size />
        skuAttrs = [<#list skuAttributes as skuAttr>{id: "${skuAttr.id?if_exists}",name: "${skuAttr.name?if_exists}:",values: [<#list skuAttr.values as value><#assign valSize=skuAttr.values?size />{id: "${value.id?if_exists}", value: "${value.value?if_exists}"}<#if value_index!=(valSize-1)>,</#if></#list>]}<#if skuAttr_index!=(attrSize-1)>,</#if></#list>];
        </#if>
        $(function () {
            sku.initSkuAttrs(skuAttrs);
        });
    </script>
</head>

<body>


<div class="fr w750 bo1 pd10 mb20">
    <div class="t1w">
        <div class="div_title">修改商品信息</div>
        <div class="clear"></div>
    </div>
</div>

<form id="defaultForm" action="${managePath}/manage/goods/editSave.do" method="post">
    <input type="hidden" name="referer" value="${referer?if_exists}"/>
    <input type="hidden" name="id" value="${goods.id?if_exists}"/>
    <input type="hidden" name="materialId" value="${goods.materialId?if_exists}"/>
    <input type="hidden" name="styleId" value="${goods.styleId?if_exists}"/>
    <input type="hidden" name="venderId" value="${goods.venderId?if_exists}"/>

    <div class="fr w750 bo1 pd10 mb20">
        <h3 class="second_title">基本信息</h3>
        <table border="0" cellspacing="10" cellpadding="0" style="width:900px;margin-bottom:20px;">
            <tr>
                <td style="text-align:right;width:100px;">商品名称：</td>
                <td style="text-align:left">
                    <input type="text" required="true" id="name" name="name" value="${goods.name?if_exists}"
                           class="input_class"/>
                </td>
            </tr>
            <tr>
                <td style="text-align:right;width:100px;">商品编码：</td>
                <td style="text-align:left">
                    <input type="text" id="code" name="code" value="${goods.code?if_exists}" class="input_class"/>
                </td>
            </tr>
            <tr>
                <td style="text-align:right;width:100px;">商品主图：</td>
                <td style="text-align:left">
                <@c.uploadHTML uploadField="uploadField" uploadNotify="uploadNotify" uploadHiddenField="uploadHiddenField" uploadHiddenFieldName="img" defaultNotifyPath="${goods.img?if_exists}" />
                </td>
            </tr>
            <tr>
                <td style="text-align:right;width:100px;">商品用途：</td>
                <td style="text-align:left">
                <#if uses?exists>
                    <#list uses as use>
                        <input type="checkbox" <#if goods.useIds?exists><#list goods.useIds as useId><#if useId=use.id>checked</#if></#list></#if> name="useIds" value="${use.id?if_exists}"/>&nbsp;${use.name?if_exists}
                        &nbsp;
                    </#list>
                </#if>
                </td>
            </tr>
        </table>
    </div>
    <div class="fr w750 bo1 pd10 mb20">
        <h3 class="second_title">类别特性</h3>
        <table border="0" cellspacing="10" cellpadding="0" style="width:900px;margin-bottom:20px;">
        <#if attributes?exists>
            <#list attributes as attr>
                <tr>
                    <td style="text-align:right;width:100px;">${attr.name?if_exists}：</td>
                    <td style="text-align:left">
                        <select name="attrValueIds" style="width:150px;">
                            <#if attr.values?exists>
                                <#list attr.values as attrValue>
                                    <option <#if goods.attrValueIds?exists><#list goods.attrValueIds as attrValueId><#if attrValueId=attrValue.id>selected</#if></#list></#if> value="${attrValue.id?if_exists}">${attrValue.value?if_exists}</option>
                                </#list>
                            </#if>
                        </select>
                    </td>
                </tr>
            </#list>
        </#if>
        </table>
    </div>
    <div class="fr w750 bo1 pd10 mb20">
        <h3 class="second_title">销售信息</h3>
        <table border="0" cellspacing="10" cellpadding="0" style="width:900px;margin-bottom:20px;">
            <tr>
                <td style="text-align:right;width:100px;">销售属性：</td>
                <td style="text-align:left">
                    库存量: <input digits="true" <#if goods.skus?exists&&goods.skus?size==1&&goods.skus[0].defaultSku>value="${goods.skus[0].curCount?if_exists}"<#else>value="0"</#if>  type="text" name="defSkuCount" class="input_class"/>
                    <input type="button" id="addSkuBtn" value="添加sku"/>
                </td>
            </tr>
            <tr>
                <td style="text-align:right;width:100px;"></td>
                <td style="text-align:left">
                    <div id="skuInput"></div>
                </td>
            </tr>
        </table>
    </div>
    <div class="fr w750 bo1 pd10 mb20">
        <table border="0" cellspacing="10" cellpadding="0" style="width:900px;">
            <tr>
                <td align="center">
                    <input type="submit" class="bt1" value="保  存"/>
                </td>
            </tr>
        </table>
    </div>
</form>

</body>
</html>