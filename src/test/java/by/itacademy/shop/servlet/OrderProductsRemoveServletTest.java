package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.ProductReadDto;
import by.itacademy.shop.utils.TestDBUtils;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class OrderProductsRemoveServletTest {

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
    void whenCallGetMethodThenRemoveProductFromOrder() throws ServletException, IOException {
        OrderProductRemoveServlet orderProductRemoveServlet = new OrderProductRemoveServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Mockito.anyString())).thenReturn(new ArrayList<>());
        when(request.getParameter(Mockito.anyString())).thenReturn("1");
        when(request.getParameter("addId")).thenReturn("4");


        orderProductRemoveServlet.doGet(request, response);

        verify(request).getParameter(Mockito.anyString());
        verify(request, times(2)).getSession();
        verify(requestDispatcher).forward(request, response);
    }

}
