package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.ProductReadDto;
import by.itacademy.shop.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.itacademy.shop.utils.UrlPath.ORDERS_PRODUCT_REMOVE;
import static by.itacademy.shop.utils.UrlPath.PRODUCTS_CATALOG;

@WebServlet(ORDERS_PRODUCT_REMOVE)
public class OrderProductRemoveServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var orderProducts = (List<ProductReadDto>) req.getSession().getAttribute("orderProducts");
        var product = productService.findById(Long.valueOf(req.getParameter("removeId")));
        product.ifPresent(orderProducts::remove);
        req.getSession().setAttribute("orderProducts", orderProducts);
        req.getRequestDispatcher(PRODUCTS_CATALOG).forward(req, resp);
    }


}
