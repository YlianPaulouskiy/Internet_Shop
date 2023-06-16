package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.OrderReadDto;
import by.itacademy.shop.dto.ProductReadDto;
import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.service.OrderService;
import by.itacademy.shop.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static by.itacademy.shop.utils.UrlPath.MENU;
import static by.itacademy.shop.utils.UrlPath.ORDERS_SAVE;

@WebServlet(ORDERS_SAVE)
public class OrderSaveServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        orderService.save(buildOrder(req));
        req.getSession().setAttribute("orderProducts", new ArrayList<>());
        resp.sendRedirect(MENU);
    }

    private OrderReadDto buildOrder(HttpServletRequest req) {
        var products = (List<ProductReadDto>) req.getSession().getAttribute("orderProducts");
        var user = (UserReadDto) req.getSession().getAttribute("user");
        return OrderReadDto.builder()
                .registrationTime(LocalDateTime.now())
                .products(products)
                .user(user)
                .build();
    }
}
