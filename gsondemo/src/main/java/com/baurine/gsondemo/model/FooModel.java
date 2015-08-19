package com.baurine.gsondemo.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by baurine on 8/19/15.
 */
public class FooModel {

    public static class Foo1 {
        public int id;
        public String body;
        public float number;
        @SerializedName("created_at")
        public String createAt;
    }

    public static class Foo2 {
        public int id;
        public String body;
        public float number;
        @SerializedName("created_at")
        public Date createAt;
    }

    public static class Foo3 {
        public int id;
        public String body;
        public float number;
        @SerializedName("created_at")
        public Date createAt;
        public Bar data;

        public static class Bar {
            public int id;
            public String name;
        }
    }
}
