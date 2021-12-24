package com.fooda.wadalzaki.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.email.GMailSender;
import com.fooda.wadalzaki.internet.CheckConnection;

public class SendFeedBackActivity extends AppCompatActivity {
    private EditText editText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feed_back);
        editText=findViewById(R.id.txt_input_email);
        findViewById(R.id.btn_send_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CheckConnection.internetConnectionAvailable(1500)){
                    if (editText.getText().toString().trim().isEmpty()){
                        editText.setError("You cann't leave this field empty");

                    }
                    else {
                        new GMailSender(SendFeedBackActivity.this).sendMessage("ak1169194@gmail.com","English grammar hints","Hallo fooda my hint is : \n"+editText.getText().toString().trim());
                        editText.setText("");
                        Toast.makeText(SendFeedBackActivity.this, "Your feedback has been sent", Toast.LENGTH_SHORT).show();

                    }

                }
                else {
                    Toast.makeText(SendFeedBackActivity.this, "Please chick your internet connection", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}