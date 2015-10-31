package org.nervtech.kisys;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView _titleTxt;
    private Typeface _bbFont;
    private int _fontColor;
    private int _fontSize;
    private TextView _currentSlot = null;

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

        addOperationSlot(l1, "l1_u", "0",false);
        addOperationSlot(l1, "l1_d", "1",false);
        addOperationSlot(l1, "l1_c", "2",false);
        addOperationSlot(l2, "l2_u", "3",false);
        addOperationSlot(l2, "l2_d", "4",false);
        addOperationSlot(l2, "l2_c", "5",false);
        addOperationSlot(l2, "l2_m", "+",false);

        addOperationSlot(l4, "l4_u", "3",true);
        addOperationSlot(l4, "l4_d", "5",true);
        addOperationSlot(l4, "l4_c", "7",true);

        OperationLine opline = new OperationLine(this);
        opline.setLayoutParams(new ViewGroup.LayoutParams(4*(_fontSize+40), 40));
        l3.addView(opline);

        // Prepare the lines of buttons:
        LinearLayout nbl1 = (LinearLayout) findViewById(R.id.nb_line_1);
        LinearLayout nbl2 = (LinearLayout) findViewById(R.id.nb_line_2);

        for(int i=0;i<5;++i) {
            addNumberButton(nbl1,i);
        }
        for(int i=5;i<10;++i) {
            addNumberButton(nbl2,i);
        }
    }

    /*
        Helper method that can be called to assign the current target slot for the next
        number button press.
     */
    public void setCurrentTargetSlot(TextView tv)
    {
        // If we are already on this slot, then there is nothing to do:
        if(_currentSlot==tv) {
            return;
        }

        // First restore the display for the previous target slot if any:
        if(_currentSlot!=null) {
            _currentSlot.setBackgroundResource(R.drawable.hidden_bg);
        }

        // Update the current slot:
        _currentSlot = tv;

        // Add the selection background on the new current slot:
        if(_currentSlot!=null) {
            _currentSlot.setBackgroundResource(R.drawable.selection_border);
        }
    }

    /*
        Method used to assign a value to the current slot.
     */
    public void setCurrentSlotValue(int val) {
        if(_currentSlot!=null) {
            _currentSlot.setText(""+val);
        }
    }

    /*
        Helper method used to add a number button on the interface
     */
    private void addNumberButton(LinearLayout parent, final int val)
    {
        int fSize = 30;
        TextView tv = new TextView(this);
        tv.setTypeface(_bbFont);
        tv.setTextSize(fSize);
        tv.setTextColor(_fontColor);
        tv.setText("" + val);

        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(100, 100);
        llp.setMargins(0,0,(val==4 || val==9) ? 0 : 20,0);
        tv.setLayoutParams(llp);

//        tv.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
//        tv.setWidth(fSize + 10);
//        tv.setPadding(10,10,10,10);
//        tv.setBackgroundColor(Color.rgb(10, 10, 10));
        tv.setBackgroundResource(R.drawable.button_bg);
//        Drawable d = ContextCompat.getDrawable(this, R.drawable.button_bg);
//        tv.setBackground(d);
        parent.addView(tv);

        // Add a click listener:
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(),"Pressed button "+val,Toast.LENGTH_SHORT).show();
                MainActivity activity = (MainActivity)v.getContext();
                activity.setCurrentSlotValue(val);
            }
        });
    }

    /*
        Helper method used to create an operation slot
     */
    private void addOperationSlot(LinearLayout parent, String name, String value, boolean editable) {
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

        // If we are editable then we should add additional behavior:
        if(editable) {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // We should assign this view as the current target slot:
                    MainActivity activity = (MainActivity)v.getContext();
                    activity.setCurrentTargetSlot((TextView)v);
                }
            });
        }
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
