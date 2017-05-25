package com.it.save.saveit.notas.texto.edit.text;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Jorge on 24/05/2017.
 */

public class EditTextPropio extends android.support.v7.widget.AppCompatEditText
{
    private Paint pincel;
    public EditTextPropio(Context context, AttributeSet attrs)
    {
        super(context,attrs);
        pincel=new Paint();
        pincel.setColor(Color.BLACK);
        pincel.setTextAlign(Paint.Align.RIGHT);
        pincel.setTextSize(28);
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        Rect rect= new Rect();
        for(int i=0;i<getLineCount();i++)
        {
            int lineBase=getLineBounds(i,rect);
            canvas.drawLine(rect.left,lineBase+2,rect.right,lineBase+2,pincel);
            canvas.drawText(""+(i+1),getWidth()-2,lineBase,pincel);
        }
        super.onDraw(canvas);
    }
}

