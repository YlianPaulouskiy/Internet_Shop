package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.service.AddressService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.itacademy.shop.utils.UrlPath.ADDRESS_DELETE;
import static by.itacademy.shop.utils.UrlPath.MENU;

@WebServlet(ADDRESS_DELETE)
public class AddressDeleteServlet extends HttpServlet {

    private final AddressService addressService = new AddressService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (UserReadDto) req.getSession().getAttribute("user");
        addressService.delete(user.getId());
        req.getSession().setAttribute("address", null);
        resp.sendRedirect(MENU);
    }
}
