package controllers.follows;

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
 * Servlet implementation class FollowsDestroyServlet
 */
@WebServlet("/follows/destroy")
public class FollowsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {}
        EntityManager em = DBUtil.createEntityManager();

        // セッションスコープからログインIDを取得
        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        // 日報を登録した社員のIDを取得
        Employee e = em.find(Employee.class, Integer.parseInt(request.getParameter("id")));

        Report r= em.find(Report.class, Integer.parseInt(request.getParameter("rid")));

       // 該当のIDのメッセージ1件のみをデータベースから取得
        //System.out.println("getfollow_id:" + request.getSession().getAttribute("getfollow_id"));
        //Follow f = em.find(Follow.class, (Follow)(request.getSession().getAttribute("getfollow_id")));

        List<Follow> getfollow_id = em.createNamedQuery("getfollow_id", Follow.class)
                .setParameter("login_id", login_employee)
                .setParameter("employee", r.getEmployee())
                .getResultList();


            em.getTransaction().begin();
            em.remove(getfollow_id.get(0));       // データ削除
            em.getTransaction().commit();
            em.close();

            request.setAttribute("login_employee", login_employee);
            request.setAttribute("employee", e);
            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("report", r);
            request.setAttribute("flush", "フォローを解除しました。");

         // セッションスコープ上の不要になったデータを削除
            request.getSession().removeAttribute("follow_id");


         // showページへ
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
            rd.forward(request, response);
        }

    }


