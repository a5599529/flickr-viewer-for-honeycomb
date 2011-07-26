/**
 * 
 */

package com.charles.task;

import com.aetrion.flickr.photos.PhotoList;
import com.charles.R;
import com.charles.dataprovider.IPhotoListDataProvider;
import com.charles.event.DefaultPhotoListReadyListener;
import com.charles.event.IPhotoListReadyListener;

import android.app.Activity;
import android.util.Log;

/**
 * Represents the task to fetch the photo list of a user.
 * <p>
 * By default, if no photo list ready listener is specified, we're going to show
 * the photo list into the photo list fragment.
 * 
 * @author charles
 */
public class AsyncPhotoListTask extends ProgressDialogAsyncTask<Void, Integer, PhotoList> {

    private IPhotoListDataProvider mPhotoListProvider;
    private IPhotoListReadyListener mPhotoListReadyListener;

    public AsyncPhotoListTask(Activity context,
            IPhotoListDataProvider photoListProvider,
            IPhotoListReadyListener listener) {
        this(context, photoListProvider, listener, context.getResources().getString(
                R.string.loading_photos));
    }

    public AsyncPhotoListTask(Activity context,
            IPhotoListDataProvider photoListProvider,
            IPhotoListReadyListener listener, String prompt) {
        super(context, prompt);
        this.mPhotoListProvider = photoListProvider;
        if( listener ==  null ) {
            mPhotoListReadyListener = new DefaultPhotoListReadyListener(context, photoListProvider);
        } else {
            mPhotoListReadyListener = listener;
        }
        this.mDialogMessage = prompt == null ? context.getResources().getString(
                R.string.loading_photos) : prompt;
    }

    @Override
    protected PhotoList doInBackground(Void... params) {
        try {
            mPhotoListProvider.invalidatePhotoList();
            return mPhotoListProvider.getPhotoList();
        } catch (Exception e) {
            Log.e("AsyncPhotoListTask", "error to get photo list: "  //$NON-NLS-1$//$NON-NLS-2$
                    + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(PhotoList result) {
        super.onPostExecute(result);
        if (mPhotoListReadyListener != null) {
            mPhotoListReadyListener.onPhotoListReady(result, false);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (mPhotoListReadyListener != null) {
            mPhotoListReadyListener.onPhotoListReady(null, true);
        }
    }

}
