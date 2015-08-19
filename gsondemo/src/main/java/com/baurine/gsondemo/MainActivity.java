package com.baurine.gsondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.baurine.gsondemo.global.Constants;
import com.baurine.gsondemo.model.FooModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

        // usage2
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        Gson gson = builder.create();
        FooModel.Foo2 foo2 = gson.fromJson(Constants.TEST_FOO_JSON_1, FooModel.Foo2.class);
        textView2.setText(foo2.createAt.toString());

        // usage3
        FooModel.Foo3 foo3 = gson.fromJson(Constants.TEST_FOO_JSON_NEST, FooModel.Foo3.class);
        textView3.setText(foo3.data.name);
    }
}
