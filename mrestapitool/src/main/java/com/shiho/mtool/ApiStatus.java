package com.shiho.mtool;

/**
 * Created by david5_huang on 2017/1/26.
 */

public interface ApiStatus {
    public static final int HTTP_SUCCESS = 200;
    public static final int API_JAVA_EXCEPTION = 998;
    public static final int API_REQUEST_NULL = 999;

    public static final String API_REQUEST_NULL_MSG = "Api request object cannot null.";
    public static final String API_RESPONSE_NULL_MSG = "Parse json string cannot null.";

    public static final String API_PARSEOBJ_NULL_MSG = "Json parse object cannot null.";
    public static final String API_PARSE_JSON2OBJ_NULL_MSG = "Parse json string fail.";

}
