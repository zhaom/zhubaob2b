$(function(){
	var li = $(".pro_detail_left .wimgdiv .imgList li");
	var count = li.length;
	var width = li.width() + 4;
	var index = 1;

	$(".pro_detail_left .wimgdiv .imgList").css("width", count * (width + 2));
	if(count > 5){
		var prev_element = $(".pro_detail_left .wimgdiv .prev");
		var next_element = $(".pro_detail_left .wimgdiv .next");
		var end_index = count - 5;

		/*点击向左滚动*/
		prev_element.click(function(){
			if(index == 0){
				return;
			}

			next_element.addClass("on");
			li.animate({left: "-" + (width * (index - 1))}, "fast");

			if(index == 1){
				$(this).removeClass("on");
				return;
			}

			--index;
		});

		/*点击向右滚动*/
		next_element.addClass("on").click(function(){
			if(index > end_index){
				return;
			}

			prev_element.addClass("on");
			li.animate({left: "-" + (width * index)}, "fast");

			if(index == end_index){
				$(this).removeClass("on");
				return;
			}

			++index;
		});
	}

	$("a", li).livequery("hover",function(){
		var $this = $(this);
		var $parent = $this.parent("li");
		var img = $("img", $this);
		var originalUrl = img.attr("original-src");
		var i = originalUrl.lastIndexOf(".");
		var ext = originalUrl.substring(i);

		$this.addClass("zoomThumbActive");
		$parent.addClass("current").siblings().removeClass("current");
		$("a", $parent.siblings()).removeClass("zoomThumbActive");
		$("#bigImg").attr({"src": originalUrl+"_440x440"+ext});
		$(".thumb a").attr("href", originalUrl);
	});
});