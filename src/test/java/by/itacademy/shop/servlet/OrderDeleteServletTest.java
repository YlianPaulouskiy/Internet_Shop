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

public class OrderDeleteServletTest {

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
    void whenCallPostMethodThenDeleteOrder() throws ServletException, IOException {
        OrderDeleteServlet orderDeleteServlet = new OrderDeleteServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getParameter("id")).thenReturn("4");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Mockito.anyString())).thenReturn(UserReadDto.builder().id(1L).build());

        orderDeleteServlet.doPost(request, response);

        verify(request).getParameter(Mockito.anyString());
        verify(session).getAttribute(Mockito.anyString());
        verify(response).sendRedirect(UrlPath.USER_ORDERS);
    }

}
