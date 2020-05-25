package com.example.andy.myutils.Utils;

import android.renderscript.Sampler;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckString {

    private static boolean checkStringNullOrEmpty(String string, String methodName){
        if (string == null || string.isEmpty()){
            LogUtil.d("MyLog","方法" + methodName + "传入方法的参数为null或''！");
            return false;
        }
        return true;
    }

    private static boolean checkArrayIndexOutOfBoundsException(String mString, int startIndex, int endIndex, String methodName){
        mString.toCharArray();
        if(startIndex < 0 || mString.length() < endIndex){
            LogUtil.d("MyLog","方法" + methodName + "传入方法的索引越界''！");
            return false;
        }
        return true;
    }

    public static String methodName(){
        Exception exception = new Exception();
        String methodName = exception.getStackTrace()[1].getMethodName();
        //String methodName = exception.getStackTrace()[0].getClassName());//调用者上级类名
        //String methodName = exception.getStackTrace()[1].getClassName());//调用者上级的上级类名
        //String methodName = exception.getStackTrace()[0].getMethodName();//调用者上级的方法名
        //String methodName = exception.getStackTrace()[1].getMethodName();//调用者上级的上级方法名
        //String methodName = exception.getStackTrace()[0].getLineNumber());//当前方法行号
        //String methodName = exception.getStackTrace()[1].getLineNumber());//调用方法行号
        return methodName;
    }

    /**D
     *数字
     */

    //字符串是否全为数字 true全是数字 false不全是数字
    public static boolean allDigital(String mString) {
        String mMethodName = methodName();
        boolean flag = true;
        Pattern mPattern = Pattern.compile("[^0-9]");
        Matcher mMatcher = mPattern.matcher(mString);
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            if (mMatcher.find()) {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    //字符串是否具有数字 true有数字 false没有数字
    public static boolean withDigital(String mString){
        String mMethodName = methodName();
        Pattern mPattern = Pattern.compile("[0-9]");
        Matcher mMatcher = mPattern.matcher(mString);
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            if(mMatcher.find()){
                //LogUtil.d("MyLog","参数：" + mString + "有数字");
                return true;
            }
        }
        //LogUtil.d("MyLog","参数：" + mString + "没有数字");
        return false;
    }

    //字符串开头是否为数字 true开头是数字 false开头不是数字
    public static boolean startWithDigital(String mString){
        String mMethodName = methodName();
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            if (Character.isDigit(mString.charAt(0))){
                //LogUtil.d("MyLog","参数：" + mString + "是数字开头");
                return true;
            }
        }
        //LogUtil.d("MyLog","参数：" + mString + "不是数字开头");
        return false;
    }

    //提取字符串中所有的数字组成字符串
    public static String cutDigitalToString(String mString) {
        String mMethodName = methodName();
        String mDigital = "";
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(mString);
            mDigital = matcher.replaceAll("").trim();
        }
        //LogUtil.d("MyLog","参数：" + mString + "中的数字是" + mDigital);
        return mDigital;
    }


    /**
     * 字母
     */
    //字符串是否全为字母 true全是字母 false不全是字母
    public static boolean allLetter(String mString){
        String mMethodName = methodName();
        boolean flag = true;
        Pattern mPattern = Pattern.compile("[^a-zA-Z]");
        Matcher mMatcher = mPattern.matcher(mString);
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            if(mMatcher.find()) {
                flag = false;
            }
        } else {
            flag = false;
        }
        return flag;
    }

    //字符串是否具有字母 true有字母 false没字母
    public static boolean withLetter(String mString){
        String mMethodName = methodName();
        Pattern mPattern = Pattern.compile("[a-zA-Z]");
        Matcher mMatcher = mPattern.matcher(mString);
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            if(mMatcher.find()){
                //LogUtil.d("MyLog","参数：" + mString + "中具有字母");
                return true;
            }
        }
        //LogUtil.d("MyLog","参数：" + mString + "中不具有字母");
        return false;
    }

    //字符串开头是否为字母 true开头是字母 false开头不是字母
    public static boolean startWithLetter(String mString){
        String mMethodName = methodName();
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            char mChar = mString.charAt(0);
            if(((mChar >= 'a' && mChar <= 'z')   ||   (mChar >= 'A' && mChar <= 'Z' ))){
                //LogUtil.d("MyLog","参数：" + mString + "是字母开头");
                return true;
            }
        }
        //LogUtil.d("MyLog","参数：" + mString + "不是字母开头");
        return false;
    }

    //提取字符串中所有的字母组成字符串
    public static String cutLetterToStirng(String mString) {
        String mMethodName = methodName();
        String mLetter = "";
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            Pattern mPattern = Pattern.compile("[^a-zA-Z]");
            Matcher mMatcher = mPattern.matcher(mString);
            mLetter = mMatcher.replaceAll("").trim();
        }
        LogUtil.d("MyLog","参数：" + mString + "中的字母是" + mLetter);
        return mLetter;
    }


    /**
     * 中文
     */
    //字符串是否全为中文
    public static boolean whetherGB(String mString){
        String mMethodName = methodName();
        boolean flag = true;
        Pattern mPattern = Pattern.compile("[^\u4e00-\u9fa5]");
        Matcher mMatcher = mPattern.matcher(mString);
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            if(mMatcher.find()){
                flag = false;
            }
        }else {
            flag = false;
        }
        return true;
    }

    //字符串中是否中文 true有中文 false没有中文
    public static boolean withGB(String mString) {
        String mMethodName = methodName();
        Pattern mPattern = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher mMatcher = mPattern.matcher(mString);
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            if(mMatcher.find()){
                //LogUtil.d("MyLog","参数：" + mString + "有中文");
                return true;
            }
        }
        //LogUtil.d("MyLog","参数：" + mString + "没有中文");
        return false;
    }

    //提取字符串中所有的中文组成字符串
    public static String cutGBToString(String mString) {
        String mMethodName = methodName();
        String mGB = "";
        Pattern mPattern = Pattern.compile("[^\u4e00-\u9fa5]");
        Matcher mMatcher = mPattern.matcher(mString);
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            mGB = mMatcher.replaceAll("").trim();
            //LogUtil.d("MyLog","参数：" + mString + "中的中文是" + mGB);
        }
        return mGB;
    }


    /**
     * 空格
     */
    //获取字符串中第一个空格的索引
    public static int FristSpaceIndex(String mString){
        String mMethodName = methodName();
        int spaceIndex = -1;
        char[] chars = mString.toCharArray();
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            for(int i=0; i < chars.length; i++){
                if(chars[i] == ' '){
                    spaceIndex = i;
                    //LogUtil.d("MyLog","参数：" + mString + "中第一个空格的索引为" + spaceIndex);
                    return spaceIndex;
                }
            }
        }
        return spaceIndex;
    }

    //获取从字符指定的起始区间后的第一个空格的索引
    public static int  intervalSpaceIndex(String mString,int statIndex, int endIndex){
        String mMethodName = methodName();
        int spaceIndex = -1;
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            if (checkArrayIndexOutOfBoundsException(mString,statIndex,endIndex,mMethodName)){
                char[] chars = mString.toCharArray();
                for(int i = statIndex; i < endIndex; i++){
                    if(chars[i] == ' '){
                        spaceIndex = i;
                        //LogUtil.d("MyLog","参数：" + mString + "中" + statIndex + "到" + endIndex + "中第一个空格的索引为" + spaceIndex);
                        return spaceIndex;
                    }
                }
            }
        }
        //LogUtil.d("MyLog","参数：" + mString + "中" + statIndex + "到" + endIndex + "中第一个空格的索引为" + spaceIndex);
        return spaceIndex;
    }


    /**
     * 验证手机号码是否合法
     */
    public static boolean isMobileNumber(String mString) {
        String mMethodName = methodName();
        Pattern mPattern = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$");
        Matcher mMatcher = mPattern.matcher(mString);
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            if (mMatcher.matches()) {
                //LogUtil.d("MyLog","参数：" + mString + "是手机号");
                return true;
            } else {
                //LogUtil.d("MyLog","参数：" + mString + "不是手机号");
                return false;
            }
        } else {
            //LogUtil.d("MyLog","参数：" + mString + "不是手机号");
            return false;
        }
    }


    /**
     * 验证电子邮箱是否合法
     */
    public static boolean isEmail(String mString){
        String mMethodName = methodName();
        Pattern mPattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher mMatcher = mPattern.matcher(mString);
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            if (mMatcher.matches()) {
                //LogUtil.d("MyLog","参数：" + mString + "是电子邮箱");
                return true;
            } else {
                //LogUtil.d("MyLog","参数：" + mString + "不是电子邮箱");
                return false;
            }
        } else {
            //LogUtil.d("MyLog","参数：" + mString + "不是电子邮箱");
            return false;
        }
    }


    /**
     * String与List的转换
     */
    public static List<String> stringToList(String mString){
        List<String> mList = new ArrayList<>();
        String mMethodName = methodName();
        if (checkStringNullOrEmpty(mString, mMethodName)) {
            char[] chars = mString.trim().toCharArray();
            for (int i = 0; i < chars.length; i++) {
                mList.add(String.valueOf(chars[i]));
            }
        }
        //LogUtil.d("MyLog",String.valueOf(mList));
        return mList;
    }

    /**
     * List转String
     */
    public static String ListToString(List<?> list) {
        StringBuffer mStringBuffer = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null || list.get(i) == "") {
                    continue;
                }
                if (list.get(i) instanceof List) {        //List中的值为List
                    mStringBuffer.append(ListToString((List<?>) list.get(i)));
                } else if (list.get(i) instanceof Map) {  //List中的值为map
                    mStringBuffer.append(MapToString((Map<?, ?>) list.get(i)));
                } else {                                  //List中的值为String
                    mStringBuffer.append(list.get(i));
                }
            }
        }
        //LogUtil.d("MyLog",mStringBuffer.toString());
        return mStringBuffer.toString();
    }

    /**
     * Map转String
     */
    public static String MapToString(Map<?, ?> map) {
        StringBuffer mStringBuffer = new StringBuffer();
        // 遍历map
        for (Object obj : map.keySet()) {
            if (obj == null) {
                continue;
            }
            Object key = obj;
            Object value = map.get(key);
            if (value instanceof List<?>) {               //Map中的值为List
                mStringBuffer.append(ListToString((List<?>) value));
                //mStringBuffer.append(key.toString() + ListToString((List<?>) value));
            } else if (value instanceof Map<?, ?>) {      //Map中的值为List
                mStringBuffer.append(MapToString((Map<?, ?>) value));
                //mStringBuffer.append(key.toString() + MapToString((Map<?, ?>) value));
            } else {                                      //Map中的值为String
                mStringBuffer.append(value.toString());
            }
        }
        //LogUtil.d("MyLog",mStringBuffer.toString());
        return mStringBuffer.toString();
    }

    /**
     * String转Map
     */
    public static Map<String, Object> StringToMap(String mapText) {
        String SEP2 = "|";
        String SEP3 = "=";
        if (mapText == null || mapText.equals("")) {
            return null;
        }
        mapText = mapText.substring(1);

        mapText = mapText;

        Map<String, Object> map = new HashMap<String, Object>();
        String[] text = mapText.split("\\" + SEP2); // 转换为数组
        for (String str : text) {
            String[] keyText = str.split(SEP3); // 转换key与value的数组
            if (keyText.length < 1) {
                continue;
            }
            String key = keyText[0]; // key
            String value = keyText[1]; // value
            if (value.charAt(0) == 'M') {
                Map<?, ?> map1 = StringToMap(value);
                map.put(key, map1);
            } else if (value.charAt(0) == 'L') {
                List<?> list = StringToList(value);
                map.put(key, list);
            } else {
                map.put(key, value);
            }
        }
        return map;
    }


    /**
     * String转List
     */
    public static List<Object> StringToList(String listText) {
        String SEP1 = "#";
        if (listText == null || listText.equals("")) {
            return null;
        }
        listText = listText.substring(1);

        listText = listText;

        List<Object> list = new ArrayList<Object>();
        String[] text = listText.split(SEP1);
        for (String str : text) {
            if (str.charAt(0) == 'M') {
                Map<?, ?> map = StringToMap(str);
                list.add(map);
            } else if (str.charAt(0) == 'L') {
                List<?> lists = StringToList(str);
                list.add(lists);
            } else {
                list.add(str);
            }
        }
        return list;
    }


}
