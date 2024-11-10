package com.lbcardoso.playlogger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class CadastrarJogoActivity extends AppCompatActivity {

    private Jogo jogo = null;
    private JogoDAO jogoDAO;

    private EditText nome;
    private EditText empresa;
    private EditText plataforma;
    private EditText data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar_jogo);

        nome = findViewById(R.id.nomeEditText);
        empresa  = findViewById(R.id.empresaEditText);
        plataforma = findViewById(R.id.plataformaEditText);
        data = findViewById(R.id.dataEditText);

        // Inicializar o banco de dados Room
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "banco-de-dados")
                .allowMainThreadQueries() //Bad for production
                .build();

        // Obter instâncias dos DAOs
        jogoDAO = db.jogoDao();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void cadastrarJogo(View view) {
        Intent intent = new Intent(this, DashboardUserActivity.class);
        jogo = new Jogo();

        if (jogo == null) {
            //Cadastro de novo jogo
            jogo.setNome(nome.getText().toString());
            jogo.setPlataforma(plataforma.getText().toString());
            jogo.setEmpresa(empresa.getText().toString());
            jogo.setData(data.getText().toString());

            jogoDAO.inserir(jogo);
            Toast.makeText(this,"Jogo cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
        }else{
            //RECEBENDO DADOS DO ATUALIZAR NA VARIAVEL 'user'
            //TODO: Update
        }
        startActivity(intent);
    }
}