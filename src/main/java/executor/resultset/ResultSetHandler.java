package executor.resultset;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public interface ResultSetHandler
{

    /**
     * 处理查询结果
     * 
     * @param resultSet
     * @return 
     * @see 
     */
    <E> List<E> handleResultSets(ResultSet resultSet);
    
    //设置connection 给联合查询做准备
    void setConnection(Connection connection);

}
