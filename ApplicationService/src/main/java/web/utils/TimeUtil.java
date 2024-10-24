package web.utils;

import java.sql.Timestamp;

public class TimeUtil {

    public static Timestamp getLocalTime() {
        return new Timestamp(System.currentTimeMillis());
    }
}
