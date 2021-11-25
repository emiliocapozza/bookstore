package my.bookstore.core.services.impl;

import de.hybris.platform.core.model.user.CustomerModel;

import java.util.List;

import my.bookstore.core.daos.RentalDao;
import my.bookstore.core.model.RentalModel;
import my.bookstore.core.services.RentalService;


public class DefaultRentalService implements RentalService
{
	//devo richiamare l'alias o la classe sovrascritta?
	RentalDao rentalDao;

	@Override
	public List<RentalModel> getActiveRentalsForCustomer(final CustomerModel customer)
	{
		// TODO exercise 6.4: add implementation
		//voglio richiamare il metodo getActiveRentalsForCustomer dal DefaultRentalDao che nel file bookstore-spring.xml è stato
		//sovrascritto con l'alias "rentalDao".

		//scritto senza spring:
		//DefaultRentalDao rentalDao=new DefaultRentalDao();
		//return rentalDao.getActiveRentalsForCustomer(customer);

		//se voglio utilizzare spring devo iniettarlo tramite costruttore o tramite setMethod--->setMethod:

		return rentalDao.getActiveRentalsForCustomer(customer);
	}


	//scrivo il setMethod
	/**
	 * @param rentalDao
	 *           the rentalDao to set
	 */
	public void setRentalDao(final RentalDao rentalDao)
	{
		this.rentalDao = rentalDao;
	}




}
