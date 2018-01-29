package com.genimous.net;

/**
 * Created by wudi on 18/1/16.
 */

public class NetAPI {

    /**
     * 服务器返回成功正确状态码
     */
    public static final int SERVER_SUCCESS = 2000;

    public static final int SERVER_FAILED = 1;

    public static String HTTP_STATUS_SUCCESS = "success";


    public static final String RELEASE_URL = "http://api.retaozi.com/";
    public static final String TEST_URL = "http://172.16.30.60:9191/";

    public static final String POST_CAPTCHA_CODE = RELEASE_URL + "mobile";
    public static final String GET_USER_INFO = RELEASE_URL + "info";
    public static final String GET_TRYGAME_LIST = RELEASE_URL + "list";
}
