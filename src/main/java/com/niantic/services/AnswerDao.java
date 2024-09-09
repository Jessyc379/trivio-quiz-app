package com.niantic.services;

import com.niantic.models.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;

@Component
public class AnswerDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AnswerDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ArrayList<Answer> getAnswersByQuestionId(int questionId) {
        ArrayList<Answer> answers = new ArrayList<>();
        String sql = """
                    SELECT *
                    FROM answer
                    WHERE question_id = ?;
                """;
        var row = jdbcTemplate.queryForRowSet(sql, questionId);

        while (row.next()) {
            answers.add(mapRowToAnswer(row));
        }

        return answers;
    }

    public Answer getAnswerByAnswerId(int answerId) {
        String sql = """
                    SELECT *
                    FROM answer
                    WHERE answer_id = ?;
                """;
        var row = jdbcTemplate.queryForRowSet(sql, answerId);

        if (row.next()) {
            return mapRowToAnswer(row);
        }

        return null;
    }

    public void updateAnswer(Answer answer) {
        String sql = """
                    UPDATE answer
                    SET question_id = ?
                        , answer_text = ?
                        , is_correct = ?
                    WHERE answer_id = ?;
                """;
        jdbcTemplate.update(sql
                , answer.getQuestionId()
                , answer.getAnswerText()
                , answer.getIsCorrect()
                , answer.getAnswerId());

    }

    private Answer mapRowToAnswer(SqlRowSet row) {
        int answerId = row.getInt("answer_id");
        int questionId = row.getInt("question_id");
        String answerText = row.getString("answer_text");
        boolean isCorrect = row.getBoolean("is_correct");

        return new Answer(answerId, questionId, answerText, isCorrect);
    }

}
