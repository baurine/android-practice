package com.baurine.volleydemo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTv;
    private ImageView mIv;
    private RequestQueue mRequestQueue;
    private ProgressDialog mProgressDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv = (TextView) findViewById(R.id.tv_content);
        mIv = (ImageView) findViewById(R.id.iv_image);
        mRequestQueue = Volley.newRequestQueue(this);
        mProgressDlg = new ProgressDialog(this);
        mProgressDlg.setMessage("Loading...");

        Button btnReqString = (Button) findViewById(R.id.btn_reqstr);
        btnReqString.setOnClickListener(this);
        Button btnReqJson = (Button) findViewById(R.id.btn_reqjson);
        btnReqJson.setOnClickListener(this);
        Button btnReqImage = (Button) findViewById(R.id.btn_reqimg);
        btnReqImage.setOnClickListener(this);
        Button btnReqPost = (Button) findViewById(R.id.btn_reqpost);
        btnReqPost.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    private void showResult(String content) {
        mProgressDlg.hide();
        mIv.setVisibility(View.GONE);
        mTv.setVisibility(View.VISIBLE);
        mTv.setText(content);
    }

    private void showImage(Bitmap bitmap) {
        mProgressDlg.hide();
        mTv.setVisibility(View.GONE);
        mIv.setVisibility(View.VISIBLE);
        mIv.setImageBitmap(bitmap);
    }

    private void testStringReq() {
        String url = "http://httpbin.org/html";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showResult(error.getMessage());
                    }
                });

        mRequestQueue.add(stringRequest);
    }

    private void testJsonReq() {
        String url = "http://httpbin.org/get?site=code&network=tutsplus";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            response = response.getJSONObject("args");
                            String site = response.getString("site");
                            String network = response.getString("network");
                            showResult("site: " + site + "\nnetwork: " + network);
                        } catch (JSONException err) {
                            showResult(err.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showResult(error.getMessage());
                    }
                });
        mRequestQueue.add(jsonRequest);
    }

    private void testImageReq() {
        String url = "http://pic2.zhimg.com/88281a6c6f0c1925e4714979e3ec0ee6_r.jpg";

        ImageRequest imgRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        showImage(response);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showResult(error.getMessage());
                    }
                });
        mRequestQueue.add(imgRequest);
    }

    private void testPostReq() {
        String url = "http://httpbin.org/post";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showResult(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showResult(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("site", "code");
                params.put("network", "tutplus");
                return params;
            }
        };

        mRequestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        mProgressDlg.show();
        switch (v.getId()) {
            case R.id.btn_reqstr:
                testStringReq();
                break;
            case R.id.btn_reqjson:
                testJsonReq();
                break;
            case R.id.btn_reqimg:
                testImageReq();
                break;
            case R.id.btn_reqpost:
                testPostReq();
            default:
                break;
        }
    }
}
