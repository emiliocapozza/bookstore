/**
 *
 */
package my.bookstore.core.jobs;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.List;

import org.apache.log4j.Logger;

import my.bookstore.core.model.BookModel;
import my.bookstore.core.services.RentalService;


/**
 * @author ecapozza
 *
 */
public class BookJobPerformable extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(BookJobPerformable.class);
	RentalService rentalService;
	ModelService modelService;

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
	@Override
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.
	 * CronJobModel)
	 */
	@Override
	public PerformResult perform(final CronJobModel cronJob)
	{
		final int numberOfBooks = 5;

		try
		{
			final List<BookModel> books = rentalService.getMostRentedBooks(numberOfBooks);

			if (books.isEmpty())
			{
				LOG.info("No book is rented");
			}
			else
			{
				for (final BookModel book : books)
				{
					book.setMostRented(book.getName() + " is the most rented book in " + cronJob.getStartTime());
					modelService.save(book);
					LOG.info(book.getMostRented());
				}
			}
			return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
		}
		catch (final Exception e)
		{
			LOG.error("An error occurs in rentalService or in modelService");
			return new PerformResult(CronJobResult.FAILURE, CronJobStatus.FINISHED);
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
	 * @param rentalService
	 *           the rentalService to set
	 */
	public void setRentalService(final RentalService rentalService)
	{
		this.rentalService = rentalService;
	}

	/*--------------ImPex cronjob------------------------------------
		 *
		INSERT_UPDATE ServicelayerJob;code[unique=true];springId
		;bookJobPerformable;bookJobPerformable
		
		INSERT_UPDATE CronJob;code[unique=true];job(code);singleExecutable;sessionLanguage(isocode)
		;myCronJob;bookJobPerformable;false;en
		
		INSERT_UPDATE Trigger;cronjob(code)[unique=true];cronExpression
		;myCronJob;0/60 0/10 * ? * * *
	
	
	---------------Aggiungere Most Rented-------------------------------------
	$productCatalog=bookstoreProductCatalog
	$onlineCatVersion=catalogversion(catalog(id[default=$productCatalog]),version[default='Online'])[unique=true,default=$productCatalog:Online]
	$supercategories=supercategories(code, $catalogVersion)
	$approved=approvalStatus(code)[default='approved']
	$lang=en
	
	
	INSERT_UPDATE Rental;rentalId[unique=true];startDate[dateformat=dd.MM.yyyy];endDate[dateformat=dd.MM.yyyy];product(code,$onlineCatVersion);customer(uid)
	;102;10.10.2015;10.12.2030;8942944779;john@hybris.com
	;103;10.11.2015;19.12.2030;8942944779;acceptanceuser@test.com
	;104;11.10.2015;18.12.2030;8942944779;zohan@customer.com
	;105;13.10.2015;17.12.2030;8942944779;lena@hybris.com
	;106;14.10.2015;15.12.2030;8942944779;sabine@hybris.com
	;202;11.10.2015;11.12.2030;8423207609;john@hybris.com
	;203;12.11.2015;12.12.2030;8423207609;acceptanceuser@test.com
	;204;13.12.2015;13.12.2030;8423207609;john@hybris.com
	;205;14.12.2015;14.12.2030;8423207609;zohan@customer.com
	;302;11.06.2015;15.02.2030;1109070349;lena@hybris.com
	;303;12.06.2015;16.02.2030;1109070349;zohan@customer.com
	;304;13.06.2015;17.02.2030;1109070349;acceptanceuser@test.com
	;305;14.06.2015;18.02.2030;1109070349;john@hybris.com
	;402;10.05.2015;15.02.2030;1185574409;sabine@hybris.com
	;403;11.05.2015;16.02.2030;1185574409;john@hybris.com
	;404;12.05.2015;17.02.2030;1185574409;acceptanceuser@test.com
	;405;13.05.2015;18.02.2030;1185574409;zohan@customer.com
	;502;10.08.2015;10.12.2016;1664834729;acceptanceuser@test.com
	;503;11.08.2015;11.12.2016;1664834729;lena@hybris.com
	;504;12.08.2015;12.12.2016;1664834729;john@hybris.com
	;505;13.08.2015;14.12.2016;1664834729;zohan@customer.com
	
	------------------------------------------------------------------------*/
}


