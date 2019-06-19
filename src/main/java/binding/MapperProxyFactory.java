package binding;

import java.lang.reflect.Proxy;

import session.SqlSession;

public class MapperProxyFactory<T>
{

    private final Class<T> mapperInterface;

  
    public MapperProxyFactory(Class<T> mapperInterface)
    {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance(SqlSession sqlSession)
    {
        MapperProxy<T> mapperProxy = new MapperProxy<T>(sqlSession, this.mapperInterface);
        return newInstance(mapperProxy);
    }

    /**
     * 根据mapper代理返回实例
     * 
     * @param mapperProxy
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    protected T newInstance(MapperProxy<T> mapperProxy)
    {
        return (T)Proxy.newProxyInstance(this.mapperInterface.getClassLoader(), new Class[] {this.mapperInterface},
            mapperProxy);
    }
}
