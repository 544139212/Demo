package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/***
 * 日期工具类
 * @author liheng
 * @since 1.0
 */
public class DateUtils {
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static final String DEFAULT_PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_PATTERN_DATE = "yyyy-MM-dd";
    public static final String DEFAULT_PATTERN_TIME = "HH:mm:ss";
    public static final String DEFAULT_PATTERN_ZONE_TIME = "yyyy-MM-dd'T'HH:mm:ss.sss'Z'";
    public static final String DEFAULT_PATTERN_DATETIME_SHORT = "yyyy-MM-dd HH:mm";


    public static String toString(Date date) {
        return toString(date, DEFAULT_PATTERN_DATETIME);
    }

    public static String toDateString(Date date) {
        return toString(date, DEFAULT_PATTERN_DATE);
    }

    public static String toTimeString(Date date) {
        return toString(date, DEFAULT_PATTERN_TIME);
    }

    public static String toString(Date date, String pattern) {
        String result = "";

        try {
            result = toFormat(new SimpleDateFormat(pattern), date);
        } catch (Exception e) {
            logger.error(String.format("Could not format '%tc' to String with pattern '%s'", date, pattern), e);
        }

        return result;
    }


    public static Date toDate(String strDate) {
        return toDate(strDate, DEFAULT_PATTERN_DATE);
    }

    public static Date toDate(String strDate, String pattern) {
        Date result = null;

        try {
            result = toParse(new SimpleDateFormat(pattern), strDate);
        } catch (Exception pe) {
            logger.error(String.format("Could not convert '%s' to Date with pattern '%s'", strDate, pattern), pe);
        }

        return result;
    }

    public static Timestamp toTimestamp(String strDate) {
        return toTimestamp(strDate, DEFAULT_PATTERN_DATETIME);
    }

    public static Timestamp toTimestamp(String strDate, String pattern) {
        Timestamp result = null;

        try {
            Date date = toParse(new SimpleDateFormat(pattern), strDate);
            if (date != null) {
                result = new Timestamp(date.getTime());
            }
        } catch (Exception pe) {
            logger.error(String.format("Could not convert '%s' to Timestamp with pattern '%s'", strDate, pattern), pe);
        }

        return result;
    }

    /**
     * 时区转换
     * @param strDate
     * @param pattern
     * @return
     */
    public  static  Date  toZoneDate(String strDate, String pattern){
        if(StringUtils.isEmpty(pattern)){
            pattern = DEFAULT_PATTERN_ZONE_TIME;
        }
        SimpleDateFormat  dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = dateFormat.parse(strDate);
        }catch (Exception e){
            logger.error(String.format("Could not convert '%s' to Timestamp with pattern '%s'", strDate, pattern), e);
        }
       return date;
    }

    public static java.sql.Date getDate() {
        return new java.sql.Date(System.currentTimeMillis());
    }

    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    private static Date toParse(SimpleDateFormat df, String dateString) {
        Date date = null;

        logger.debug("parse '{}' to Date with pattern '{}'", dateString, df.toPattern());

        if (dateString == null) {
            logger.error("strDate is null!");
        } else {
            try {
                date = new Date(df.parse(dateString).getTime());
            } catch (Exception e) {
                logger.error(String.format("Could not parse '%s' to Date with pattern '%s'", dateString, df.toPattern()), e);
            }
        }

        return date;
    }

    private static String toFormat(SimpleDateFormat df, Date date) {
        String dateStr = null;

        logger.debug("format {} to String with pattern '{}'", date, df.toPattern());

        if (date == null) {
            logger.error("aDate is null!");
        } else {
            try {
                dateStr = df.format(date);
            } catch (Exception e) {
                logger.error(String.format("Could not format '%tc' to String with pattern '%s'", date, df.toPattern()), e);
            }
        }
        return dateStr;
    }




}