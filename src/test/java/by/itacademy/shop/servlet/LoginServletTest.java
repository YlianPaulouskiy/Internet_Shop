package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.AddressReadDto;
import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.utils.JspHelper;
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

public class LoginServletTest {

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
    void whenCallPostMethodThenSetAttrUserAndRedirect() throws ServletException, IOException {
        LoginServlet loginServlet = new LoginServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn("maxik123@gmail.com");
        when(request.getParameter("password")).thenReturn("qwerty123");

        loginServlet.doPost(request, response);

        verify(request).getSession();
        verify(session).setAttribute(Mockito.anyString(), Mockito.any());
        verify(request, times(2)).getParameter(Mockito.anyString());
        verify(response).sendRedirect(UrlPath.MENU);
    }

    @Test
    void whenCallGetMethodThenForwardToLoginPage() throws ServletException, IOException {
        LoginServlet loginServlet = new LoginServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);

        loginServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }


}
