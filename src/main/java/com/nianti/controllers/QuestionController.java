package com.nianti.controllers;

import com.nianti.models.Question;
import com.nianti.models.Quiz;
import com.nianti.services.AnswerDao;
import com.nianti.services.QuestionDao;
import com.nianti.services.QuizDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class QuestionController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;

    @GetMapping("/questions")
    public String getQuestionsByQuizId(Model model, @RequestParam(required = true) int quizId) {
        Quiz quiz = quizDao.getQuizById(quizId);
        ArrayList<Question> questions = questionDao.getQuestionByQuizId(quizId);

        model.addAttribute("quiz", quiz);
        model.addAttribute("questions", questions);

        return "/questions/questions";
    }

    @GetMapping("/questions/{quizId}/{questionId}/edit")
    public String editQuestion(Model model, @PathVariable int quizId, @PathVariable int questionId) {
        Question question = questionDao.getQuestionByQuestionId(questionId);

        model.addAttribute("question", question);
        model.addAttribute("action", "edit");

        return "/questions/add-edit";
    }

    @PostMapping("/questions/{quizId}/{questionId}/edit")
    public String editQuestion(Model model,
                               @Valid @ModelAttribute("question") Question question, BindingResult result,
                               @PathVariable int quizId,
                               @PathVariable int questionId) {
        if (result.hasErrors()) {
            model.addAttribute("isInvalid", true);
            model.addAttribute("action", "edit");
            return "/questions/add-edit";
        }
        question.setQuestionId(questionId);
        question.setQuizId(quizId);
        questionDao.updateQuestion(question);

        return "redirect:/questions?quizId=" + question.getQuizId();

    }
//
//    @GetMapping("/quizzes/{quizId}/edit")
//    public String addQuestion(Model model){
//        model.addAttribute("question", new Question());
//        model.addAttribute("action", "add");
//        return "quizzes/add-edit";
//
//    }

//    @PostMapping("/quizzes/{quizId}/edit")
//    public String addQuestion(Model model, @Valid @ModelAttribute("question") Question question, BindingResult result){
//        if(result.hasErrors())
//        {
//            model.addAttribute("isInvalid", true);
//            // redirect back to the add page
//            return "quizzes/add-edit";
//        }
//
//        questionDao.addQuestions(question);
//        return "redirect:/questions/add";
//
//    }
}