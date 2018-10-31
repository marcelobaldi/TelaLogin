package com.example.tela_cadastro02;

import java.io.Serializable;

public class d_Cadastro_End_Campos implements Serializable {                                        //Implements p/ Passar Dados p/ Outra Tela

    private String cep;                                                                             //Campos Valores
    private String rua;
    private String numero;
    private String comple;
    private String bairro;
    private String cidade;
    private String estado;
    private String datanasc;
    private String cpf;

    public String getCep() {return cep;}                                                            //GetSet dos Campos
    public void setCep(String cep) {this.cep = cep;}
    public String getRua() {return rua;}
    public void setRua(String rua) {this.rua = rua;}
    public String getNumero() {return numero;}
    public void setNumero(String numero) {this.numero = numero;}
    public String getComple() {return comple;}
    public void setComple(String comple) {this.comple = comple;}
    public String getBairro() {return bairro;}
    public void setBairro(String bairro) {this.bairro = bairro;}
    public String getCidade() {return cidade;}
    public void setCidade(String cidade) {this.cidade = cidade;}
    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}
    public String getDatanasc() {return datanasc;}
    public void setDatanasc(String datanasc) {this.datanasc = datanasc;}
    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}

    public d_Cadastro_End_Campos(String rua, String numero) {                                       //Construtor Campos (Campos Obrigatórios)
        this.rua = rua;
        this.numero = numero;
    }
}
