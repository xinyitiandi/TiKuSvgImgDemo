package com.img.demo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/10/9.
 */

public class StringUtils {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateFormat dateMinuteFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static DateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat dateFormatMonth = new SimpleDateFormat("yyyy-MM");
    private static DateFormat dateFormatDateDot = new SimpleDateFormat("yyyy.MM.dd");
    private static DateFormat dateFormatTime = new SimpleDateFormat("HH:mm:ss");
    private static DateFormat timeMinuteFormat = new SimpleDateFormat("HH:mm");


    /**
     * 是否为空串
     * @param text
     * @return
     */
    public static boolean isTrimEmpty(String text) {
        return text == null || "".equals(text.trim());

    }

    /**
     * 将时间转换成时间戳，时间字符串的格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param timeText
     * @return
     * @throws Exception
     */
    public static long convertTime(String timeText) throws Exception {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return dateFormat.parse(timeText).getTime();
    }

    /**
     * 将时间戳转换成日期，时间字符串的格式为：yyyy-MM-dd
     * @param time
     * @return
     */
    public static String convertTimeToDate(long time) {
        return dateFormatDate.format(new Date(time));
    }

    /**
     * 将时间戳转换成日期，时间字符串的格式为：yyyy-MM-dd
     * @param time
     * @return
     */
    public static String convertTimeToMonth(long time) {
        return dateFormatMonth.format(new Date(time));
    }

    /**
     * 将已知的年月日转换成日期，时间字符串的格式为：yyyy-MM-dd
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static String convertTimeToDate(int year, int monthOfYear, int dayOfMonth) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return dateFormatDate.format(gregorianCalendar.getTime());
    }

    /**
     * 将已知的年月日转换成日期，时间字符串的格式为：yyyy.MM.dd
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static String convertTimeToDateDot(int year, int monthOfYear, int dayOfMonth) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return dateFormatDateDot.format(gregorianCalendar.getTime());
    }

    /**
     * 将时间戳转换成日期，时间字符串的格式为：yyyy-MM-dd
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static long convertDateToTime(int year, int monthOfYear, int dayOfMonth) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return gregorianCalendar.getTime().getTime();
    }

    /**
     * 将已知的年月日时分秒转换成日期时间，时间字符串的格式为：yyyy-MM-dd HH:mm
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @param hour
     * @param minute
     * @return
     */
    public static String convertTimeToDate(int year, int monthOfYear, int dayOfMonth, int hour, int minute) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth, hour, minute);
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return dateMinuteFormat.format(gregorianCalendar.getTime());
    }

    /**
     * 将已知的时分转换成时间，时间字符串的格式为：HH:mm
     * @param hour
     * @param minute
     * @return
     */
    public static String convertTimeToHM(int hour, int minute) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(0, 0, 0, hour, minute);
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return timeMinuteFormat.format(gregorianCalendar.getTime());
    }

    /**
     * 将时间戳转换成时分秒，时间字符串的格式为：HH:mm:ss
     * @param time
     * @return
     */
    public static String convertTimeToTime(long time) {
        return dateFormatTime.format(new Date(time));
    }

    /**
     * 将intArray中的int通过英文逗号“,”拼接起来，得到string
     * @param integers
     * @return
     */
    public static String intArraytoString(ArrayList<Integer> integers) {
        StringBuilder sb = new StringBuilder();
        if (integers != null && integers.size() > 0) {
            sb.append(integers.get(0));
            for (int i = 1; i < integers.size(); i++) {
                sb.append(",");
                sb.append(integers.get(i));
            }
        }

        return sb.toString();
    }

    /**
     * 将参数phone转成中间4个数字为*的号码。
     * @param phone
     * @return
     */
    public static String getHidePhone(String phone) {
        if (!isTrimEmpty(phone) && phone.length() > 7) {
            StringBuilder phoneSB = new StringBuilder();
            phoneSB.append(phone.substring(0, 3));
            phoneSB.append("****");
            phoneSB.append(phone.substring(7, phone.length()));
            phone = phoneSB.toString();
        }

        return phone;
    }

    /**
     * @param targetStr 要处理的字符串
     * @description 切割字符串，将文本和img标签碎片化，如"ab<img>cd"转换为"ab"、"<img>"、"cd"
     */
    public static List<String> cutStringByImgTag(String targetStr) {
        List<String> splitTextList = new ArrayList<String>();
        Pattern pattern = Pattern.compile("<img.*?src=\\\"(.*?)\\\".*?>");
        Matcher matcher = pattern.matcher(targetStr);
        int lastIndex = 0;
        while (matcher.find()) {
            if (matcher.start() > lastIndex) {
                splitTextList.add(targetStr.substring(lastIndex, matcher.start()));
            }
            splitTextList.add(targetStr.substring(matcher.start(), matcher.end()));
            lastIndex = matcher.end();
        }
        if (lastIndex != targetStr.length()) {
            splitTextList.add(targetStr.substring(lastIndex, targetStr.length()));
        }
        return splitTextList;
    }

    /**
     * 获取img标签中的src值
     * @param content
     * @return
     */
    public static String getImgSrc(String content){
        String str_src = null;
        //目前img标签标示有3种表达式
        //<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
        //开始匹配content中的<img />标签
        Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
        Matcher m_img = p_img.matcher(content);
        boolean result_img = m_img.find();
        if (result_img) {
            while (result_img) {
                //获取到匹配的<img />标签中的内容
                String str_img = m_img.group(2);

                //开始匹配<img />标签中的src
                Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
                Matcher m_src = p_src.matcher(str_img);
                if (m_src.find()) {
                    str_src = m_src.group(3);
                }
                //结束匹配<img />标签中的src

                //匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
                result_img = m_img.find();
            }
        }
        return str_src;
    }
}
