package login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.StudentDAO;

@WebServlet("/login")
public class LogInServlet extends HttpServlet {

    private StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/views/login/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String studentIdStr = request.getParameter("student_id");   //testID: 2024120004
        String password = request.getParameter("password");    //testPW: kims0004

        try {
            int studentId = Integer.parseInt(studentIdStr);

            boolean result = studentDAO.isValidUser(studentId, password);

            if (result == true) {
                HttpSession session = request.getSession();
                session.setAttribute("student_id", studentId);
                session.setAttribute("password", password);

                // 추후 수정: 수강신청 페이지로 이동
                request.setAttribute("success", "수강신청 페이지로 이동합니다.");
                request.getRequestDispatcher("WEB-INF/views/login/index.jsp").forward(request, response);
                //
            } else {
                request.setAttribute("error", "학번 또는 비밀번호가 일치하지 않습니다.");
                request.getRequestDispatcher("WEB-INF/views/login/index.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "유효한 학번을 입력해 주세요.");
            request.getRequestDispatcher("WEB-INF/views/login/index.jsp").forward(request, response);
        }
    }
}
