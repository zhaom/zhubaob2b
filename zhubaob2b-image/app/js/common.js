$(function(){
	/* Springframework Restful API */
	jQuery.each(["head", "put", "delete"], function(i, method){
		jQuery[method] = function(url, data, callback, type){
			// shift arguments if data argument was omitted
			if(jQuery.isFunction(data)){
				type = type||callback;
				callback = data;
				data = undefined;
			}

			return jQuery.ajax({url: url + ("?".indexOf(url) == - 1 ? "?" : "&") + "_method=" + method, type: method, dataType: type, data: data, success: callback});
		};
	});

	if($.validator){
		$.validator.addMethod("isMobile", function(value, element){
			return value&&(/^1(([38]\d)|(4[57])|(5[012356789]))\d{8}$/.test(value));
     	});

		$.validator.addMethod("isQuantity", function(value, element){
			return value&&(/^[1-9]\d*$/.test(value));
     	});

		$.validator.addMethod("isWeight", function(value, element){
			return value&&(/^[1-9]\d*(\.\d+)?$/.test(value));
     	});

		$.validator.addMethod("isValidCode", function(value, element){
			return value&&(/^[a-zA-Z\d]+$/.test(value));
     	});
	}

	$(".user-dialog-login-btn").live("click", function(){
		$.dialog({id: "user-login-dialog", fixed: true, width: 470, height: 300, lock: true, title: "用户登录", content: '<iframe src="/User/Login.html?isAjax=1" class="user-login-iframe"></iframe>'});
		return false;
	});
});