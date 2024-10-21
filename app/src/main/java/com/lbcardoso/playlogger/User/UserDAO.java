package com.lbcardoso.playlogger.User;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void inserirUser(User user);

    @Query("SELECT * FROM usuarios")
    List<User> obterTodosUsuarios();

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    User buscarUsuarioPorEmail(String email);

    @Delete
    void excluir(User user);

    @Update
    void atualizar(User user);
}
