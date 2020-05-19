package br.com.alura.cursoAlura.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Habilidade {
	
	private String nome;
	
	private String nivel;
	
	public Habilidade(){}

}
