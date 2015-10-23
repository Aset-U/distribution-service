package servlet;


import action.*;
import dao.*;
import dao.mysql.*;
import entity.*;
import resource.ConfigurationManager;
import resource.MessageManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ControllerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String page = null;

        Action action = ActionFactory.getInstance().getAction(req);

        ActionResult result = action.execute(req);

        page = result.getView();

        if (result.isRedirection()) {
            resp.sendRedirect(req.getContextPath() + page);
            return;
        }

        if  (page != null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        }
        else {
            page = ConfigurationManager.getProperty("page.index");
            req.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullpage"));
            resp.sendRedirect(req.getContextPath() + page);
        }
    }

    @Override
     protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String query = req.getQueryString();
        ActionResult result = null;

        if (query.contains("page")){
            String pageAction = req.getParameter("page");
            Action action = new ShowPageAction(pageAction + ".jsp");
            result = action.execute(req);

        }
       else if (query.contains("command")){
            Action action = ActionFactory.getInstance().getAction(req);
            result = action.execute(req);
        }

        String page = result.getView();

        RequestDispatcher dispatcher = req.getRequestDispatcher(page);
        dispatcher.forward(req, resp);
    }
}
