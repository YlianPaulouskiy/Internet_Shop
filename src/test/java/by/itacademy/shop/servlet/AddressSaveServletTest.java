package by.itacademy.shop.servlet;

import by.itacademy.shop.dto.AddressReadDto;
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

public class AddressSaveServletTest {

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
    void whenCallPostMethodThenSaveAddressAndForward() throws ServletException, IOException {
        AddressSaveServlet addressSaveServlet = new AddressSaveServlet();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("city")).thenReturn("City");
        when(request.getParameter("street")).thenReturn("Street");
        when(request.getParameter("house")).thenReturn("House");
        when(request.getParameter("flat")).thenReturn("Flat");
        when(session.getAttribute("user")).thenReturn(UserReadDto.builder().id(1L).build());
        when(session.getAttribute("address")).thenReturn(AddressReadDto.builder().build());
        when(request.getRequestDispatcher(UrlPath.MENU)).thenReturn(requestDispatcher);

        addressSaveServlet.doPost(request, response);

        verify(request, times(4)).getSession();
        verify(request, times(4)).getParameter(Mockito.anyString());
        verify(session, times(3)).getAttribute(Mockito.anyString());
        verify(session).setAttribute(Mockito.anyString(), Mockito.any());
        verify(response).sendRedirect(UrlPath.MENU);

    }

}
