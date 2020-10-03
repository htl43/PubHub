package examples.pubhub.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class RemoveTag
 */
@WebServlet("/RemoveTag")
public class RemoveTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String isbn13 = request.getParameter("isbn13");
		String tag_name = request.getParameter("tag_name");
		System.out.println(tag_name);
		System.out.println(isbn13);
		
		TagDAO database = DAOUtilities.getTagDAO();
		boolean isSuccess = database.deleteTag(isbn13, tag_name);
		if(isSuccess){
			request.getSession().setAttribute("message", "Tag successfully published");
			request.getSession().setAttribute("messageClass", "alert-success");
		}else {
			request.getSession().setAttribute("message", "There was a problem publishing the Tag");
			request.getSession().setAttribute("messageClass", "alert-danger");
			
		}
		response.sendRedirect(request.getContextPath() + "/TagPublished");
	}

}
