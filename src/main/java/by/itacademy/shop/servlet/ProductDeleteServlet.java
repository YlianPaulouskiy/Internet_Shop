package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.itacademy.shop.utils.UrlPath.PRODUCTS_DELETE;
import static by.itacademy.shop.utils.UrlPath.USER_PRODUCTS;

@WebServlet(PRODUCTS_DELETE)
public class ProductDeleteServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (UserReadDto) req.getSession().getAttribute("user");
        productService.delete(Long.parseLong(req.getParameter("id")));
        req.setAttribute("products", productService.findAllByUserId(user.getId()));
        resp.sendRedirect(USER_PRODUCTS);
    }
}
