package com.nianti.controllers;


import com.nianti.models.Answer;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuizDao quizDao;

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @GetMapping("/questions/{quizId}")
    public String getQuestionsById(Model model, @PathVariable int quizId){
        List<Question> questions = questionDao.getQuestionByQuizId(quizId);

        model.addAttribute("questions", questions);

        return "questions/questions";
    }

//
//    //added 9.6PM
//    @GetMapping("/quizzes/{quizId}/edit")
//    public String editQuestion(Model model, @PathVariable int quizId, @PathVariable int questionId){
//
//        Question question = questionDao.getQuestion(quizId, questionId);
//
//
//
//        model.addAttribute("question", question);
//        model.addAttribute("action" ,"edit");
//
//        return "/quizzes/add-edit-quiz";
//        //not sure if we can do this on same page without seperation
//
//    }
//
//    //added 9.6PM
//    @PostMapping("/quizzes/{quizId}/edit")
//    public String editQuestion(Model model, @Valid @ModelAttribute("question")
//    Question question, @PathVariable int questionId, BindingResult result){
//        if(result.hasErrors()){
//            model.addAttribute("isInvalid", true);
//            return "quizzes/add-edit-quiz";
//        }
//        question.setQuestionId(questionId);
//        questionDao.updateQuestion(question);
//
//        return "redirect:/quizzes/manage";
////        @PathVariable int quizId
//    }
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