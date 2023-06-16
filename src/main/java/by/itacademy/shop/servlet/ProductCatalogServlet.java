package by.itacademy.shop.servlet;

import by.itacademy.shop.service.ProductService;
import by.itacademy.shop.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.itacademy.shop.utils.UrlPath.PRODUCTS_CATALOG;

@WebServlet(PRODUCTS_CATALOG)
public class ProductCatalogServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("products", productService.findAll());
        req.getRequestDispatcher(JspHelper.getPath("product-catalog")).forward(req,resp);
    }
}
