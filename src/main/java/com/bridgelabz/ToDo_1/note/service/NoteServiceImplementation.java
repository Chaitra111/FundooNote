package com.bridgelabz.ToDo_1.note.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javax.validation.ConstraintViolationException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.bridgelabz.ToDo_1.note.model.Description;
import com.bridgelabz.ToDo_1.note.model.Label;
import com.bridgelabz.ToDo_1.note.model.Link;
import com.bridgelabz.ToDo_1.note.model.NoteDTO;
import com.bridgelabz.ToDo_1.note.model.NoteModel;
import com.bridgelabz.ToDo_1.note.repository.LabelElasticRepository;
import com.bridgelabz.ToDo_1.note.repository.LabelRepository;
import com.bridgelabz.ToDo_1.note.repository.NoteElasticRepository;
import com.bridgelabz.ToDo_1.note.repository.NoteMongoRepository;
import com.bridgelabz.ToDo_1.userservice.model.User;
import com.bridgelabz.ToDo_1.utility.JsoupImpl;
import com.bridgelabz.ToDo_1.utility.Messages;
import com.bridgelabz.ToDo_1.utility.PreConditions;
import com.bridgelabz.ToDo_1.utility.Utility;
import com.bridgelabz.ToDo_1.utility.exceptionService.LoginExceptionHandler;
import com.bridgelabz.ToDo_1.utility.exceptionService.ToDoException;
import com.bridgelabz.ToDo_1.utility.exceptionService.UserExceptionHandler;

/**
 * @author Chaitra Ankolekar
 * Purpose :Implementation for all the api's in services
 */
@Service
public class NoteServiceImplementation implements NoteServices {
	
	@Autowired
	NoteMongoRepository noteRepository;

	/*@Autowired
	private UserRepository userRepository;
	*/
	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private LabelElasticRepository labelElasiticRepo;
	
	@Autowired
	private NoteElasticRepository noteElasiticRepo;
	
	@Autowired
	ModelMapper model;
	
	@Autowired
	Messages messages;
	
	@Autowired
	Utility utility;
	
	NoteModel notes = new NoteModel();
	 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static final Logger logger = LoggerFactory.getLogger(NoteServiceImplementation.class);

	public void createNote(NoteDTO note, String jwtToken) throws UserExceptionHandler {
		
		logger.info("creating note");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		PreConditions.checkNotNull(note.getTitle(), messages.get("13"));
		PreConditions.checkNotNull(note.getDescription(), messages.get("14"));
		NoteModel noteModel = model.map(note, NoteModel.class);
		noteModel.setId(sdf.format(new Date()));
		//noteModel.setEmailId(noteModel.getEmailId());
		//noteModel.setTitle(noteModel.getTitle());
		//noteModel.setDescription(noteModel.getDescription());
		//noteModel.setArchive(false);
		//noteModel.setpin(false);
		noteModel.setCreatedAt(formatter.format(new Date()));
        noteModel.setEditedAt( formatter.format(new Date()));
		noteRepository.save(noteModel);
		noteElasiticRepo.save(noteModel);
		logger.info("note created");
	}

	@Override
    public void doCreateNote(NoteDTO note) throws IOException, UserExceptionHandler{
      
        PreConditions.checkNotNull(note.getTitle(), messages.get("13"));
		PreConditions.checkNotNull(note.getDescription(), messages.get("14"));
		NoteModel noteModel = model.map(note, NoteModel.class);
		if(!note.getDescription(). equals(""))
        {           
            String noteDescription=note.getDescription();       
            noteModel.setDescription(makeDescription(noteDescription));
        }
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        noteModel.setId(sdf.format(new Date()));
        //noteModel.setEmailId(noteModel.getEmailId());
        noteModel.setCreatedAt(formatter.format(new Date()));
        noteModel.setEditedAt( formatter.format(new Date()));
        //noteModel.setTitle(noteModel.getTitle() );
        //noteModel.setArchive(false);
        //noteModel.setpin(false);
       
/*
        for (int i = 0; i < note.getLabel().size(); i++) {
            note.getLabel().get(i). setNoteId(noteid);
            note.getLabel().get(i). setUserId(authorId);
            labelRepository.save(note.getLabel() .get(i));
            labelElasiticRepo.save(note. getLabel().get(i));
        }
        notes.setLabel(note.getLabel() );
        if (note.getArchive().equals(" true") && note.getPinned().equals("true" )) {
            note.setArchive("true");
            note.setPinned("false");
        }*/
        noteRepository.save(noteModel);
        noteElasiticRepo.save(noteModel);
    }
	
