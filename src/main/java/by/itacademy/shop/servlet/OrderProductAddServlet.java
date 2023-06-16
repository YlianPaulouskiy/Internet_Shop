package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.ProductReadDto;
import by.itacademy.shop.service.OrderService;
import by.itacademy.shop.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.itacademy.shop.utils.UrlPath.*;

@WebServlet(ORDERS_PRODUCT_ADD)
public class OrderProductAddServlet extends HttpServlet {

    private final ProductService productService = new ProductService();
    private final List<ProductReadDto> orderProducts = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var product = productService.findById(Long.valueOf(req.getParameter("addId")));
        product.ifPresent(orderProducts::add);
        req.getSession().setAttribute("orderProducts", orderProducts);
        req.getRequestDispatcher(PRODUCTS_CATALOG).forward(req, resp);
    }
}

