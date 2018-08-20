package com.bridgelabz.ToDo_1.note.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.ToDo_1.note.model.Label;
import com.bridgelabz.ToDo_1.note.model.NoteDTO;
import com.bridgelabz.ToDo_1.note.model.NoteModel;
import com.bridgelabz.ToDo_1.note.service.NoteServiceImplementation;
import com.bridgelabz.ToDo_1.userservice.dto.Response;
import com.bridgelabz.ToDo_1.utility.exceptionService.LoginExceptionHandler;
import com.bridgelabz.ToDo_1.utility.exceptionService.ToDoException;
import com.bridgelabz.ToDo_1.utility.exceptionService.UserExceptionHandler;

/**
 * @author Chaitra Ankolekar
 * Purpose :NoteController with API
 */
@RestController
@RequestMapping(value="/notes")
public class NoteController {
	
	//public static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	@Autowired
	NoteServiceImplementation services;

	/**
	 * To create a note for user
	 * @param note
	 * @param jwtToken
	 * @return
	 * @throws ToDoException 
	 */
	@RequestMapping(value = "/createnote", method = RequestMethod.POST)
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO note, HttpServletRequest request )//,@RequestParam String jwtToken)
			throws LoginExceptionHandler, UserExceptionHandler, ToDoException {
		
		String email = (String) request.getAttribute("Authorization");
		//System.out.println(email);
		services.createNote(note, email);
		Response response = new Response();
		response.setMessage("created note Successfull!!");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	/**
	 * @param note
	 * @param request
	 * @return response
	 * @throws IOException
	 * @throws UserExceptionHandler
	 */
	@RequestMapping(value = "/createnoteLink", method = RequestMethod.POST)
	public ResponseEntity<Response> createLinkNote(@RequestBody NoteDTO note, HttpServletRequest request )//,@RequestParam String jwtToken)
			throws IOException, UserExceptionHandler {
		
		String email = (String) request.getAttribute("Authorization");
		//System.out.println(email);
		services. doCreateNote(note);
		Response response = new Response();
		response.setMessage("created note link Successfull!!");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * Method to update the note
	 * @param jwtToken
	 * @param title
	 * @param content
	 * @param noteId
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws ToDoException
	 * @throws UserExceptionHandler 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/updatenote/{noteId}", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateNote( @RequestParam String title,
			@RequestParam String description, @PathVariable String noteId, HttpServletRequest request ) throws LoginExceptionHandler, ToDoException, UserExceptionHandler, IOException {
		String email = (String) request.getAttribute("Authorization");
		System.out.println(email);
		//String jwtToken=request.getHeader("Authorization");
		services.updateNote(email, title,description,noteId);
		Response response = new Response();
		response.setMessage("updated note successfull!!");
		response.setStatus(200);
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

	/**
	 * Method to delete the note
	 * @param title
	 * @param token
	 * @param note
	 * @return
	 * @throws LoginExceptionHandling
	 * @throws ToDoException
	 * @throws UserExceptionHandler 
	 */
	@RequestMapping(value = "/deletenote/{id}", method = RequestMethod.POST)
	public ResponseEntity<Response> deleteNote(@PathVariable String id, HttpServletRequest request )
			throws LoginExceptionHandler, ToDoException, UserExceptionHandler {
		String email = (String) request.getAttribute("Authorization");
		services.deleteNote(id,email);
		Response response = new Response();
		response.setMessage("note deleted successfull!!");
		response.setStatus(200);
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

	/**
	 * Method to display all the notes
	 * @param token
	 * @return
	 * @throws ToDoException
	 * @throws UserExceptionHandler 
	 * @throws LoginExceptionHandler 
	 */
	@RequestMapping(value = "/viewnotes", method = RequestMethod.GET)
	public ResponseEntity<List<NoteModel>>  viewNotes(HttpServletRequest request ) throws ToDoException {
		String jwtToken=request.getHeader("Authorization");
		List<NoteModel> note=services.viewNotes(jwtToken);
		return new ResponseEntity<List<NoteModel>>(note,HttpStatus.CREATED);
	}
	
	/**
	 * @param id
	 * @return
	 * @throws ToDoException
	 * @throws LoginExceptionHandler
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/setpin/{id}", method = RequestMethod.POST)
	public ResponseEntity<Response> setPin(@PathVariable String id, HttpServletRequest request) throws ToDoException, LoginExceptionHandler {
		String email = (String) request.getAttribute("Authorization");
		services.setPin(id);
		Response response = new Response();
		response.setMessage("note pinned");
		response.setStatus(200);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * @param id
	 * @return
	 * @throws ToDoException
	 * @throws LoginExceptionHandler
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/setarchive/{id}", method = RequestMethod.POST)
	public ResponseEntity<Response> setArchive(@PathVariable String id, HttpServletRequest request) throws ToDoException, LoginExceptionHandler {
		String email = (String) request.getAttribute("Authorization");
		services.setArchive(id);
		Response response = new Response();
		response.setMessage("note archived");
		response.setStatus(200);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	/**
	 * @param noteId
	 * @return
	 * @throws ToDoException
	 */
	@RequestMapping(value = "/trashnote{noteId}", method = RequestMethod.PUT)
	public ResponseEntity<Response> trashNote(@PathVariable String noteId) throws ToDoException {
		services.trashNote(noteId);
		Response response = new Response();
		response.setStatus(200);
		response.setMessage("note sent totrash");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
}
	/**
	 * @param tokenbridgeit
	 * 
	 * @param id
	 * @param reminderTime
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/setreminder/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> setReminder(@PathVariable String id,@RequestParam String reminderTime, HttpServletRequest request) throws Exception {
		String email = (String) request.getAttribute("Authorization");
		NoteModel note=services.setReminder(id,reminderTime);
		return new ResponseEntity<>(new Response("Response:" +note.toString(),200),HttpStatus.OK);
	}
		
	/**
	 * @param label
	 * @param jwtToken
	 * @param request
	 * @return response
	 * @throws ToDoException
	 * @throws LoginExceptionHandler
	 * @throws UserExceptionHandler
	 */
	@RequestMapping(value = "/createlabel", method = RequestMethod.POST)
	public ResponseEntity<Response> createlabel(@RequestBody Label label, HttpServletRequest request) throws ToDoException, LoginExceptionHandler, UserExceptionHandler{
		String userId = (String) request.getAttribute("Authorization");
		//String userId = (String) request.getAttribute("userId");
		services.createlabel(label, userId);
		Response response = new Response();
		response.setMessage("created label Successfull!!");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	/**
	 * @param noteId
	 * @param labelName
	 * @param jwtToken
	 * @return response
	 * @throws ToDoException
	 * @throws LoginExceptionHandler
	 * @throws UserExceptionHandler
	 */
	@RequestMapping(value = "/addlabel/{noteId}", method = RequestMethod.POST)
	public ResponseEntity<Response> addLabel(@PathVariable String noteId ,@RequestParam String labelName, HttpServletRequest request) throws ToDoException, LoginExceptionHandler, UserExceptionHandler{
		String email = (String) request.getAttribute("Authorization");
		services.addlabel(noteId,labelName);
		Response response = new Response();
		response.setMessage("adding label successfull!!");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * @param labelName
	 * @param noteId
	 * @param httpServletRequest
	 * @return
	 * @throws ToDoException 
	 * @throws UserExceptionHandler 
	 */
	@RequestMapping(value = "/updatelabel/{labelId}", method = RequestMethod.PUT)
    public ResponseEntity<Response> updateLabel(@RequestParam String labelName, @PathVariable String labelId, 
        HttpServletRequest request) throws UserExceptionHandler, ToDoException {
		String email = (String) request.getAttribute("Authorization");
        services.updateLabel(labelName,labelId);
        Response response = new Response();
		response.setMessage("Note Updated");
		response.setStatus(200);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

	/**
	 * @param id
	 * @return
	 * @throws UserExceptionHandler 
	 * @throws ToDoException 
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/deleteLabel/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Response> deletelabel(@PathVariable String id, HttpServletRequest request) throws UserExceptionHandler, ToDoException {
		String jwtToken = request.getHeader("Authorization");
		services.deleteLabel(id);
		Response response = new Response();
		response.setMessage("Label  Deleted");
		response.setStatus(200);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}