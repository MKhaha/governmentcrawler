package com.crawler.test;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/11/14.
 */

public class OrderInTheArea implements Serializable{
    /**
     * regionId : 330324
     * orderListVOList : [{"orderId":332,"userNumber":8773330300002,"regionId":330324,"organizationId":26,"orderStatusId":1,"orderTypeId":null,"orderAmount":null,"deadlineTime":null,"linkMan":"谷常满","linkPhone":"13757734130","linkAddress":"浙江省永嘉县鹤盛镇岭头乡非农户","orderContent":[{"contentId":null,"orderId":null,"specificationId":"DPL450-175","num":332}]}]
     */

    private int regionId;

    private List<OrderListVOListBean> orderListVOList;

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public List<OrderListVOListBean> getOrderListVOList() {
        return orderListVOList;
    }

    public void setOrderListVOList(List<OrderListVOListBean> orderListVOList) {
        this.orderListVOList = orderListVOList;
    }


    @Override
    public String toString() {
        return "OrderInTheArea{" +
                "regionId=" + regionId +
                ", orderListVOList=" + orderListVOList +
                '}';
    }
}
