package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.service.UserService;
import by.itacademy.shop.utils.JspHelper;
import by.itacademy.shop.utils.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.login(req.getParameter("login"), req.getParameter("password"))
                .ifPresentOrElse(userDto -> onLoginSuccess(userDto, req, resp),
                        () -> onLoginFail(req, resp));
    }

    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("logFail","Email or password is not correct");
        try {
            doGet(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void onLoginSuccess(UserReadDto userDto, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", userDto);
        try {
            resp.sendRedirect(UrlPath.MENU);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
