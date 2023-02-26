package framework.handler;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class DispatcherFilter
 */
@WebFilter("/api/*")
public class DispatcherFilter implements Filter {
	Logger log = Logger.getLogger(this.getClass().getCanonicalName());

    /**
     * Default constructor. 
     */
    public DispatcherFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		log.debug("call destroy");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		log.debug("before ServletRequest doFilter");

		// pass the request along the filter chain
		doFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
		
		log.debug("after ServletRequest doFilter");
	}
	
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		boolean filterCheck=true;

		log.debug("before HttpServletRequest doFilter");
		
		String auth_check = request.getHeader("auth_check")!=null?request.getHeader("auth_check"):"no";
		String auth_token = request.getHeader("auth_token");
		
		if(auth_check.equals("yes") && (auth_token==null || auth_token.equals(""))) {
			request.getRequestDispatcher("/json/errors/authTokenIsNotExists.jsp").forward(request, response);
			filterCheck=false;
		}

		if(filterCheck) {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}

		
		log.debug("after HttpServletRequest doFilter");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		log.debug("call init");
	}

}
