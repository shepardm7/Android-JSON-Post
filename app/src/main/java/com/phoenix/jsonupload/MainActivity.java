package com.phoenix.jsonupload;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    public static String name, contact, age, email;
    public static Context mainContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainContext = getApplicationContext();

        Button uploadButton = findViewById(R.id.button);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = ((EditText) findViewById(R.id.editName)).getText().toString();
                contact = ((EditText) findViewById(R.id.editContact)).getText().toString();
                age = ((EditText) findViewById(R.id.editAge)).getText().toString();
                email = ((EditText) findViewById(R.id.editEmail)).getText().toString();

                SendPostRequest process = new SendPostRequest();
                process.execute();
            }
        });

    }
}
