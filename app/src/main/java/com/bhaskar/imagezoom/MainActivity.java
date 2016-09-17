package com.bhaskar.imagezoom;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    ImageView ivGanesh,ivMagnifiedGlass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivGanesh = (ImageView) findViewById(R.id.ivGanesh);
        ivMagnifiedGlass = (ImageView) findViewById(R.id.ivMagnifiedGlass);
        final int windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        final int windowheight = getWindowManager().getDefaultDisplay().getHeight();

        ivGanesh.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivMagnifiedGlass.getLayoutParams();
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        ivMagnifiedGlass.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x_cord = (int)event.getRawX();
                        int y_cord = (int)event.getRawY();

                        if(x_cord>windowwidth){x_cord=windowwidth;}
                        if(y_cord>windowheight){y_cord=windowheight;}

                        layoutParams.leftMargin = x_cord -25;
                        layoutParams.topMargin = y_cord - 75;

                        ivMagnifiedGlass.setLayoutParams(layoutParams);
                        cropImage(x_cord,y_cord);
                        break;
                    case MotionEvent.ACTION_UP:
                        ivMagnifiedGlass.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void cropImage(int x,int y){
        BitmapDrawable drawable = (BitmapDrawable) ivGanesh.getDrawable();
        Bitmap bitmapOrg = drawable.getBitmap();

        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();

        int newWidth = 500;
        int newHeight = 500;

/*// calculate the scale - in this case = 0.4f
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;*/

// createa matrix for the manipulation
        Matrix matrix = new Matrix();
// resize the bit map
        matrix.postScale(2.0f, 2.0f);

// recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, x, y,
                newWidth, newHeight, matrix, true);

// make a Drawable from Bitmap to allow to set the BitMap
// to the ImageView, ImageButton or what ever
        BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);

        //ImageView imageView = new ImageView(this);

// set the Drawable on the ImageView
        ivMagnifiedGlass.setImageDrawable(bmd);
        //resizedBitmap.recycle();
        //bitmapOrg.recycle();
    }
}
