package com.example.navitruck.Utils;

public class Constants {

    public static String API_BASE_URL = "http://192.168.1.8:8080";
    public static String HEADER_STRING = "Authorization";

    public static String SETTINGS = "mysettings";

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
