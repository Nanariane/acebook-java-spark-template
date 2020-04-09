import models.Model;
import models.Sql2oModel;
import models.UserName;
import org.flywaydb.core.Flyway;
import org.sql2o.Sql2o;
import org.sql2o.converters.UUIDConverter;
import org.sql2o.quirks.PostgresQuirks;
import spark.ModelAndView;
import spark.Redirect;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static spark.Spark.get;
import static spark.Spark.post;

public class Main {


    public static void main(String[] args) {
        String dbName = "acebook";
        for (String a : args) {
            dbName = a;
        }
        System.out.println(dbName);
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/" + dbName, null, null).load();
        flyway.migrate();

        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/" + dbName, null, null, new PostgresQuirks() {
            {
                // make sure we use default UUID converter.
                converters.put(UUID.class, new UUIDConverter());
            }
        });

        Model model = new Sql2oModel(sql2o);

        get("/", (request, response) -> {

//            HashMap homepage = new HashMap();

            return new ModelAndView(new HashMap(), "templates/homepage.vtl");
        }, new VelocityTemplateEngine());

        post("/", (request, response) -> {

                    String user_name = request.queryParams("user_name_1");
                    String password = request.queryParams("psw");
                    model.addUser(user_name, password);

//                HashMap homepage = new HashMap();

                    response.redirect("/dashboard");
                    return null;
                }

        );


        get("/dashboard", (req, res) -> {
            model.getAllPosts();
            HashMap posts = new HashMap();
            posts.put("posts", model.getAllPosts());

            posts.put("userName", model.getUserName());

            return new ModelAndView(posts, "templates/dashboard.vtl");
        }, new VelocityTemplateEngine());


        post("/dashboard", (request, response) -> {

            String content = request.queryParams("send_post");
            LocalDateTime currentTimestamp = LocalDateTime.now();
            model.createPost(content, String.valueOf(currentTimestamp));



            response.redirect("/dashboard");
            return null;
        });



    }
}
