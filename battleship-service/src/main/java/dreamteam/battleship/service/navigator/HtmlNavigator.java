package dreamteam.battleship.service.navigator;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    private JSONObject jsonObject;
    final static Logger logger = Logger.getLogger(HtmlNavigator.class);

    private static String WEB_INF_PATH = "/src/main/webapp/";
    private static String MODULE_NAME = "/battleship-service";
    @Override
    public void init() throws ServletException {
        String navigationFile = getServletConfig().getInitParameter("navigationFile");
        JSONParser parser = new JSONParser();
        StringBuilder path = new StringBuilder(System.getProperty("user.dir"));
        if(!path.toString().contains(MODULE_NAME)){
            path.append(MODULE_NAME);
        }
        path.append(WEB_INF_PATH + navigationFile);
        logger.debug("Reading the file: " + System.getProperty("user.dir") + navigationFile);
        try (FileReader fileReader = new FileReader(path.toString().replace("//","/"))) {
            Object obj = parser.parse(fileReader);
            jsonObject = ((JSONObject) obj);

        } catch (ParseException e) {
            logger.debug(e);
        } catch (Exception e) {
            logger.debug(e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();
        RequestDispatcher view = req.getRequestDispatcher(jsonObject.get(path).toString());
        view.forward(req, resp);
    }
}
