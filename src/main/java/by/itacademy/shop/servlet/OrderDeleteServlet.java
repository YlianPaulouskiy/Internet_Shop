package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.itacademy.shop.utils.UrlPath.ORDERS_DELETE;
import static by.itacademy.shop.utils.UrlPath.USER_ORDERS;

@WebServlet(ORDERS_DELETE)
public class OrderDeleteServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        orderService.delete(Long.valueOf(req.getParameter("id")));
        var user = (UserReadDto) req.getSession().getAttribute("user");
        req.setAttribute("orders", orderService.findAllByUserId(user.getId()));
        resp.sendRedirect(USER_ORDERS);
    }
}
