package com.zhubao.b2b.platform.service.impl;

import com.zhubao.b2b.common.exception.ServiceException;
import com.zhubao.b2b.platform.dao.GoodsMaterialDAO;
import com.zhubao.b2b.platform.model.GoodsMaterial;
import com.zhubao.b2b.platform.service.GoodsMaterialService;

import java.util.List;

/**
 * User: xiaoping lu
 * Date: 13-10-11
 * Time: 下午4:28
 */
public class GoodsMaterialServiceImpl extends BasicServiceSupport implements GoodsMaterialService {

    private GoodsMaterialDAO goodsMaterialDAO;

    public void setGoodsMaterialDAO(GoodsMaterialDAO goodsMaterialDAO) {
        this.goodsMaterialDAO = goodsMaterialDAO;
    }

    @Override
    public void createMaterial(GoodsMaterial material) {
        try {
            goodsMaterialDAO.insert(material);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void updateMaterial(GoodsMaterial material) {
        try {
            goodsMaterialDAO.update(material);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public void deleteMaterial(String materialId) {
        try {
            goodsMaterialDAO.delete(materialId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public GoodsMaterial getMaterial(String materialId) {
        try {
            return goodsMaterialDAO.select(materialId);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public GoodsMaterial getMaterialByAlias(String alias) {
        try {
            return goodsMaterialDAO.selectByAlias(alias);
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

    @Override
    public List<GoodsMaterial> getMaterials() {
        try {
            return goodsMaterialDAO.selectList();
        } catch (Exception e) {
            logger.error("Error occurred.", e);
            throw new ServiceException("Error occurred.", e);
        }
    }

}
