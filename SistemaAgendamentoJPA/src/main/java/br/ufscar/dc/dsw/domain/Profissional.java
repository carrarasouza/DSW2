package br.ufscar.dc.dsw.domain;

import java.util.List;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@SuppressWarnings("serial")
@Entity
@Table(name = "Profissional")
public class Profissional extends AbstractEntity{

    // Change to number and format ?
    @NotBlank
    @Column(nullable = false, unique = false, length = 40)
    private String especialidade;

    @Column(nullable = true, unique = true, length = 500)
    private String curriculo;

    @OneToMany(mappedBy = "profissional")
    private List<Consulta> consultas;


    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCurriculo() {
        return curriculo;
    }

    public void setCurriculo(String curriculo) {
        this.curriculo = curriculo;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(List<Consulta> consultas) {
        this.consultas = consultas;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("Nome: " + this.getNome() + ", ");
        sb.append("especialidade: " + especialidade + ", ");
        sb.append("curriculo: " + curriculo + ", ");
        sb.append("]");
        return sb.toString(); 
    }
}
