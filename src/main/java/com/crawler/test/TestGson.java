package com.crawler.test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guotao on 2017/11/15.
 * com.crawler.test
 * governmentcrawler
 */
public class TestGson {



    public static void main(String[] args) {

        String jsonString = "[{\"regionId\":330324,\n" +
                "            \"orderListVOList\":\n" +
                "                            [{\"orderId\":14,\"userNumber\":8773330300005,\"regionId\":330324,\"organizationId\":26,\"orderStatusId\":2,\"orderTypeId\":null,\"orderAmount\":null,\"deadlineTime\":null,\"linkMan\":\"订单撤销\",\"linkPhone\":\"15858261319\",\"linkAddress\":\"订单撤销测试接口专用订单\",\n" +
                "                            \"orderContent\":[{\"contentId\":null,\"orderId\":null,\"specificationId\":\"YSP35.5\",\"num\":1}]},\n" +
                "                            {\"orderId\":333,\"userNumber\":8773330300004,\"regionId\":330324,\"organizationId\":26,\"orderStatusId\":1,\"orderTypeId\":null,\"orderAmount\":null,\"deadlineTime\":null,\"linkMan\":\"可处理订单\",\"linkPhone\":\"15858261319\",\"linkAddress\":\"可处理订单\",\n" +
                "                            \"orderContent\":[{\"contentId\":null,\"orderId\":null,\"specificationId\":\"YSP35.5\",\"num\":1}]}]},\n" +
                "\n" +
                " {\"regionId\":330300,\n" +
                "            \"orderListVOList\":\n" +
                "                            [{\"orderId\":331,\"userNumber\":8773330300001,\"regionId\":330300,\"organizationId\":26,\"orderStatusId\":2,\"orderTypeId\":null,\"orderAmount\":null,\"deadlineTime\":null,\"linkMan\":\"钟金荣\",\"linkPhone\":\"15967751724\",\"linkAddress\":\"浙江省永嘉县岭头乡岭南村环村东路40号\",\n" +
                "                            \"orderContent\":[{\"contentId\":null,\"orderId\":null,\"specificationId\":\"YSP35.5\",\"num\":1}]}]}]";


        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(jsonString).getAsJsonArray();

        Gson gson = new Gson();
        List<OrderInTheArea> orderInTheAreaArrayList = new ArrayList<>();

        List<OrderListVOListBean> orderListVOListBeanList = new ArrayList<>();

        //加强for循环遍历JsonArray
        for (JsonElement user : jsonArray) {
            //使用GSON，直接转成Bean对象
            OrderInTheArea orderInTheArea = gson.fromJson(user, OrderInTheArea.class);
            orderInTheAreaArrayList.add(orderInTheArea);

            System.out.println(orderInTheArea);
            orderInTheArea.getOrderListVOList().size();
            System.out.println(orderInTheArea.getOrderListVOList().size());

            for (OrderListVOListBean orderListVOListBean : orderInTheArea.getOrderListVOList()) {
                orderListVOListBeanList.add(orderListVOListBean);
            }
        }

        System.out.println(orderListVOListBeanList.size());
        System.out.println(orderListVOListBeanList);
//        System.out.println(orderInTheAreaArrayList);
    }
}
