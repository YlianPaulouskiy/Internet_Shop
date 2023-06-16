package by.itacademy.shop.servlet;

import by.itacademy.shop.utils.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class LogoutServletTest {

    @Test
    void whenCallPostMethodSessionInvalidateAndRedirect() throws ServletException, IOException {
        LogoutServlet logoutServlet = new LogoutServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);

        logoutServlet.doPost(request, response);

        verify(request, times(1)).getSession();
        verify(session).invalidate();
        verify(response).sendRedirect(UrlPath.LOGIN);
    }
}
