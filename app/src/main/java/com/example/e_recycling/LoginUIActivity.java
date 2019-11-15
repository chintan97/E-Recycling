package com.example.e_recycling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class LoginUIActivity extends AppCompatActivity {

    TextView redirect_register;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_ui);

        redirect_register = (TextView) findViewById(R.id.click_new_user);
        redirect_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click(v);
            }
        });
    }

    public void click(View v){
        Intent intent;
        intent = new Intent(getApplicationContext(), RegisterUIActivity.class);
        switch (v.getId()){
            case R.id.click_new_user:
                intent = new Intent(getApplicationContext(), RegisterUIActivity.class);
                break;
        }

        startActivity(intent);
    }
}
