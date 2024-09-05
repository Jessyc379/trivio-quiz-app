package com.nianti.services;

import com.nianti.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class QuestionDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public QuestionDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Question> getQuestionByQuizId(int quizId) {
        return null;
    }


    public int getTotalNumberOfQuestionsByQuizId(int quizId) {
        String sql = """
                        SELECT COUNT(*) AS total
                        FROM question
                        WHERE quiz_id = ?;
                    """;

        var row = jdbcTemplate.queryForRowSet(sql, quizId);

        if (row.next()) {
            return row.getInt("total");
        }

        return 0;
    }
}
