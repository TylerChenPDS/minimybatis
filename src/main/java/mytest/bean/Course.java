package mytest.bean;

public class Course {
	private Integer id;
	private String coursename;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	@Override
	public String toString() {
		return "Course [id=" + id + ", coursename=" + coursename + "]";
	}
	
}
