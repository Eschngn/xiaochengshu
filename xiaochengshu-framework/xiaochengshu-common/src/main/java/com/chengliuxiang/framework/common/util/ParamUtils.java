package com.chengliuxiang.framework.common.util;

import java.util.regex.Pattern;

public final class ParamUtils {
    private ParamUtils() {
    }

    private static final int NICK_NAME_MIN_LENGTH = 2;
    private static final int NICK_NAME_MAX_LENGTH = 24;

    private static final String NICK_NAME_REGEX = "[!@#$%^&*(),.?\":{}|<>]";

    public static boolean checkNickname(String nickname) {
        if (nickname.length() < NICK_NAME_MIN_LENGTH || nickname.length() > NICK_NAME_MAX_LENGTH) {
            return false;
        }
        Pattern pattern = Pattern.compile(NICK_NAME_REGEX);
        return !pattern.matcher(nickname).find();
    }

    private static final int ID_MIN_LENGTH = 6;
    private static final int ID_MAX_LENGTH = 15;

    private static final String ID_REGEX = "^[a-zA-Z0-9_]+$";

    public static boolean checkXiaochengshuId(String xiaochengshuId) {
        if (xiaochengshuId.length() < ID_MIN_LENGTH || xiaochengshuId.length() > ID_MAX_LENGTH) {
            return false;
        }
        Pattern pattern = Pattern.compile(ID_REGEX);
        return pattern.matcher(xiaochengshuId).matches();
    }

    public static boolean checkLength(String str, int length) {
        if (str.isEmpty() || str.length() > length) {
            return false;
        }
        return true;
    }

}
