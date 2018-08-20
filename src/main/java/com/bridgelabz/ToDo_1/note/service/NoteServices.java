package com.bridgelabz.ToDo_1.note.service; 

import java.io.IOException;
import java.util.List;

import com.bridgelabz.ToDo_1.note.model.Label;
import com.bridgelabz.ToDo_1.note.model.NoteDTO;
import com.bridgelabz.ToDo_1.note.model.NoteModel;
import com.bridgelabz.ToDo_1.utility.exceptionService.LoginExceptionHandler;
import com.bridgelabz.ToDo_1.utility.exceptionService.ToDoException;
import com.bridgelabz.ToDo_1.utility.exceptionService.UserExceptionHandler;

/**
 * @author Chaitra Ankolekar
 * Purpose :NoteService to perform CRUD operation on note
 */
public interface NoteServices {
	
	public void createNote(NoteDTO note, String jwtToken) throws UserExceptionHandler;

	List<NoteModel> viewNotes(String noteId) throws ToDoException, UserExceptionHandler, LoginExceptionHandler;

	void deleteNote(String id, String token) throws ToDoException, UserExceptionHandler, LoginExceptionHandler;

	void updateNote(String jwtToken, String title, String description, String id) throws ToDoException, LoginExceptionHandler, IOException;

	void setPin(String id) throws ToDoException, LoginExceptionHandler;

	void setArchive(String id);

	NoteModel setReminder(String id, String reminderTime) throws Exception;
	
	public void createlabel(Label label, String token) throws UserExceptionHandler, ToDoException;

	void addlabel(String noteid, String labelname)throws UserExceptionHandler, ToDoException ;

	void updateLabel(String labelName, String labelId) throws UserExceptionHandler, ToDoException;

	void deleteLabel(String id) throws UserExceptionHandler, ToDoException;

	void doCreateNote(NoteDTO note) throws IOException, UserExceptionHandler;

	void trashNote(String id) throws ToDoException;
}