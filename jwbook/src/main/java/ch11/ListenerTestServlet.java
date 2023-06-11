package ch11;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/ListenerTestServlet")
public class ListenerTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	ServletContext sc;
	
    public ListenerTestServlet() {
        super();
    }

	//서블릿 객체 가져오는 부분.
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		sc = getServletContext();
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sc.setAttribute("name", "홍길동");
		HttpSession s = request.getSession();
		s.setAttribute("ssName", s.getId()+": 세션 속성 저장!");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
