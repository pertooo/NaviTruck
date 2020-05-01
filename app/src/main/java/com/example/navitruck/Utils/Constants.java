package com.example.navitruck.Utils;

public class Constants {

    public static String API_BASE_URL = "http://172.20.10.3:8080"; //192.168.1.8
    public static String HEADER_STRING = "Authorization";

    public static String SETTINGS = "mysettings";

    public static String CURRENCY = "$";
    public static String UNIT = "";
    public static String Distance = "Mi";


    public static boolean SEEN_LAST_TASK = true;

    public enum TaskStatusObj{

        ASSIGNED(1),
        ASSIGNED_TO_OTHER(2),
        NOT_ASSIGNED(3);

        private int statusCode;
        TaskStatusObj(int statusCode) {
            this.statusCode = statusCode;
        }

    }


}
