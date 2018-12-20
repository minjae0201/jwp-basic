package core.jdbc;

 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;


public class JdbcTemplate {

	 public void update(String sql, PreparedStatementSetter pss) throws  DataAccessException {

	         try(Connection con = ConnectionManager.getConnection();
	        	PreparedStatement  pstmt = con.prepareStatement(sql);) {
	            pss.setParameters(pstmt);
	            pstmt.executeUpdate();

	         } catch (SQLException e) {
				throw new DataAccessException(e);
			} 
	 }

	 public void update(String sql, Object... parameters) throws  DataAccessException {

      update(sql, createPreparedStatementSetter(parameters));

	 }

	 

		public <T>List<T> query(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws DataAccessException{
			ResultSet rs = null;
			try(Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);) {
				
				pss.setParameters(pstmt);

				rs =pstmt.executeQuery();

				List<T> result = new ArrayList<T>();

				while(rs.next()) {
					result.add(rowMapper.mapRow(rs));
				}
				return result;
			} catch (SQLException e) {
				throw new DataAccessException(e);
			} finally {
				if(rs != null)
					try {
						rs.close();
					} catch (SQLException e) {
						throw new DataAccessException();
					}
				}
			
		}

		public <T>List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) throws DataAccessException{
			return query(sql, createPreparedStatementSetter(parameters), rowMapper);
			}
		
		public <T>  T queryForObject(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper) throws  DataAccessException {
			List<T> result = query(sql,pss,rowMapper);
			if(result.isEmpty()) 
				return null;
			return result.get(0);
		}

		public <T>  T queryForObject(String sql, RowMapper<T> rowMapper,Object... parameters) throws  DataAccessException {
			return queryForObject(sql, createPreparedStatementSetter(parameters), rowMapper);
		}

		private PreparedStatementSetter createPreparedStatementSetter(Object...parameters) {
			return new PreparedStatementSetter() {
				public void setParameters(PreparedStatement pstmt) throws SQLException{
					for(int i = 0; i < parameters.length; i++) {
		        		pstmt.setObject(i+1,parameters[i]);

		        	}
				}
			};
		}
		
		public void update(PreparedStatementCreator psc, KeyHolder holder) {
	         try(Connection con = ConnectionManager.getConnection()){
	        	 PreparedStatement ps = psc.createPreparedStatement(con);
	        	 ps.executeUpdate();
	        	 
	        	 ResultSet rs = ps.getGeneratedKeys();
	        	 if(rs.next()) {
	        		 holder.setId(rs.getLong(1));
	        	 }
	        	 rs.close();
	         } catch (SQLException e) {
				throw new DataAccessException(e);
			} 
	 }
}


