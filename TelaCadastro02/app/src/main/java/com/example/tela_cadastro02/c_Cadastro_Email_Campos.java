package com.example.tela_cadastro02;

import java.io.Serializable;

public class c_Cadastro_Email_Campos implements Serializable {                                      //Implements p/ Passar Dados p/ Outra Tela

    private String id;                                                                              //Campos Valores + Campo ID
    private String nome;
    private String email;
    private String senha1;
    private String senha2;

    public String getId() {return id;}                                                              //GetSet dos Campos
    public void setId(String id) {this.id = id;}
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getSenha1() {return senha1;}
    public void setSenha1(String senha1) {this.senha1 = senha1;}
    public String getSenha2() {return senha2;}
    public void setSenha2(String senha2) {this.senha2 = senha2;}

    public c_Cadastro_Email_Campos(String nome, String email, String senha1, String senha2) {       //Construtor Campos (Campos Obrigatórios)
        this.nome = nome;
        this.email = email;
        this.senha1 = senha1;
        this.senha2 = senha2;
    }
}


