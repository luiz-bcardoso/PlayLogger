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
                .fallbackToDestructiveMigration() // Permite migração com deleção
                .allowMainThreadQueries() //Bad for production
                .build();

        // Obter instâncias dos DAOs
        jogoDAO = db.jogoDao();

        //(Editar) PEGAR OS DADOS QUE VEM NO INTENT DO ATUALIZAR
        Intent it = getIntent(); //pega intenção
        if(it.hasExtra("jogo")) {
            jogo = (Jogo) it.getSerializableExtra("jogo");
            nome.setText(jogo.getNome());
            plataforma.setText(jogo.getPlataforma());
            empresa.setText(jogo.getEmpresa());
            data.setText(jogo.getData());
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void cadastrarJogo(View view) {
        Intent intent = new Intent(this, DashboardUserActivity.class);

        if (jogo == null) {
            //Cadastro de novo jogo
            jogo = new Jogo();
            jogo.setNome(nome.getText().toString());
            jogo.setPlataforma(plataforma.getText().toString());
            jogo.setEmpresa(empresa.getText().toString());
            jogo.setData(data.getText().toString());

            jogoDAO.inserir(jogo);
            Toast.makeText(this,"Jogo cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
        }else{
            //RECEBENDO DADOS DO ATUALIZAR NA VARIAVEL 'jogo'
            jogo.setNome(nome.getText().toString());
            jogo.setPlataforma(plataforma.getText().toString());
            jogo.setEmpresa(empresa.getText().toString());
            jogo.setData(data.getText().toString());

            jogoDAO.atualizar(jogo); //inserir o aluno
            Toast.makeText(this,"Jogo atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        }
        startActivity(intent);
    }
}