package test;

import java.util.List;

import examples.pubhub.dao.BookDAO;
import examples.pubhub.dao.BookDAOImpl;
import examples.pubhub.dao.TagDAO;
import examples.pubhub.dao.TagDAOImpl;
import examples.pubhub.model.*;

public class TestTagDAO {
	
	public static void main(String[] args) {
		TagDAO dao = new TagDAOImpl();
		List<Tag> list = dao.getAllTagsForBook("1111");
		
		for (int i = 0; i < list.size(); i++) {
			Tag t = list.get(i);
			System.out.println(t.getIsbn13());
		}
		
		BookDAO bookDAO = new BookDAOImpl();
		List<Book> newList = bookDAO.getAllBooks();
		for (int i = 0; i < newList.size(); i++) 
		{
			Book t = newList.get(i);
			System.out.println(t.getTitle() + " " + t.getIsbn13());		
		}	
		
	}
}
