package com.example.estructuras.Mapping.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class AsignacionRecursoDto {
    @JsonProperty("desastre")
    private String desastre;
    @JsonProperty("recursos")
    private List<RecursoAsignadoDto> recursos;

    public String getDesastre() {
        return desastre;
    }

    public void setDesastre(String desastre) {
        this.desastre = desastre;
    }

    public List<RecursoAsignadoDto> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<RecursoAsignadoDto> recursos) {
        this.recursos = recursos;
    }
}
