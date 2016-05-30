package br.com.memorygame.myfirstgame;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton btnLevel1;
    AdapterMenu adapterMenu;
    ViewPager mViewPager;
    private int[] mResources = {
            R.drawable.level1,
            R.drawable.level2,
            R.drawable.level3,
            R.drawable.level4,
            R.drawable.level5,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adapterMenu = new AdapterMenu(this,mResources);

        mViewPager = (ViewPager) findViewById(R.id.levels);
        mViewPager.setAdapter(adapterMenu);

        mViewPager.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent JogoLevel1 = new Intent(MainActivity.this, JogoLevels.class);
                startActivity(JogoLevel1);
            }

        });
    }
}