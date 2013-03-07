package com.readysteady;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContext implements ApplicationContextAware {

	public static org.springframework.context.ApplicationContext context;

	
	/**
	 * This method is called from within the ApplicationContext once it is done
	 * starting up, it will stick a reference to itself into this bean.
	 * 
	 * @param context
	 *          a reference to the ApplicationContext.
	 */
	@Override
	public void setApplicationContext(final org.springframework.context.ApplicationContext arg0) throws BeansException {
		ApplicationContext.context = arg0;
	}

}
