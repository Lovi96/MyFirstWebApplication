import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by lovi on 2017.03.24..
 */
@javax.servlet.annotation.WebServlet(name = "ServletofSearch")
public class ServletofSearch extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String cucli = request.getParameter("textInput");
        PrintWriter writer = response.getWriter();
        writer.println("Your feedback was recorded! "+cucli);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
