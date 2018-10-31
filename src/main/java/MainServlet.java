import com.alibaba.fastjson.JSON;

import commands.AjaxCommand;
import commands.NotFoundCommand;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MainServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(MainServlet.class);
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        log.info("Main Servlet started");
        log.info("Есть соединение! IP: "+req.getRemoteAddr());
        String commandString = req.getServletPath().substring(1);
        String ajaxResponse = null;
        if (!commandString.isEmpty())
        {
            String className = "commands."+commandString+"Command";


            Class commandClass;
            try {
                commandClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                commandClass = NotFoundCommand.class;

            }
            try {
                AjaxCommand ajaxCommand = (AjaxCommand)commandClass.newInstance();
                ajaxResponse = JSON.toJSONString(ajaxCommand.doCommand(req.getParameterMap()));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        writer.println(ajaxResponse);
        writer.close();

    }
}