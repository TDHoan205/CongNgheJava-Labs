package vn.edu.eaut.lab6.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab6.model.Student;
import vn.edu.eaut.lab6.store.StudentStore;

import java.util.ArrayList;
import java.util.List;

@WebListener
public class DataInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        // Initialize at least 5 sample students
        List<Student> sampleList = new ArrayList<>();
        sampleList.add(new Student("SV001", "Nguyen Van An", "DCCNTT12", "an@example.com"));
        sampleList.add(new Student("SV002", "Tran Thi Binh", "DCCNTT12", "binh@example.com"));
        sampleList.add(new Student("SV003", "Le Van Cuong", "DCCNTT13", "cuong@example.com"));
        sampleList.add(new Student("SV004", "Pham Thi Dung", "DCCNTT13", "dung@example.com"));
        sampleList.add(new Student("SV005", "Hoang Van Em", "DCCNTT14", "em@example.com"));

        // Store into StudentStore and ServletContext
        for (Student s : sampleList) {
            StudentStore.add(s);
        }
        context.setAttribute("sampleStudents", sampleList);

        System.out.println("[DATA INIT LISTENER] Da khoi tao " + sampleList.size() + " sinh vien mau vao ServletContext va StudentStore!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        int currentCount = StudentStore.getTotalCount();
        System.out.println("[DATA INIT LISTENER] Ung dung dang dung. Tong so sinh vien hien tai trong he thong: " + currentCount);
    }
}
