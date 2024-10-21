package com.lbcardoso.playlogger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.lbcardoso.playlogger.User.User;
import com.lbcardoso.playlogger.User.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class DashboardUserActivity extends AppCompatActivity {

    private TextView nome;
    private ListView listView;
    private UserDAO userDAO;
    private List<User> usuarios;
    private List<User> usuariosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard_user);

        //Pegar nome do usuario da outra intent
        Intent intent = getIntent();
        String primeiroNome = intent.getStringExtra("primeiro-nome");
        nome = findViewById(R.id.textView_nome);
        nome.setText(primeiroNome);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void listarUsuarios(View view) {
        Intent intent = new Intent(this, ListarUsuariosActivity.class);
        startActivity(intent);
    }
}