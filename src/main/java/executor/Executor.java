package executor;

import java.util.List;

import mapping.MappedStatement;

public interface Executor
{

    /**
     * 查询数据库
     * @param ms
     * @param parameter
     * @return 
     * @see 
     */
    <E> List<E> doQuery(MappedStatement ms, Object parameter);
    
    /**
     * 更新操作
     * @param ms
     * @param parameter 
     */
    void doUpdate(MappedStatement ms, Object parameter);
    
    
    /**
     * 插入操作
     * @param ms
     * @param parameter
     */
    void doInset(MappedStatement ms, Object parameter);
    
    
    /**
     * 删除操作
     * @param ms
     * @param parameter
     */
    void doDelete(MappedStatement ms, Object parameter);
    
    //关闭链接
    void close();
    
    //提交事务
    void commit();
}
