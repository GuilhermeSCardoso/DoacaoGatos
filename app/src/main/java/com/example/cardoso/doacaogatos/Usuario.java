package com.example.cardoso.doacaogatos;

import android.graphics.Bitmap;

/**
 * Created by Guilherme Cardoso on 30/11/2017.
 */

public class Usuario {

    public String id, nome, fone, email, senha;
    public Bitmap foto;

    public Usuario() {

    }

    public Usuario(String id, String nome, String fone, String email, String senha, Bitmap foto) {
        this.id = id;
        this.nome = nome;
        this.fone = fone;
        this.email = email;
        this.senha = senha;
        this.foto = foto;
    }

    public Usuario(String id, String nome, String fone, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.fone = fone;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(Bitmap foto) {
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }

}
