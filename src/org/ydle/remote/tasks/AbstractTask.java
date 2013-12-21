package org.ydle.remote.tasks;

import org.ydle.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

public abstract class AbstractTask<P,T> extends AsyncTask<P, Void, T> {

	private ProgressDialog waitDialog;
	Activity context;

	private int dialogueTitle = R.string.chargement;
	private int dialogueMsg = R.string.recherche;

	public AbstractTask(Activity context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		if (waitDialog == null) {
			waitDialog = new ProgressDialog(context);
			waitDialog.setIndeterminate(true);
		}

		waitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

			public void onCancel(DialogInterface dialog) {
				if (AbstractTask.this != null) {
					AbstractTask.this.cancel(true);
				}
				context.finish();
			}
		});
		waitDialog.setTitle(getDialogTitle());
		waitDialog.setMessage(getDialogMsg());
		waitDialog.show();
	}

	@Override
	protected void onPostExecute(T result) {
		super.onPostExecute(result);
		if (waitDialog != null)
			waitDialog.dismiss();
	}

	public void setDialogTitle(int dialogueTitle) {
		this.dialogueTitle = dialogueTitle;
	}

	public void setDialogueMsg(int dialogueMsg) {
		this.dialogueMsg = dialogueMsg;
	}
	
	public String getDialogTitle(){
		return context.getString(dialogueTitle);
	}
	
	public String getDialogMsg(){
		return context.getString(dialogueMsg);
	}

}
