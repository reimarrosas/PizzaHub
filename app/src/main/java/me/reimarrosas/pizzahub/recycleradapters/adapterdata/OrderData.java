package me.reimarrosas.pizzahub.recycleradapters.adapterdata;

import me.reimarrosas.pizzahub.models.Order;
import me.reimarrosas.pizzahub.models.Pair;
import me.reimarrosas.pizzahub.models.Single;
import me.reimarrosas.pizzahub.models.Triple;

public class OrderData {

    private Single footerData;
    private Pair headerData;
    private Triple contentData;
    private DataType type;
    private boolean isTitle;
    private Order order;

    public OrderData(Single footerData, Pair headerData, Triple contentData, DataType type, boolean isTitle) {
        this.footerData = footerData;
        this.headerData = headerData;
        this.contentData = contentData;
        this.type = type;
        this.isTitle = isTitle;
    }

    public Single getFooterData() {
        return footerData;
    }

    public Pair getHeaderData() {
        return headerData;
    }

    public Triple getContentData() {
        return contentData;
    }

    public DataType getType() {
        return type;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public enum DataType {
        HEADER, CONTENT, FOOTER
    }

}
