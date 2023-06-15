package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.StringUserDto;
import by.itacademy.shop.exception.EntityExistsException;
import by.itacademy.shop.service.UserService;
import by.itacademy.shop.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

import static by.itacademy.shop.utils.UrlPath.LOGIN;
import static by.itacademy.shop.utils.UrlPath.REGISTRATION;

@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var userDto = StringUserDto.builder()
                .name(req.getParameter("name"))
                .lastName(req.getParameter("lastname"))
                .email(req.getParameter("email"))
                .phone(req.getParameter("phone"))
                .password(req.getParameter("password"))
                .build();
        try {
            userService.create(userDto);
            resp.sendRedirect(LOGIN);
        } catch (ConstraintViolationException | EntityExistsException exception) {
            req.setAttribute("errors", exception.getMessage().split(","));
            doGet(req, resp);
        }
    }
}