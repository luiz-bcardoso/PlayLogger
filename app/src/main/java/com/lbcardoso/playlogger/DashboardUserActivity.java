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

import com.lbcardoso.playlogger.Jogo.Jogo;
import com.lbcardoso.playlogger.Jogo.JogoDAO;
import com.lbcardoso.playlogger.User.User;
import com.lbcardoso.playlogger.User.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class DashboardUserActivity extends AppCompatActivity {

    private TextView nome;
    private ListView listView;

    private JogoDAO jogoDAO;

    private List<Jogo> jogos;
    private List<Jogo> jogosFiltrados = new ArrayList<>();

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

        // Inicializar o banco de dados Room
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "banco-de-dados")
                .allowMainThreadQueries() //Bad for production
                .build();

        // Obter instâncias dos DAOs
        jogoDAO = db.jogoDao();

        //vincular variaveis com os campos do layout
        listView = findViewById(R.id.listView_jogos);
        jogos = jogoDAO.obterTodosJogos();
        jogosFiltrados.addAll(jogos);

        //ArrayAdapter já vem pronto no android para colocar essa lista de alunos na listview
        ArrayAdapter<Jogo> adaptador = new ArrayAdapter<Jogo>(this,
                android.R.layout.simple_list_item_1,
                jogos);

        //colocar na listView o adaptador
        listView.setAdapter(adaptador);

        //registrar o menu de contexto (excluir e atualizar) na listview
        registerForContextMenu(listView);

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