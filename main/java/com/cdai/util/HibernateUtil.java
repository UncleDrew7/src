package com.cdai.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.common.base.Optional;

/**
 * Created by apple on 05/08/2017.
 */
public class HibernateUtil {

    private static Optional<SessionFactory> sessionFactory = Optional.absent();

    private static SessionFactory buildSessionFactory(){
        try {
//            /Create the sessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        }catch(Throwable ex){
            //make suer you log the exception , as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

    }

    public static SessionFactory getSessionFactory(){
        if(sessionFactory.isPresent()){
            return sessionFactory.get();
        }
        sessionFactory = Optional.fromNullable(buildSessionFactory());
        return sessionFactory.get();
    }

    public static void shutdown(){
        //close caches and connection pools
        getSessionFactory().close();
    }
}
