package com.example.bianca.googlemaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText userLogin;
    private EditText password;
    private Button btnLoginUser;
    private Button btnRegisterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userLogin = findViewById(R.id.user);
        password = findViewById(R.id.passwordUser);
        btnLoginUser = findViewById(R.id.btnLogin);
        btnRegisterUser = findViewById(R.id.btnRegister);

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        btnLoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                i.putExtra("Usuario", "Bianca Forbeci");
                startActivity(i);
            }
        });




    }
}
