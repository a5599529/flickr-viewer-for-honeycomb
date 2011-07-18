/*
 * Created on Jun 15, 2011
 *
 * Copyright (c) Sybase, Inc. 2011   
 * All rights reserved.                                    
 */

package com.charles.actions;

import android.app.Activity;

import com.charles.FlickrViewerApplication;
import com.charles.R;
import com.charles.dataprovider.InterestingPhotosDataProvider;
import com.charles.dataprovider.PaginationPhotoListDataProvider;
import com.charles.task.AsyncPhotoListTask;

/**
 * @author qiangz
 */
public class ShowInterestingPhotosAction extends ActivityAwareAction {

	public ShowInterestingPhotosAction(Activity activity) {
		super(activity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.charles.actions.IAction#execute()
	 */
	@Override
	public void execute() {
		FlickrViewerApplication app = (FlickrViewerApplication) mActivity
				.getApplication();
		final PaginationPhotoListDataProvider photoListDataProvider = new InterestingPhotosDataProvider();
		photoListDataProvider.setPageSize(app.getPageSize());
		final AsyncPhotoListTask task = new AsyncPhotoListTask(mActivity,
				photoListDataProvider, null, mActivity.getResources()
						.getString(R.string.task_loading_interest));
		task.execute();
	}

}