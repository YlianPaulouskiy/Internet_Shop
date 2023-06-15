package by.itacademy.shop.servlet;

import by.itacademy.shop.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.itacademy.shop.utils.UrlPath.LOGIN;
import static by.itacademy.shop.utils.UrlPath.MENU;

@WebServlet(MENU)
public class MenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(LOGIN);
        }
        req.getRequestDispatcher(JspHelper.getPath("menu")).include(req, resp);
    }
}
