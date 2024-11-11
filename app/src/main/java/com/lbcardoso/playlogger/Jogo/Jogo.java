package com.lbcardoso.playlogger.Jogo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.lbcardoso.playlogger.User.User;

import java.io.Serializable;



@Entity(tableName = "jogos"/*,
        foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "id",
        childColumns = "id_jogador",
        onDelete = ForeignKey.CASCADE
)*/)
public class Jogo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    /*@ColumnInfo(name="id_jogador")
    private int id_jogador;*/

    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "empresa")
    private String empresa;

    @ColumnInfo(name = "plataforma")
    private String plataforma;

    @ColumnInfo(name = "data")
    private String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
/*
    public int getId_jogador() {
        return id_jogador;
    }

    public void setId_jogador(int id_jogador) {
        this.id_jogador = id_jogador;
    }
*/
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return  "[J]: "+nome+" | " +
                "[E]: "+empresa+" | " +
                "[P]: "+plataforma;
    }
}