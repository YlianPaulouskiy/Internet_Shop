package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.ProductReadDto;
import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.exception.EntityExistsException;
import by.itacademy.shop.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.math.BigDecimal;

import static by.itacademy.shop.utils.UrlPath.PRODUCTS_SAVE;
import static by.itacademy.shop.utils.UrlPath.USER_PRODUCTS;

@WebServlet(PRODUCTS_SAVE)
public class ProductSaveServlet extends HttpServlet {

    private final ProductService productService = new ProductService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var user = (UserReadDto) req.getSession().getAttribute("user");
            productService.save(buildProduct(user, req));
            req.setAttribute("products", productService.findAllByUserId(user.getId()));
            resp.sendRedirect(USER_PRODUCTS);
        } catch (ConstraintViolationException | EntityExistsException exception) {
            req.setAttribute("errors", exception.getMessage().split(","));
            req.getRequestDispatcher(USER_PRODUCTS).forward(req, resp);
        }
    }

    private ProductReadDto buildProduct(UserReadDto user, HttpServletRequest req) {
        try {
            return ProductReadDto.builder()
                    .name(req.getParameter("name"))
                    .description(req.getParameter("description"))
                    .price(new BigDecimal(req.getParameter("price")))
                    .user(user)
                    .build();
        } catch (NumberFormatException exception) {
            return ProductReadDto.builder()
                    .name(req.getParameter("name"))
                    .description(req.getParameter("description"))
                    .price(BigDecimal.ZERO)
                    .user(user)
                    .build();
        }
    }
}
