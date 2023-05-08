package com.driver;

public class ConvertTime {


    public static int convertTimeFromStringToInt(String stringTime){
        String [] time = stringTime.split(":");
        String HH = time[0];
        String MM = time[1];
        int hh = Integer.valueOf(HH);
        int mm = Integer.valueOf(MM);
        return hh*60 + mm;

    }
    public static String convertTimeFromIntToString(int intTime){
        int hh = intTime/60;
        int mm = intTime%60;
        String HH = String.valueOf(hh);
        String MM = String.valueOf(mm);
        if(hh < 10){
            HH = "0"+ HH;
        }
        return HH + ":" + MM;
    }
}

