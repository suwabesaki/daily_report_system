package controllers.follows;

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
import models.Follow;
import models.Report;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsCreateServlet
 */
@WebServlet("/follows/create")
public class FollowsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsCreateServlet() {
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

            Follow f = new Follow ();

         // セッションスコープからログインIDを取得
            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
            f.setLogin_id (login_employee);
         // 日報を登録した社員のIDを取得
            Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
            f.setEmployee(e);
         // 日報を登録した社員のIDを取得
            // System.out.println("employee_id:" + request.getParameter("id"));
            //Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));
            //request.getSession().setAttribute("employee_id", e.getId());
            Report r= em.find(Report.class, Integer.parseInt(request.getParameter("rid")));
         // 更新日時
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            f.setCreated_at(currentTime);

         // データベースに保存
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
            em.close();

            request.setAttribute("login_employee", login_employee);
            request.setAttribute("employee", e);
            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("report", r);
            request.setAttribute("flush", "フォローしました。");


         // showページへ
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
            rd.forward(request, response);
}
}

