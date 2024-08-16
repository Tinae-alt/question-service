package com.xyztechch.question_service.controllers;

import com.xyztechch.question_service.dto.QuestionDto;
import com.xyztechch.question_service.dto.ResponseDto;
import com.xyztechch.question_service.model.Question;
import com.xyztechch.question_service.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("questions")
public class QuestionController {
  private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("allQuestions")
    public List<Question> getQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{cat}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("cat") String category){
        log.info("===Category==={}",category);
        return questionService.getAllQuestionsByCategory(category);
    }
    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody QuestionDto questionDto){
        log.info("===Question to add==={}",questionDto);
        return questionService.addQuestion(questionDto);
    }

    //generate
    @GetMapping("generate")
    public ResponseEntity<List<Integer>> generateQuestionForQuiz(@RequestParam String category, @RequestParam int numQ)
    {
        log.info("===Category==={},{}",category,numQ);
        return questionService.getQuestionsForQuiz(category,numQ);
    }
    //getQuestions(quiz id)
    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionDto>> getQuestiosFromId(@RequestBody List<Integer> ids) {
        return questionService.getQuestionsFromId(ids);
    }
    //getScore()
    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<ResponseDto> responses) {
        return questionService.getScore(responses);
    }

}
