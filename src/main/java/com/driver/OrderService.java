package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public OrderService() {
        this.orderRepository=new OrderRepository();
    }

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addOrder(Order order) {
        orderRepository.saveOrder(order);
    }

    public void addPartner(String partnerId) {
        orderRepository.savePartner(partnerId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
//        Order order = getOrderById(orderId);
        DeliveryPartner deliveryPartner = getPartnerById(partnerId);

        int numberOfOrders = deliveryPartner.getNumberOfOrders();
        deliveryPartner.setNumberOfOrders(numberOfOrders+1);

        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getDeliveryPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        DeliveryPartner deliveryPartner = getPartnerById(partnerId);
        return deliveryPartner.getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public List<String> getAllAssignOrders() {
        return orderRepository.getAllAssignOrders();
    }


    public Integer getCountOfUnassignedOrders() {
        int numberOfAllOrders = getAllOrders().size();
        int numberOfAllAssignOrders = getAllAssignOrders().size();
        return numberOfAllOrders -numberOfAllAssignOrders;
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        //countOfOrders that are left after a particular time of a DeliveryPartner
        int count = 0;
        List<String> allOrders = orderRepository.getAllOrdersFromADeliverPartner(partnerId);
        for(String orderId : allOrders){
            Order order = getOrderById(orderId);
            int deliveryTime = order.getDeliveryTime();
            int timeInt = ConvertTime.convertTimeFromStringToInt(time);
            if(deliveryTime > timeInt){
                count++;
            }
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        int maxTime = Integer.MIN_VALUE;
        String timeString = "";
        List<String> allOrders = orderRepository.getAllOrdersFromADeliverPartner(partnerId);
        for(String orderId : allOrders) {
            Order order = getOrderById(orderId);
            int deliveryTime = order.getDeliveryTime();
            if(deliveryTime > maxTime){
                maxTime = deliveryTime;
                timeString = ConvertTime.convertTimeFromIntToString(deliveryTime);
            }
        }
        return timeString;
    }

    public void deletePartnerById(String partnerId) {
        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.

        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
}
