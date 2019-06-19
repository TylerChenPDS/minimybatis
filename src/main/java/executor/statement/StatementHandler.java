package executor.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface StatementHandler
{

    /**
     * SQL预处理
     * 
     * @param paramConnection
     * @return
     * @throws SQLException 
     * @see 
     */
    PreparedStatement prepare(Connection paramConnection) throws SQLException;

    /**
     * 查询数据库
     * 
     * @param preparedStatement
     * @return
     * @throws SQLException 
     * @see 
     */
    ResultSet query(PreparedStatement preparedStatement) throws SQLException;
    
    /**
     * update
     * 
     * @param preparedStatement
     * @throws SQLException 
     */
    void update(PreparedStatement preparedStatement) throws SQLException;
}
