package util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    private static final String TAG = "MY_TRAVELS";
    public EditText editText;

    public DatePickerFragment(EditText edit_text) {
        editText = edit_text;
    }

    public DatePickerFragment() {

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = 0;
        int month = 0;
        int day = 0;

        if (editText.getText().toString().equals("")) {
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                c.setTime(sdf.parse(editText.getText().toString()));
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
            } catch (ParseException e) {
                Log.e(TAG, "Erro convertendo data DatePicker: " + e.getMessage());
            }
        }

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String sDay = "";
        String sMonth = "";
        String sYear = "";

        if (day < 10) {
            sDay = 0 + String.valueOf(day);
        } else {
            sDay = String.valueOf(day);
        }

        if (month < 9) {
            sMonth = 0 + String.valueOf(month + 1);
        } else {
            sMonth = String.valueOf(month + 1);
        }
        sYear = String.valueOf(year);

        editText.setText(sDay + "/" + sMonth + "/" + sYear);
    }
}