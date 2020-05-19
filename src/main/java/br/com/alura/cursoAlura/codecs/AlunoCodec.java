package br.com.alura.cursoAlura.codecs;

import br.com.alura.cursoAlura.models.*;
import org.bson.*;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlunoCodec implements CollectibleCodec<Aluno> {
	
	private Codec<Document> codec;
	
	public AlunoCodec(Codec<Document> codec) {
		this.codec = codec;
	}

	@Override
	public void encode(BsonWriter writer, Aluno aluno, EncoderContext encoder) {
		final ObjectId id = aluno.getId();
		final String nome = aluno.getNome();
		final Date dataNascimento = aluno.getDataNascimento();
		final Curso curso = aluno.getCurso();
		final List<Habilidade> habilidades = aluno.getHabilidades();
		final List<Nota> notas = aluno.getNotas();
		final Contato contato = aluno.getContato();
		final Document document = new Document();
		document.put("_id", id);
		document.put("nome", nome);
		document.put("data_nascimento", dataNascimento);
		document.put("curso", new Document("nome", curso.getNome()));
		if (habilidades != null){
			final List<Document> habilidadesDocument = new ArrayList<>();
			for (Habilidade habilidade : habilidades) {
				habilidadesDocument.add(new Document("nome", habilidade.getNome())
						.append("nivel", habilidade.getNivel()));
				
			}
			document.put("habilidades", habilidadesDocument);
		}
		if (notas != null) {
			final List<Double> notasParaSalvar = new ArrayList<>();
			for (Nota nota : notas) {
				notasParaSalvar.add(nota.getValor());
			}
			document.put("notas", notasParaSalvar);
			
		}
		final List<Double> coordinates = new ArrayList<Double>();
		for(Double location : contato.getCoordinates()){
			coordinates.add(location);
		}
		
		document.put("contato", new Document()
				.append("endereco" , contato.getEndereco())
				.append("coordinates", coordinates)
				.append("type", contato.getType()));
		codec.encode(writer, document, encoder);
	}

	@Override
	public Class<Aluno> getEncoderClass() {
		return Aluno.class;
	}

	@Override
	public Aluno decode(BsonReader reader, DecoderContext decoder) {
		final Document document = codec.decode(reader, decoder);
		final Aluno aluno = new Aluno();
		aluno.setId(document.getObjectId("_id"));
		aluno.setNome(document.getString("nome"));
		aluno.setDataNascimento(document.getDate("data_nascimento"));
		final Document curso = (Document) document.get("curso");
		if (curso != null) {
			final String nomeCurso = curso.getString("nome");
			aluno.setCurso(new Curso(nomeCurso));
		}
		final List<Double> notas = (List<Double>) document.get("notas");
		if (notas != null) {
			final List<Nota> notasDoAluno = new ArrayList<>();
			for (Double nota : notas) {
				notasDoAluno.add(new Nota(nota));
			}
			aluno.setNotas(notasDoAluno);
		}
		final List<Document> habilidades = (List<Document>) document.get("habilidades");
		if (habilidades != null) {
			final List<Habilidade> habilidadesDoAluno = new ArrayList<>();
			for (Document documentHabilidade : habilidades) {
				habilidadesDoAluno.add(new Habilidade(documentHabilidade.getString("nome"), 
						documentHabilidade.getString("nivel")) );
			}
			aluno.setHabilidades(habilidadesDoAluno);
		}
		final Document contato = (Document) document.get("contato");
		if (contato != null) {
			final String endereco = contato.getString("contato");
			final List<Double> coordinates = (List<Double>) contato.get("coordinates");
			aluno.setContato(new Contato(endereco, coordinates));
		}
		return aluno;
	}

	@Override
	public boolean documentHasId(Aluno aluno) {
		return aluno.getId() == null;
	}

	@Override
	public Aluno generateIdIfAbsentFromDocument(Aluno aluno) {
		return documentHasId(aluno) ? aluno.criarId() : aluno;
	}

	@Override
	public BsonValue getDocumentId(Aluno aluno) {
		if (!documentHasId(aluno)) {
			throw new IllegalStateException("Esse Document nao tem id");
		}
		
		return new BsonString(aluno.getId().toHexString());
	}

}
