/**
 *
 */
package my.bookstore.facades.populators;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import my.bookstore.core.model.BookModel;
import my.bookstore.facades.product.data.BookData;


/**
 * @author ecapozza
 *
 */
public class BookstoreProductReversePopulator implements Populator<BookData, BookModel>
{
	private CatalogVersionService catalogVersionService;

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final BookData source, final BookModel target) throws ConversionException
	{
		final CatalogVersionModel catalogVersion = catalogVersionService.getCatalogVersion("bookstoreProductCatalog", "Staged");
		target.setName(source.getName());
		target.setISBN10(source.getCodice10());
		target.setISBN13(source.getCodice13());
		target.setPublisher(source.getPublisher());
		target.setLanguage(source.getLanguage());
		target.setRentable(source.getRentable());
		target.setCode(source.getCode());
		target.setCatalogVersion(catalogVersion);

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
