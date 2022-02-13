package br.ufscar.dc.dsw.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
@Table(name = "Pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false, unique = false, length = 60)
    private String nome;

    @NotBlank
    @Column(nullable = false, unique = true, length = 60)
    private String email;

    @NotBlank
    @Column(nullable = false, unique = false, length = 60)
    private String senha;

    @NotBlank
    @Column(nullable = false, unique = true, length = 15)
    private String CPF;

    @NotBlank
    @Column(nullable = false, unique = false, length = 60)
    private String papel;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append("Id: "+ id + ", ");
        sb.append("Nome: " + nome + ", ");
        sb.append("email: " + email + ", ");
        sb.append("cpf: " + CPF + ", ");
        sb.append("papel: " + papel + ", ");
        sb.append("]");
        return sb.toString(); 
    }
/*
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        AbstractEntity<?> other = (AbstractEntity<?>) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }*/

}
