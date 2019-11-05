package controllers.employees;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class EmployeesFollowServlet
 */
@WebServlet("/employees/follow")
public class EmployeesFollowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesFollowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String _token = (String)request.getParameter("_token");
           if(_token != null && _token.equals(request.getSession().getId())) {}

               EntityManager em = DBUtil.createEntityManager();
               Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
               e.setFollow_flag(1);
               e.setUpdated_at(new Timestamp(System.currentTimeMillis()));
               em.getTransaction().begin();
               em.getTransaction().commit();
               em.close();
               request.setAttribute("employee", e);
               request.setAttribute("_token", request.getSession().getId());
               request.getSession().setAttribute("flush", "フォローしました。");

               RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/employees/show.jsp");
               rd.forward(request, response);


}
}
