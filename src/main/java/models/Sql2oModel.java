package models;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.UUID;

public class Sql2oModel implements Model {

    private Sql2o sql2o;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;

    }

    @Override
    public void addUser(String user_name, String password) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID personUuid = UUID.randomUUID();
            conn.createQuery("insert into person(user_id, user_name, password) VALUES (:user_id, :user_name, :password)")
                    .addParameter("user_id", personUuid)
                    .addParameter("user_name", user_name)
                    .addParameter("password", password)
                    .executeUpdate();
            conn.commit();

        }
    }

    @Override
    public void createPost(String content, String time) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID postsUuid = UUID.randomUUID();
            conn.createQuery("insert into posts(post_id, content, time) VALUES (:post_id, :content, :time)")
                    .addParameter("post_id", postsUuid)
                    .addParameter("content", content)
                    .addParameter("time", time)
                    .executeUpdate();
            conn.commit();

        }
    }

    @Override
    public List<Post> getAllPosts() {
        try (Connection conn = sql2o.open()) {

        List<Post> items = conn.createQuery("select * from posts ORDER BY time DESC")

                    .executeAndFetch(Post.class);
            return items;
        }
    }

    @Override
    public List getUserName() {
        try (Connection conn = sql2o.open()) {
            List<UserName> userName = conn.createQuery("select user_name from person ORDER BY password DESC LIMIT 1")
            .executeAndFetch(UserName.class);
            return userName;
        }
    }

    @Override
    public void signUp(String first_name, String last_name, String user_name, String email, String password) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID signUpUuid = UUID.randomUUID();
            conn.createQuery("insert into signup(user_id, first_name, last_name, user_name, email, password) VALUES (:user_id, :first_name, :last_name, :user_name, :email, :password)")
                    .addParameter("user_id", signUpUuid)
                    .addParameter("first_name", first_name)
                    .addParameter("last_name", last_name)
                    .addParameter("user_name", user_name)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .executeUpdate();
            conn.commit();

        }
    }

}