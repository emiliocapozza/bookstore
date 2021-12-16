/**
 *
 */
package my.bookstore.storefront.controllers.book;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import my.bookstore.facades.book.impl.DefaultBookFacade;
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

	@RequestMapping(value = "/mostRentedBook", method = RequestMethod.GET)
	@ResponseBody
	public List<MostRentedBookData> printMostRentedBooks(@RequestParam(value = "numberOfBooks") final int numberOfBooks)
	{
		//final int numberOfBooks = 5;
		return defaultBookFacade.returnMostRentedBooks(numberOfBooks);
	}





}
