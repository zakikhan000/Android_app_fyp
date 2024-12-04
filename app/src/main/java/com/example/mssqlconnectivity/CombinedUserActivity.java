package com.example.mssqlconnectivity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CombinedUserActivity extends AppCompatActivity {
    private ListView listView;
    private CombinedUserAdapter adapter;
    private List<CombinedUser> combinedUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined_user);

        listView = findViewById(R.id.listView);
        combinedUsers = new ArrayList<>();
        adapter = new CombinedUserAdapter(this, combinedUsers);
        listView.setAdapter(adapter);

        fetchCombinedUserData();
    }

    private void fetchCombinedUserData() {
        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<CombinedDataResponse> call = apiService.getCombinedUserData();
        call.enqueue(new Callback<CombinedDataResponse>() {
            @Override
            public void onResponse(Call<CombinedDataResponse> call, Response<CombinedDataResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    combinedUsers.clear();
                    combinedUsers.addAll(response.body().getCombinedData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CombinedDataResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
