package framework.handler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Map.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DispatcherServlet
 */
@WebServlet("/api/*")
public class DispatcherServlet extends HttpServlet {
	Map<String, Controller> handler;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("DispatcherServlet init");
		System.out.println(config);
		
		URL res = config.getServletContext().getClassLoader().getResource("url.properties");
		Properties prop = new Properties();

		handler=new HashMap<>();
		try {
			prop.load(res.openStream());
			Set<Entry<Object, Object>> entry = prop.entrySet();
			Iterator<Entry<Object, Object>> ite = entry.iterator();
			while(ite.hasNext()) {
				Entry<Object, Object> handlerEntry = ite.next();
				Controller controller = (Controller) Class.forName(handlerEntry.getValue().toString()).newInstance();
				handler.put(handlerEntry.getKey().toString(), controller);
			}
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doDo(request, response);
	}
	
	/**
	 * get 방식과 post 방식의 처리를 이 doDo 함수에서 모두 처리한다.
	 * 
	 * @param request : 요청 객체
	 * @param response : 응답 객체
	 * @throws IOException 
	 * @throws ServletException 
	 */
	protected void doDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");

		String url = request.getRequestURI().substring((request.getContextPath()+"/api").length());
		Controller controller = handler.get(url);
		
		if(controller!=null) {
			String	path = controller.execute(request, url);
			String jsonPath = "/json/"+path+".jsp";

			request.getRequestDispatcher(jsonPath).forward(request, response);
		}else {
			request.getRequestDispatcher("/json/controllerNotFound.jsp").forward(request, response);
		}
	}

}
