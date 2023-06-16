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

public class ProductSaveServletTest {

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
    void whenCallPostMethodThenSaveOneProduct() throws ServletException, IOException {
        ProductSaveServlet productSaveServlet = new ProductSaveServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(UserReadDto.builder().id(1L).build());
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("description")).thenReturn("description");
        when(request.getParameter("price")).thenReturn("11");

        productSaveServlet.doPost(request, response);

        verify(request).getSession();
        verify(request, times(3)).getParameter(Mockito.anyString());
        verify(response).sendRedirect(UrlPath.USER_PRODUCTS);
    }
}
