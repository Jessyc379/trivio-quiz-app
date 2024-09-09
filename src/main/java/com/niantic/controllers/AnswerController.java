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

    //    @GetMapping("/questions/{quizId}/add")
//    public String addQuestion(Model model, @PathVariable int quizId) {
//        Question question = new Question();
//        question.setQuizId(quizId);
//
//        model.addAttribute("question", question);
//        model.addAttribute("action", "add");
//        model.addAttribute("title", "Add Answer");
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