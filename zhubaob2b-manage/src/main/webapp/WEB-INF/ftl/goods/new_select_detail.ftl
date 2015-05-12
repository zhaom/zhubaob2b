<html>
<head>
    <title>添加商品,填写详细信息</title>
    <script src="${imageMainSitePath}/js/jquery.js" type="text/javascript"></script>
    <script src="${imageMainSitePath}/js/jquery.validate.js" type="text/javascript"></script>
    <script src="${imageFilePath}/manage/js/validate.js" type="text/javascript"></script>
    <@c.uploadJS uploadField="uploadField" uploadNotify="uploadNotify" uploadHiddenField="uploadHiddenField" />
    <script src="${imageFilePath}/manage/js/sku.js" type="text/javascript"></script>
    <script src="${imageFilePath}/manage/js/img.js" type="text/javascript"></script>
    <script type="text/javascript">
        var skuAttrs = [];
        <#if skuAttributes?exists>
            <#assign attrSize=skuAttributes?size />
        skuAttrs = [<#list skuAttributes as skuAttr>{id: "${skuAttr.id?if_exists}", name: "${skuAttr.name?if_exists}:", values: [<#list skuAttr.values as value><#assign valSize=skuAttr.values?size />{id: "${value.id?if_exists}", value: "${value.value?if_exists}"}<#if value_index!=(valSize-1)>,</#if></#list>]}<#if skuAttr_index!=(attrSize-1)>,</#if></#list>];
        </#if>
        $(function () {
            sku.initSkuAttrs(skuAttrs);
        });
    </script>
</head>

<body>

<div class="fr w750 bo1 pd10 mb20">
    <div class="t1w">
        <div class="div_title">填写详细信息</div>
        <div class="clear"></div>
    </div>
</div>

<form id="defaultForm" action="${managePath}/manage/goods/newSave.do" method="post">
    <input type="hidden" name="referer" value="${referer?if_exists}"/>
    <input type="hidden" name="materialId" value="${goods.materialId?if_exists}"/>
    <input type="hidden" name="styleId" value="${goods.styleId?if_exists}"/>

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
                    <input type="text" id="code" name="code" value="${goods.code?if_exists}"
                           class="input_class"/>
                </td>
            </tr>
            <tr>
                <td style="text-align:right;width:100px;">商品用途：</td>
                <td style="text-align:left">
                <#if uses?exists>
                    <#list uses as use>
                        <input type="checkbox" name="useIds" value="${use.id?if_exists}"/>&nbsp;${use.name?if_exists}
                        &nbsp;
                    </#list>
                </#if>
                </td>
            </tr>
            <tr>
                <td style="text-align:right;width:100px;">商品图片：</td>
                <td style="text-align:left">
                <@c.uploadHTML uploadField="uploadField" />
                </td>
            </tr>
            <tr>
                <td style="text-align:right;width:100px;"></td>
                <td style="text-align:left">
                    <div id="imgInput"></div>
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
                                    <option value="${attrValue.id?if_exists}">${attrValue.value?if_exists}</option>
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
        <h3 class="second_title">价格信息</h3>
        <table border="0" cellspacing="10" cellpadding="0" style="width:900px;margin-bottom:20px;">
            <tr>
                <td style="text-align:right;width:100px;">商品价格：</td>
                <td style="text-align:left">
                    <input type="radio" name="priceCondition1"  value="1"> 一口价商品  价格:
                    <input number="true" type="text" value="0" name="priceInput1" class="input_class"/>
                </td>
            </tr>
            <tr>
                <td style="text-align:right;width:100px;"></td>
                <td style="text-align:left">
                    <input type="radio" name="priceCondition1"  value="0">  非一口价商品
                    贵金属材料估重(g): <input number="true" type="text" value="0" name="materialWeight" class="input_class"/>
                    <br/>
                    <span style="margin-left:50px;">
                          <input type="radio" name="priceCondition2"  value="0">  贵金属材料称重计价
                    </span>
                    <span style="margin-left:20px;">
                          <input type="radio" name="priceCondition2"  value="1"> 贵金属材料一口价:
                          <input number="true" type="text" value="0" name="priceInput2" class="input_class"/>
                    </span>
                    <br/>
                    <span style="margin-left:50px;">
                          <input type="checkbox" name="priceCondition3"  value="1">  非贵金属材料价格:
                          <input number="true" type="text" value="0" name="priceInput3" class="input_class"/>
                    </span>
                    <br/>
                    <span style="margin-left:50px;">
                          <input type="radio" name="priceCondition4"  value="0">  手工费称重计价
                    </span>
                    <span style="margin-left:20px;">
                          <input type="radio" name="priceCondition4"  value="1"> 手工费一口价:
                          <input number="true" type="text" value="0" name="priceInput4" class="input_class"/>
                    </span>
                </td>
            </tr>
        </table>
    </div>
    <div class="fr w750 bo1 pd10 mb20">
        <h3 class="second_title">销售信息</h3>
        <table border="0" cellspacing="10" cellpadding="0" style="width:900px;margin-bottom:20px;">
            <tr>
                <td style="text-align:right;width:100px;">销售属性：</td>
                <td style="text-align:left">
                    库存量: <input digits="true" type="text" value="0" name="defSkuCount" class="input_class"/>
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
