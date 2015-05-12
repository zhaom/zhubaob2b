package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.exception.ServiceException;
import com.zhubao.b2b.common.id.IdFactory;
import com.zhubao.b2b.platform.dao.*;
import com.zhubao.b2b.platform.entity.ShopCartGoodsGroupItem;
import com.zhubao.b2b.platform.model.Goods;
import com.zhubao.b2b.platform.model.GoodsSku;
import com.zhubao.b2b.platform.model.ShopCart;
import com.zhubao.b2b.platform.model.ShopCartGoods;
import com.zhubao.b2b.platform.service.ShopCartService;
import com.zhubao.b2b.platform.utils.AttributeSpecTranslateUtils;
import com.zhubao.b2b.platform.utils.PriceHelper;

import java.math.BigDecimal;
import java.util.*;

/**
 * User: xiaoping lu
 * Date: 13-8-21
 * Time: 下午5:15
 */
public class ShopCartServiceImpl extends BasicServiceSupport implements ShopCartService {

    private ShopCartDAO shopCartDAO;
    private ShopCartGoodsDAO shopCartGoodsDAO;
    private GoodsDAO goodsDAO;
    private GoodsSkuDAO goodsSkuDAO;
    private AttributeSpecTranslateUtils attributeSpecTranslateUtils;
    private VenderDAO venderDAO;
    private PriceHelper priceHelper;

    public void setShopCartDAO(ShopCartDAO shopCartDAO) {
        this.shopCartDAO = shopCartDAO;
    }

    public void setShopCartGoodsDAO(ShopCartGoodsDAO shopCartGoodsDAO) {
        this.shopCartGoodsDAO = shopCartGoodsDAO;
    }

    public void setGoodsDAO(GoodsDAO goodsDAO) {
        this.goodsDAO = goodsDAO;
    }

    public void setGoodsSkuDAO(GoodsSkuDAO goodsSkuDAO) {
        this.goodsSkuDAO = goodsSkuDAO;
    }

    public void setAttributeSpecTranslateUtils(AttributeSpecTranslateUtils attributeSpecTranslateUtils) {
        this.attributeSpecTranslateUtils = attributeSpecTranslateUtils;
    }

    public void setVenderDAO(VenderDAO venderDAO) {
        this.venderDAO = venderDAO;
    }

    public void setPriceHelper(PriceHelper priceHelper) {
        this.priceHelper = priceHelper;
    }

