package aditya.ritik.dbms_project;

import android.widget.Button;
import android.widget.TextView;
        import android.app.Activity;
        import android.app.DatePickerDialog;
        import android.app.Dialog;
        import android.app.DialogFragment;
        import android.app.TimePickerDialog;
        import android.icu.text.SimpleDateFormat;
        import android.icu.util.Calendar;
        import android.os.Build;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.text.format.DateFormat;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;
        import android.view.animation.AlphaAnimation;
        import android.view.animation.Animation;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.TimePicker;
        import android.widget.Toast;
        import java.util.Date;
public class dinein extends AppCompatActivity {
    TextView mDateDisplay;
    Button mPickDate;
    ImageView next, prev;
    int d = 0, t = 0, page = 0, type = 0,i1=0,i2=0,i3=0,i4=0;
    int year;
    int month;
    int day;
    int sel;
    RelativeLayout seat,selector,display;
    TextView mTimeDisplay;
    Button mPickTime;
    ImageButton t1,t2,t3,t4;
    EditText name;
    EditText email;
    static int mhour;
    static int mminute;
    java.sql.Time timeValue;
    SimpleDateFormat format;
    dbhelper db = new dbhelper(this);
    static final int TIME_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinein);
        seat=(RelativeLayout)findViewById(R.id.seat);
        selector=(RelativeLayout)findViewById(R.id.selector);

        final Calendar c = Calendar.getInstance();
        t1=(ImageButton)findViewById(R.id.t1);
        t2=(ImageButton) findViewById(R.id.t2);
        t3=(ImageButton) findViewById(R.id.t3);
        t4=(ImageButton) findViewById(R.id.t4);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        mDateDisplay = (TextView) findViewById(R.id.date);
        mPickDate = (Button) findViewById(R.id.datepicker);
        mTimeDisplay = (TextView) findViewById(R.id.time);
        mPickTime = (Button) findViewById(R.id.timepicker);
        mhour = c.get(Calendar.HOUR_OF_DAY);
        mminute = c.get(Calendar.MINUTE);
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        //Pick time's click event listener
        mPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                DatePickerDialog dd = new DatePickerDialog(dinein.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                try {
                                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                    String dateInString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                    Date date = formatter.parse(dateInString);
                                    mDateDisplay.setText(formatter.format(date).toString());
                                    formatter = new SimpleDateFormat("dd/MMM/yyyy");
                                } catch (Exception ex) {
                                }
                            }
                        }, year, month, day);
                dd.show();
            }
        });
        mPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog td = new TimePickerDialog(dinein.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                try {
                                    String dtStart = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                                    format = new SimpleDateFormat("hh:mm");
                                    timeValue = new java.sql.Time(format.parse(dtStart).getTime());
                                    mTimeDisplay.setText(String.valueOf(timeValue));
                                } catch (Exception ex) {
                                    mTimeDisplay.setText(ex.getMessage().toString());
                                }
                            }
                        },
                        mhour, mminute,
                        DateFormat.is24HourFormat(dinein.this)
                );
                td.show();
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i1==0) {
                    t1.setBackgroundResource(R.mipmap.table_selected);i1=1;sel=1;
                }
                else{
                    t1.setBackgroundResource(R.mipmap.table_not_selected);
                    i1=0;
                }
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i2==0) {
                    t2.setBackgroundResource(R.mipmap.table_selected);i2=1;sel=2;
                }
                else{
                    t2.setBackgroundResource(R.mipmap.table_not_selected);i2=0;
                }
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i3==0) {
                    t3.setBackgroundResource(R.mipmap.table_selected);i3=1;sel=3;
                }
                else{
                    t3.setBackgroundResource(R.mipmap.table_not_selected);i3=0;
                }
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i4==0) {
                    t4.setBackgroundResource(R.mipmap.table_selected);i4=1;sel=4;
                }
                else{
                    t4.setBackgroundResource(R.mipmap.table_not_selected);i4=0;
                }
            }
        });
        next=(ImageView)findViewById(R.id.next);
        next.setVisibility(View.VISIBLE);
        prev=(ImageView)findViewById(R.id.prev);
        prev.setVisibility(View.INVISIBLE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev();
            }
        });
    }
    public void prev()
    {
        if(page==1){
            prev.setVisibility(View.INVISIBLE);page=0;
            seat.setVisibility(View.INVISIBLE);
            selector.setVisibility(View.VISIBLE);
            AlphaAnimation animation = new AlphaAnimation(1.0f,0f);animation.setDuration(200);seat.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation animation) {}
                @Override public void onAnimationEnd(Animation animation) {seat.setVisibility(View.GONE);}
                @Override public void onAnimationRepeat(Animation animation) {}
            });
        }
    }
    public void next()
    {
        if(page==0) {
            seat.setVisibility(View.VISIBLE);page=1;
            selector.setVisibility(View.INVISIBLE);
            prev.setVisibility(View.VISIBLE);
            AlphaAnimation animation = new AlphaAnimation(0f, 1.0f);animation.setDuration(200);
            if(type==2){
                prev.setVisibility(View.VISIBLE);
                seat.startAnimation(animation);
            }
            if(db.getData(1,mDateDisplay.getText().toString(),mTimeDisplay.getText().toString())==0){
                t1.setBackgroundResource(R.mipmap.table_selected);
                t1.setEnabled(false);
            }
            if(db.getData(2,mDateDisplay.getText().toString(),mTimeDisplay.getText().toString())==0){
                t2.setBackgroundResource(R.mipmap.table_selected);
                t2.setEnabled(false);
            }
            if(db.getData(3,mDateDisplay.getText().toString(),mTimeDisplay.getText().toString())==0){
                t3.setBackgroundResource(R.mipmap.table_selected);
                t3.setEnabled(false);
            }
            if(db.getData(4,mDateDisplay.getText().toString(),mTimeDisplay.getText().toString())==0){
                t4.setBackgroundResource(R.mipmap.table_selected);
                t4.setEnabled(false);
            }
        }
        else if(page==1){
            db.insertContact(new Contact(sel,name.getText().toString(),email.getText().toString(),mTimeDisplay.getText().toString(),mDateDisplay.getText().toString()));
            Toast.makeText(dinein.this,"Booking Confirmed",Toast.LENGTH_LONG).show();

            selector.setVisibility(View.INVISIBLE);
        }
    }
}

