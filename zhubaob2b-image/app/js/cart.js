(function($, $win, document){
	if($win.zhubao == undefined){
		$win.zhubao = {};
	}

	if($win.zhubao.cart == undefined){
		$win.zhubao.cart = {};
	}

	$.extend($win.zhubao.cart, {
		getSku: function(productId, attributeValueIds, callback){
			$.getJSON("/product/getSku/"+productId, {attributeValueIds: attributeValueIds}, function(response){
				if(callback&&typeof callback == "function"){
					callback($win.zhubao, response);
				}
			});
		},

		getAmount: function(productId, skuId, callback){
			$.getJSON("/product/getStoreAmount/"+productId, {skuId: skuId}, function(response){
				if(callback&&typeof callback == "function"){
					callback($win.zhubao, response);
				}
			});
		},

		addAmount: function(productId, obj, attributeValueIds, callback){
			if(!obj){
				return;
			}

			obj = $(obj);
			var value = parseInt(obj.val());
			++value;

			if(typeof attributeValueIds == "string"){
				$win.zhubao.cart.getAmount(productId, attributeValueIds, function($this, response){
					if(typeof callback == "function"){
						callback($this, value, response);
					}
				});
			}else{
				$win.zhubao.cart.getSku(productId, attributeValueIds, function(object, response){
					var sku = response.sku;
					if(sku){
						if(sku.curCount&&sku.curCount > value){
							obj.val(value).attr("amount", value);
						}else{
							obj.val(sku.curCount||0).attr("amount", sku.curCount||0);
						}
						$(".add-product-to-cart-btn").removeClass("disabled");
					}
				});
			}
		},

		reduceAmount: function(productId, obj, attributeValueIds, callback){
			if(!obj){
				return;
			}

			obj = $(obj);
			var value = parseInt(obj.val());
			--value;
			if(value < 1){
				value = 1;
			}

			if(typeof attributeValueIds == "string"){
				$win.zhubao.cart.getAmount(productId, attributeValueIds, function($this, response){
					if(typeof callback == "function"){
						callback($this, value, response);
					}
				});
			}else{
				$win.zhubao.cart.getSku(productId, attributeValueIds, function(object, response){
					var sku = response.sku;
					if(sku){
						if(sku.curCount&&sku.curCount > value){
							obj.val(value).attr("amount", value);
						}else{
							obj.val(sku.curCount||0).attr("amount", sku.curCount||0);
						}
						$(".add-product-to-cart-btn").removeClass("disabled");
					}
				});
			}
		},

		editGoodsAmount: function(productId, amount, callback){
			$.getJSON("/cart/editGoodsAmount/"+productId, {amount: amount}, function(response){
				if(typeof callback == "function"){
					callback($this, response);
				}
			});
		},

		add: function(productId, attributeValueIds, amount, callback){
			var dialogId = "cart-dialog-"+(new Date()).getTime();

			if(attributeValueIds !== null){
				if(attributeValueIds.length == 0){
					$.dialog({id: dialogId, fixed: true, width: 400, height: 120, title: "操作提示", content: "请选择商品属性", ok: true, okVal: "确定"});
					return false;
				}
			}

			$win.zhubao.cart.getSku(productId, attributeValueIds, function($this, response){
				var sku = response.sku;
				if(!sku||!sku.curCount||sku.curCount < parseInt(amount)){
					var remain_amount = sku&&sku.curCount ? sku.curCount : 0;
					$.dialog({id: dialogId, fixed: true, width: 400, height: 120, title: "操作提示", content: "库存不足(剩余库存："+remain_amount+")", ok: true, okVal: "确定"});
					return false;
				}

				$.post("/cart/add", {goodsId: productId, skuId: sku.id, amount: amount}, function(response){
					$.dialog({
						id: dialogId, 
						fixed: true, 
						width: 400, 
						height: 120, 
						title: 
						"操作提示", 
						content: response.state ? "商品添加成功" : "商品添加失败："+response.message, 
						ok: true, 
						okVal: response.state ? "继续购买" : "确定",
						cancel: response.state ? function(){
								window.location.href = "/cart";
								return true;
							} : false,
						cancelVal: response.state ? "前往购物车" : undefined,
					});
				});
			});
		}
	});
})(jQuery, window, document);