package com.lbcardoso.playlogger.Jogo;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JogoDAO {
    @Insert
    void inserir(Jogo jogo);

    @Update
    void atualizar(Jogo jogo);

    @Delete
    void excluir(Jogo jogo);

    @Query("SELECT * FROM jogos")
    List<Jogo> obterTodosJogos();

}
