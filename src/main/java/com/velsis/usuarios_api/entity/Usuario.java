package com.velsis.usuarios_api.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import org.hibernate.validator.constraints.br.CPF;

import com.velsis.usuarios_api.enums.Constants;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

/**
 * 
 * Usuario
 * 
 * Essa entity armazena dados de usuario
 * Não pode existir mais de um usuário com o mesmo CPF
 * 
 */
@Entity 
@Table(name = "Usuario")
public class Usuario {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @CPF 
    @NotNull
    @NotBlank
    @Size(min = 11, max = 11)  
    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    @NotNull
    @NotBlank 
    @Size(min = 3, max = 100)
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotNull
    @PastOrPresent 
    @Column(name = "dataNascimento")
    private LocalDate dataNascimento;

    @Column(name = "dataCriacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "dataUltimaAlteracao", nullable = false)
    private LocalDateTime dataUltimaAlteracao;

    @Valid 
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now(ZoneId.systemDefault());
        dataUltimaAlteracao = LocalDateTime.now(ZoneId.systemDefault());
        this.removerFormatacaoCpf();
    }

    @PreUpdate
    protected void onUpdate() {
        dataUltimaAlteracao = LocalDateTime.now(ZoneId.systemDefault());
        this.removerFormatacaoCpf();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataUltimaAlteracao() { return dataUltimaAlteracao; }
    public void setDataUltimaAlteracao(LocalDateTime dataUltimaAlteracao) { this.dataUltimaAlteracao = dataUltimaAlteracao; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco) { this.endereco = endereco; }

    private String removerFormatacaoCpf() {
        if (this.cpf == null) {
            return null;
        }

        return this.cpf.replaceAll("\\D", "");
    }

    @Override
	public String toString() {
		return this.id.toString().concat(Constants.CARACTERESPACO).concat(this.nome);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Usuario)) {
			return false;
		}
		Usuario usuario = (Usuario) o;
		return Objects.equals(id, usuario.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}