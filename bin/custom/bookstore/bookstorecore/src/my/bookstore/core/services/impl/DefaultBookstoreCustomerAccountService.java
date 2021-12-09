package my.bookstore.core.services.impl;

import de.hybris.platform.commerceservices.customer.impl.DefaultCustomerAccountService;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.internal.dao.DefaultGenericDao;

import java.util.HashMap;
import java.util.List;

import my.bookstore.core.enums.RewardStatusLevel;
import my.bookstore.core.model.BookModel;
import my.bookstore.core.services.BookstoreCustomerAccountService;


public class DefaultBookstoreCustomerAccountService extends DefaultCustomerAccountService
		implements BookstoreCustomerAccountService
{
	DefaultGenericDao<CustomerModel> customerDao;

	@Override
	public void updateRewardStatusPoints(final CustomerModel customer, final OrderModel order)
	{
		int total = 0; //represents total number of point that Customer will get for this order
		for (final AbstractOrderEntryModel entry : order.getEntries())
		{
			final BookModel book = (BookModel) entry.getProduct();
			total += book.getRewardPoints() * entry.getQuantity();

		}

		// TODO exercise 6.2 : update customer points
		customer.setPoints(customer.getPoints() + total);
		//aggiorno i punti attuali del customer(usando il getMethod) aggiungendo i punti totali dell'ordine
		getModelService().save(customer);
		//salvo le modifiche fatte alla ClassModel2
	}

	public List<CustomerModel> getAllCustomersForLevel(final RewardStatusLevel level)
	{
		// TODO exercise 6.3 : implement the method
		//devo filtratre tutti i customer per RewardStatusLevel=level
		//--->creo una mappa chiave-valore da usare nella funzione find del customerDao
		final HashMap<String, RewardStatusLevel> params = new HashMap<String, RewardStatusLevel>();
		params.put(CustomerModel.REWARDSTATUSLEVEL, level);

		//la find cerca e restituisce tutti i customer con attributo e valore indicati nella Mappa params
		final List<CustomerModel> customersForLevel = customerDao.find(params);

		return customersForLevel;
	}


	/**
	 * @param customerDao
	 *           the customerDao to set
	 */
	public void setCustomerDao(final DefaultGenericDao customerDao)
	{
		this.customerDao = customerDao;
	}

}
