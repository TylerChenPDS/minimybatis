package mytest.mapper;


import java.util.List;

import mytest.bean.Myuser;


public interface UserMapper
{

    Myuser getUser(String id);
    
    List<Myuser> getAll();
    
    void updateUser(String id);
}
