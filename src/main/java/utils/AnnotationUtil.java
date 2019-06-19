package utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import annotation.Collection;
import annotation.Delete;
import annotation.Insert;
import annotation.Result;
import annotation.Select;
import annotation.Update;
import constants.Constant.SqlType;
import mapping.MappedStatement;
import mapping.MappedStatement.MappedStatementCollectionQuery;
import session.Configuration;

public class AnnotationUtil {
	public static void readMapperJava(String clazzname, Configuration configuration) {
		try {
			Class<?> clazz = Class.forName(clazzname);
			Method[] methods = clazz.getMethods();
			List<MappedStatement> statements = new ArrayList<>();
			for(Method method : methods) {
				if(method.getAnnotations() != null && method.getAnnotations().length != 0) {
					MappedStatement statement = new MappedStatement();
					String sql = null;
					String sqlId = clazzname + "." + method.getName();
					String namespace = clazzname;
					if(method.isAnnotationPresent(Select.class)) {
						statement.setSqlCommandType(SqlType.SELECT);
						Select selectAn = method.getAnnotation(Select.class);
						sql = selectAn.value();
						Result result = method.getAnnotation(Result.class);
						Class<?> resultClazz = result.value();
						statement.setResultType(resultClazz.getName());
						
						Collection collectionAn = method.getAnnotation(Collection.class);
						if(collectionAn != null) {
							MappedStatementCollectionQuery collectionQuery = new MappedStatementCollectionQuery();
							String select = collectionAn.select();
							String collectionFiledName = collectionAn.collectionFiledName();
							String[] useFiledNames = collectionAn.useFiledName();
							Class<?> resultType = collectionAn.resultType();
							collectionQuery.setSelect(select);
							collectionQuery.setUseFiledNames(useFiledNames);
							collectionQuery.setCollectionFiledName(collectionFiledName);
							collectionQuery.setResultType(resultType);
							statement.setCollectionQuery(collectionQuery);	
							statement.setHasCollection(true);
						}
							
								
					}else if(method.isAnnotationPresent(Insert.class)) {
						statement.setSqlCommandType(SqlType.INSERT);
						Insert insertAn = method.getAnnotation(Insert.class);
						sql = insertAn.value();
					}else if(method.isAnnotationPresent(Delete.class)) {
						statement.setSqlCommandType(SqlType.DELETE);
						Delete deleteAn = method.getAnnotation(Delete.class);
						sql = deleteAn.value();
					}else if(method.isAnnotationPresent(Update.class)) {
						statement.setSqlCommandType(SqlType.UPDATE);
						Update updateAn = method.getAnnotation(Update.class);
						sql = updateAn.value();
					}
					statement.setNamespace(namespace);
					statement.setSqlId(sqlId);
					statement.setSql(sql);
					statements.add(statement);
					
					configuration.addMappedStatement(sqlId, statement);
	                configuration.addMapper(Class.forName(namespace));
					
					System.out.println(statement);
				}
			}
		} catch (ClassNotFoundException e) {
			System.err.println("未找到：" + clazzname );
			e.printStackTrace();
		}
	}
}
