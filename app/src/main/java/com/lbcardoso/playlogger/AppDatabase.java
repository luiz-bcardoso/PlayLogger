package com.lbcardoso.playlogger;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lbcardoso.playlogger.Jogo.Jogo;
import com.lbcardoso.playlogger.Jogo.JogoDAO;

import com.lbcardoso.playlogger.User.User;
import com.lbcardoso.playlogger.User.UserDAO;


@Database(entities = {User.class, Jogo.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO usuarioDao();
    public abstract JogoDAO jogoDao();


}
