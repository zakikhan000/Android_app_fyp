package com.example.mssqlconnectivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CombinedDataAdapter extends RecyclerView.Adapter<CombinedDataAdapter.CombinedDataViewHolder> {

    private List<CombinedDataResponse.CombinedData> combinedDataList;

    public CombinedDataAdapter(List<CombinedDataResponse.CombinedData> combinedDataList) {
        // Filter the list to include only items where getContent() and getTitle() are not null
        this.combinedDataList = new ArrayList<>();
        for (CombinedDataResponse.CombinedData data : combinedDataList) {
            if (data.getContent() != null && data.getTitle() != null) {
                this.combinedDataList.add(data);
            }
        }
    }

    @Override
    public CombinedDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_combined_data, parent, false);
        return new CombinedDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CombinedDataViewHolder holder, int position) {
        CombinedDataResponse.CombinedData data = combinedDataList.get(position);

        // Username and visibility logic
        if ("visible".equalsIgnoreCase(data.getUsernameVisibility())) {
            holder.usernameTextView.setText(data.getUsername());
        } else {
            holder.usernameTextView.setText(data.getAnonymousName());
        }

        // First name and visibility logic
        if ("visible".equalsIgnoreCase(data.getNameVisibility())) {
            holder.firstNameTextView.setText(data.getFirstName());
        } else {
            holder.firstNameTextView.setVisibility(View.GONE); // Hide the first name field
        }

        holder.TitlePost.setText(data.getTitle());
        holder.ageTextView.setText(String.valueOf(data.getContent()));

        // Profile image visibility logic
        if ("visible".equalsIgnoreCase(data.getProfileVisibility()) && data.getRealImage() != null) {
            holder.profileImageView.setImageResource(R.drawable.ic_launcher_foreground); // Set a placeholder while loading
            new Thread(() -> {
                try {
                    byte[] decodedString = android.util.Base64.decode(data.getRealImage(), android.util.Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    // Update the ImageView on the main thread
                    holder.profileImageView.post(() -> {
                        holder.profileImageView.setImageBitmap(decodedByte);
                        holder.profileImageView.setVisibility(View.VISIBLE);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    holder.profileImageView.post(() -> holder.profileImageView.setVisibility(View.GONE));
                }
            }).start();
        } else {
            holder.profileImageView.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return combinedDataList.size();
    }

    public static class CombinedDataViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;
        TextView firstNameTextView;
        TextView ageTextView;
        TextView TitlePost;
        ImageView profileImageView;

        public CombinedDataViewHolder(View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            firstNameTextView = itemView.findViewById(R.id.firstNameTextView);
            ageTextView = itemView.findViewById(R.id.ageTextView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
            TitlePost = itemView.findViewById(R.id.titleTextView);
        }
    }
}
