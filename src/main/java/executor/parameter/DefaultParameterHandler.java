package executor.parameter;

import java.sql.PreparedStatement;

public class DefaultParameterHandler implements ParameterHandler
{

    private Object parameter;

    public DefaultParameterHandler(Object parameter)
    {
        this.parameter = parameter;
    }
    
    
    /**
     * 将SQL参数设置到PreparedStatement中
     *
     * @param paramPreparedStatement
     */
    // 注 感觉这里语义不清楚，，应该够造时把 PreparedStatement 传进去
    // 而setParameter 应该把
    @Override
    public void setParameters(PreparedStatement ps)
    {
        try
        {
            if (null != parameter)
            {
                if (parameter.getClass().isArray())
                {
                    Object[] params = (Object[])parameter;
                    
                    for (int i = 0; i < params.length; i++ )
                    {
                        //Mapper保证传入参数类型匹配，这里就不做类型转换了
                        ps.setObject(i + 1, params[i]);
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
