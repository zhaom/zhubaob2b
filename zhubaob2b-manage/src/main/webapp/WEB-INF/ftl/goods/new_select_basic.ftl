<html>
<head>
    <title>添加商品,选择材料/款式</title>
    <script src="${imageMainSitePath}/js/jquery.js" type="text/javascript"></script>
    <script src="${imageMainSitePath}/js/jquery.validate.js" type="text/javascript"></script>
    <script src="${imageFilePath}/manage/js/validate.js" type="text/javascript"></script>
</head>

<body>

<div class="fr w750 bo1 pd10 mb20">
    <div class="t1w">
        <div class="div_title">选择商品材料及款式</div>
        <div class="clear"></div>
    </div>
    <div class="pd10">
        <div class="per_cont">
        <form id="defaultForm" action="${managePath}/manage/goods/newSelectDetail.do" method="post">
            <input type="hidden" name="referer" value="${referer?if_exists}"/>
            <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center">
                        材料：<select required="true" name="materialId" >>
                        <#if materials?exists>
                            <#list materials as material>
                                <option value="${material.id?if_exists}">${material.name?if_exists}</option>
                            </#list>
                        </#if>
                        </select>&nbsp;&nbsp;
                        款式：<select required="true" name="styleId" >
                        <#if styles?exists>
                            <#list styles as style>
                                <option value="${style.id?if_exists}">${style.name?if_exists}</option>
                            </#list>
                        </#if>
                        </select>
                    </td>
                    <td align="right">
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" class="bt1" value="下一步"/>
                    </td>
                </tr>
            </table>
        </form>
        </div>
    </div>
<div>
</body>
</html>