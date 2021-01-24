package com.stealthfueldelivery.app.delivery.others;

public class UrlConstants {

//    public static String BASE_URL = "https://fuelwebservice.biddrip.a2hosted.com/WebService.asmx/";
    public static String BASE_URL = "https://fuelwebservice.biddrip.mochahosted.com/WebService.asmx/";


    public static String LOGIN = BASE_URL + "DeliveryBoyLogin";
    public static String FORGOT_PASSWORD = BASE_URL + "DeliveryBoyForgetPassword";
    public static String NEWORDER = BASE_URL + "GetNewOrderAssignToDeliveryBoyDetails";
    public static String SUBMITORDER = BASE_URL + "DeliveryboySubmitTotalAmt";
    public static String DELIVEREDORDER = BASE_URL + "GetAllListDeliveryboyID";
    public static String CHANGEPASSWORD = BASE_URL + "DeliveryBoyChangePassword";
    public static String NOTIFICATION = BASE_URL + "GetListOfDeliveryNotification";

}