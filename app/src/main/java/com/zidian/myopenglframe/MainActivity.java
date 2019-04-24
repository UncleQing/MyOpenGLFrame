package com.zidian.myopenglframe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnOnClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_main_1:
                intent = new Intent(MainActivity.this, GraphActivity.class);
                intent.putExtra(GraphActivity.TAG, GraphActivity.TYPE_SIMPLE);
                break;
            case R.id.btn_main_2:
                intent = new Intent(MainActivity.this, GraphActivity.class);
                intent.putExtra(GraphActivity.TAG, GraphActivity.TYPE_HIGH);
                break;
            case R.id.btn_main_3:
                intent = new Intent(MainActivity.this, GraphActivity.class);
                intent.putExtra(GraphActivity.TAG, GraphActivity.TYPE_COLOR);
                break;
            case R.id.btn_main_4:
                intent = new Intent(MainActivity.this, PictureActivity.class);
                break;
            case R.id.btn_main_5:

                break;
        }
        startActivity(intent);
    }
}
