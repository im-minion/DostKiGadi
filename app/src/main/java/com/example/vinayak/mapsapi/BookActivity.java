package com.example.vinayak.mapsapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BookActivity extends AppCompatActivity {

    EditText noOfFriends;
    TextView noOfCarsAval;
    Button BookButton;
    int cars;
    int friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        noOfFriends = (EditText) findViewById(R.id.editText);
        noOfCarsAval = (TextView) findViewById(R.id.noOfcar);
        BookButton = (Button) findViewById(R.id.btn);

        cars = Integer.parseInt(String.valueOf(noOfCarsAval.getText()));
        //Log.w("CAR VALE IS : ", String.valueOf(cars));//working
        //friends = Integer.parseInt(String.valueOf(noOfFriends.getText()));
    }

    public void carBooked(View view) {
        //boolean getNoOfFriendsEntered = noOfFriends.getText().equals("");
        //Log.w("getNoOfFriendsEntered"," : "+ getNoOfFriendsEntered);//working
        if (!TextUtils.isEmpty(noOfFriends.getText())) {

            //Toast.makeText(this, "Please enter the no. of friends", Toast.LENGTH_SHORT).show();
            friends = Integer.parseInt(String.valueOf(noOfFriends.getText()));
            if (friends >= 1 && friends <= 2) {
                if (cars == 0) {
                    Toast.makeText(this, "Oooo!Sorry No Cars are available", Toast.LENGTH_SHORT).show();
                } else {
                    cars--;
                    String temp = String.valueOf(cars);
                    noOfCarsAval.setText(temp);
                    Toast.makeText(this, "Hoola!Car is Booked!Wait For it!!", Toast.LENGTH_SHORT).show();
                    noOfFriends.setText("");
                }
            } else {

                if (friends <= 0) {
                    Toast.makeText(this, "Friends can't be 0 or less", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Friends must be less than 3", Toast.LENGTH_SHORT).show();
                }

            }
        } else {

            Toast.makeText(this, "Please enter the no. of friends", Toast.LENGTH_SHORT).show();

        }
    }
}
