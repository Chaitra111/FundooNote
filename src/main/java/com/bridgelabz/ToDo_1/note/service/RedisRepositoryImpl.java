package com.bridgelabz.ToDo_1.note.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.bridgelabz.ToDo_1.note.repository.RedisRepository;
import com.bridgelabz.ToDo_1.userservice.model.User;
import com.bridgelabz.ToDo_1.utility.Utility;
import com.bridgelabz.ToDo_1.utility.exceptionService.ToDoException;

@Repository
public class RedisRepositoryImpl implements RedisRepository<String, User> {

	private static final String key="TOKEN";
	@Autowired
	private Utility util;
	@Autowired
	private RedisTemplate<String, User> redisTemplate;
	private HashOperations<String, String, String> hashOps;


	RedisRepositoryImpl(RedisTemplate<String, User> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	@Override
	public void saveInRedis(String jwtoken) throws ToDoException {
		System.out.println("TOKEN:"+jwtoken);
		String  mailId  = util.parseJwt(jwtoken).getSubject();
		System.out.println("TOKEN:"+jwtoken);
		hashOps.put(key,  mailId , jwtoken);
	}
	
	@Override
	public String getFromRedis(String userId) {
		return hashOps.get(key, userId);
	}
}
