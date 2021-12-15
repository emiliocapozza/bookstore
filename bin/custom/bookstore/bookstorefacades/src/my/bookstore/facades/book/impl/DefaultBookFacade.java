/**
 *
 */
package my.bookstore.facades.book.impl;

import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;

import java.util.List;

import org.apache.log4j.Logger;

import my.bookstore.core.model.BookModel;
import my.bookstore.core.services.RentalService;
import my.bookstore.facades.book.BookFacade;
import my.bookstore.facades.product.data.BookData;


/**
 * @author ecapozza
 *
 */
public class DefaultBookFacade implements BookFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultBookFacade.class);
	private RentalService rentalService;
	private Converter<BookModel, BookData> mostRentedBookConverter;

	/*
	 * (non-Javadoc)
	 *
	 * @see my.bookstore.facades.book.BookFacade#getMostRentedBooks(int)
	 */
	@Override
	public List<BookData> returnMostRentedBooks(final int numberOfBooks)
	{
		final List<BookModel> bookModelList = rentalService.getMostRentedBooks(numberOfBooks);

		try
		{
			if (bookModelList.isEmpty())
			{
				LOG.info("There aren't rented books");
				return null;
			}
			else
			{
				return mostRentedBookConverter.convertAll(bookModelList);
			}

		}
		catch (final IllegalArgumentException e)
		{
			LOG.error("The argument of the method must be an integer bigger then zero");
			return null;
		}
		catch (final ConversionException e)
		{
			LOG.error("An error occurs during the conversione type");
			return null;
		}
	}

	/**
	 * @return the rentalService
	 */
	public RentalService getRentalService()
	{
		return rentalService;
	}

	/**
	 * @return the mostRentedBookConverter
	 */
	public Converter<BookModel, BookData> getMostRentedBookConverter()
	{
		return mostRentedBookConverter;
	}

	/**
	 * @param mostRentedBookConverter
	 *           the mostRentedBookConverter to set
	 */
	public void setMostRentedBookConverter(final Converter<BookModel, BookData> mostRentedBookConverter)
	{
		this.mostRentedBookConverter = mostRentedBookConverter;
	}

	/**
	 * @param rentalService
	 *           the rentalService to set
	 */
	public void setRentalService(final RentalService rentalService)
	{
		this.rentalService = rentalService;
	}











}
