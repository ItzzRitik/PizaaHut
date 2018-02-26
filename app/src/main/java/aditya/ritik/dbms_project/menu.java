package aditya.ritik.dbms_project;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class menu extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    Button date,time;
    ArrayList<String> Iname = new ArrayList<String>();
    ArrayList<String> Iprice = new ArrayList<String>();
    ArrayList<String> Iquan = new ArrayList<String>();
    ListView items_list;
    ImageView next,prev;int d=0,t=0,page=0,type=0,date_selected=0;
    RelativeLayout menu,checkout;
    RecyclerView recyclerView;
    TextView dText,tText,total;
    PizzaAdapter adapter;
    List<pizza> pizzaList;
    Calendar c = Calendar.getInstance();
    public void onBackPressed() {
        finish();overridePendingTransition(R.anim.fade_in,0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menu=(RelativeLayout) findViewById(R.id.expanded_menu);
        checkout=(RelativeLayout) findViewById(R.id.checkOut);
        items_list=(ListView)findViewById(R.id.items_list);
        total=(TextView) findViewById(R.id.total);
        dText=(TextView) findViewById(R.id.date_text);
        tText=(TextView) findViewById(R.id.time_text);
        date=(Button)findViewById(R.id.date);
        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:date.setBackground(getResources().getDrawable(R.drawable.circular_press));break;
                    case MotionEvent.ACTION_UP:date.setBackground(getResources().getDrawable(R.drawable.circular));
                        new DatePickerDialog(menu.this,menu.this,c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE)).show();break;
                }return false;}});

        time=(Button)findViewById(R.id.time);
        time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:time.setBackground(getResources().getDrawable(R.drawable.circular_press));break;
                    case MotionEvent.ACTION_UP:time.setBackground(getResources().getDrawable(R.drawable.circular));
                        if(date_selected==1) {new TimePickerDialog(menu.this,menu.this,c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),false).show();}
                        else{Snackbar.make(next, "Select The Date First", Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE",new View.OnClickListener() {@Override public void onClick(View view) {}})
                                    .setActionTextColor(getResources().getColor(R.color.colorAccent)).show();}break;
                }return false;}});

        prev=(ImageView)findViewById(R.id.prev);prev.setVisibility(View.INVISIBLE);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {prev();}
        });

        next=(ImageView)findViewById(R.id.next);next.setVisibility(View.INVISIBLE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {next();}
        });

        recyclerView = (RecyclerView)findViewById(R.id.menu_list);
        pizzaList = new ArrayList<>();
        adapter = new PizzaAdapter(this, pizzaList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareMenu();

        type=getIntent().getIntExtra("type",0);
        if(type!=2){next.performClick();next.setVisibility(View.VISIBLE);}
    }
    public void prev()
    {
        if(page==1){
            prev.setVisibility(View.INVISIBLE);page=0;
            AlphaAnimation animation = new AlphaAnimation(1.0f,0f);animation.setDuration(200);menu.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation animation) {}
                @Override public void onAnimationEnd(Animation animation) {menu.setVisibility(View.GONE);}
                @Override public void onAnimationRepeat(Animation animation) {}
            });
        }
        else if(page==2)
        {
            AlphaAnimation animation = new AlphaAnimation(1.0f,0f);animation.setDuration(200);checkout.startAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation animation) {}
                @Override public void onAnimationEnd(Animation animation) {checkout.setVisibility(View.GONE);}
                @Override public void onAnimationRepeat(Animation animation) {}
            });page=1;prev.setVisibility(View.INVISIBLE);
        }
    }
    public void next()
    {
        if(page==0) {
            menu.setVisibility(View.VISIBLE);page=1;
            AlphaAnimation animation = new AlphaAnimation(0f, 1.0f);animation.setDuration(200);
            if(type==2){prev.setVisibility(View.VISIBLE);menu.startAnimation(animation);}
        }else if(page==1){
            items_list.setAdapter(new CustomAdapter(menu.this,Iname.toArray(new String[Iname.size()]),Iquan.toArray(new String[Iquan.size()]),Iprice.toArray(new String[Iprice.size()])));
            checkout.setVisibility(View.VISIBLE);page=2;
            double sum = 0;
            for (int i=0; i<Iprice.size(); i++){sum+=Integer.parseInt(Iprice.get(i).substring(1))*Integer.parseInt(Iquan.get(i));}
            total.setText("Total = ₹"+sum);
            AlphaAnimation animation = new AlphaAnimation(0f, 1.0f);animation.setDuration(200);
            prev.setVisibility(View.VISIBLE);checkout.startAnimation(animation);
        }
    }
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        date_selected=1;
        if(dayOfMonth>=c.get(Calendar.DATE) && monthOfYear>=c.get(Calendar.MONTH) && year>=c.get(Calendar.YEAR))
        {
            if(dayOfMonth==c.get(Calendar.DATE) && monthOfYear==c.get(Calendar.MONTH) && year==c.get(Calendar.YEAR)){date.setText("TODAY");
                dText.setVisibility(View.INVISIBLE);}
            else{date.setText(dayOfMonth+"/"+monthOfYear+"/"+year);dText.setVisibility(View.VISIBLE);}d=1;
            if(t==1){next.setVisibility(View.VISIBLE);}
        }
        else{
            Snackbar.make(next, "We Can't Deliver Pizza in the Past", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE",new View.OnClickListener() {@Override public void onClick(View view) {}})
                    .setActionTextColor(getResources().getColor(R.color.colorAccent )).show();
        }
    }
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if((hourOfDay<c.get(Calendar.HOUR_OF_DAY) || minute<c.get(Calendar.MINUTE)) && date.getText().toString().equals("TODAY"))
        {
            Snackbar.make(next, "We Can't Deliver Pizza in the Past", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE",new View.OnClickListener() {@Override public void onClick(View view) {}})
                    .setActionTextColor(getResources().getColor(R.color.colorAccent)).show();
        }
        else{
            StringBuilder sb = new StringBuilder();
            if(hourOfDay>=12){sb.append(hourOfDay-12).append( ":" ).append(minute).append(" PM");}
            else{sb.append(hourOfDay).append( ":" ).append(minute).append(" AM");}
            time.setText(sb);t=1;tText.setVisibility(View.VISIBLE);
            if(d==1){next.setVisibility(View.VISIBLE);}
        }
    }
    private void prepareMenu() {
        int[] covers = new int[]{R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
                R.drawable.p8,R.drawable.p9, R.drawable.p10,R.drawable.p11,R.drawable.p12,R.drawable.p13,R.drawable.p14,R.drawable.p15,
                R.drawable.p16, R.drawable.p17,R.drawable.p18, R.drawable.p19,R.drawable.p20,};

        pizza a = new pizza("Veggie Supreme",480, covers[0]);
        pizzaList.add(a);
        a = new pizza("Exotica",480, covers[1]);
        pizzaList.add(a);
        a = new pizza("Paneer Vegorama",480,covers[2]);
        pizzaList.add(a);
        a = new pizza("Chicken Supreme",525, covers[3]);
        pizzaList.add(a);
        a = new pizza("Triple Chicken",525, covers[4]);
        pizzaList.add(a);
        a = new pizza("Chicken Italiano",525, covers[5]);
        pizzaList.add(a);
        a = new pizza("Ultimate Chicken",625, covers[6]);
        pizzaList.add(a);
        a = new pizza("Tandoori Paneer",425, covers[7]);
        pizzaList.add(a);
        a = new pizza("Veggie Lover",335, covers[8]);
        pizzaList.add(a);
        a = new pizza("Country Feast",525, covers[9]);
        pizzaList.add(a);
        a = new pizza("Chicken Tikka",425, covers[10]);
        pizzaList.add(a);
        a = new pizza("Double Trouble",475, covers[11]);
        pizzaList.add(a);
        a = new pizza("Chickeroni",525, covers[12]);
        pizzaList.add(a);
        a = new pizza("Double Cheese",325, covers[13]);
        pizzaList.add(a);
        a = new pizza("Veggie Feast",275, covers[14]);
        pizzaList.add(a);
        a = new pizza("Rawalpindi Chana",420, covers[15]);
        pizzaList.add(a);
        a = new pizza("Chicken N Spicy",345, covers[16]);
        pizzaList.add(a);
        a = new pizza("Keema Masala",425, covers[17]);
        pizzaList.add(a);
        a = new pizza("Margherita",340, covers[18]);
        pizzaList.add(a);
        a = new pizza("Chicken Sausage",340, covers[19]);
        pizzaList.add(a);
        adapter.notifyDataSetChanged();
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;
        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;
            if (includeEdge){
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;
                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }
}
