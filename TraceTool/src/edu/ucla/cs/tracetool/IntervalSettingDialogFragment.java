package edu.ucla.cs.tracetool;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class IntervalSettingDialogFragment extends DialogFragment {
	private EditText intervalEditText;
	private MainActivity activity;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		activity = (MainActivity) getActivity();

		builder.setMessage("Set Trace Interval (ms)");
		View view = inflater.inflate(R.layout.dialog_interval, null);
		builder.setView(view);

		builder.setPositiveButton("OK", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				activity.setInterval(Long.parseLong(intervalEditText.getText()
						.toString()));
			}
		});

		builder.setNegativeButton("Cancel", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		AlertDialog dialog = builder.create();
		intervalEditText = (EditText) view.findViewById(R.id.intervalEditText);
		if (intervalEditText == null) {
			Log.d("tracetool", "Not found EditText instance");
		} else {
			intervalEditText.setText("" + activity.getInterval());
		}

		return dialog;
	}
}
