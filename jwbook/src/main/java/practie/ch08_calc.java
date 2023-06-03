package practie;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//컨트롤러와 db 연결없이 구성했을 때 서블릿 파일.
//이것도 나름 vc로 구성한 내용.
@WebServlet("/ch08/calc")
public class ch08_calc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ch08_calc() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int n1 = Integer.parseInt(request.getParameter("first"));
		int n2 = Integer.parseInt(request.getParameter("second"));
		
		long result = 0;
		
		switch(request.getParameter("op")) {
		case "+" : result=n1+n2; break;
		case "-" : result=n1-n2; break;
		case "*" : result=n1*n2; break;
		case "/" : result=n1/n2; break;
		}
		request.setAttribute("result", result);
		getServletContext().getRequestDispatcher("/practice/ch08_calc.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
