
package annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Collection {
	String value() default "";

	String select();// select 语句

	String collectionFiledName();// 对应对象List的Name

	String[] useFiledName();//使用到的本对象里面的fileName
	
	Class<?> resultType();//联合查询的返回值
}