	  /**
	 * @param noteDescription
	 * @return
	 * @throws IOException
	 */
	public static Description makeDescription(String noteDescription) throws IOException{
	        Description desc= new Description();
	        List<Link> linkList=new ArrayList<>();
	        List<String>simpleList=new ArrayList<>();
	        String[] descriptionArray= noteDescription.split(" ");
	        for(int i=0;i<descriptionArray.length; i++)
	        {
	            if(descriptionArray[i]. startsWith("http://") || descriptionArray[i]. startsWith("https://") )
	            { 
	                Link link= new Link();
	                link.setLinkTitle(JsoupImpl. getTitle(descriptionArray[i])) ;
	                link.setLinkDomainName( JsoupImpl.getDomain( descriptionArray[i]));
	                link.setLinkImage(JsoupImpl. getImage(descriptionArray[i])) ;
	                System.out.println(link);
	                linkList.add(link);                                                           
	            }   
	            else if(!descriptionArray[i]. equals("") && (!descriptionArray[i]. startsWith("http://") || !descriptionArray[i]. startsWith("https://")) )
	            {
	                simpleList.add( descriptionArray[i]);
	            }
	        }
	        desc.setSimpleDescription( simpleList);
	        desc.setLinkDescription( linkList);
	        return desc;
	    }

	@Override
	public void updateNote(String jwtToken, String title, String description, String id) throws ToDoException, IOException {
			
		logger.info("updating the note");
		Optional<NoteModel> note = noteRepository.findById(id);
		// Optional<NoteModel> note = noteElasiticRepo.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("1"));
		notes = note.get();
		if (!notes.getDescription().equals("")) {
			String noteDescription = description;
			notes.setDescription(makeDescription(noteDescription));
		}
		notes.setTitle(title);
		notes.setCreatedAt(formatter.format(new Date()));
		notes.setEditedAt(formatter.format(new Date()));
		noteRepository.save(notes);
		noteElasiticRepo.save(notes);
	}

