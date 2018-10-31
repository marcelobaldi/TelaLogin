package com.example.tela_cadastro02;

public class b_Login_Email_Campos {
    private String  email;                                          //Campos
    private String  senha;

    public String getEmail() {return email;}                        //GetSet
    public void setEmail(String email) {this.email = email;}
    public String getSenha() {return senha;}
    public void setSenha(String senha) {this.senha = senha;}

    public b_Login_Email_Campos(String email, String senha) {       //Construtor
        this.email = email;
        this.senha = senha;
    }
}
