/**
 *
 */
package my.bookstore.facades.book;

import java.util.List;

import my.bookstore.facades.product.data.BookData;
import my.bookstore.facades.product.data.MostRentedBookData;


/**
 * @author ecapozza
 *
 */
public interface BookFacade
{
	public List<MostRentedBookData> returnMostRentedBooks(int numberOfBooks);

	public boolean insertBook(BookData bookData);

}
