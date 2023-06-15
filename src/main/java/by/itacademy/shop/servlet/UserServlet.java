package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.StringUserDto;
import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.exception.EntityExistsException;
import by.itacademy.shop.service.AddressService;
import by.itacademy.shop.service.UserService;
import by.itacademy.shop.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

import static by.itacademy.shop.utils.UrlPath.MENU;
import static by.itacademy.shop.utils.UrlPath.USER;

@WebServlet(USER)
public class UserServlet extends HttpServlet {

    private final UserService userService = new UserService();
    private final AddressService addressService = new AddressService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var user = (UserReadDto) req.getSession().getAttribute("user");
        addressService.findByUserId(user.getId())
                .ifPresent(addressReadDto -> req.getSession().setAttribute("address", addressReadDto));
        req.getRequestDispatcher(JspHelper.getPath("user")).include(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var updateUser = createUpdatedUser(req);
        try {
            if (userService.update(updateUser)) {
                req.getSession().setAttribute("user", updateUser);
                resp.sendRedirect(MENU);
            }
        } catch (ConstraintViolationException | EntityExistsException exception) {
            req.setAttribute("errors", exception.getMessage().split(","));
            doGet(req, resp);
        }
    }

    private UserReadDto createUpdatedUser(HttpServletRequest req) {
        var user = (UserReadDto) req.getSession().getAttribute("user");
        var name = req.getParameter("name");
        var lastName = req.getParameter("lastname");
        var phone = req.getParameter("phone");
        var email = req.getParameter("email");
        var password = req.getParameter("password");
        return UserReadDto.builder()
                .id(user.getId())
                .name(name == null || name.isBlank()
                        ? user.getName() : name)
                .lastName(lastName == null || lastName.isBlank()
                        ? user.getLastName() : lastName)
                .phone(phone == null || phone.isBlank()
                        ? user.getPhone() : phone)
                .email(email== null || email.isBlank()
                        ? user.getEmail() : email)
                .password(password == null || password.isBlank()
                        ? user.getPassword() : password)
                .build();
    }

}
