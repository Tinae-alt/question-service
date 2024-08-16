package com.xyztechch.question_service.service;


import com.xyztechch.question_service.dao.QuestionDao;
import com.xyztechch.question_service.dto.QuestionDto;
import com.xyztechch.question_service.dto.ResponseDto;
import com.xyztechch.question_service.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;
   public List<Question> getAllQuestions(){
       List<Question> questions = questionDao.findAll();
       return questions;
    }

    public ResponseEntity<List<Question>> getAllQuestionsByCategory(String category) {
        List<Question> questionsByCategory = questionDao.findByCategory(category);
        return new ResponseEntity<> (questionsByCategory, HttpStatus.OK);
    }

    public ResponseEntity<String> addQuestion(QuestionDto questionDto) {
                Question question = Question.builder()
                        .category(questionDto.getCategory())
                        .questionTitle(questionDto.getQuestionTitle())
                        .option1(questionDto.getOption1())
                        .option2(questionDto.getOption2())
                        .option3(questionDto.getOption3())
                        .option4(questionDto.getOption4())
                        .rightAnswer(questionDto.getRightAnswer())
                        .difficultyLevel(questionDto.getDifficultyLevel())
                        .build();
                questionDao.save(question);
                String response = "success";
                return ResponseEntity.ok(response);

    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, int numQ) {
        List<Integer> questionList = questionDao.findByRandomQuestionsByCategory(category,numQ);
   return  new ResponseEntity<>(questionList, HttpStatus.OK);
   }


    public ResponseEntity<List<QuestionDto>> getQuestionsFromId(List<Integer> ids) {
        List<QuestionDto> questionDtos = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for (Integer id : ids) {
            questions.add(questionDao.findById(Long.valueOf(id)).get());
        }
        for (Question question : questions) {
             QuestionDto questionDto = new QuestionDto();
             questionDto.setQuestionTitle(question.getQuestionTitle());
             questionDto.setCategory(question.getCategory());
             questionDto.setOption1(question.getOption1());
             questionDto.setOption2(question.getOption2());
             questionDto.setOption3(question.getOption3());
             questionDto.setOption4(question.getOption4());
             questionDtos.add(questionDto);
        }
        return new ResponseEntity<>(questionDtos, HttpStatus.OK);
   }

    public ResponseEntity<Integer> getScore(List<ResponseDto> responses) {

        int result = 0;
        for (ResponseDto responseDto : responses) {
            Question question = questionDao.findById((long) responseDto.getId()).get();
            if(responseDto.getAnswer().equals(question.getRightAnswer())){
                result += 1;
            }
        }
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
