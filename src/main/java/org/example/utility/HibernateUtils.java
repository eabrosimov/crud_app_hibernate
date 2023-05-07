package org.example.utility;

import org.example.model.Developer;
import org.example.model.Skill;
import org.example.model.Specialty;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .configure()
                    .addAnnotatedClass(Developer.class)
                    .addAnnotatedClass(Skill.class)
                    .addAnnotatedClass(Specialty.class)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}
