package practie;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;


@WebServlet("/ch09/studentController")
public class ch09_studentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	ch09_studentDAO dao;
    
    @Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		dao = new ch09_studentDAO();
	}
    
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		String view = "";
		
		if(request.getParameter("action") == null) getServletContext().getRequestDispatcher("/ch09/studentController?action=reload").forward(request, response);
		else {
			switch(action) {
			case "upload" : view=upload(request, response); break;
			case "reload" : view=reload(request, response); break;
			}
			getServletContext().getRequestDispatcher("/practice/" + view).forward(request, response);
		}
	}
	
	//학생 정보 등록 메서드
	private String upload(HttpServletRequest request, HttpServletResponse response) {
		ch09_student s = new ch09_student();
		try {
			BeanUtils.populate(s, request.getParameterMap());
		} catch(Exception e) {
			e.printStackTrace();
		}
		dao.insert(s);
		return reload(request, response);
	}
	//초기 화면이자 새로고침시 전체 정보 가져오는 메서드
	private String reload(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("students", dao.getAll());
		return "ch09_student.jsp";
	}
}
