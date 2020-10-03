package examples.pubhub.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;

@WebServlet("/PublishTag")
public class PublishTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("publishTag.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String isbn13 = request.getParameter("isbn13");	
		String tag_name = request.getParameter("tag_name");
		Tag tmpTag = new Tag();
		tmpTag.setIsbn13(request.getParameter("isbn13"));
		tmpTag.setTagName(request.getParameter("tag_name"));
		
		BookDAO bookDatabase = DAOUtilities.getBookDAO();
		Book listBooks = bookDatabase.getBookByISBN(isbn13);
				
		if (listBooks != null) {
			TagDAO tagDatabase = DAOUtilities.getTagDAO();
			List<Tag> listTags = tagDatabase.getAllTagsForBook(isbn13);
			List<String> listTag_name = new ArrayList<String>();
			for (Tag tag : listTags) {
				listTag_name.add(tag.getTagName());
			}
			if (listTag_name.contains(tag_name)) {
				request.getSession().setAttribute("message", " the tag_name of " + tag_name + " for isbn of " + isbn13 + " is already in use");
				request.getSession().setAttribute("messageClass", "alert-danger");
				request.getRequestDispatcher("publishTag.jsp").forward(request, response);

			} else {
				boolean isSuccess = tagDatabase.addTag(tmpTag);
				if(isSuccess){
					request.getSession().setAttribute("message", "Tag successfully published");
					request.getSession().setAttribute("messageClass", "alert-success");

					response.sendRedirect(request.getContextPath() + "/TagPublished");
				}else {
					request.getSession().setAttribute("message", "There was a problem publishing the book");
					request.getSession().setAttribute("messageClass", "alert-danger");
					request.getRequestDispatcher("publishTag.jsp").forward(request, response);
					
				}
			}
		} else {
			
			request.getSession().setAttribute("message", "There is not exist any isbn13 of " + isbn13);
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("publishTag.jsp").forward(request, response);
		}
		
	}

}
