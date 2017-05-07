package com.example.noahtager.draganddropredo;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView  txt1, txt2, txt3, txt4;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = (ImageView) findViewById(R.id.ImageView);
        txt2 = (ImageView) findViewById(R.id.ImageView2);
        txt3 = (ImageView) findViewById(R.id.ImageView3);
        txt4 = (ImageView) findViewById(R.id.target);

        txt1.setOnLongClickListener(longClickListener);
        txt2.setOnLongClickListener(longClickListener);
        txt3.setOnLongClickListener(longClickListener);

        txt4.setOnDragListener(dragListener);



    }

    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("","");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(data, myShadowBuilder, v, 0);
                return true;
            } else {
                v.startDrag(data, myShadowBuilder, v, 0);
                return true;
            }

        }

    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();
            final View thisView = (View) event.getLocalState();
            switch (dragEvent){
                case DragEvent.ACTION_DRAG_STARTED:
                    //Log.d("string", "go");
                    final View view = (View) event.getLocalState();
                    if (view.getId() == R.id.ImageView) {
                        // txt4.setText("TextView is dragged");
                        Log.d("Item you're trying on", "Coin Hoodie Black");
                    } else if (view.getId() == R.id.ImageView2) {
                        Log.d("Item you're trying on", "Coin Tee Black");
                    } else if (view.getId() == R.id.ImageView3) {
                        Log.d("Item you're trying on", "Coin Hoodie Grey");
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    //Log.d("drop", "beendropped");
                   // ImageView dropTarget = (ImageView) v;
                    View v2 = (View) event.getLocalState();
                    //Log.d("v", v.toString());
                    //Log.d("v2", v2.toString());
                    v2.setVisibility(View.INVISIBLE);
                    break;

            }

            return true;

        }


    };
    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            txt4.setImageBitmap(imageBitmap);
        }
    }
}
