package mytest.mapper.annotest;


import java.util.List;

import annotation.Delete;
import annotation.Insert;
import annotation.Result;
import annotation.Select;
import annotation.Update;
import mytest.bean.Myuser;


public interface TestUserMapper
{
	
	@Result(Myuser.class)
	@Select("select * from myuser where id = #{id}")
    Myuser getUser(String id);
    
	@Result(Myuser.class)
	@Select("select * from myuser")
    List<Myuser> getAll();
    
	@Update("update myuser set username = #{username} where id = #{id}")
    void updateUser(String username, String id);
	
	@Insert("insert into myuser(username,password) values(#{username},#{password})")
	void addUser(String username,String password);
	
	@Delete("delete from myuser where id = #{id}")
	void deleteUser(Integer id);
}
