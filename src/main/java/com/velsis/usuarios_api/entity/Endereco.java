package com.velsis.usuarios_api.entity;

import java.util.Objects;

import com.velsis.usuarios_api.enums.Constants;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity 
@Table 
public class Endereco {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id", nullable = false, unique = true)
    private Integer id;

    @NotBlank 
    @NotNull 
    @Size(min = 3, max = 200) 
    @Column(name = "logradouro", nullable = false, length = 200, unique = true)
    private String logradouro;

    @NotNull 
    @Column(name = "numero")
    private Integer numero;

    @NotBlank 
    @NotNull 
    @Size(min = 3, max = 100)
    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;

    @NotBlank 
    @NotNull
    @Size(min = 2, max = 2)  
    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    @NotBlank 
    @NotNull 
    @Size(min = 9, max = 9) 
    @Column(name = "cep", nullable = false, length = 9, unique = true)
    private String cep;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

@Override
	public String toString() {
		return this.logradouro.concat(Constants.CARACTERESPACO).concat(this.cidade);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Usuario)) {
			return false;
		}
		Endereco endereco = (Endereco) o;
		return Objects.equals(id, endereco.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

}