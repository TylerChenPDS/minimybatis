package binding;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Collection;

import annotation.Delete;
import mapping.MappedStatement;
import session.SqlSession;

public class MapperProxy<T> implements InvocationHandler, Serializable
{

    private static final long serialVersionUID = -7861758496991319661L;

    private final SqlSession sqlSession;

    private final Class<T> mapperInterface; //mapper接口

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface)
    {

        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    // 这个地方相当于全部执行了sqlSession 里面的方法。
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
    {
        try
        {
        	//getDeclaringClass() 方法返回表示声明由此Method对象表示的方法的类的Class对象，如果该方法是该类的方法的时候
        	//即查看是否
            if (Object.class.equals(method.getDeclaringClass())) 
            {
                return method.invoke(this, args);
            }
            return  this.execute(method, args);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 根据SQL指令执行对应操作
     * 
     * @param method
     * @param args
     * @return 
     */
    private Object execute(Method method,  Object[] args)
    {
        String statementId = this.mapperInterface.getName() + "." + method.getName();
        MappedStatement ms = this.sqlSession.getConfiguration().getMappedStatement(statementId);
        
        Object result = null;
        switch (ms.getSqlCommandType())
        {
        	//查询语句
            case SELECT:
            {
                Class<?> returnType = method.getReturnType();
                
                // 如果返回的是list，应该调用查询多个结果的方法，否则只要查单条记录
                if (Collection.class.isAssignableFrom(returnType))
                {
                    //ID为mapper类全名+方法名
                    result = sqlSession.selectList(statementId, args);
                }
                else
                {
                    result = sqlSession.selectOne(statementId, args);
                }
                break;
            }
            //更新
            case UPDATE:
            {
                sqlSession.update(statementId, args);
                break;
            }
            case DELETE:{
            	sqlSession.delete(statementId, args);
            	break;
            }
            case INSERT:{
            	sqlSession.insert(statementId, args);
            	break;
            }
            default:
            {
                break;
            }
        }
        
        return result;
    }
    
}
