<#macro page pagination pageLink>
<p>
    共<b>${pagination.totalRecords?if_exists}条信息</b> 当前第<b>${pagination.page}/${pagination.totalPages}</b>页
    <#if (pagination.page>1)>
        <a href="${pageLink?if_exists}&page=1">首页</a>
        <a href="${pageLink?if_exists}&page=${pagination.page-1}">上一页</a>
    </#if>
    <#if (pagination.page<pagination.totalPages)>
        <a href="${pageLink?if_exists}&page=${pagination.page+1}">下一页</a>
        <a href="${pageLink?if_exists}&page=${pagination.totalPages}">尾页</a>
    </#if>
</p>
</#macro>

<#macro uploadHTML uploadField="uploadField">
<input type="file" name="${uploadField}" id="${uploadField}"/>
<a href="javascript:void(0);" onclick="$('#${uploadField}').uploadify('upload')">上传</a>|
<a href="javascript:void(0);" onclick="$('#${uploadField}').uploadify('cancel')">取消上传</a>
</#macro>

<#macro uploadJS uploadField="uploadField" uploadNotify="uploadNotify" uploadHiddenField="uploadHiddenField">
<link href="http://image.zhubao.com/css/jquery.uploadify/css/uploadify.css" rel="stylesheet" type="text/css"/>
<script src="http://image.zhubao.com/js/jquery.uploadify.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $("#${uploadField}").uploadify({
            'swf': 'http://image.zhubao.com/css/jquery.uploadify/images/uploadify.swf',
            'uploader': '${imageUploadPath}',
            'buttonText': '添加图片',
            'fileTypeDesc': 'Image Files',
            'fileTypeExts': '*.gif; *.jpg; *.png',
            'auto': false,
            'multi': true,
            'onUploadSuccess': function (file, data, response) {
                eval("res=" + data);
                imgOpts.addImg(res.fileUrl, res.fileExt);
            }
        });
    });
</script>
</#macro>