package com.bridgelabz.ToDo_1.utility;

import org.springframework.lang.Nullable;

import com.bridgelabz.ToDo_1.utility.exceptionService.ToDoException;
import com.bridgelabz.ToDo_1.utility.exceptionService.UserExceptionHandler;

/**
 * @author Chaitra Ankolekar
 * Purpose :PreConditions class to check the fields
 */
public class PreConditions {

	/**
	 * @param reference
	 * @param errorMessage
	 * @return
	 * @throws UserExceptionHandler 
	 */
	public static <T> T checkNull(T reference,@Nullable Object errorMessage) throws UserExceptionHandler {
		if(reference=="") {
			throw new UserExceptionHandler("Note already created");
		}
		return reference;
	}
	public static <T> T checkNotNull(T reference,@Nullable Object errorMessage) throws UserExceptionHandler{
		if(reference=="") {
			throw new UserExceptionHandler("Note id doesnot  exist");
		}
		return reference;
	}
	public  <T> T CheckPassword(T resource) throws UserExceptionHandler  {
        if (resource == null) {
            throw new UserExceptionHandler(("invalid password"));
        }
        return resource;
}
	public static boolean isPresentInDb(boolean reference,@Nullable Object errorMessage) throws ToDoException {
        if (!reference) {
            throw new ToDoException(String.valueOf(errorMessage));
        }
        return reference;
}
}


