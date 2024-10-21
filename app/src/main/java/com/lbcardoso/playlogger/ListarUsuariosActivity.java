package com.lbcardoso.playlogger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.lbcardoso.playlogger.User.User;
import com.lbcardoso.playlogger.User.UserDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListarUsuariosActivity extends AppCompatActivity {

    private ListView listView;
    private UserDAO userDAO;
    private List<User> usuarios;
    private List<User> usuariosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_usuarios);

        // Inicializar o banco de dados Room
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "banco-de-dados")
                .allowMainThreadQueries() //Bad for production
                .build();

        // Obter instâncias dos DAOs
        userDAO = db.usuarioDao();

        //vincular variaveis com os campos do layout
        listView = findViewById(R.id.listView_usuarios);
        usuarios = userDAO.obterTodosUsuarios();
        usuariosFiltrados.addAll(usuarios); //só os alunos que foram consultados

        //ArrayAdapter já vem pronto no android para colocar essa lista de alunos na listview
        ArrayAdapter<User> adaptador = new ArrayAdapter<User>(this,
                android.R.layout.simple_list_item_1,
                usuarios);

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

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    public void excluir(MenuItem item){
        //pegar qual a posicao do item da lista que eu selecionei para excluir
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)
                item.getMenuInfo();
        final User usuarioExcluir = usuariosFiltrados.get(menuInfo.position);
        //mensagem perguntando se quer realmente excluir
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir este usuário?")
                .setNegativeButton("NÃO",null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        usuariosFiltrados.remove(usuarioExcluir);
                        usuarios.remove(usuarioExcluir);
                        userDAO.excluir(usuarioExcluir);
                        listView.invalidateViews();
                    }
                } ).create(); //criar a janela
        dialog.show(); //manda mostrar a janela
    }

    public void atualizar(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final User usuarioUpdate = usuariosFiltrados.get(menuInfo.position);

        //Ao selecionar atualizar, abrir a janela de cadastro e enviar esse aluno para lá
        Intent it = new Intent(this, MainActivity.class);

        //será preenchido com os dados do aluno que quer atualizar, podemos alterar e salvar
        it.putExtra("user", (Serializable) usuarioUpdate);
        startActivity(it);
    }

    public void voltarDashboard(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}