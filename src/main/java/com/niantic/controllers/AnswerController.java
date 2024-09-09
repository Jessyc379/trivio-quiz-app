package com.niantic.controllers;

import com.niantic.models.Answer;
import com.niantic.models.Question;
import com.niantic.services.AnswerDao;
import com.niantic.services.QuestionDao;
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

    @GetMapping("/answers/{questionId}/add")
    public String addAnswer(Model model, @PathVariable int questionId) {
        Answer answer = new Answer();
        answer.setQuestionId(questionId);

        model.addAttribute("answer", answer);
        model.addAttribute("action", "add");
        model.addAttribute("title", "Add Answer");

        return "/answers/add-edit";
    }

    @PostMapping("/answers/{questionId}/add")
    public String addQuestion(Model model,
                              @Valid @ModelAttribute("answer") Answer answer, BindingResult result,
                              @PathVariable int questionId) {
        if (result.hasErrors()) {
            model.addAttribute("isInvalid", true);
            model.addAttribute("action", "add");
            return "/answers/add-edit";
        }

        answer.setQuestionId(questionId);
        answerDao.addAnswer(answer);

        return "redirect:/answers?questionId=" + questionId;
    }

    @GetMapping("/answers/{questionId}/{answerId}/edit")
    public String editAnswer(Model model, @PathVariable int answerId) {
        Answer answer = answerDao.getAnswerByAnswerId(answerId);

        model.addAttribute("answer", answer);
        model.addAttribute("action", "edit");
        model.addAttribute("title", "Edit Answer");

        return "/answers/add-edit";
    }

    @PostMapping("/answers/{questionId}/{answerId}/edit")
    public String editAnswer(Model model,
                             @Valid @ModelAttribute("answer") Answer answer, BindingResult result,
                             @PathVariable int questionId,
                             @PathVariable int answerId) {
        if (result.hasErrors()) {
            model.addAttribute("isInvalid", true);
            model.addAttribute("action", "edit");
            return "/answers/add-edit";
        }
        answer.setAnswerId(answerId);
        answer.setQuestionId(questionId);
        answerDao.updateAnswer(answer);

        return "redirect:/answers?questionId=" + questionId;

    }
}