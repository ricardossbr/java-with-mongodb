package br.com.alura.cursoAlura.controllers;

import br.com.alura.cursoAlura.models.Aluno;
import br.com.alura.cursoAlura.repositorys.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GeolocalizacaoController {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@GetMapping("/geolocalizacao/iniciarpesquisa")
	public String inicializarPesquisa(Model model){
		final List<Aluno> alunos = alunoRepository.obterTodosAlunos();
		model.addAttribute("alunos", alunos);
		return "geolocalizacao/pesquisar";
	}
	
	@GetMapping("/geolocalizacao/pesquisar")
	public String pesquisar(@RequestParam("alunoId") String alunoId, Model model){
		final Aluno aluno = alunoRepository.obterAlunoPor(alunoId);
		final List<Aluno> alunosProximos = alunoRepository.pesquisaPorGeolocalizacao(aluno);
		model.addAttribute("alunosProximos", alunosProximos);
		return "geolocalizacao/pesquisar";
	}

}
