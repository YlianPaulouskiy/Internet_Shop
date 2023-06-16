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

public class UserServletTest {

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
    void whenCallPostMethodThenUpdateUser() throws ServletException, IOException {
        UserServlet userServlet = new UserServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(UserReadDto.builder().id(1L).build());
        when(request.getParameter("name")).thenReturn("Name");
        when(request.getParameter("lastname")).thenReturn("LastName");
        when(request.getParameter("phone")).thenReturn("+375123112312");
        when(request.getParameter("email")).thenReturn("example@gmail.com");
        when(request.getParameter("password")).thenReturn("qwerty123");

        userServlet.doPost(request, response);

        verify(request, times(2)).getSession();
        verify(response).sendRedirect(UrlPath.MENU);
    }

@Test
    void whenCallGetMethodThenGoToUserPage() throws ServletException, IOException {
        UserServlet userServlet = new UserServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);
        when(session.getAttribute("user")).thenReturn(UserReadDto.builder().id(1L).build());

        userServlet.doGet(request, response);

        verify(request, times(2)).getSession();
        verify(requestDispatcher).include(request, response);
    }


}
