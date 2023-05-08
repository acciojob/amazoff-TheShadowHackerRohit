package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {

    private Map<String,Order> orderMap = new HashMap<>();
    private Map<String,DeliveryPartner> deliveryPartnerMap = deliveryPartnerMap = new HashMap<>() ;
    private Map<String,String> orderDeliveryPartnerMap = new HashMap<>() ;//orderId,partnerId
    private Map<String, List<String>> ordersOfOneDeliverPartnerMap = new HashMap<>();//partnerId, list of orderId

//    public OrderRepository() {
//        orderMap = new HashMap<>();
//        deliveryPartnerMap = new HashMap<>();
//        orderDeliveryPartnerMap = new HashMap<>();//orderId,partnerId
//        ordersOfOneDeliverPartnerMap = new HashMap<>();//partnerId, list of orderId
//    }

    public void saveOrder(Order order) {
        orderMap.put(order.getId(),order);
    }
    public Order getOrderById(String orderId) {
        if(orderMap.containsKey(orderId)){
            return orderMap.get(orderId);
        }
        else return null;

    }

    public void savePartner(String partnerId) {
        DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
        deliveryPartnerMap.put(partnerId,deliveryPartner);
    }

    public DeliveryPartner getDeliveryPartnerById(String partnerId) {
        if(deliveryPartnerMap.containsKey(partnerId)){
            return deliveryPartnerMap.get(partnerId);
        }
        else return null;

    }
    public void addOrderPartnerPair(String orderId, String partnerId) {

        orderDeliveryPartnerMap.put(orderId,partnerId);

        if(ordersOfOneDeliverPartnerMap.containsKey(partnerId) == false){
            List<String> orderList = new ArrayList<>();
            orderList.add(orderId);
            ordersOfOneDeliverPartnerMap.put(partnerId,orderList);
        }
        else{
            List<String> orderList = ordersOfOneDeliverPartnerMap.get(partnerId);
            orderList.add(orderId);
            ordersOfOneDeliverPartnerMap.put(partnerId,orderList);
        }

    }


    public List<String> getOrdersByPartnerId(String partnerId) {
        //DeliveryPartner deliveryPartner = getDeliveryPartnerById(partnerId);
        return ordersOfOneDeliverPartnerMap.get(partnerId);
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }

    public List<String> getAllAssignOrders() {
        return new ArrayList<>(orderDeliveryPartnerMap.keySet());
    }

    public List<String> getAllOrdersFromADeliverPartner(String partnerId) {
        return new ArrayList<>(ordersOfOneDeliverPartnerMap.get(partnerId));
    }

    public void deletePartnerById(String partnerId) {
        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.

        List<String> allOrdersOfAPartner = ordersOfOneDeliverPartnerMap.get(partnerId);
        for(String orderId : allOrdersOfAPartner){
            if(orderMap.containsKey(orderId) == false){
                orderMap.put(orderId,getOrderById(orderId));
            }
            orderDeliveryPartnerMap.remove(orderId);
        }
        ordersOfOneDeliverPartnerMap.remove(partnerId);
        deliveryPartnerMap.remove(partnerId);
    }

    public void deleteOrderById(String oId) {
        //Delete an order and also
        // remove it from the assigned order of that partnerId

        String partnerId = orderDeliveryPartnerMap.get(oId);

        List<String> allOrdersOfAPartner = ordersOfOneDeliverPartnerMap.get(partnerId);

        int idx = 0;
        int size = allOrdersOfAPartner.size();
        for(int i = 0 ; i < size ; i++ ){
            if(allOrdersOfAPartner.get(i).equals(oId)){
                idx = i;
            }
        }

        allOrdersOfAPartner.remove(idx);
        ordersOfOneDeliverPartnerMap.put(partnerId,allOrdersOfAPartner);

        orderDeliveryPartnerMap.remove(oId);
        orderMap.remove(oId);

    }
}
