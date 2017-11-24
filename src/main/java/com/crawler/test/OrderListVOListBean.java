package com.crawler.test;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by guotao on 2017/11/15.
 * com.crawler.test
 * governmentcrawler
 */
public class OrderListVOListBean implements Serializable {
    /**
     * orderId : 332
     * userNumber : 8773330300002
     * regionId : 330324
     * organizationId : 26
     * orderStatusId : 1
     * orderTypeId : null
     * orderAmount : null
     * deadlineTime : null
     * linkMan : 谷常满
     * linkPhone : 13757734130
     * linkAddress : 浙江省永嘉县鹤盛镇岭头乡非农户
     * orderContent : [{"contentId":null,"orderId":null,"specificationId":"DPL450-175","num":332}]
     */

    private int orderId;
    private long userNumber;
    private int regionId;
    private int organizationId;
    private int orderStatusId;
    private int orderTypeId;
    private int orderAmount;
    private Date deadlineTime;
    private String linkMan;
    private String linkPhone;
    private String linkAddress;
    private List<OrderContentBean> orderContent;
    private int isSelect;

    public int getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public long getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(long userNumber) {
        this.userNumber = userNumber;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public int getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(int orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Date getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(Date deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public List<OrderContentBean> getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(List<OrderContentBean> orderContent) {
        this.orderContent = orderContent;
    }

    public static class OrderContentBean implements Serializable{
        /**
         * contentId : null
         * orderId : null
         * specificationId : DPL450-175
         * num : 332
         */

        private int contentId;
        private int orderId;
        private String specificationId;
        private int num;

        public int getContentId() {
            return contentId;
        }

        public void setContentId(int contentId) {
            this.contentId = contentId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getSpecificationId() {
            return specificationId;
        }

        public void setSpecificationId(String specificationId) {
            this.specificationId = specificationId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "OrderContentBean{" +
                    "contentId=" + contentId +
                    ", orderId=" + orderId +
                    ", specificationId='" + specificationId + '\'' +
                    ", num=" + num +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OrderListVOListBean{" +
                "orderId=" + orderId +
                ", userNumber=" + userNumber +
                ", regionId=" + regionId +
                ", organizationId=" + organizationId +
                ", orderStatusId=" + orderStatusId +
                ", orderTypeId=" + orderTypeId +
                ", orderAmount=" + orderAmount +
                ", deadlineTime=" + deadlineTime +
                ", linkMan='" + linkMan + '\'' +
                ", linkPhone='" + linkPhone + '\'' +
                ", linkAddress='" + linkAddress + '\'' +
                ", orderContent=" + orderContent +
                ", isSelect=" + isSelect +
                '}';
    }
}
