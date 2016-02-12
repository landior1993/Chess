package com.saphala.chess;

import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.widget.GridLayout;
import android.widget.ImageView;

/**
 * Created by Ronal on 2/12/2016.
 */
public class Initials {
    private ImageView[][] imgArray;
    private String hitam = "#F08512";
    private String putih = "#977C7C";
    private Activity activity;
    private int width;

    Initials(Activity activity){
        imgArray = new ImageView[8][8];
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        imgArray = new ImageView[8][8];
        this.activity = activity;
        startGrid();
    }

    //masukkan imageview ke gridlayout
    public void startGrid(){
        GridLayout gridLayout = (GridLayout)activity.findViewById(R.id.gridChess);
        gridLayout.removeAllViews();
        int total = 64;
        int column = 8;
        int row = total / column;
        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row);
        for (int i = 0;i<8;i++){
            String warna;
            for (int j = 0;j<8;j++){
                if (j % 2 == 0) warna = hitam;
                else warna = putih;

                ImageView imageView = new ImageView(activity);
                imageView.setBackgroundColor(Color.parseColor(warna));
                imageView.setAdjustViewBounds(true);
                imageView.setMinimumWidth(width / 8);
                imageView.setMinimumHeight(width / 8); // pake width supaya rata (persegi)
                imageView.setMaxHeight(width / 8);
                imageView.setMaxWidth(width / 8);

                gridLayout.addView(imageView);
                imgArray[i][j] = imageView;
            }
            String temp = hitam;
            hitam = putih;
            putih = temp;
        }
    }

    public void clearImageGrid(){
        for (int i = 0;i<8;i++){
            for (int j = 0;j<8;j++) imgArray[i][j].setImageDrawable(null);
        }
    }
    public void setImageGrid(int posx, int posy, char bidakCatur){
        ImageView gambar = imgArray[posx][posy];
        if (bidakCatur == 'K') gambar.setImageResource(R.drawable.rajaputih);
        else if (bidakCatur == 'Q') gambar.setImageResource(R.drawable.ratuputih);
        else if (bidakCatur == 'B') gambar.setImageResource(R.drawable.gajahputih);
        else if (bidakCatur == 'N') gambar.setImageResource(R.drawable.kudaputih);
        else if (bidakCatur == 'R') gambar.setImageResource(R.drawable.bentengputih);
        else if (bidakCatur == 'k') gambar.setImageResource(R.drawable.rajahitam);
        else if (bidakCatur == 'q') gambar.setImageResource(R.drawable.ratuhitam);
        else if (bidakCatur == 'b') gambar.setImageResource(R.drawable.gajahhitam);
        else if (bidakCatur == 'n') gambar.setImageResource(R.drawable.kudahitam);
        else if (bidakCatur == 'r') gambar.setImageResource(R.drawable.bentenghitam);
    }
}
