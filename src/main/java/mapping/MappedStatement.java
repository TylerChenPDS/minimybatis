package mapping;

import java.util.Arrays;

import constants.Constant.SqlType;

public final class MappedStatement 
{

    /** mapper文件的namespace */
    private String namespace;

    /** sql的id属性 */
    private String sqlId;

    /** sql语句，对应源码的sqlSource */
    private String sql;

    /** 返回类型 */
    private String resultType;

    /** sqlCommandType对应select/update/insert等 */
    private SqlType sqlCommandType;
    
    /** 是否被有联合查询  **/
    private boolean hasCollection;
    
    private MappedStatementCollectionQuery collectionQuery;

	public static class MappedStatementCollectionQuery{
    	private String select;
    	private String collectionFiledName;
    	private String[] useFiledNames;
    	private Class<?> resultType;
		public String getSelect() {
			return select;
		}
		public void setSelect(String select) {
			this.select = select;
		}
		public String getCollectionFiledName() {
			return collectionFiledName;
		}
		public void setCollectionFiledName(String collectionFiledName) {
			this.collectionFiledName = collectionFiledName;
		}
		public String[] getUseFiledNames() {
			return useFiledNames;
		}
		public void setUseFiledNames(String[] useFiledNames) {
			this.useFiledNames = useFiledNames;
		}
		public Class<?> getResultType() {
			return resultType;
		}
		public void setResultType(Class<?> resultType) {
			this.resultType = resultType;
		}
		@Override
		public String toString() {
			return "MappedStatementCollectionQuery [select=" + select + ", collectionFiledName=" + collectionFiledName
					+ ", useFiledNames=" + Arrays.toString(useFiledNames) + ", resultType=" + resultType + "]";
		}
		
    	
    }
    
	 public MappedStatementCollectionQuery getCollectionQuery() {
			return collectionQuery;
		}


		public void setCollectionQuery(MappedStatementCollectionQuery collectionQuery) {
			this.collectionQuery = collectionQuery;
		}
    
    
    
    public boolean isHasCollection() {
		return hasCollection;
	}


	public void setHasCollection(boolean hasCollection) {
		this.hasCollection = hasCollection;
	}


	public String getNamespace()
    {
        return namespace;
    }

   
    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    
    public String getSqlId()
    {
        return sqlId;
    }

    
    public void setSqlId(String sqlId)
    {
        this.sqlId = sqlId;
    }

   
    public String getSql()
    {
        return sql;
    }

    
    public void setSql(String sql)
    {
        this.sql = sql;
    }

    
    public String getResultType()
    {
        return resultType;
    }

    
    public void setResultType(String resultType)
    {
        this.resultType = resultType;
    }

    
    
    public SqlType getSqlCommandType()
    {
        return sqlCommandType;
    }

    
    public void setSqlCommandType(SqlType sqlCommandType)
    {
        this.sqlCommandType = sqlCommandType;
    }

   
    @Override
	public String toString() {
		return "MappedStatement [namespace=" + namespace + ", sqlId=" + sqlId + ", sql=" + sql + ", resultType="
				+ resultType + ", sqlCommandType=" + sqlCommandType + ", hasCollection=" + hasCollection
				+ ", collectionQuery=" + collectionQuery + "]";
	}
    
    
    

}