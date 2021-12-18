package org.training.campus.onlineshop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AllRequestsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		resp.setStatus(HttpServletResponse.SC_OK);
		Map<String, Object> pageVariables = createPageVariablesMap(req);
		String message = req.getParameter("value");
		pageVariables.put("message", Objects.requireNonNullElse(message, "<no message>"));
		resp.getWriter().append(PageGenerator.instance().getPage("page.html", pageVariables));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
		var map = new HashMap<String, Object>();
		map.put("method", request.getMethod());
		map.put("URL", request.getRequestURL());
		map.put("pathInfo", request.getPathInfo());
		map.put("sessionId", request.getSession().getId());
		map.put("parameters", request.getParameterMap().toString());
		return map;
	}

}
