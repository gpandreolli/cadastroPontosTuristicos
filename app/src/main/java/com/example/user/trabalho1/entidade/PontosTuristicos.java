package com.example.user.trabalho1.entidade;

import android.widget.ImageView;

import java.io.File;
import java.io.Serializable;

public class PontosTuristicos implements Serializable {

    private int codPontoTuristico;
    private String nome;
    private String descricao;
    private Double latitude;
    private Double longitude;
    private String foto;

    public int getCodPontoTuristico() {
        return codPontoTuristico;
    }

    public void setCodPontoTuristico(int codPontoTuristico) {
        this.codPontoTuristico = codPontoTuristico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {

        return getNome() ;
    }


}
