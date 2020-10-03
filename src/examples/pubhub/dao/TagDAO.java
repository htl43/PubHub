package examples.pubhub.dao;

import java.util.List;

import examples.pubhub.model.Tag;

/**
 * Interface for our Data Access Object to handle database queries related to Books.
 */
public interface TagDAO {

	public List<Tag> getAllTags();
	public List<Tag> getAllTagsForBook(String isbn13);
	public List<Tag> getAllIsbnForTag(String tag_name);
	
	public boolean addTag(Tag tag);
	public boolean updateTag(Tag tag);
	public boolean deleteTag(String isbn, String tag_name);
}