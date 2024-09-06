package com.nianti.controllers;


import com.nianti.models.Question;
import com.nianti.models.Quiz;
import com.nianti.services.QuestionDao;
import com.nianti.services.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;

    @GetMapping("/quizzes/{quizId}/question/{questionNumber}")
    public String getQuestion(Model model, @PathVariable int quizId, @PathVariable int questionNumber) {
        Question question = questionDao.getQuestion(quizId, questionNumber);
        model.addAttribute("question", question);

        return "/quizzes/fragments/quiz-question";
    }

}
