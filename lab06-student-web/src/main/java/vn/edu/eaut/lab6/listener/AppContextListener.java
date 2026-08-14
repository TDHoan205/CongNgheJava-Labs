package vn.edu.eaut.lab6.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("==================================================");
        System.out.println("[LISTEN] Ung dung Lab 6 da khoi dong thanh cong!");
        System.out.println("==================================================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("==================================================");
        System.out.println("[LISTEN] Ung dung Lab 6 da dung hoat dong!");
        System.out.println("==================================================");
    }
}
