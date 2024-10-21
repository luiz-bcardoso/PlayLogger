package com.lbcardoso.playlogger.User;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void inserirUser(User user);

    @Query("SELECT * FROM usuarios")
    List<User> obterTodosUsuarios();

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    User buscarUsuarioPorEmail(String email);
}
