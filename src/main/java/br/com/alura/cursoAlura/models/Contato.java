package br.com.alura.cursoAlura.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Contato {
	
	private String endereco;
	
	private List<Double> coordinates;
	
	private String type = "Point";

	public Contato() {}
	
	public Contato(String endereco, List<Double> coordinates){
		this.endereco = endereco;
		this.coordinates = coordinates;
	}

}
