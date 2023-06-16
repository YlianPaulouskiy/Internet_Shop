package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.AddressReadDto;
import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.exception.EntityExistsException;
import by.itacademy.shop.service.AddressService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

import static by.itacademy.shop.utils.UrlPath.ADDRESS_SAVE;
import static by.itacademy.shop.utils.UrlPath.MENU;

@WebServlet(ADDRESS_SAVE)
public class AddressSaveServlet extends HttpServlet {

    private final AddressService addressService = new AddressService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (UserReadDto) req.getSession().getAttribute("user");
        var address = buildAddress(req);

        if (req.getSession().getAttribute("address") != null) {
            addressService.update(user.getId(), address);
        } else {
            try {
                addressService.save(user.getId(), address);
            } catch (ConstraintViolationException | EntityExistsException exception) {
                req.setAttribute("errors", exception.getMessage().split(","));
                req.getRequestDispatcher(MENU).forward(req, resp);
            }
        }
        req.getSession().setAttribute("address", address);
        resp.sendRedirect(MENU);
    }

    private AddressReadDto buildAddress(HttpServletRequest req) {
        var address = (AddressReadDto) req.getSession().getAttribute("address");
        var city = req.getParameter("city");
        var street = req.getParameter("street");
        var house = req.getParameter("house");
        var flat = req.getParameter("flat");
        return AddressReadDto.builder()
                .city(city == null || city.isBlank()
                        ? address.getCity() : city)
                .street(street == null || street.isBlank()
                        ? address.getStreet() : street)
                .house(house == null || house.isBlank()
                        ? address.getHouse() : house)
                .flat(flat == null || flat.isEmpty()
                        ? address.getFlat() : flat)
                .build();
    }

}
