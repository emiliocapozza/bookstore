/**
 *
 */
package my.bookstore.storefront.controllers.book;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import my.bookstore.facades.book.impl.DefaultBookFacade;
import my.bookstore.facades.product.data.BookData;
import my.bookstore.facades.product.data.MostRentedBookData;


/**
 * @author ecapozza
 *
 */
@Controller
@RequestMapping("/book")
public class BookController
{
	@Resource
	private DefaultBookFacade defaultBookFacade;

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public boolean insertBook(@RequestBody final BookData bookData)
	{
		return defaultBookFacade.insertBook(bookData);
	}

	@RequestMapping(value = "/insertProva", method = RequestMethod.POST)
	@ResponseBody
	public boolean insert(@RequestParam(value = "name", required = true) final String name,
			@RequestParam(value = "edition", required = true) final String edition,
			@RequestParam(value = "ISBN10", required = true) final String ISBN10,
			@RequestParam(value = "ISBN13", required = true) final String ISBN13,
			@RequestParam(value = "code", required = true) final String code,
			@RequestParam(value = "language", required = true) final String language,
			@RequestParam(value = "publisher", required = true) final String publisher,
			@RequestParam(value = "rentable", required = true) final Boolean rentable)
	{
		return defaultBookFacade.insert(name, edition, ISBN10, ISBN13, code, language, publisher, rentable);
	}

	@RequestMapping(value = "/mostRentedBook", method = RequestMethod.GET)
	@ResponseBody
	public List<MostRentedBookData> printMostRentedBooks(
			@RequestParam(value = "numberOfBooks", required = true) final int numberOfBooks)
	{
		//final int numberOfBooks = 5;
		return defaultBookFacade.returnMostRentedBooks(numberOfBooks);
	}





}
