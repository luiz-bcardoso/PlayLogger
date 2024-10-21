package com.lbcardoso.playlogger;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lbcardoso.playlogger.User.User;
import com.lbcardoso.playlogger.User.UserDAO;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO usuarioDao();
}
