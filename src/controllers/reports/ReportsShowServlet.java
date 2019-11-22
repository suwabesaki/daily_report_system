package controllers.reports;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class ReportsShowServlet
 */
@WebServlet("/reports/show")
public class ReportsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Report r = em.find(Report.class, Integer.parseInt(request.getParameter("id")));

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");


        long follow_count = (long)em.createNamedQuery("getMYfollowCount", Long.class)
                .setParameter("login_id", login_employee)
                .setParameter("employee", r.getEmployee())
                .getSingleResult();


        //System.out.println("follow_count:" + follow_count);

     // 該当のIDをデータベースから取得
       System.out.println(login_employee);
       System.out.println(r.getEmployee());
        //Follow getfollow_id = (Follow)em.createNamedQuery("getfollow_id", Follow.class)
                //.setParameter("login_id", login_employee)
                //.setParameter("employee", r.getEmployee())
                //.getSingleResult();

       List<Follow> getfollow_id = em.createNamedQuery("getfollow_id", Follow.class)
               .setParameter("login_id", login_employee)
               .setParameter("employee", r.getEmployee())
               .getResultList();

        em.close();

        // フォローIDをセッションスコープに登録
        request.getSession().setAttribute("getfollow_id",getfollow_id);



        request.setAttribute("report", r);
        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("follow_count", follow_count);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}
