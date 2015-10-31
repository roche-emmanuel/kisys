package org.nervtech.kisys;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by kenshin on 31/10/2015.
 */
public class OperationLine extends View {

    protected Paint _paint;

    public OperationLine(Context context) {
        super(context);

        // Prepare the paint to use for rendering:
        _paint = new Paint();
        _paint.setColor(Color.rgb(250, 250, 250));
        _paint.setAntiAlias(true);
        _paint.setStrokeWidth(10.0f);
        _paint.setStyle(Paint.Style.STROKE);
        _paint.setStrokeJoin(Paint.Join.ROUND);
        _paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Retrieve the canvas width:
        int w = canvas.getWidth();
        int h = canvas.getHeight();
        canvas.drawLine(0.0f,h/2.0f,w,h/2,_paint);
    }
}
