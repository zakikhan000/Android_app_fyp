package com.example.mssqlconnectivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CombinedUserAdapter extends ArrayAdapter<CombinedUser> {
    private Context context;
    private List<CombinedUser> users;

    public CombinedUserAdapter(Context context, List<CombinedUser> users) {
        super(context, 0, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_combined_user, parent, false);
        }

        CombinedUser user = users.get(position);

        // Find views to populate
        TextView firstNameTextView = convertView.findViewById(R.id.firstNameTextView);
        TextView lastNameTextView = convertView.findViewById(R.id.lastNameTextView);
        TextView ageTextView = convertView.findViewById(R.id.ageTextView);
        TextView cityTextView = convertView.findViewById(R.id.cityTextView);
        TextView countryTextView = convertView.findViewById(R.id.countryTextView);
        TextView emailTextView = convertView.findViewById(R.id.emailTextView);
        TextView phoneNoTextView = convertView.findViewById(R.id.phoneNoTextView);
        TextView usernameTextView = convertView.findViewById(R.id.usernameTextView);

        // Set the values
        firstNameTextView.setText(user.getFirstName());
        lastNameTextView.setText(user.getLastName());
        ageTextView.setText(String.valueOf(user.getAge()));
        cityTextView.setText(user.getCity());
        countryTextView.setText(user.getCountry());
        emailTextView.setText(user.getEmail());
        phoneNoTextView.setText(user.getPhoneNo());
        usernameTextView.setText(user.getUsername());

        return convertView;
    }
}
