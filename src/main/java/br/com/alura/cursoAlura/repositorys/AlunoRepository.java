package br.com.alura.cursoAlura.repositorys;

import br.com.alura.cursoAlura.models.Aluno;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlunoRepository extends BaseRepository{
	
	public void salvar(Aluno aluno){
		
		super.getConection();
		MongoCollection<Aluno> alunos = super.getCollection();
		if (aluno.getId() == null) {
			alunos.insertOne(aluno);
		}else{
			alunos.updateOne(Filters.eq("_id", aluno.getId()), new Document("$set", aluno));
		}
		
		closeConection();
	}
	
	public List<Aluno> obterTodosAlunos(){

		return popularAlunos(super.getAlunos());
	}
	
	public Aluno obterAlunoPor(String id){
		getConection();
		MongoCollection<Aluno> alunos = super.getCollection();
		Aluno aluno = alunos.find(Filters.eq("_id", new ObjectId(id))).first();
		
		return aluno;
		
	}

	public List<Aluno> pesquisarPor(String nome) {
		var result = super.getCollection().find(Filters.eq("nome", nome), Aluno.class).iterator();
		final List<Aluno> alunos = popularAlunos(result);
		closeConection();
		return alunos;
	}



	private List<Aluno> popularAlunos(MongoCursor<Aluno> resultados){
		List<Aluno> alunos = new ArrayList<>();
		while(resultados.hasNext()){
			alunos.add(resultados.next());
		}
		return alunos;
	}

	public List<Aluno> pesquisarPor(String classificacao, double nota) {
		getConection();
		MongoCollection<Aluno> alunoCollection = super.getCollection();
		MongoCursor<Aluno> resultados = null;
		if (classificacao.equals("reprovados")) {
			resultados = alunoCollection.find(Filters.lt("notas", nota)).iterator();
		}else if(classificacao.equals("aprovados")){
			resultados = alunoCollection.find(Filters.gte("notas", nota)).iterator();
		}
		List<Aluno> alunos = popularAlunos(resultados);
		closeConection();
		return alunos;
		
	}

	public List<Aluno> pesquisaPorGeolocalizacao(Aluno aluno) {
		getConection();
		final MongoCollection<Aluno> alunoCollection = super.getCollection();
		alunoCollection.createIndex(Indexes.geo2dsphere("contato"));
		final List<Double> coordinates = aluno.getContato().getCoordinates();
		final Point pontoReferencia = new Point(new Position(coordinates.get(0), coordinates.get(1)));
		final MongoCursor<Aluno> resultados = alunoCollection.find(Filters.nearSphere("contato", pontoReferencia, 2000.0, 0.0)).limit(2).skip(1).iterator();
		final List<Aluno> alunos = popularAlunos(resultados);
		closeConection();
		return alunos;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
