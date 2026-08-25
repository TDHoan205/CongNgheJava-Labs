package vn.edu.eaut.lab10.config;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationLifecycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        JPAUtil.getEntityManagerFactory();
        Logger.info("Application started");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JPAUtil.close();
        AbandonedConnectionCleanupThread.checkedShutdown();
        Logger.info("Application stopped");
    }
}
