package com.example.nerdfriday;

class Estudante {
	String nome;
	int matricula;

	Estudante(String nome, int matricula) {
		this.nome = nome;
		this.matricula = matricula;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setMatricula(int matricula) {
		this.nome = nome;
	}

	public int getMatricula() {
		return this.matricula;
	}

	public String getSaudacao() {
		return "Ola, sou "+ this.nome + " e minha matricula e " + this.matricula;
	}
}