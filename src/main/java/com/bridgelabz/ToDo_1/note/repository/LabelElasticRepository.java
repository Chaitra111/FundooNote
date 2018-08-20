package com.bridgelabz.ToDo_1.note.repository;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bridgelabz.ToDo_1.note.model.Label;

public interface LabelElasticRepository extends ElasticsearchRepository<Label, String>{

	Optional<Label> findByLabelNameAndUserId(String labelName, long userId);

	
}
