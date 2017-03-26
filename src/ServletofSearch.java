import java.io.*;
import java.sql.Timestamp;
import java.util.Objects;

@javax.servlet.annotation.WebServlet(name = "ServletofSearch")
public class ServletofSearch extends javax.servlet.http.HttpServlet {
	protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF8");
		writeToFile(request, response);
	}

	protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF8");
		navbarBuilder(response);
		bodyBuilder(response);
		footerBuilder(response);
	}

	private void navbarBuilder(javax.servlet.http.HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		writer.print("<html lang=\"en\"><head><meta charset=\"UTF-8\"><title>Feedback Page</title><link rel=\"stylesheet\" href=\"index.css\"><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\"><link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">");
		writer.print("</head><body><div class=\"bg\"></div><header class=\"navbar\" role=\"banner\"><div class=\"container\"><nav class=\"navbar navbar-inverse\">");
		writer.print("<div class=\"container-fluid\"><div class=\"navbar-header\"><button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\"data-target=\"#bs-example-navbar-collapse-1\" aria-expanded=\"false\">");
		writer.print("<span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span>");
		writer.print("<span class=\"icon-bar\"></span></button><a class=\"navbar-brand\" href=\"index.html\">Airplanes</a></div>");
		writer.print("<div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\"><ul class=\"nav navbar-nav\">");
		writer.print("<li class=\"menuItemCustom\"><a href=\"#\">More about aviation</a></li>\n" +
				"                        <li class=\"menuItemCustom\"><a href=\"#\">Encyclopedia of planes</a></li>\n" +
				"                        <li class=\"menuItemCustom\"><a href=\"/ServletofSearch\">TweetCool</a></li>\n" +
				"                        <li class=\"menuItemCustom\"><a href=\"#\">About</a></li><li class=\"dropdown\">");
		writer.print("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">Favourite Planes <span class=\"caret\"></span></a>");
		writer.print("<ul class=\"dropdown-menu\"><li><a href=\"page1.html\">Curtiss P-40E</a></li><li role=\"separator\" class=\"divider\"></li><li><a href=\"page2.html\">McDonnell Douglass F-15A</a></li>");
		writer.print("<li role=\"separator\" class=\"divider\"></li><li><a href=\"page3.html\">Fairchild Republic A-10C</a></li></ul></li></ul>");
		writer.print("</div></div></nav></div></header><div class=\"container\"><form class=\"form\" method=\"post\" action=\"./ServletofSearch\">\n" +
				"        <div class=\"form-group\">\n" +
				"        <input type=\"text\" class=\"form-control\" placeholder=\"Username\" name=\"textInput2\">\n" +
				"            <input type=\"text\" class=\"form-control\" placeholder=\"Instant feedback\" name=\"textInput\">\n" +
				"        </div>\n" +
				"        <button type=\"submit\" class=\"btn btn-default\">Send</button>" +
				"    </form><form class=\"form\" method=\"get\" action=\"./ServletofSearch\">" +
				"        <div class=\"form-group\">\n" +
				"        </div>\n" +
				"        <button type=\"submit\" class=\"btn btn-default\">Refresh</button></form>");
	}

	private void bodyBuilder(javax.servlet.http.HttpServletResponse response) throws IOException {

		boolean wasUser = false;
		String relativePath = "tweets.txt";
		InputStream inputStream = getServletContext().getResourceAsStream(relativePath);
		PrintWriter writer = response.getWriter();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
		String currentLine = "";
		while((currentLine = br.readLine()) != null) {
			if(Objects.equals(currentLine, "user:") || (Objects.equals(currentLine, "feedback:"))) {
				continue;
			}
			if(!Objects.equals(currentLine, "user:") && !Objects.equals(currentLine, "feedback:") && !wasUser) {
				writer.print("<div class=\"panel panel-info\"><div class=\"panel-heading\">" + currentLine + "</div>");
				wasUser = true;
			} else if(!Objects.equals(currentLine, "user:") && !Objects.equals(currentLine, "feedback:") && wasUser) {
				writer.print("<div class=\"panel-body\">" + currentLine + "</div></div>");
				wasUser = false;
			}
		}
		inputStream.close();
		br.close();

	}

	private void writeToFile(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException, javax.servlet.ServletException {
		String feedBack = request.getParameter("textInput");
		String userName = request.getParameter("textInput2");
		if(userName.equals("") || userName.equals(" ")) {
			userName = "anon";
		}
		if(feedBack.equals("") || feedBack.equals(" ")) {
			doGet(request, response);
		} else {
			String relativePath = ".\\tweets.txt";
			String diskPath = getServletContext().getRealPath(relativePath);
			System.out.println(diskPath);
			//FileWriter fw = new FileWriter("D:\\Programok\\apache-tomcat-8.5.12\\webapps\\ROOT\\tweets.txt", true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(diskPath, true), "UTF-8"));
			PrintWriter out = new PrintWriter(bw);
			out.println("user:");
			Timestamp ts = new Timestamp(System.currentTimeMillis());
			out.println(userName + " || " + ts);
			out.println("feedback:");
			out.println(feedBack);
			System.out.println("beirtam!");
			doGet(request, response);
			out.close();
			bw.close();
			//fw.close();
		}
	}

	private void footerBuilder(javax.servlet.http.HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		writer.print("</div><script src=\"https://code.jquery.com/jquery-3.1.1.slim.min.js\" integrity=\"sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n\" crossorigin=\"anonymous\"></script><script src=\"https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js\" integrity=\"sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb\" crossorigin=\"anonymous\"></script><script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script></body></html>");
	}
}