package com.example.myapplication;

import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Obtener el nombre de usuario del Intent
        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");

        // Mostrar el mensaje de bienvenida con el nombre de usuario
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewWelcomeMessage = findViewById(R.id.textViewWelcomeMessage);
        textViewWelcomeMessage.setText("Bienvenido " + nombreUsuario);
    }
}