package com.mssd.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassPathResource {
    public boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4])|(18[0,1,2,3,4,5-9])|(17[0-8])|(147))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}