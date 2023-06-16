package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.UserReadDto;
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

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class ProductCatalogServletTest {

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
    void whenCallGetMethodThenForwardToProductCatalogPage() throws ServletException, IOException {
        ProductCatalogServlet productCatalogServlet = new ProductCatalogServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);

        productCatalogServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

}
