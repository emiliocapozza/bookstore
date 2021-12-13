package my.bookstore.core.daos.impl;

import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import my.bookstore.core.daos.RentalDao;
import my.bookstore.core.model.BookModel;
import my.bookstore.core.model.RentalModel;


public class DefaultRentalDao extends AbstractItemDao implements RentalDao
{

	@Resource
	CatalogVersionService catalogVersionService;

	@Override
	public List<RentalModel> getActiveRentalsForCustomer(final CustomerModel customer)
	{
		/*
		 * This could be done using GenericDao but for learning purposes we are using Flexible Search
		 *
		 * When figuring out when rentals start/end, we want to be generous: begin at the start of the first day, and stop
		 * at the end of the last day. For that, we need two date variables: dayStart and dayEnd.
		 */

		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		final Date dayStart = cal.getTime(); // dayStart
		cal.add(Calendar.DATE, 1);
		final Date dayEnd = cal.getTime(); // dayEnd

		/*
		 * Now, your part of the implementation. Use the Flexible Search statement you created in exercise 5.2:
		 *
		 * SELECT {r.PK} FROM {Rental as r JOIN Customer as c ON {c.PK}={r.customer}} WHERE {c.uid}='john@hybris.com' AND
		 * {r.startDate}<=NOW() AND {r.endDate}>=NOW()
		 *
		 * Now implement it in Java:
		 */
		// ---------------------------------------------------------------------------
		// TODO exercise 5.3: Get the list of all the active rentals for this customer

		final String queryString = "SELECT {r." + RentalModel.PK + "} " + "FROM {" + RentalModel._TYPECODE + " as r JOIN "
				+ CustomerModel._TYPECODE + " as c on {c." + CustomerModel.PK + "}={r." + RentalModel.CUSTOMER + "}} " + " WHERE {c."
				+ CustomerModel.UID + "}=?customer" + " AND {r." + RentalModel.STARTDATE + "}<=?day1 " + " AND {r."
				+ RentalModel.ENDDATE + "}>=?day2 ";

		// 1. Compile a query from this string
		final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);

		// 2. Add the query parameters
		// ...
		// need an instance of java.util.Date for query param value
		query.addQueryParameter("day1", dayStart);
		query.addQueryParameter("day2", dayEnd);
		query.addQueryParameter("customer", customer.getUid());

		// 3. Execute!

		return getFlexibleSearchService().<RentalModel> search(query).getResult();

	}

	@Override
	public List<BookModel> getMostRentedBooks(final int numberOfBooks) throws IllegalArgumentException
	{
		if (numberOfBooks <= 0)
		{
			throw new IllegalArgumentException();
		}
		else
		{

			/*
			 * Use the Flexible Search statement you created in exercise 5.4:
			 *
			 * SELECT {b.PK} FROM {Book as b JOIN Rental as r on {b.PK}={r.product}} GROUP BY {b.PK} ORDER BY
			 * count({r.rentalId}) DESC
			 */

			// ----------------------------------------------
			// TODO exercise 5.5: Get the 5 most rented books

			final String queryString = "SELECT {b." + BookModel.PK + "} FROM {" + BookModel._TYPECODE + " as b JOIN "
					+ RentalModel._TYPECODE + " as r ON {b." + BookModel.PK + "}={r." + RentalModel.PRODUCT + "}}" + "GROUP BY {b."
					+ BookModel.PK + "} ORDER BY count({r." + RentalModel.RENTALID + "}) DESC";

			// 1. Compile a query from this string final
			final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);

			// 2. Add the query parameter // ...
			query.setNeedTotal(true);
			query.setCount(numberOfBooks);
			query.setStart(0);
			// 3. Execute
			final SearchResult<BookModel> books = getFlexibleSearchService().search(query);

			return books.getResult();
		}
	}

}
