package com.genimous.net;

/**
 * Created by wudi on 18/1/16.
 */

public class NetAPI {

    /**
     * 服务器返回成功正确状态码
     */
    public static final int SERVER_SUCCESS=0;

    public static final int SERVER_FAILED=1;


    public static final String RELEASE_URL = "http://api.360fangxindai.com:9191/";
    public static final String TEST_URL = "http://172.16.30.60:9191/";

    public static final String POST_CAPTCHA_CODE = RELEASE_URL+"v1/api/phone/sendCode";
}
