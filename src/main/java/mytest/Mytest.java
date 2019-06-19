package mytest;


import org.junit.Test;

import mytest.bean.Myuser;
import mytest.bean.Student;
import mytest.mapper.UserMapper;
import mytest.mapper.annotest.StudentMapper;
import mytest.mapper.annotest.TestUserMapper;
import session.SqlSession;
import session.SqlSessionFactory;
import session.SqlSessionFactoryBuilder;

public class Mytest {

	@Test
	public void test01() {
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
		SqlSession session = factory.openSession();
		UserMapper userMapper = session.getMapper(UserMapper.class);
		
		Myuser user = userMapper.getUser("1");
		System.out.println("******* " + user);
		System.out.println("******* " + userMapper.getUser("2"));
		System.out.println("*******all " + userMapper.getAll());

		userMapper.updateUser("1");
		System.out.println("*******all " + userMapper.getAll());
	}
	
	@Test
	public void test02() {
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
		SqlSession session = factory.openSession();
		TestUserMapper testUserMapper = session.getMapper(TestUserMapper.class);
//		Myuser user = testUserMapper.getUser("1");
//		System.out.println(user);
		System.out.println(testUserMapper.getAll());
		
		testUserMapper.addUser("12312313131", "123123123132");
		
		System.out.println(testUserMapper.getAll());
		
		testUserMapper.deleteUser(3);
		System.out.println(testUserMapper.getAll());
	}
	
	@Test
	public void test003() {
		try {
			
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build("conf.properties");
			SqlSession session = factory.openSession();
			
			StudentMapper studentMapper = session.getMapper(StudentMapper.class);
			
			Student student = studentMapper.getStudent(1);
			System.out.println(student);
			System.out.println(student.getCourses());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}
