package com.zhubao.b2b.common.utils;

/**
 * User: xiaoping lu
 * Date: 13-9-9
 * Time: 下午4:53
 */
public class Constants {

	public final static int STATUS_SHOW = 0;
	public final static int STATUS_DELETE = 1;

	public final static String RESULT_OK = "OK";
	public final static String RESULT_ERROR = "ERROR";

	public final static String ORDER_STATUS_WAIT_WEIGHT = "WAIT_WEIGHT";              // 客户提交，待供应商称重
	public final static String ORDER_STATUS_WEIGHTING = "WEIGHTING";                   // 供应商称重中
	public final static String ORDER_STATUS_WEIGHTED = "WEIGHTED";                       // 供应商已称重，待客户确认付款
	public final static String ORDER_STATUS_WAIT_DELIVERY = "WAIT_DEVELIVERY";          // 客户已付款，待供应商发货
	public final static String ORDER_STATUS_WAIT_CONFIRM = "WAIT_CONFIRM";               // 供应商已发货，待客户收货确认
	public final static String ORDER_STATUS_ENDED = "ENDED";                              // 客户收货确认，订单完成
	public final static String ORDER_STATUS_CANCELED = "CANCELED";                       // 订单已取消

	public final static String ORDER_PAYWAY_TYPE_PREPAY = "prepay";                   // 预付
	public final static String ORDER_PAYWAY_TYPE_HEDGING = "hedging";                // 风控
	public final static String ORDER_PAYWAY_TYPE_ONLINE = "online";                  // 在线支付

	public final static String GOODS_PRICE_TYPE_FIXED_PRICE = "fixed";                  // 定价

	public final static String GOODS_PRICE_TYPE_VALUATED_MANUALFEE = "valuatedmanualfee";        // 按克记手工费
	public final static String GOODS_PRICE_TYPE_VALUATED_MATERIALFEE = "valuatedmaterialfee";   // 按克记材料费
	public final static String GOODS_PRICE_TYPE_FIXED_MATERIALFEE = "fixedmaterialfee";           // 定价材料费

	public final static String GOODS_PRICE_TYPE_FIXED_MANUALFEE = "fixedmanualfee";               // 定价手工费

	public final static String GOODS_MATERIAL_AU = "AU";
	public final static String GOODS_MATERIAL_AG = "AG";
	public final static String GOODS_MATERIAL_PT = "PT";

	public final static String SCOPE_ALL = "all";
	public final static String SCOPE_NONE = "none";

	public final static String BALANCE_ORDER_STATUS_INIT = "WAIT_BALANCE";
	public final static String BALANCE_ORDER_STATUS_ENDED = "BALANCED";

	public final static String GOODS_SALES_TYPE_SALE = "sale";
	public final static String GOODS_SALES_TYPE_CANCEL = "cancel";

	public final static String GOODS_ORDER_BY_SELL_COUNT = "sellCount";
	public final static String GOODS_ORDER_BY_ON_SHELF_TIME = "onShelfTime";

	public final static String SUPER_USER_ID = "chuangqianmingyueguang";

	public final static String ORDER_TRANSFER_LOG_SUBMIT = "提交订单";
	public final static String ORDER_TRANSFER_LOG_WEIGHTED = "称重完成";
	public final static String ORDER_TRANSFER_LOG_PAYED = "已经付款";
	public final static String ORDER_TRANSFER_LOG_SHIPPED = "已经发货";
	public final static String ORDER_TRANSFER_LOG_CONFIRMED = "已经确认";
	public final static String ORDER_TRANSFER_LOG_CANCELED = "已经取消";

	public final static String PAYWAY_HEDGING_KSHOP = "zhubaob2b_hedging_kshop";	// 风控套保支付方式 ID
}
