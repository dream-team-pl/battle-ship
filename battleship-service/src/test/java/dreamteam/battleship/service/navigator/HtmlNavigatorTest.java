package dreamteam.battleship.service.navigator;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Testing the navigation servlet
 */
public class HtmlNavigatorTest {

    HtmlNavigator navigator;
    MockServletConfig config;

    @BeforeTest
    public void init(){
        navigator = new HtmlNavigator();
        config = new MockServletConfig();
        config.addInitParameter("navigationFile", "/WEB-INF/html-navigation.json");
    }

    @Test
    public void checkIfFileIsRedden(){
        // when

        // then
        try {
            navigator.init(config);
        } catch (ServletException e) {
            fail("Test not passed");
        }
    }

    @Test
    public void testIfTheParserWorksProperly(){
        MockHttpServletRequest reqParam = new MockHttpServletRequest();
        reqParam.setPathInfo("/register");
        HttpServletRequest req = new HttpServletRequestWrapper(reqParam);

        MockHttpServletResponse resParam = new MockHttpServletResponse();
        HttpServletResponse res = new HttpServletResponseWrapper(resParam);
        try {
            navigator.init(config);

            navigator.doGet(req, res);
        } catch (Exception e) {
            fail();
        }
        assertEquals(resParam.getForwardedUrl(), "/view/register.html");
    }

}