package binding;

import java.util.HashMap;
import java.util.Map;

import session.SqlSession;

public class MapperRegistry
{
	//所注册进来的Mapper，及其对应的 MapperProxyFactory
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();
   
    public <T> void addMapper(Class<T> type)
    {
        this.knownMappers.put(type, new MapperProxyFactory<T>(type));
    }
    
   
    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> type, SqlSession sqlSession)
    {
      MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>)this.knownMappers.get(type);
      return mapperProxyFactory.newInstance(sqlSession);
    }
}
