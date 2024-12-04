package com.example.mssqlconnectivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> users;

    public UserAdapter(Context context, List<User> users) {
        super(context, 0, users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_user, parent, false);
        }

        User user = users.get(position);

        // Find views to populate
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView ageTextView = convertView.findViewById(R.id.ageTextView);
        TextView emailTextView = convertView.findViewById(R.id.emailTextView);
        TextView phoneNoTextView = convertView.findViewById(R.id.phoneNoTextView);
        TextView usernameTextView = convertView.findViewById(R.id.usernameTextView);

        // Set the values

        emailTextView.setText(user.getEmail());
        phoneNoTextView.setText(user.getPhoneNo());
        usernameTextView.setText(user.getUsername());

        return convertView;
    }
}
