package com.baurine.gsondemo.global;

/**
 * Created by baurine on 8/19/15.
 */
public class Constants {
    public static final String TEST_FOO_JSON_1 = "{" +
            "\"id\": 100," +
            "\"body\": \"It is my post\"," +
            "\"number\": 0.13," +
            "\"created_at\": \"2015-08-19 12:14:34\"" +
            "}";

    public static final String TEST_FOO_JSON_NEST = "{" +
            "\"id\": 100," +
            "\"body\": \"It is my post\"," +
            "\"number\": 0.13," +
            "\"created_at\": \"2015-08-19 12:14:34\"," +
            "\"data\": {" +
            "\"id\": 200," +
            "\"name\": \"bar\"" +
            "}" +
            "}";

    public static final String TEST_FOO_JSON_ARRAY = "[{" +
            "\"id\": 100," +
            "\"body\": \"It is my post 1\"," +
            "\"number\": 0.13," +
            "\"created_at\": \"2015-08-19 12:14:34\"" +
            "},{" +
            "\"id\": 101," +
            "\"body\": \"It is my post 2\"," +
            "\"number\": 0.14," +
            "\"created_at\": \"2015-08-19 14:58:34\"" +
            "}]";
}
