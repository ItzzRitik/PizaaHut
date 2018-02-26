package aditya.ritik.dbms_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class user_desk extends AppCompatActivity {
    Button dine,homeDel,take;
    public void onBackPressed() {
        finish();overridePendingTransition(R.anim.fade_in,0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_desk);

        dine=(Button)findViewById(R.id.dine);
        dine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:dine.setBackground(getResources().getDrawable(R.drawable.button));break;
                    case MotionEvent.ACTION_UP:dine.setBackground(getResources().getDrawable(R.drawable.button_pressed));
                        Intent getMenu = new Intent(user_desk.this, dinein.class);
                        getMenu.putExtra("type",1);
                        user_desk.this.startActivity(getMenu);
                        overridePendingTransition(R.anim.fade_in,0);break;
                }return false;}});

        homeDel=(Button)findViewById(R.id.homeDel);
        homeDel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:homeDel.setBackground(getResources().getDrawable(R.drawable.button));break;
                    case MotionEvent.ACTION_UP:homeDel.setBackground(getResources().getDrawable(R.drawable.button_pressed));
                        Intent getMenu = new Intent(user_desk.this, menu.class);
                        getMenu.putExtra("type",2);
                        user_desk.this.startActivity(getMenu);
                        overridePendingTransition(R.anim.fade_in,0);break;
                }return false;}});

        take=(Button)findViewById(R.id.take);
        take.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:take.setBackground(getResources().getDrawable(R.drawable.button));break;
                    case MotionEvent.ACTION_UP:take.setBackground(getResources().getDrawable(R.drawable.button_pressed));
                        Intent getMenu = new Intent(user_desk.this, menu.class);
                        getMenu.putExtra("type",3);
                        user_desk.this.startActivity(getMenu);
                        overridePendingTransition(R.anim.fade_in,0);break;
                }return false;}});
    }
}
