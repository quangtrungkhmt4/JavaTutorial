import com.mongodb.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static final String ID = "_id";
    private static final String DAY = "day";
    private static final String CALL_ID = "call_id";
    private static final String USER_ID = "user_id";
    private static final String FRIEND_ID = "friend_id";
    private static final String HOUR = "hour";
    private static final String BEFORE_POINT = "before_point";
    private static final String POINT_BACK = "point_back";
    private static final String AFTER_POINT = "after_point";
    private static final String PERCENTAGE = "percentage";
    private static final String EVENT_ID = "event_id";
    private static final String DRAW_ID = "draw_id";
    private static final String LIST_LOG = "lst_log";

    private static final String first_day_of_month = "/01";

    static Mongo mongo = new Mongo("localhost", 27017);

    static DB db = mongo.getDB("testdb");
    static DBCollection collection = db.getCollection("log_event_point_back");
    private static final String MINUTE_SECOND = "0000";


    public static void main(String[] args) throws Exception {


        int year = 2020;
        int month = 1;
        int day = 12;

        String callId = "callId";
        String userId = "userId";
        String friendId = "friendId";
        int beforePoint = 100;
        int pointBack = 10;
        int afterPoint = 110;
        double percent = 2.5;
        String eventId = "eventId";


//        long milisTime = currentTime();
//        String currentDay = convertDate(milisTime, "yyyyMMdd");
//        BasicDBObject findDayObj = new BasicDBObject(DAY, currentDay);
//
//        for (int i=0; i <= 10; i++){
//            BasicDBObject insertObj = new BasicDBObject();
//            insertObj.append(CALL_ID, callId);
//            insertObj.append(USER_ID, userId);
//            insertObj.append(FRIEND_ID, friendId);
//            insertObj.append(BEFORE_POINT, beforePoint);
//            insertObj.append(POINT_BACK, pointBack);
//            insertObj.append(AFTER_POINT, afterPoint);
//            insertObj.append(PERCENTAGE, percent);
//            insertObj.append(EVENT_ID, eventId);
//            insertObj.append(HOUR, convertDate(milisTime, "yyyyMMddHH"));
//            insertObj.append(DRAW_ID, drawId);
//
//            BasicDBObject updateObj = new BasicDBObject(LIST_LOG, insertObj);
//            BasicDBObject updateCommand = new BasicDBObject("$push", updateObj);
//
//            collection.update(findDayObj, updateCommand, true, false,new WriteConcern(true));
//        }

        long timeZone = 7;
        String val = "2020/01";
        String drawId = "drawId";


//        System.out.println("ngay 11");
//        getListHour("2020/01/11", 7, "drawId");
////        System.out.println("ngay 12");
//        getListHour("2020/01/12", 7, "drawId");
//
////        System.out.println("thang 01");
//        getListDay(val, timeZone, drawId);
//
////        System.out.println("nam 2020");
//        getListMonth("2020", 7, "drawId");




    }

    public static Map<String, Map<Double, Rate>> getListMonth(String val, long timeZone, String drawId) throws Exception {
//        System.out.println("nam");
        Map<String, Map<Double, Rate>> rateMap = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            Map<Double, Rate> map = new HashMap<>();
            String month = String.valueOf(i);
            if (i < 10) {
                month = "0" + String.valueOf(i);
            }
            String time = val +"/" + month;
            Map<String, Map<Double, Rate>> mapDay = getListDay(time, timeZone, drawId);



            mapDay.forEach((timeDay, doubleRateMap) -> {

                doubleRateMap.forEach((percent, rate) -> {
                    if (map.containsKey(percent)){
                        Rate oldRate = map.get(percent);
                        oldRate.setPoint(oldRate.getPoint() + rate.getPoint());
                        oldRate.setCount(oldRate.getCount() + rate.getCount());
                    }else {
                        map.put(percent, rate);
                    }

                });
            });
            rateMap.put(time, map);
        }
        rateMap.forEach((time, doubleRateMap) -> {
            String temp = "";
            System.out.println("time: " + time + "\n");
            doubleRateMap.forEach((percent, rate) -> {
                System.out.println("\tpercent: " + percent + " - count: " + rate.getCount() + " - point: " + rate.getPoint() + "\n");
            });

        });
        return rateMap;
    }

    public static Map<String, Map<Double, Rate>> getListDay(String val, long timeZone, String drawId) throws Exception {
//        System.out.println("thang");
        val += first_day_of_month;
        String fromDay = getBeforeDay(val);
        String toDay = getLastDayOfMonth(val);
        long addTime = timeZone * 3600 * 1000;
        long fromTime = getFromTime(val);
        long toTime = getToTimeInDays(val);

        BasicDBObject findObject = new BasicDBObject();
        BasicDBList ands = new BasicDBList();
        BasicDBObject gte = new BasicDBObject("$gte", fromDay);
        BasicDBObject lte = new BasicDBObject("$lte", toDay);
        ands.add(new BasicDBObject(DAY, lte));
        ands.add(new BasicDBObject(DAY, gte));
        findObject.append("$and", ands);
        BasicDBObject sortObj = new BasicDBObject(DAY, 1);
        DBCursor cursor = collection.find(findObject).sort(sortObj);

        Map<String, Map<Double, Rate>> rateMap = new HashMap<>();
//        List<Stastic> stasticList = new ArrayList<>();
        while (cursor.hasNext()) {
            DBObject dbO = cursor.next();
            BasicDBList listHour = (BasicDBList) dbO.get(LIST_LOG);
            if (listHour != null) {
                listHour.forEach(hour -> {
                    DBObject obj = (DBObject) hour;
                    String serverHour = (String) obj.get(HOUR) + MINUTE_SECOND;
                    long serverTime = parse(serverHour).getTime();
                    long clientTime = serverTime + addTime;
                    if (clientTime >= fromTime && clientTime <= toTime) {
                        String drawTypeId = (String) obj.get(DRAW_ID);
                        if (drawTypeId.equals(drawId)) {
                            String clientDay = format_yyyyMMdd(new Date(clientTime));
                            double per = (double) obj.get(PERCENTAGE);
                            int pointBack = (int) obj.get(POINT_BACK);

                            if (rateMap.containsKey(clientDay)) {
                                Map<Double, Rate> currentRateMap = rateMap.get(clientDay);
                                if (currentRateMap.containsKey(per)) {
                                    Rate rate = currentRateMap.get(per);
                                    rate.setCount(rate.getCount() + 1);
                                    rate.setPoint(rate.getPoint() + pointBack);
                                } else {
                                    currentRateMap.put(per, new Rate(1, pointBack));
                                }
                            } else {
                                Map<Double, Rate> currentRateMap = new HashMap<>();
                                currentRateMap.put(per, new Rate(1, pointBack));
                                rateMap.put(clientDay, currentRateMap);
                            }
                        }
                    }
                });
            }
        }
        rateMap.forEach((time, doubleRateMap) -> {
            String temp = "";
            System.out.println("time: " + time + "\n");
            doubleRateMap.forEach((percent, rate) -> {
                System.out.println("\tpercent: " + percent + " - count: " + rate.getCount() + " - point: " + rate.getPoint() + "\n");
            });

        });
        return rateMap;
    }

    public static Map<String, Map<Double, Rate>> getListHour(String val, long timeZone, String drawId) throws Exception {
//        System.out.println("ngay");
        String fromDay = getBeforeDay(val);
        String toDay = getAfterDay(val);
        long addTime = timeZone * 3600 * 1000;
        long fromTime = getFromTime(val);
        long toTime = getToTimeHours(val);


        BasicDBObject findObject = new BasicDBObject();
        BasicDBList ands = new BasicDBList();
        BasicDBObject gte = new BasicDBObject("$gte", fromDay);
        BasicDBObject lte = new BasicDBObject("$lte", toDay);
        ands.add(new BasicDBObject(DAY, lte));
        ands.add(new BasicDBObject(DAY, gte));
        findObject.append("$and", ands);
        BasicDBObject sortObj = new BasicDBObject(DAY, 1);
        DBCursor cursor = collection.find(findObject).sort(sortObj);

        Map<String, Map<Double, Rate>> rateMap = new HashMap<>();
//        List<Stastic> stasticList = new ArrayList<>();
        while (cursor.hasNext()) {
            DBObject dbO = cursor.next();
            BasicDBList listHour = (BasicDBList) dbO.get(LIST_LOG);

            if (listHour != null) {
                listHour.forEach(hour -> {
                    DBObject obj = (DBObject) hour;
                    String serverHour = obj.get(HOUR) + MINUTE_SECOND;
                    long serverTime = parse(serverHour).getTime();
                    long clientTime = serverTime + addTime;
                    if (clientTime >= fromTime && clientTime <= toTime) {
                        String drawTypeId = (String) obj.get(DRAW_ID);
                        if (drawTypeId.equals(drawId)) {
                            String clientDay = format_yyyyMMddHH(new Date(clientTime));
                            double per = (double) obj.get(PERCENTAGE);
                            int pointBack = (int) obj.get(POINT_BACK);

                            if (rateMap.containsKey(clientDay)) {
                                Map<Double, Rate> currentRateMap = rateMap.get(clientDay);
                                if (currentRateMap.containsKey(per)) {
                                    Rate rate = currentRateMap.get(per);
                                    rate.setCount(rate.getCount() + 1);
                                    rate.setPoint(rate.getPoint() + pointBack);
                                } else {
                                    currentRateMap.put(per, new Rate(1, pointBack));
                                }
                            } else {
                                Map<Double, Rate> currentRateMap = new HashMap<>();
                                currentRateMap.put(per, new Rate(1, pointBack));
                                rateMap.put(clientDay, currentRateMap);
                            }


//                            System.out.println(clientDay);
//
//                            String time = (String) obj.get(HOUR);
//                            Stastic sta = new Stastic(per, pointBack, time);
//                            stasticList.add(sta);
                        }
                    }

                });
            }
        }
        rateMap.forEach((time, doubleRateMap) -> {
            System.out.println("time: " + time + "\n");
            doubleRateMap.forEach((percent, rate) -> {
                System.out.println("\tpercent: " + percent + " - count: " + rate.getCount() + " - point: " + rate.getPoint() + "\n");
            });

        });
        return rateMap;
    }

    private static Map<String, Map<Double, Rate>> analyticLog(List<Stastic> stastics) {
        Map<String, Map<Double, Rate>> rateMap = new HashMap<>();
        stastics.forEach(stastic -> {
            if (rateMap.containsKey(stastic.getTime())) {
                Map<Double, Rate> currentRateMap = rateMap.get(stastic.getTime());
                if (currentRateMap.containsKey(stastic.getPercent())) {
                    Rate rate = currentRateMap.get(stastic.getPercent());
                    rate.setCount(rate.getCount() + 1);
                    rate.setPoint(rate.getPoint() + stastic.getPointBack());
                } else {
                    currentRateMap.put(stastic.getPercent(), new Rate(1, stastic.getPointBack()));
                }
            } else {
                Map<Double, Rate> currentRateMap = new HashMap<>();
                currentRateMap.put(stastic.getPercent(), new Rate(1, stastic.getPointBack()));
                rateMap.put(stastic.getTime(), currentRateMap);
            }
        });
        return rateMap;
    }

    public static String format_yyyyMMddHH(Date d) {
        if (d == null) {
            return null;
        }
        StringBuilder buidler = new StringBuilder()
                .append(d.getYear() + JavaDate_StartYear)
                .append(format2DNumber(d.getMonth() + 1))
                .append(format2DNumber(d.getDate()))
                .append(format2DNumber(d.getHours()));
//                .append( getMillisecond( d ) );
        return buidler.toString();
    }


    public static Date parse(String yyyyMMddHHmmss) {
        if (yyyyMMddHHmmss == null || yyyyMMddHHmmss.isEmpty()) {
            return null;
        }
        int year = Integer.parseInt(yyyyMMddHHmmss.substring(0, 4));
        year -= JavaDate_StartYear;
        int month = Integer.parseInt(yyyyMMddHHmmss.substring(4, 6));
        month--;
        int date = Integer.parseInt(yyyyMMddHHmmss.substring(6, 8));
        int hour = Integer.parseInt(yyyyMMddHHmmss.substring(8, 10));
        int min = Integer.parseInt(yyyyMMddHHmmss.substring(10, 12));
        int second = Integer.parseInt(yyyyMMddHHmmss.substring(12, 14));
//            int millisecond = Integer.parseInt( yyyyMMddHHmmssSSS.substring( 14, 17 ) );
        Date d = new Date(year, month, date, hour, min, second);
//            d.setTime( d.getTime() + millisecond );
        return d;
    }


    public static String convertDate(long milisecond, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(milisecond));
    }

    public static long currentTime() {
        TimeZone tz = TimeZone.getDefault();
        return System.currentTimeMillis() - tz.getRawOffset();
    }

    private static String getAfterDay(String dateTime) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse_yyyy_MM_dd(dateTime));
        cal.add(Calendar.DATE, 1);
        return format_yyyyMMdd(cal.getTime());
    }

    private static String getLastDayOfMonth(String dateTime) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse_yyyy_MM_dd(dateTime));
        cal.add(Calendar.MONTH, 1);
        return format_yyyyMMdd(cal.getTime());
    }

    public static String getBeforeDay(String dateTime) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse_yyyy_MM_dd(dateTime));
        cal.add(Calendar.DATE, -1);
        return format_yyyyMMdd(cal.getTime());
    }

    public static long getFromTime(String dateTime) throws Exception {
        long time = parse_yyyy_MM_dd(dateTime).getTime();
        return time;
    }

    public static long getToTimeInDays(String dateTime) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse_yyyy_MM_dd(dateTime));
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.SECOND, -1);
        long time = cal.getTime().getTime();
        return time;
    }

    private static long getToTimeHours(String dateTime) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse_yyyy_MM_dd(dateTime));
        cal.add(Calendar.DATE, 1);
        cal.add(Calendar.SECOND, -1);
        long time = cal.getTime().getTime();
        return time;
    }


    public static Date parse_yyyy_MM_dd(String yyyyMMdd) {
        if (yyyyMMdd == null || yyyyMMdd.isEmpty()) {
            return null;
        }
        int year = Integer.parseInt(yyyyMMdd.substring(0, 4));
        year -= JavaDate_StartYear;
        int month = Integer.parseInt(yyyyMMdd.substring(5, 7));
        month--;
        int date = Integer.parseInt(yyyyMMdd.substring(8, 10));
//            int millisecond = Integer.parseInt( yyyyMMddHHmmssSSS.substring( 14, 17 ) );
        Date d = new Date(year, month, date);
//            d.setTime( d.getTime() + millisecond );
        return d;
    }

    public static String format_yyyyMMdd(Date d) {
        if (d == null) {
            return null;
        }
        StringBuilder buidler = new StringBuilder()
                .append(d.getYear() + JavaDate_StartYear)
                .append(format2DNumber(d.getMonth() + 1))
                .append(format2DNumber(d.getDate()));
//                .append( getMillisecond( d ) );
        return buidler.toString();
    }

    public static final int JavaDate_StartYear = 1900;

    private static String format2DNumber(int n) {
        return n > 9 ? String.valueOf(n) : (Zero + n);
    }

    private static final String Zero = "0";

}
