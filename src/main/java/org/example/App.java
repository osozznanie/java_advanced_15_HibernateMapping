package org.example;

import org.example.model.Comment;
import org.example.model.Post;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.jboss.logging.annotations.Pos;

import java.util.HashSet;
import java.util.Set;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        config.configure("/META-INF/hibernate.cfg.xml");

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties())
                .buildServiceRegistry();
        Session session = config.buildSessionFactory(serviceRegistry).openSession();

        Post post1 = new Post();
        post1.setTitle("My Holiday");

        Post post2 = new Post();
        post2.setTitle("My Job");

        Comment comment1 = new Comment();
        comment1.setAuthorName("Vlad");
        comment1.setPost(post1);

        Comment comment2 = new Comment();
        comment2.setAuthorName("Polina");
        comment2.setPost(post1);

        Set<Comment> comments = new HashSet<>();
        comments.add(comment1);
        comments.add(comment2);

        post1.setComments(comments);

        // save to DB
        Transaction transaction = session.beginTransaction();
        session.save(post1);
        transaction.commit();

        // read from DB
        Post postDB = (Post) session.get(Post.class, 1);
        System.out.println(postDB + "---->" + postDB.getComments());

        Comment commentDB = (Comment) session.get(Comment.class, 2);
        System.out.println(commentDB + "---->" + commentDB.getPost());





    }
}
