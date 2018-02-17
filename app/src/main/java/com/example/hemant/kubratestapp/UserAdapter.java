package com.example.hemant.kubratestapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hemant on 2/16/2018.
 */

public class UserAdapter extends ArrayAdapter<User> {

    /* Constructor */

    public UserAdapter(@NonNull Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        // Check if the existing view can be reused, else create a new view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,
                    false);
        }

        // Get the object located in that position in the list
        User currentUser = getItem(position);

        // Find the textview for the user name
        TextView userName = listItemView.findViewById(R.id.name);

        // Set the name of the currentUser
        userName.setText(currentUser.getName());

        // Find the textview for the user address
        TextView userAddress = listItemView.findViewById(R.id.address);

        // Set the address of currentUser
        userAddress.setText(currentUser.getAddress());

        return listItemView;
    }
}
