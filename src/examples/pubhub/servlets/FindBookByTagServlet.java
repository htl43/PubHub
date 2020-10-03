package examples.pubhub.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

/**
 * Servlet implementation class FindBookByTagServlet
 */
@WebServlet("/FindBookByTag")
public class FindBookByTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("findBookByTag.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tag_name = request.getParameter("tag_name");
	
		TagDAO tagDatabase = DAOUtilities.getTagDAO();
		List<Tag> listTags = tagDatabase.getAllIsbnForTag(tag_name);
		
		if (!listTags.isEmpty()) {
			List<Book> bookList = new ArrayList<Book>();
			BookDAO bookDatabase = DAOUtilities.getBookDAO();
			for (Tag tag : listTags) {
				Book book = bookDatabase.getBookByISBN(tag.getIsbn13());
				bookList.add(book);
			}
			request.setAttribute("books", bookList);
			request.getSession().setAttribute("message", "Book successfully found");
			request.getSession().setAttribute("messageClass", "alert-success");
			request.getRequestDispatcher("bookPublishingHome.jsp").forward(request, response);
//			response.sendRedirect(request.getContextPath() + "/TagPublished");				
		} else {	
			request.getSession().setAttribute("message", "There is not exist any boon with tag " + tag_name);
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("findBookByTag.jsp").forward(request, response);
		}
	}

}
