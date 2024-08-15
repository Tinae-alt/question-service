package com.xyztechch.question_service.dao;

import com.xyztechch.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Long> {
    List<Question> findByCategory(String category);

    @Query(value = "SELECT q.id FROM question q Where q.category=:category  ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Integer> findByRandomQuestionsByCategory(String category, int numQ);
}