	@Override
	public void trashNote(String id) throws ToDoException {
		
		Optional<NoteModel> note = noteRepository.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("1"));
		NoteModel notes = note.get();
		notes.setTrash(true);
		noteRepository.save(notes);
	}

	@Override
	public void deleteNote(String id, String token) throws ToDoException, UserExceptionHandler, LoginExceptionHandler {

		logger.info("deleting the note");
		//String mailId = utility.parseJwt(token).getId();
		//Optional<User> users = userRepository.findByemailId(mailId);
		Optional<NoteModel> note = noteElasiticRepo.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("1"));		
		noteRepository.deleteById(id);
	}

	@Override
	public List<NoteModel> viewNotes(String noteId) throws ToDoException {
		
		logger.info("view notes");
		//Optional<User> users = userRepository.findByemailId(mailId);
		//System.out.println(mailId);
		//PreConditions.isPresentInDb(users.isPresent(), messages.get("2"));
		List<NoteModel> note = noteElasiticRepo.findByemailId(noteId);
		if(note==null) {
	    System.out.println(messages.get("1"));
		}
		return note;
	}
	
	@Override
	public void setArchive(String id) {

		logger.info("setting archive");
		Optional<NoteModel> note = noteElasiticRepo.findById(id);
		if (note.isPresent()) {
			notes = note.get();
			notes.setArchive(true);
			noteRepository.save(notes);
			noteElasiticRepo.save(notes);
			logger.info("note archived");
		}
	}

	@Override
	public void setPin(String id) throws ToDoException, LoginExceptionHandler {

		logger.info("setting pin");
		//Optional<NoteModel> note = noteRepository.findById(id);
		Optional<NoteModel> note = noteElasiticRepo.findById(id);
		PreConditions.isPresentInDb(note.isPresent(), messages.get("1"));		
		notes = note.get();
		notes.setpin(true);
		noteRepository.save(notes);
		noteElasiticRepo.save(notes);
		logger.info("note pinned");
	}

	@Override
	public NoteModel setReminder(String id, String reminderTime) throws Exception {

		logger.info("setting reminder");
		//Optional<NoteModel> note =noteRepository.findById(id);
		Optional<NoteModel> note = noteElasiticRepo.findById(id);
		Timer timer = null;
		if (note.isPresent()) {
			Date reminder = new SimpleDateFormat("yyyy/MM/dd HH:mm").parse(reminderTime);
			long timeDifference = reminder.getTime() - new Date().getTime();
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					System.out.println("Reminder ");// + note.toString());

				}
			}, timeDifference);
		}
		return note.get();
	}
	
	@Override
	public void createlabel(Label label, String userId) throws UserExceptionHandler, ToDoException {
		
		logger.info("creating label");
		if (label == null)
			throw new ToDoException("Note cannot be created with empty title and description");
		// PreConditions.checkNotNull(label, "Note cannot be created with empty title
		// and description");
		List<Label> list = labelRepository.findAll();
		// Optional<Label> list = labelRepository.findByLabelName(label);
		for (Label l : list) {
			if (l.getLabelName().equals(label.getLabelName())) {
				throw new UserExceptionHandler(messages.get("3"));
			}
		}
		User users = new User();
		label.setUserId(users.getUserId());
		label.setLabelName(label.getLabelName());
		try {
			labelRepository.save(label);
			labelElasiticRepo.save(label);
		} catch (DataIntegrityViolationException | ConstraintViolationException e) {
			throw new UserExceptionHandler(messages.get("4"));
		}
		logger.info("label created");
	}
	
	@Override
	  public void addlabel(String noteid, String labelname) throws ToDoException 
      {
		Label label = new Label();
		Optional<NoteModel> note = noteRepository.findById(noteid);
		if (note.get().getLabelName() == null) {
			List<Label> newLabelList = new ArrayList<>();
			note.get().setLabelName(newLabelList);
		}
		List<Label> list = labelRepository.findAll();
		for (Label l : list) {

			if (l.getLabelName().equals(labelname)) {
				Optional<NoteModel> optionalnote = noteRepository.findById(noteid);
				PreConditions.isPresentInDb(optionalnote.isPresent(), messages.get("1"));
				label.setLabelName(labelname);
				label.setLabelId(l.getLabelId());
				note.get().getLabelName().add(label);
				noteRepository.save(note.get());
			}
		}
	}
	
	@Override
	public void updateLabel(String labelName, String labelId) throws UserExceptionHandler, ToDoException {
		
		logger.info("updating label");
		Optional<Label> label = labelRepository.findById(labelId);
		PreConditions.isPresentInDb(label.isPresent(), messages.get("5"));
		Label labels=label.get();
		labels.setLabelName(labelName);
		labelRepository.save(label.get());
		labelElasiticRepo.save(label.get());
		logger.info("label updated");
	}

	@Override
	public void deleteLabel(String id) throws UserExceptionHandler, ToDoException {
		
		logger.info("deleting label");
		Optional<Label> optionallabel = labelElasiticRepo.findById(id);
		PreConditions.isPresentInDb(optionallabel.isPresent(), messages.get("5"));
		labelRepository.deleteById(id);
		labelElasiticRepo.deleteById(id);
	}
}