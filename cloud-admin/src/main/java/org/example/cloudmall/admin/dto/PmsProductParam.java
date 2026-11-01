package org.example.cloudmall.admin.dto;

import org.example.cloudmall.model.PmsProduct;
import org.example.cloudmall.model.PmsProductAttributeValue;
import org.example.cloudmall.model.PmsSkuStock;

import java.util.List;

/**
 * 商品创建/更新/详情参数，包含商品基础信息、属性值列表、SKU 列表
 */
public class PmsProductParam extends PmsProduct {

    private List<PmsProductAttributeValue> productAttributeValueList;

    private List<PmsSkuStock> skuStockList;

    public List<PmsProductAttributeValue> getProductAttributeValueList() {
        return productAttributeValueList;
    }

    public void setProductAttributeValueList(List<PmsProductAttributeValue> productAttributeValueList) {
        this.productAttributeValueList = productAttributeValueList;
    }

    public List<PmsSkuStock> getSkuStockList() {
        return skuStockList;
    }

    public void setSkuStockList(List<PmsSkuStock> skuStockList) {
        this.skuStockList = skuStockList;
    }
}
