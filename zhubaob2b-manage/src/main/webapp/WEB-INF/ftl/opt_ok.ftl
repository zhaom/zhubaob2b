${message?if_exists}
<script language="javascript">
    alert('${actionMessage?default("${message?if_exists}")}');
    <#if referer?exists>
    window.document.location.href = '${referer}';
    </#if>
</script>