package com.nianti.controllers;


import com.nianti.models.Question;
import com.nianti.models.Quiz;
import com.nianti.services.QuestionDao;
import com.nianti.services.QuizDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

@Controller
public class QuizController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;


    @GetMapping("/quizzes")
    public String allActiveQuizzes(Model model) {
        var quizzes = quizDao.getAllQuizzes().stream().filter(Quiz::getIsLive).toList();

        model.addAttribute("title", "All Quizzes");
        model.addAttribute("quizzes", quizzes);

        return "/quizzes/index";
    }

    @GetMapping("/quizzes/manage")
    public String allQuizzes(Model model) {
        var quizzes = quizDao.getAllQuizzes();

        model.addAttribute("title", "Manage Quizzes");
        model.addAttribute("quizzes", quizzes);

        return "/quizzes/quiz-management";
    }

    @GetMapping("/quizzes/{quizId}")
    public String quizPage(Model model, @PathVariable int quizId) {
        Quiz quiz = quizDao.getQuizById(quizId);
        model.addAttribute("quiz", quiz);

        // get number of questions
        int questionsTotal = questionDao.getTotalNumberOfQuestionsByQuizId(quizId);
        model.addAttribute("questionsTotal", questionsTotal);

        return "/quizzes/take-quiz";
    }


    @GetMapping("/quizzes/{quizId}/edit")
    public String editQuiz(Model model, @PathVariable int quizId) {

        Quiz quiz = quizDao.getQuizById(quizId);
        model.addAttribute("quiz", quiz);
        model.addAttribute("action", "edit");
        return "/quizzes/add-edit-quiz";
    }


    @PostMapping("/quizzes/{quizId}/edit")
    public String editQuiz(Model model, @Valid @ModelAttribute("quiz") Quiz quiz, BindingResult result, @PathVariable int quizId) {
        if (result.hasErrors()) {
            model.addAttribute("isInvalid", true);
            model.addAttribute("action", "edit");
            return "quizzes/add-edit-quiz";
        }
        quiz.setQuizId(quizId);
        quizDao.updateQuiz(quiz);

        return "redirect:/quizzes/manage";
    }
    @GetMapping("/quizzes/add")
    public String addQuiz(Model model){
        model.addAttribute("quiz", new Quiz());
        model.addAttribute("action", "add");

        return "quizzes/add-edit-quiz";
    }
    //added 9.6 PM
    @PostMapping("/quizzes/add")
    public String addQuiz(Model model, @Valid @ModelAttribute("quiz") Quiz quiz, BindingResult result)
    {
        if(result.hasErrors())
        {
            model.addAttribute("isInvalid", true);
            model.addAttribute("action", "add");

            return "quizzes/add-edit-quiz";
        }
        quizDao.addQuiz(quiz);
        return "redirect:/quizzes/manage";

    }





}
