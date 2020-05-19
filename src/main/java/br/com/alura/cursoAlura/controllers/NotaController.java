package br.com.alura.cursoAlura.controllers;

import br.com.alura.cursoAlura.models.Aluno;
import br.com.alura.cursoAlura.models.Nota;
import br.com.alura.cursoAlura.repositorys.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NotaController {
	
	@Autowired
	private AlunoRepository repository;
	
	@GetMapping("/nota/cadastrar/{id}")
	public String cadastrar(@PathVariable String id, Model model){
		final Aluno aluno = repository.obterAlunoPor(id);
		model.addAttribute("aluno", aluno);
		model.addAttribute("nota", new Nota());
		return "nota/cadastrar";
	}
	
	@PostMapping("/nota/salvar/{id}")
	public String salvar(@PathVariable String id, @ModelAttribute Nota nota){
		final Aluno aluno = repository.obterAlunoPor(id);
		repository.salvar(aluno.adicionar(aluno, nota));
		return "redirect:/aluno/listar";
	}
	
	@GetMapping("/nota/iniciarpesquisa")
	public String iniciarPesquisa(){
		return "nota/pesquisar";
	}
	
	@GetMapping("/nota/pesquisar")
	public String pesquisarPor(@RequestParam("classificacao") String classificacao,
			@RequestParam("notacorte") String notaCorte, Model model){
		final List<Aluno> alunos = repository.pesquisarPor(classificacao, Double.parseDouble(notaCorte));
		model.addAttribute("alunos", alunos);
		return "nota/pesquisar"; 
	}
}
