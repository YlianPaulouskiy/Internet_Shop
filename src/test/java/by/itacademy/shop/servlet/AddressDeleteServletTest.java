package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.UserReadDto;
import by.itacademy.shop.service.AddressService;
import by.itacademy.shop.utils.TestDBUtils;
import by.itacademy.shop.utils.UrlPath;
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

public class AddressDeleteServletTest {

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
    void whenCallPostMethodThenDeleteAddressAndRedirect() throws ServletException, IOException {
        AddressDeleteServlet addressDeleteServlet = new AddressDeleteServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute(Mockito.anyString())).thenReturn(UserReadDto.builder().id(1L).build());

        addressDeleteServlet.doPost(request, response);

        verify(request, times(2)).getSession();
        verify(session).getAttribute(Mockito.anyString());
        verify(session).setAttribute(Mockito.anyString(), Mockito.any());
        verify(response).sendRedirect(UrlPath.MENU);

    }
}
