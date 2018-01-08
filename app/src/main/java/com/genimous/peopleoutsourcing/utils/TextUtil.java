package com.genimous.peopleoutsourcing.utils;

import android.content.Context;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wudi on 18/1/8.
 */

public class TextUtil {
    /**
     * 判断一个List 是否为null 或者是否长度为0
     *
     * @param list
     * @return
     */
    public static boolean isListEmpty(List list) {
        if (list == null) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为null 或者 长度为0 或者 只包含空字符
     *
     * @param pString
     * @return
     */
    public static boolean isEmptyString(String pString) {
        if (pString == null) {
            return true;
        }
        if (pString.length() == 0 || pString.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取字符串的Base64格式
     */
    public static String getBase64(String target) {
        String url = "";
        if (!TextUtils.isEmpty(target)) {
            byte[] bytes = Base64.encode(target.getBytes(), Base64.DEFAULT);
            try {
                url = new String(bytes, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            url = url.replace("=", "").replace("\n", "").replace("\r", "");
        }
        return url;
    }

    public static String longToDate(long time) {
        if (time != 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date dt = new Date(time);
            String sDateTime = sdf.format(dt);
            return sDateTime;
        } else {
            return "";
        }
    }

    public static String numConvert(int num) {
        String strNum;
        if (num > 0) {
            if (num >= 10000) {
                String currentNum = String.valueOf(num);
                String hundreds = currentNum.substring(currentNum.length() - 3, currentNum.length() - 2);
                int remainder = num % 10000 / 1000;
                num = num / 10000;
                if (remainder > 0) {
                    if (hundreds.equals("0")) {
                        hundreds = "";
                    }
                    strNum = num + "." + remainder + hundreds + "w";
                } else {
                    strNum = num + "w";
                }
            } else {
                strNum = num + "";
            }
            return strNum;
        } else {
            return "";
        }
    }

    public static String monthRepayment(float rate, int money, int deadline) {
        if (money > 0) {
            float repay = (money * (1 + deadline * rate / 100)) / deadline;
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            return decimalFormat.format(repay);
        } else {
            return "";
        }
    }

    public static String interest(float rate, int money, int deadline) {
        if (money > 0) {
            float interest = rate * money * deadline / 100;
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            return decimalFormat.format(interest);
        } else {
            return "";
        }
    }

    public static void changeTextSize(final Context context, final TextView textView) {
        if (textView.getText().length() > 7) {
            final TextPaint paint = textView.getPaint();
            textView.post(new Runnable() {

                @Override
                public void run() {
                    int textSize = (int) (DimenUtils.px2dp(context, textView.getTextSize()) * (textView.getWidth() / paint.measureText(textView.getText().toString())));
                    if (textSize > 2) {
                        textView.setTextSize(textSize - 2);
                    } else {
                        textView.setTextSize(1);
                    }
                }
            });
        } else {
            textView.setTextSize(26);
        }
    }

    public static long stringToLong(String strTime, String formatType) {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }
}
