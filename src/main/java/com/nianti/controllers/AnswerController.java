package com.nianti.controllers;


import com.nianti.models.Answer;
import com.nianti.models.Question;
import com.nianti.services.AnswerDao;
import com.nianti.services.QuestionDao;
import com.nianti.services.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class AnswerController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @GetMapping("/answers/{questionId}")
    public String getAnswers(Model model, @PathVariable int questionId) {
        ArrayList<Answer> answers = answerDao.getAnswersByQuestionId(questionId);
        model.addAttribute("answers", answers);
        return "/answers/fragments/quiz-answers";
    }


}
