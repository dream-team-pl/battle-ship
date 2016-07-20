package dreamteam.battleship.service.navigator;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by egolesor on 19.07.16.
 */
public class HtmlNavigator extends HttpServlet {

    private String navigationFile;
    final static Logger logger = Logger.getLogger(HtmlNavigator.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        navigationFile = getServletConfig().getInitParameter("navigationFile");
        String path = req.getPathInfo();
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(
                    "src/main/webapp"+navigationFile));

            RequestDispatcher view = req.getRequestDispatcher(((JSONObject) obj).get(path).toString());
            view.forward(req, resp);

        } catch (Exception e) {
            logger.error("html parser error");
        }

    }
}
