package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.utils.TestDBUtils;
import by.itacademy.shop.utils.UrlPath;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class RegistrationServletTest {

    @BeforeEach
    public void setUp() throws Exception {
        TestDBUtils.initDb();
    }

    @AfterAll
    static void tearDown() {
        try {
            TestDBUtils.getConnection().close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void whenCallPostMethodThenSaveNewUser() throws ServletException, IOException {
        RegistrationServlet registrationServlet = new RegistrationServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("lastname")).thenReturn("lastname");
        when(request.getParameter("email")).thenReturn("example@gmail.com");
        when(request.getParameter("phone")).thenReturn("+3331231241212");
        when(request.getParameter("password")).thenReturn("qwerty123");

        registrationServlet.doPost(request, response);

        verify(request, times(5)).getParameter(Mockito.anyString());
        verify(response).sendRedirect(UrlPath.LOGIN);
    }

    @Test
    void whenCallGetMethodThenForwardToRegistrationPage() throws ServletException, IOException {
        RegistrationServlet registrationServlet = new RegistrationServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);

        registrationServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

}
