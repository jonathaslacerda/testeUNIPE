package br.com.unipe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.unipe.domain.Example;
import br.com.unipe.util.LoggerPadrao;

@Service
public class ExampleService {

	private List<Example> lista = new ArrayList<Example>();

	public Example cadastrar(Example example) throws Exception {
		
		if(!containsName(example.getNome())) {
			lista.add(example);
			return example;
		}
		
		LoggerPadrao.error("Erro de CRUD: ", new Exception("Example com nome " + example.getNome() + " já existe"));
		throw new Exception("Example com nome " + example.getNome() + " já existe");
	}
	
	private boolean containsName(String name){
	    return lista.stream().filter(o -> o.getNome().equals(name)).findFirst().isPresent();
	}
	
	public List<Example> listar() {
		return lista;
	}
	
	public Example pesquisar(String nome) {
		for (Example example : lista) {
			if(nome.equals(example.getNome())) {
				return example;
			}
		}
		return null;
	}
	
	public void delete(String nome) throws Exception {
		boolean del = false;
		
		for (int i = 0; i < lista.size(); i++) {
			if(nome.equals(lista.get(i).getNome())) {
				lista.remove(i);
				del = true;
			}
		}
		
		if(!del) {
			String message = "Não existe Example com nome " + nome;
			LoggerPadrao.error(message, new Exception("Não existe Example com nome " + nome));
		}
	}
	
}
