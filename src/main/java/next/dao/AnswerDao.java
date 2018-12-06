package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import next.model.Answer;

public class AnswerDao {
	public List<Answer> findAll(long questionId) throws SQLException {

    	JdbcTemplate jdbcTemplate = new JdbcTemplate();

    	String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE questionId=? ORDER BY answerId DESC";
    	return  jdbcTemplate.query(sql,(ResultSet rs)->{
        	return new Answer(rs.getLong("answerId"),
        			rs.getString("writer"),
        			rs.getString("contents"),
        			rs.getTimestamp("createdDate"),
        			rs.getLong("questionId"));
        	}, questionId);
    }

    public Answer findById(Long answerId) throws SQLException {

    	JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE questionId=?";

        return jdbcTemplate.queryForObject(sql,(ResultSet rs)->{
        	return new Answer(rs.getLong("answerId"),
        			rs.getString("writer"),
        			rs.getString("contents"),
        			rs.getTimestamp("createdDate"),
        			rs.getLong("answerId"));
        	}, answerId);
    }
}
