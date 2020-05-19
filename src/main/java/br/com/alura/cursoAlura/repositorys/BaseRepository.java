package br.com.alura.cursoAlura.repositorys;

import br.com.alura.cursoAlura.codecs.AlunoCodec;
import br.com.alura.cursoAlura.models.Aluno;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

public abstract class BaseRepository {

    private MongoClient cliente;
    private MongoDatabase bancaDeDados;

    protected void getConection() {
        final Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);

        final AlunoCodec alunoCodec = new AlunoCodec(codec);

        final CodecRegistry registro = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromCodecs(alunoCodec));

        MongoClientOptions opcoes = MongoClientOptions.builder().codecRegistry(registro).build();

        this.cliente = new MongoClient("localhost:27017", opcoes);
        this.bancaDeDados = cliente.getDatabase("test");

    }

    protected MongoCursor<Aluno> getAlunos(){
        this.getConection();
        final MongoCursor<Aluno> result = this.getCollection().find().iterator();
        this.closeConection();
        return result;
    }

    protected Aluno getByid(){


        return null;

    }




    protected MongoCollection<Aluno>  getCollection(){
        return this.bancaDeDados.getCollection("alunos", Aluno.class);
    }

    protected void closeConection() {
        this.cliente.close();
    }
}
