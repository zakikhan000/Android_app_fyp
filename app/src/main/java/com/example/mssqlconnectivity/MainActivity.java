package com.example.mssqlconnectivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mssqlconnectivity.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ImageGeneratingApi.Adapter;
import ImageGeneratingApi.ImageModel;
import ImageGeneratingApi.SpacingItemDecoration;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<ImageModel> imageList;
    Adapter adapter;
    ImageModel imageModel1;

    String gg;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imageList = new ArrayList<>();
        adapter = new Adapter(imageList, MainActivity.this);
        binding.recyclerView.setAdapter(adapter);
        GridLayoutManager ggm = new GridLayoutManager(MainActivity.this, 2);
        binding.recyclerView.setLayoutManager(ggm);

        // Apply item decoration to reduce gap
        int spacing = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing);
        binding.recyclerView.addItemDecoration(new SpacingItemDecoration(spacing));

        binding.btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageList.clear();
                String question = binding.editText.getText().toString().trim();
                gg = question;
                url = "https://lexica.art/api/v1/search?q=" + gg;
                if (!question.equals("")) {
                    Toast.makeText(MainActivity.this, "Message is sent", Toast.LENGTH_SHORT).show();
                    callApi(gg);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter image title", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void callApi(String questiono) {
        binding.prgressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonArray;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    jsonArray = jsonObject.getJSONArray("images");
                    Log.e("responseData", response.toString());
                    Log.e("ImageUrl", jsonArray.getJSONObject(0).getString("srcSmall"));

                    for (int i = 0; i < jsonArray.length(); i++) {
                        imageModel1 = new ImageModel(jsonArray.getJSONObject(i).getString("srcSmall"));
                        imageList.add(imageModel1);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                binding.prgressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("onErrorResponse", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
