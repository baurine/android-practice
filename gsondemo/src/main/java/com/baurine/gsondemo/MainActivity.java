package com.baurine.gsondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.baurine.gsondemo.global.Constants;
import com.baurine.gsondemo.model.FooModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testGson();
    }

    private void testGson() {
        TextView textView1 = (TextView) findViewById(R.id.tv_content_1);
        TextView textView2 = (TextView) findViewById(R.id.tv_content_2);
        TextView textView3 = (TextView) findViewById(R.id.tv_content_3);
        TextView textView4 = (TextView) findViewById(R.id.tv_content_4);

        // usage1
        FooModel.Foo1 foo1 = new Gson().fromJson(Constants.TEST_FOO_JSON_1, FooModel.Foo1.class);
        textView1.setText(foo1.createAt);

        // usage2: GsonBuilder
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();
        FooModel.Foo2 foo2 = gson.fromJson(Constants.TEST_FOO_JSON_1, FooModel.Foo2.class);
        textView2.setText(foo2.createAt.toString());

        // usage3: nested json object
        FooModel.Foo3 foo3 = gson.fromJson(Constants.TEST_FOO_JSON_NEST, FooModel.Foo3.class);
        textView3.setText(foo3.data.name);

        // usage4: array
        Type listType = new TypeToken<ArrayList<FooModel.Foo1>>() {
        }.getType();
        ArrayList<FooModel.Foo1> foo1s = new Gson().fromJson(Constants.TEST_FOO_JSON_ARRAY,
                listType);
        textView4.setText(foo1s.get(foo1s.size() - 1).body);
    }
}
