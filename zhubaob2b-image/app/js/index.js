$(function(){
	//首页焦点图轮换
	var index = 0;
	var focus = $(".index_focus .focus ul");
	var focus_li = $(".index_focus .focus ul li");
	var focus_num = $(".index_focus .num li");
	var li_width = focus_li.width();
	var li_count = focus_li.length;

	function foucs_selected(index){
		focus_num.eq(index).addClass("current").siblings().removeClass("current");
	}

	var timer = setInterval(focus_move, 5000);

	function focus_move(){
		if(index == li_count){
			focus.animate({left: "0px"}, "slow");
			index = 0;
			foucs_selected(index);
		}else{
			focus.animate({left: -index * li_width}, "slow");
			foucs_selected(index);
			++index;
		}
	}

	focus_num.hover(function(){
		clearInterval(timer);
		index = $(this).index();

		focus.stop().animate({left: -index * li_width}, "slow");
		foucs_selected(index);
	}, function(){
		timer = setInterval(focus_move, 5000);
	});

	$(".all_product .floor .product dl").hover(function(){
		$("dd", this).css("bottom", "0px");
	}, function(){
		$("dd", this).css("bottom", "-47px");
	});
});