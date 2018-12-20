package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;

import core.jdbc.JdbcTemplate;
import core.jdbc.KeyHolder;
import next.controller.qna.addAnswerController;
import next.model.Answer;

public class AnswerDao {
	private final static Logger log = LoggerFactory.getLogger(AnswerDao.class);
	public Answer insert(Answer answer) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "INSERT INTO ANSWERS(writer, contents, createdDate, questionId)" +
					 "VALUES(?, ?, ?, ?)";
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, answer.getWriter());
				pstmt.setString(2, answer.getContents());
				pstmt.setTimestamp(3, new Timestamp(answer.getTimeFromCreatedDate()));
				pstmt.setLong(4, answer.getQuestionId());
				return pstmt;
			}
		};
		
		KeyHolder keyHolder = new KeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		log.debug("keyHolder Id: {}", keyHolder.getId());
		return findById(keyHolder.getId());
	}
	public void delete(Long answerId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "DELETE FROM ANSWERS WHERE answerId=?";
		jdbcTemplate.update(sql, answerId);
	}
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

        String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE answerId=?";

        return jdbcTemplate.queryForObject(sql,(ResultSet rs)->{
        	return new Answer(rs.getLong("answerId"),
        			rs.getString("writer"),
        			rs.getString("contents"),
        			rs.getTimestamp("createdDate"),
        			rs.getLong("answerId"));
        	}, answerId);
    }
}
