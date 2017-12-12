package br.com.silas.letusknow.model;

import java.util.ArrayList;
import java.util.List;

public class Votos {

    private List<Voto> votos;

    public List<Voto> getVotos() {
        if (votos == null) {
            votos = new ArrayList<>();
        }
        return votos;
    }

    public void setVotos(List<Voto> votos) {
        this.votos = votos;
    }

}
