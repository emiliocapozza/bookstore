/**
 *
 */
package my.bookstore.facades.book;

import java.util.List;

import my.bookstore.facades.product.data.BookData;


/**
 * @author ecapozza
 *
 */
public interface BookFacade
{
	public List<BookData> returnMostRentedBooks(int numberOfBooks);

}
