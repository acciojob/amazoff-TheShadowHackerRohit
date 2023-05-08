package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        //this.deliveryTime = convertTimeFromStringToInt(deliveryTime);
        this.deliveryTime = ConvertTime.convertTimeFromStringToInt(deliveryTime);


    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

//    public int convertTimeFromStringToInt(String stringTime){
//        String [] time = stringTime.split(":");
//           String HH = time[0];
//           String MM = time[1];
//           int hh = Integer.valueOf(HH);
//           int mm = Integer.valueOf(MM);
//           return hh*60 + mm;
//
//    }
//    public String convertTimeFromIntToString(int intTime){
//        int hh = intTime/60;
//        int mm = intTime%60;
//        String HH = String.valueOf(hh);
//        String MM = String.valueOf(mm);
//        if(hh < 10){
//            HH = "0"+ HH;
//        }
//        return HH + ":" + MM;
//    }
}
