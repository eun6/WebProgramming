package practie;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import ch08.ProductService;

//컨트롤러
@WebServlet("/ch08/calcController")
public class ch08_calcController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	ch08_calculatorDAO dao;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		dao = new ch08_calculatorDAO();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String view = "";
		
		if (action == null) {
			getServletContext().getRequestDispatcher("/ch08/calcController?action=form").forward(request, response);
		} else {
			if (action.equals("result")) view=result(request,response);
			else if (action.equals("form")) view=form(request,response);
		}
		getServletContext().getRequestDispatcher("/practice/" + view).forward(request, response);
	}
	
	//계산화면
	private String form(HttpServletRequest request, HttpServletResponse response) {
		return "ch08_calcform.jsp";
	}
	//결과화면
	private String result(HttpServletRequest request, HttpServletResponse response) {
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
		
		ch08_calculator c = new ch08_calculator();
		try {
			BeanUtils.populate(c, request.getParameterMap());
		}catch (Exception e) {
			e.printStackTrace();
		}
		dao.insert(c);
		return "ch08_calc.jsp";
	}
}
