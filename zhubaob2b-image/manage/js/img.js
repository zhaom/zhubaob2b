var imgOpts = {
    imgCount: 0,
    deleteImg: function (c) {
        $("#imgItem" + c).remove();
    },
    addImg: function (url, ext) {
        var html = '<span id="imgItem' + this.imgCount + '" style="float:left;padding-right:10px;">';
        html += '<p><img src="' + url + '_100x100.' + ext + '" width="100" height="100"/></p>';
        html += '<input type="hidden" name="imgs" value="' + url + '" />';
        html += '<input type="radio" checked name="img" value="' + url + '" />主图';
        html += '<a href="javascript:void(0);" onclick="imgOpts.deleteImg(' + this.imgCount + ')">删除</a>';
        html += '</span>';
        $("#imgInput").append($(html));
        this.imgCount++;
    }
}