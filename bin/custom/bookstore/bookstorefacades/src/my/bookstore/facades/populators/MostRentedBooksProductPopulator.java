/**
 *
 */
package my.bookstore.facades.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import my.bookstore.core.model.BookModel;
import my.bookstore.facades.product.data.MostRentedBookData;


/**
 * @author ecapozza
 *
 */
public class MostRentedBooksProductPopulator implements Populator<ProductModel, MostRentedBookData>
{

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(final ProductModel source, final MostRentedBookData target) throws ConversionException
	{
		target.setName(source.getName());

		if (source instanceof BookModel)
		{
			target.setMostRented(((BookModel) source).getMostRented());
		}


	}

}
