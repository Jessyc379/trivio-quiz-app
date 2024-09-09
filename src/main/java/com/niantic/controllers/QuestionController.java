package com.niantic.controllers;

import com.niantic.models.Question;
import com.niantic.models.Quiz;
import com.niantic.services.QuestionDao;
import com.niantic.services.QuizDao;
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
        model.addAttribute("title", "Quiz Questions");

        return "/questions/questions";
    }

    @GetMapping("/questions/{quizId}/add")
    public String addQuestion(Model model, @PathVariable int quizId) {
        Question question = new Question();
        question.setQuizId(quizId);

        model.addAttribute("question", question);
        model.addAttribute("action", "add");
        model.addAttribute("title", "Add New Question");

        return "/questions/add-edit";
    }

    @PostMapping("/questions/{quizId}/add")
    public String addQuestion(Model model,
                              @Valid @ModelAttribute("question") Question question, BindingResult result,
                              @PathVariable int quizId) {
        if (result.hasErrors()) {
            model.addAttribute("isInvalid", true);
            model.addAttribute("action", "add");
            return "/questions/add-edit";
        }
        question.setQuizId(quizId);
        questionDao.addQuestion(question);

        return "redirect:/questions?quizId=" + question.getQuizId();

    }

    @GetMapping("/questions/{quizId}/{questionId}/edit")
    public String editQuestion(Model model, @PathVariable int quizId, @PathVariable int questionId) {
        Question question = questionDao.getQuestionByQuestionId(questionId);

        model.addAttribute("question", question);
        model.addAttribute("action", "edit");
        model.addAttribute("title", "Edit Question");

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
}