package vn.edu.eaut.lab7.listener;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.*;

@WebListener
public class AppListener implements ServletContextListener, HttpSessionListener {
    public void contextInitialized(ServletContextEvent event) {
        System.out.println("[LAB7] Ung dung khoi dong.");
    }

    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("[LAB7] Ung dung dung.");
    }

    public void sessionCreated(HttpSessionEvent event) {
        System.out.println("[LAB7] Session created: " + event.getSession().getId());
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        System.out.println("[LAB7] Session destroyed: " + event.getSession().getId());
    }
}
