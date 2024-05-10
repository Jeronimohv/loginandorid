package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private View buttonLogin;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Inicializar la base de datos
        mDatabase = openOrCreateDatabase("mi_base_de_datos", MODE_PRIVATE, null);

        // Crear tabla si no existe
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS usuarios (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "usuario TEXT, " +
                        "contraseña TEXT);"
        );

        // Verificar si ya existe el usuario ADMIN en la base de datos
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM usuarios WHERE usuario=?", new String[]{"ADMIN"});
        if (cursor.getCount() == 0) {
            // Insertar usuario ADMIN con contraseña 1234
            mDatabase.execSQL("INSERT INTO usuarios (usuario, contraseña) VALUES (?, ?)", new String[]{"ADMIN", "1234"});
        }
        cursor.close();

        // Configurar el click listener del botón de inicio de sesión
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Verificar las credenciales en la base de datos
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM usuarios WHERE usuario=? AND contraseña=?", new String[]{username, password});
        if (cursor.getCount() > 0) {
            // Inicio de sesión exitoso
            cursor.moveToFirst();
            @SuppressLint("Range") String nombreUsuario = cursor.getString(cursor.getColumnIndex("usuario"));
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            // Redirigir a la actividad de bienvenida
            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
            intent.putExtra("nombreUsuario", nombreUsuario);
            startActivity(intent);
            finish(); // Finalizar la actividad de inicio de sesión
        } else {
            // Credenciales incorrectas
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }
}