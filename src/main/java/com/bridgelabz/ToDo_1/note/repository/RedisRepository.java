package com.bridgelabz.ToDo_1.note.repository;

import com.bridgelabz.ToDo_1.userservice.model.User;
import com.bridgelabz.ToDo_1.utility.exceptionService.ToDoException;

@SuppressWarnings("hiding")
public interface RedisRepository<String, User> {

	void saveInRedis(String token) throws ToDoException;

	String getFromRedis(String userId);
}




