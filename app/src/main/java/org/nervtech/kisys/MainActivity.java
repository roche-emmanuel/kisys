package org.nervtech.kisys;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView _titleTxt;
    private Typeface _bbFont;
    private int _fontColor;
    private int _fontSize;

    /* We should add additional text views here to render the operation numbers:*/
    private HashMap<String, TextView> _texts = new HashMap<String, TextView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        /* Change the font for the text title: */
        _fontColor = Color.rgb(250, 250, 250);
        _fontSize = 70;
        _titleTxt = (TextView) findViewById(R.id.activity_title);
        _bbFont = Typeface.createFromAsset(getAssets(), "fonts/erasdust.ttf");

        _titleTxt.setTypeface(_bbFont);
        _titleTxt.setTextSize(32);
        _titleTxt.setTextColor(_fontColor);

        // Retrieve the line 1/2/3 and 4:
        LinearLayout l1 = (LinearLayout) findViewById(R.id.line_1);
        LinearLayout l2 = (LinearLayout) findViewById(R.id.line_2);
        LinearLayout l3 = (LinearLayout) findViewById(R.id.line_3);
        LinearLayout l4 = (LinearLayout) findViewById(R.id.line_4);

        addOperationSlot(l1, "l1_u", "0");
        addOperationSlot(l1, "l1_d", "1");
        addOperationSlot(l1, "l1_c", "2");
        addOperationSlot(l2, "l2_u", "3");
        addOperationSlot(l2, "l2_d", "4");
        addOperationSlot(l2, "l2_c", "5");
        addOperationSlot(l2, "l2_m", "+");

        addOperationSlot(l4, "l4_u", "3");
        addOperationSlot(l4, "l4_d", "5");
        addOperationSlot(l4, "l4_c", "7");

        OperationLine opline = new OperationLine(this);
        opline.setLayoutParams(new ViewGroup.LayoutParams(4*(_fontSize+40), 40));
        l3.addView(opline);
    }

    public void addOperationSlot(LinearLayout parent, String name, String value) {
        parent.setPadding(50, 0, 50, 0);
        parent.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)parent.getLayoutParams();
//        params.height = _fontSize+20;

        TextView tv = new TextView(this);
        tv.setTypeface(_bbFont);
        tv.setTextSize(_fontSize);
        tv.setTextColor(_fontColor);
        tv.setText(value);
//        tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tv.setWidth(_fontSize+40);

        _texts.put(name, tv);
        parent.addView(tv, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
