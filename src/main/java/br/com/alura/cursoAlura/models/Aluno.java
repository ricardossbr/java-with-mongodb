package br.com.alura.cursoAlura.models;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Aluno {
	
	private ObjectId id;
	
	private String nome;
	
	private Date dataNascimento;
	
	private Curso curso;
	
	private List<Nota> notas;
	
	private List<Habilidade> habilidades;
	
	private Contato contato;


	public Aluno criarId() {
		setId(new ObjectId());
		return this;
	}

	public Aluno adicionar(Aluno aluno, Habilidade habilidade) {
		List<Habilidade> habilidades = aluno.getHabilidades();
		habilidades.add(habilidade);
		aluno.setHabilidades(habilidades);
		return aluno;
	}

	public Aluno adicionar(Aluno aluno, Nota nota) {
		List<Nota> notas = aluno.getNotas();
		notas.add(nota);
		aluno.setNotas(notas);
		return aluno;
		
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}
	
}
