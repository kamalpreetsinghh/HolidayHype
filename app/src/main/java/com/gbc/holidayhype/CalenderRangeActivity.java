package com.gbc.holidayhype;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.gbc.holidayhype.databinding.ActivityRangeCalenderBinding;
import com.wisnu.datetimerangepickerandroid.CalendarPickerView;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Calendar;
import java.util.Date;

public class CalenderRangeActivity extends AppCompatActivity {

    private ActivityRangeCalenderBinding binding;
//    TextView checkInText;
    Date selectedDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityRangeCalenderBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        final CalendarPickerView cal = findViewById(R.id.calendar_view);
        cal.init(
                buildMinimumDate(),
                DateTime.now(DateTimeZone.UTC).plusDays(10).toDate()
        )
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(selectedDate);
        cal.setOnDateResolvedListener(new CalendarPickerView.OnDateResolvedListener() {
            @Override
            public void onMinDateResolved(Date date) {
                selectedDate = date;
//                checkInText.setText(selectedDate.toString());
                selectedDate = date;
                Intent intent = new Intent();
                intent.putExtra("selectedDate",selectedDate.toString());
//                checkInText.setText(selectedDate.toString());
                setResult(RESULT_OK,intent);
                finish();
                Log.d("onMinDateResolved", date.toString());
            }

            @Override
            public void onMaxDateResolved(Date date) {
                selectedDate = date;
//                checkInText.setText(selectedDate.toString());
                selectedDate = date;
                Intent intent = new Intent();
                intent.putExtra(HotelsFragment.EXTRA_KEY_TEST, selectedDate.toString());
                setResult(HotelsFragment.RESULT_CODE, intent); // You can also send result without any data using setResult(int resultCode)
                finish();
            }
        });

        cal.init(
                DateTime.now(DateTimeZone.UTC).toDate(),
                DateTime.now(DateTimeZone.UTC).plusDays(10).toDate()
        )
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(selectedDate);

        cal.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                Log.d("onDateSelected", date.toString());
                selectedDate = date;

                Intent intent = new Intent();
                intent.putExtra(HotelsFragment.EXTRA_KEY_TEST, selectedDate.toString());
                setResult(HotelsFragment.RESULT_CODE, intent); // You can also send result without any data using setResult(int resultCode)
                finish();

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });


    }


    private Date buildMinimumDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.YEAR, 2019);

        return calendar.getTime();
    }
}