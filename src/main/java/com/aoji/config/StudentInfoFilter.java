package com.aoji.config;

import com.aoji.model.StudentInfo;
import com.aoji.model.res.Consultor;
import com.aoji.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*")
public class StudentInfoFilter implements Filter {

    public static final Logger logger = LoggerFactory.getLogger(StudentInfoFilter.class);

    public static final String STUDENT_INFO_KEY = "studentInfoKey";

    @Autowired
    StudentService studentService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        String studentNo = req.getParameter("studentNo");
        HttpSession session = req.getSession();
        if(studentService != null) {
            if (StringUtils.hasText(studentNo)) {
                StudentInfo student = (StudentInfo) session.getAttribute(STUDENT_INFO_KEY);
                if (student == null || !studentNo.equals(student.getStudentNo())) {
                    //获取学生信息
                    StudentInfo studentInfo = new StudentInfo();
                    studentInfo.setStudentNo(studentNo);
                    student = studentService.get(studentInfo);
                    if (student != null) {
                        Consultor consultor = studentService.getConsultorByStudentNo(studentNo);
                        if (consultor != null) {
                            student.setSalesConsultant(consultor.getConsultorname());
                        }
                    }
                    session.setAttribute(STUDENT_INFO_KEY, student);
                }
            } else {
                session.removeAttribute(STUDENT_INFO_KEY);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
