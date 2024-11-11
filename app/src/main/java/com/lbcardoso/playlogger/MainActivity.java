package com.lbcardoso.playlogger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.lbcardoso.playlogger.User.User;
import com.lbcardoso.playlogger.User.UserDAO;

public class MainActivity extends AppCompatActivity {

    private UserDAO userDAO;
    private EditText nome;
    private EditText email;

    private User usuario = null;

    public AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Atribui valores dos campos no MainActivity
        nome = findViewById(R.id.editText_nome);
        email = findViewById(R.id.editText_email);

        // Inicializar o banco de dados Room
        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "banco-de-dados")
                        .fallbackToDestructiveMigration() // Permite migração com deleção
                        .allowMainThreadQueries() //Bad for production
                        .build();

        // Obter instâncias dos DAOs
        userDAO = db.usuarioDao();

        //(Editar) PEGAR OS DADOS QUE VEM NO INTENT DO ATUALIZAR
        Intent it = getIntent(); //pega intenção
        if(it.hasExtra("user")) {
            usuario = (User) it.getSerializableExtra("user");
            nome.setText(usuario.getNome().toString());
            email.setText(usuario.getEmail().toString());
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void consultarUsuario(View view) {
        Intent intent = new Intent(this, DashboardUserActivity.class);

        if(usuario == null){
            usuario = new User();
            User usuarioRegistrado = userDAO.buscarUsuarioPorEmail(email.getText().toString());

            if (usuarioRegistrado == null) {
                //Usuario com mesmo email foi encontrado, procedendo como login
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                userDAO.inserirUser(usuario);
                intent.putExtra("primeiro-nome",usuario.getNome().split(" ")[0]);
                Toast.makeText(this,"Novo usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            }else{
                //Usuário não encontrado com este email, procedendo como register
                intent.putExtra("primeiro-nome", usuarioRegistrado.getNome().split(" ")[0]);
                Toast.makeText(this,"Seja bem-vindo novamente "+usuarioRegistrado.getNome()+"!", Toast.LENGTH_SHORT).show();
            }
        }else{
            //RECEBENDO DADOS DO ATUALIZAR NA VARIAVEL 'user'
            usuario.setNome(nome.getText().toString());
            usuario.setEmail(email.getText().toString());

            userDAO.atualizar(usuario); //inserir o aluno
            Toast.makeText(this,"Usuário atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        }

        //Envia o usuario para dashboard
        startActivity(intent);
    }


}