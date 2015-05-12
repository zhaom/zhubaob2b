package com.zhubao.b2b.common.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public final class OrderStatusMap {
	
	private static Map<String, String> data = null;
	
	static {
		if(data == null){
			data = new LinkedHashMap<String, String>();
			data.put(Constants.ORDER_STATUS_WAIT_WEIGHT, "待称重");
			data.put(Constants.ORDER_STATUS_WEIGHTING, "称重中");
			data.put(Constants.ORDER_STATUS_WEIGHTED, "待付款");
			data.put(Constants.ORDER_STATUS_WAIT_DELIVERY, "待发货");
			data.put(Constants.ORDER_STATUS_WAIT_CONFIRM, "待确认收货");
			data.put(Constants.ORDER_STATUS_ENDED, "已完成");
			data.put(Constants.ORDER_STATUS_CANCELED, "已取消");
		}
	}
	
	public static String getDescription(String key){
		return data.get(key);
	}
	
	public static Map<String, String> getAll(){
		return data;
	}

}