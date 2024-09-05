package com.nianti.controllers;


import com.nianti.services.QuestionDao;
import com.nianti.services.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;

@Controller
public class QuizController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;


}
