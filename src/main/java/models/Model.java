package models;


import java.util.List;

public interface Model {
    void addUser(String user_name, String password);
    void createPost(String content, String time);
    List getAllPosts();
    List getUserName();
}


