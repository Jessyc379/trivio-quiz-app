package com.nianti.controllers;


import com.nianti.models.Quiz;
import com.nianti.services.QuestionDao;
import com.nianti.services.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.sql.DataSource;

@Controller
public class QuizController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;

    @GetMapping("/quizzes/{quizId}")
    public String quizPage(Model model, @PathVariable int quizId) {
        Quiz quiz = quizDao.getQuizById(quizId);
        model.addAttribute("quiz",quiz);

        // get number of questions
        int questionsTotal = questionDao.getTotalNumberOfQuestionsByQuizId(quizId);
        model.addAttribute("questionsTotal",questionsTotal);

        return "quizzes/index";
    }


}
