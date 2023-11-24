package com.meerkat.vaccine.order.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zxw
 * @date 2022/04/12 11:46
 */
public class NameCheckUtil {

    private static final Pattern PATTERN_UP = Pattern.compile("^[\\u2E80-\\u9FFF]+$");

    public static boolean checkName(String name) {
        Matcher matcher = PATTERN_UP.matcher(name);
        return matcher.matches();
    }


}
