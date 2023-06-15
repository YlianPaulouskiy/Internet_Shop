package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.itacademy.shop.utils.UrlPath.LOGIN;
import static by.itacademy.shop.utils.UrlPath.USER_DELETE;

@WebServlet(USER_DELETE)
public class UserDeleteServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (UserReadDto) req.getSession().getAttribute("user");
        userService.delete(user.getId());
        req.getSession().invalidate();
        resp.sendRedirect(LOGIN);
    }
}
