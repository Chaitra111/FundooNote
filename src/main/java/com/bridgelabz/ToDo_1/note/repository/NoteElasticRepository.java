package com.bridgelabz.ToDo_1.note.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bridgelabz.ToDo_1.note.model.Label;
import com.bridgelabz.ToDo_1.note.model.NoteModel;

public interface NoteElasticRepository extends ElasticsearchRepository<NoteModel, String> {

	public List<NoteModel> findByemailId(String id);

	public Optional<NoteModel> findById(String id);

	public void deleteById(String id);

	public List<NoteModel> findNoteByemailId(long id);

	//public void save(NoteModel note);
	
	public void save(Label label);
}
