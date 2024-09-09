package com.nianti.controllers;

import com.nianti.models.Answer;
import com.nianti.models.Question;
import com.nianti.services.AnswerDao;
import com.nianti.services.QuestionDao;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class AnswerController {
    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @GetMapping("/answers")
    public String getAnswersByQuestionId(Model model, @RequestParam(required = true) int questionId) {
        Question question = questionDao.getQuestionByQuestionId(questionId);
        ArrayList<Answer> answers = answerDao.getAnswersByQuestionId(questionId);

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        model.addAttribute("title", "Question Answers");

        return "/answers/answers";
    }

//    @GetMapping("/questions/{quizId}/add")
//    public String addQuestion(Model model, @PathVariable int quizId) {
//        Question question = new Question();
//        question.setQuizId(quizId);
//
//        model.addAttribute("question", question);
//        model.addAttribute("action", "add");
//
//        return "/questions/add-edit";
//    }
//
//    @PostMapping("/questions/{quizId}/add")
//    public String addQuestion(Model model,
//                              @Valid @ModelAttribute("question") Question question, BindingResult result,
//                              @PathVariable int quizId) {
//        if (result.hasErrors()) {
//            model.addAttribute("isInvalid", true);
//            model.addAttribute("action", "add");
//            return "/questions/add-edit";
//        }
//        question.setQuizId(quizId);
//        questionDao.addQuestion(question);
//
//        return "redirect:/questions?quizId=" + question.getQuizId();
//
//    }
//
//    @GetMapping("/questions/{quizId}/{questionId}/edit")
//    public String editQuestion(Model model, @PathVariable int quizId, @PathVariable int questionId) {
//        Question question = questionDao.getQuestionByQuestionId(questionId);
//
//        model.addAttribute("question", question);
//        model.addAttribute("action", "edit");
//
//        return "/questions/add-edit";
//    }
//
//    @PostMapping("/questions/{quizId}/{questionId}/edit")
//    public String editQuestion(Model model,
//                               @Valid @ModelAttribute("question") Question question, BindingResult result,
//                               @PathVariable int quizId,
//                               @PathVariable int questionId) {
//        if (result.hasErrors()) {
//            model.addAttribute("isInvalid", true);
//            model.addAttribute("action", "edit");
//            return "/questions/add-edit";
//        }
//        question.setQuestionId(questionId);
//        question.setQuizId(quizId);
//        questionDao.updateQuestion(question);
//
//        return "redirect:/questions?quizId=" + question.getQuizId();
//
//    }
}