    @Override
    public ShopCart getShopCart(String customerId) {
        logger.debug("user [{}] get shopcart");
        try {
            ShopCart cart = shopCartDAO.select(customerId);
            if (cart != null) {
                logger.debug("cart is exists");
                List<ShopCartGoods> goodsList = shopCartGoodsDAO.selectListByShopCartId(cart.getId());
                if (goodsList != null) {
                    logger.debug("has goods in shopcart");
                    Map<String, List<ShopCartGoods>> goodsMap = new HashMap<String, List<ShopCartGoods>>();
                    Map<String, Float> priceMap = new HashMap<String, Float>();
                    Map<String, Integer> amountMap = new HashMap<String, Integer>();

                    for (ShopCartGoods goods : goodsList) {
                        // 初始化购物车商品 sku 信息
                        GoodsSku sku = goodsSkuDAO.select(goods.getGoodsId(), goods.getSkuId());
                        if (sku != null && sku.getSkuAttrValueIds() != null) {
                            sku.setSkuAttrSpecs(attributeSpecTranslateUtils.translateGoodsSkuAttrValueIdsToSkuSpecs(sku.getSkuAttrValueIds()));
                        }
                        goods.setSku(sku);

                        // 对购物车商品分组
                        String venderId = goods.getVenderId();
                        if (goodsMap.get(venderId) != null && priceMap.get(venderId) != null && amountMap.get(venderId) != null) {
                            goodsMap.get(venderId).add(goods);
                            priceMap.put(venderId, priceMap.get(venderId) + priceHelper.calculateTotalPrice(goods.getPrices(), goods.getMaterialId(), goods.getMaterialWeight(), goods.getPrice(), goods.getIsFixedPrice(), goods.getAmount()));
                            amountMap.put(venderId, amountMap.get(venderId) + 1);
                        } else {
                            goodsMap.put(venderId, new ArrayList<ShopCartGoods>());
                            goodsMap.get(venderId).add(goods);
                            priceMap.put(venderId, priceHelper.calculateTotalPrice(goods.getPrices(), goods.getMaterialId(), goods.getMaterialWeight(), goods.getPrice(), goods.getIsFixedPrice(), goods.getAmount()));
                            amountMap.put(venderId, 1);
                        }
                    }

                    cart.setCartGoodsGroupItems(new ArrayList<ShopCartGoodsGroupItem>());
                    ShopCartGoodsGroupItem groupItem = null;
                    for (String venderId : goodsMap.keySet()) {
                        groupItem = new ShopCartGoodsGroupItem();
                        groupItem.setCartGoodsList(goodsMap.get(venderId));
                        groupItem.setVender(venderDAO.selectByKShopAgencyId(venderId));
                        groupItem.setTotalPrice(priceMap.get(venderId).floatValue());
                        groupItem.setAmount(amountMap.get(venderId));
                        cart.getCartGoodsGroupItems().add(groupItem);
                    }
                }
            }
            return cart;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void deleteShopCart(String customerId) {
        logger.debug("user [{}] delete shopcart");
        try {
            ShopCart cart = shopCartDAO.select(customerId);
            if (cart != null) {
                shopCartGoodsDAO.deleteByShopCartId(cart.getId());
            }
            shopCartDAO.delete(customerId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void createShopCartGoods(String customerId, String goodsId, String skuId, int amount) {
        logger.debug("user [{}] create shopcart with [{} {} {}]",new Object[] {customerId, goodsId, skuId, amount});
        try {
            ShopCart cart = shopCartDAO.select(customerId);
            if (cart == null) {
                logger.debug("new shopcart");
                cart = new ShopCart();
                cart.setId(IdFactory.generateId());
                cart.setCustomerId(customerId);
                cart.setCreateTime(Calendar.getInstance().getTimeInMillis());
                shopCartDAO.insert(cart);
            }

            cart = shopCartDAO.select(customerId);

            ShopCartGoods cartGoods = shopCartGoodsDAO.select(cart.getId(), goodsId, skuId);
            if (cartGoods != null) {
                logger.debug("[{} {}] already exists, so just update amount", goodsId, skuId);
                shopCartGoodsDAO.updateAmount(cart.getId(), cartGoods.getId(), cartGoods.getAmount() + amount);
            } else {
                logger.debug("add [{} {}] to shopcart", goodsId, skuId);
                Goods goods = goodsDAO.select(null, goodsId);
                if (goods != null) {
                    cartGoods = new ShopCartGoods();
                    cartGoods.setId(IdFactory.generateId());
                    cartGoods.setShopCartId(cart.getId());
                    cartGoods.setName(goods.getName());
                    cartGoods.setCode(goods.getCode());
                    cartGoods.setGoodsId(goods.getId());
                    cartGoods.setVenderId(goods.getVenderId());
                    cartGoods.setImg(goods.getImg());
                    cartGoods.setAmount(amount);
                    cartGoods.setPrices(goods.getPrices());
                    cartGoods.setSkuId(skuId);
                    cartGoods.setIsFixedPrice(goods.getIsFixedPrice());
                    cartGoods.setPrice(goods.getPrice());
                    cartGoods.setMaterialWeight(goods.getMaterialWeight());
                    cartGoods.setMaterialId(goods.getMaterialId());
                    shopCartGoodsDAO.insert(cartGoods);
                }else {
                    logger.error("goods info has some fatal error, so exception");
                    throw  new Exception("this goods you selected is not in goods collection");
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void deleteShopCartGoods(String customerId, String shopCartGoodsId) {
        logger.debug("user [{}] delete shopcart goods [{}]", customerId, shopCartGoodsId);
        try {
            ShopCart cart = shopCartDAO.select(customerId);
            if (cart != null) {
                shopCartGoodsDAO.delete(cart.getId(), shopCartGoodsId);
            }
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void updateShopCartGoodsAmount(String customerId, String shopCartGoodsId, int amount) {
        logger.debug("user [{}] update shopcart goods amount [{}, {}]", new Object[]{customerId, shopCartGoodsId, amount});
        try {
            ShopCart cart = shopCartDAO.select(customerId);
            if (cart != null) {
                shopCartGoodsDAO.updateAmount(cart.getId(), shopCartGoodsId, amount);
            }
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public Integer getShopCartGoodsAmount(String customerId) {
        try {
            ShopCart cart = shopCartDAO.select(customerId);
            int amount = 0;
            if (cart != null) {
                List<ShopCartGoods> cartGoodsList = shopCartGoodsDAO.selectListByShopCartId(cart.getId());
                if (cartGoodsList != null) {
                    amount = cartGoodsList.size();
                }
            }
            return amount;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public float getShopCartTotalPrice(String customerId) {
        try {
            ShopCart cart = shopCartDAO.select(customerId);
            float totalPrice = 0.00f;
            if (cart != null) {
                List<ShopCartGoods> cartGoodsList = shopCartGoodsDAO.selectListByShopCartId(cart.getId());
                if (cartGoodsList != null) {
                    for (ShopCartGoods cartGoods : cartGoodsList) {
                        totalPrice += priceHelper.calculateTotalPrice(cartGoods.getPrices(), cartGoods.getMaterialId(), cartGoods.getMaterialWeight(), cartGoods.getPrice(), cartGoods.getIsFixedPrice(), cartGoods.getAmount());
                    }
                }
            }
            return totalPrice;
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }
}
