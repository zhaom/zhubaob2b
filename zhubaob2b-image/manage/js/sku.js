var sku = {
    skuAttrs: [],
    skuCount: 0,
    deleteSku: function (c) {
        $("#skuItem" + c).remove();
    },
    addSku: function () {
        if (this.skuAttrs.length == 0) {
            alert("当前款式没有设置sku属性");
            return;
        }
        var html = '<div id="skuItem' + this.skuCount + '">';
        $.each(this.skuAttrs, function (i, attr) {
            html += attr.name;
            html += '<select name="skuSel' + sku.skuCount + '" style="width:150px;">';
            $.each(attr.values, function (i, val) {
                html += '<option value="' + val.id + '">' + val.value + '</option>';
            });
            html += "</select>";
        });
        html += '库存量: <input type="text" required="true" digits="true" style="width:50px" name="skuSel' + this.skuCount + 'Count"/>';
        html += '<input type="button" onclick="sku.deleteSku(' + this.skuCount + ');" value="删除"/>';
        html += '</div>';
        $("#skuInput").append($(html));
        this.skuCount++;
    },
    initSkuAttrs: function (attrs) {
        this.skuAttrs = attrs;
    }
}

$(function () {
    $("#addSkuBtn").click(function () {
        sku.addSku();
    });
});