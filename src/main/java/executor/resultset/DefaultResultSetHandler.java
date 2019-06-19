package executor.resultset;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import executor.parameter.DefaultParameterHandler;
import executor.parameter.ParameterHandler;
import executor.statement.SimpleStatementHandler;
import executor.statement.StatementHandler;
import mapping.MappedStatement;
import mapping.MappedStatement.MappedStatementCollectionQuery;

public class DefaultResultSetHandler implements ResultSetHandler
{

    private final MappedStatement mappedStatement;

    /**
     * @param mappedStatement
     */
    public DefaultResultSetHandler(MappedStatement mappedStatement)
    {
        this.mappedStatement = mappedStatement;
    }
    
    
    //给联合查询准备的
    private Connection connection;
    public void setConnection(Connection connection) {
    	this.connection = connection;
    }

    /**
     * 处理查询结果，通过反射设置到返回的实体类
     *
     * @param paramStatement
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public <E> List<E> handleResultSets(ResultSet resultSet)
    {
        try
        {

            List<E> result = new ArrayList<>();

            if (null == resultSet)
            {
                return null;
            }

            while (resultSet.next())
            {
                // 通过反射实例化返回类
                Class<?> entityClass = Class.forName(mappedStatement.getResultType());
                E entity = (E)entityClass.newInstance();
                Field[] declaredFields = entityClass.getDeclaredFields();

                for (Field field : declaredFields)
                {
                    // 对成员变量赋值
                    field.setAccessible(true);
                    Class<?> fieldType = field.getType();

                    // 目前只实现了string和int转换
                    if (String.class.equals(fieldType))
                    {
                        field.set(entity, resultSet.getString(field.getName()));
                    }
                    else if (int.class.equals(fieldType) || Integer.class.equals(fieldType))
                    {
                        field.set(entity, resultSet.getInt(field.getName()));
                    }
                    else if(byte[].class.equals(fieldType)){
                    	field.set(entity, resultSet.getBytes(field.getName()));
                    }else if(Collection.class.isAssignableFrom(fieldType)) {//如果是集合类型，,执行联合查询
                    	if(mappedStatement.isHasCollection()) {//查看是否定义了CollectionQuery
                    		MappedStatementCollectionQuery collectionQuery = mappedStatement.getCollectionQuery();
                    		MappedStatement statement = new MappedStatement();
                    		statement.setResultType(collectionQuery.getResultType().getName());
                    		statement.setSql(collectionQuery.getSelect());
                    		
                    		
                    		String[] useFiledNames = collectionQuery.getUseFiledNames();
                    		Object[] parameter = new Object[useFiledNames.length];
                    		for(int i = 0;i < parameter.length; i ++) {
                    			parameter[i] = resultSet.getObject(useFiledNames[i]);
                    		}
                    		
                    		// 1.实例化StatementHandler对象，
                    		StatementHandler statementHandler = new SimpleStatementHandler(statement);
                    		
                    		// 2.通过StatementHandler和connection获取PreparedStatement
                			// 将 #{ } 换成了 ?
                			PreparedStatement preparedStatement = statementHandler.prepare(connection);
                			
                			// 3.实例化ParameterHandler，将SQL语句中?参数化
                			ParameterHandler parameterHandler = new DefaultParameterHandler(parameter);
                			parameterHandler.setParameters(preparedStatement);
                			
                			// 4.执行SQL，得到结果集ResultSet
                			ResultSet collectionresultSet = statementHandler.query(preparedStatement);
                			
                			
                			// 5.实例化ResultSetHandler，通过反射将ResultSet中结果设置到目标resultType对象中
                			ResultSetHandler resultSetHandler = new DefaultResultSetHandler(statement);
                			
                			Object obj = resultSetHandler.handleResultSets(collectionresultSet);
                			field.set(entity, obj);
//                			BeanUtils.setProperty(rs, fieldName, entity.getValue());
                    	}
                    }
                    else{
                        // 其他类型自己转换，这里就直接设置了
                        field.set(entity, resultSet.getObject(field.getName()));
                    }
                }

                result.add(entity);
            }

            return result;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
