/**
 *
 */
package my.bookstore.facades.book.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;

import org.apache.log4j.Logger;

import my.bookstore.core.model.BookModel;
import my.bookstore.core.services.RentalService;
import my.bookstore.facades.book.BookFacade;
import my.bookstore.facades.product.data.BookData;
import my.bookstore.facades.product.data.MostRentedBookData;


/**
 * @author ecapozza
 *
 */
public class DefaultBookFacade implements BookFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultBookFacade.class);
	private RentalService rentalService;
	private Converter<BookModel, MostRentedBookData> mostRentedBookConverter;
	private Converter<BookData, BookModel> bookReverseConverter;
	private ModelService modelService;
	private CatalogVersionService catalogVersionService;

	/*
	 * (non-Javadoc)
	 *
	 * @see my.bookstore.facades.book.BookFacade#getMostRentedBooks(int)
	 */
	@Override
	public List<MostRentedBookData> returnMostRentedBooks(final int numberOfBooks)
	{
		try
		{
			final List<BookModel> bookModelList = rentalService.getMostRentedBooks(numberOfBooks);
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

	@Override
	public boolean insertBook(final BookData bookData)
	{
		try
		{
			if (bookData == null)
			{
				LOG.info("Inserted book is a null book");
				return false;
			}
			else
			{
				final BookModel bookModel = modelService.create(BookModel.class);
				final BookModel book2 = bookReverseConverter.convert(bookData, bookModel);
				modelService.save(book2);
				return true;
			}
		}
		catch (final ModelSavingException e)
		{
			LOG.error("An error occurs in saving book");
			e.printStackTrace();
			return false;
		}
		catch (final ConversionException e)
		{
			LOG.error("An error occurs during the conversione type");
			return false;
		}
	}

	public boolean insert(final String name, final String edition, final String ISBN10, final String ISBN13, final String code,
			final String language, final String publisher, final Boolean rentable)
	{
		try
		{
			final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion("bookstoreProductCatalog", "Staged");
			final BookModel bookModel = modelService.create(BookModel.class);
			bookModel.setName(name);
			bookModel.setISBN10(ISBN10);
			bookModel.setISBN13(ISBN13);
			bookModel.setCatalogVersion(catalogVersion);
			bookModel.setCode(code);
			bookModel.setLanguage(language);
			bookModel.setPublisher(publisher);
			bookModel.setRentable(rentable);
			bookModel.setEdition(edition);
			modelService.save(bookModel);
			return true;
		}
		catch (final ModelSavingException e)
		{
			LOG.error("An error occurs in saving book");
			e.printStackTrace();
			return false;
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
	public Converter<BookModel, MostRentedBookData> getMostRentedBookConverter()
	{
		return mostRentedBookConverter;
	}

	/**
	 * @param mostRentedBookConverter
	 *           the mostRentedBookConverter to set
	 */
	public void setMostRentedBookConverter(final Converter<BookModel, MostRentedBookData> mostRentedBookConverter)
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

	/**
	 * @return the bookReverseConverter
	 */
	public Converter<BookData, BookModel> getBookReverseConverter()
	{
		return bookReverseConverter;
	}

	/**
	 * @param bookReverseConverter
	 *           the bookReverseConverter to set
	 */
	public void setBookReverseConverter(final Converter<BookData, BookModel> bookReverseConverter)
	{
		this.bookReverseConverter = bookReverseConverter;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the catalogVersionService
	 */
	public CatalogVersionService getCatalogVersionService()
	{
		return catalogVersionService;
	}

	/**
	 * @param catalogVersionService
	 *           the catalogVersionService to set
	 */
	public void setCatalogVersionService(final CatalogVersionService catalogVersionService)
	{
		this.catalogVersionService = catalogVersionService;
	}
















}
