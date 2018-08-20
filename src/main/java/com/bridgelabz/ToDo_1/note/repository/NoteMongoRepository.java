package com.bridgelabz.ToDo_1.note.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.ToDo_1.note.model.Label;
import com.bridgelabz.ToDo_1.note.model.NoteModel;

/**
 * @author Chaitra Ankolekar
 * Purpose :Repository which extends MongoRepository which already have built-in all CRUD operations
 */
@Repository
public interface NoteMongoRepository extends MongoRepository<NoteModel, String> {
	
	public List<NoteModel> findByemailId(String id);

	public Optional<NoteModel> findById(String id);

	public void deleteById(String id);

	public List<NoteModel> findNoteByemailId(long id);

	public void save(Label label);
}