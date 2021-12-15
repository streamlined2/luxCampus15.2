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
		Map<String, Object> pageVariables = createPageVariablesMap(req);
		pageVariables.put("message", "Hi!");

		resp.setContentType("text/html;charset=UTF-8");
		resp.setStatus(HttpServletResponse.SC_OK);

		resp.getWriter().append(PageGenerator.instance().getPage("page.html", pageVariables));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<String, Object> pageVariables = createPageVariablesMap(req);
		String message = req.getParameter("message");
		resp.setContentType("text/html;charset=UTF-8");
		if (Objects.isNull(message) || message.isEmpty()) {
			resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
		} else {
			resp.setStatus(HttpServletResponse.SC_OK);
		}
		pageVariables.put("message", message == null ? "" : message);
		resp.getWriter().append(PageGenerator.instance().getPage("page.html", pageVariables));
	}

	private static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
		Map<String, Object> pageVariables = new HashMap<>();
		pageVariables.put("method", request.getMethod());
		pageVariables.put("URL", request.getRequestURL());
		pageVariables.put("pathInfo", request.getPathInfo());
		pageVariables.put("sessionId", request.getSession().getId());
		pageVariables.put("parameters", request.getParameterMap());
		return pageVariables;
	}

}
