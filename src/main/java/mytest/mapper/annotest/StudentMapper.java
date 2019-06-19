package mytest.mapper.annotest;

import annotation.Collection;
import annotation.Result;
import annotation.Select;
import mytest.bean.Course;
import mytest.bean.Student;

public interface StudentMapper {
	
	@Collection(select="select b.id,b.coursename from s_c a,course b where a.cid = b.id and a.sid = #{id}",
			collectionFiledName="courses",
			useFiledName="id",
			resultType=Course.class)
	@Result(Student.class)
	@Select("select * from student where id = #{id}")
	Student getStudent(Integer id);
}
