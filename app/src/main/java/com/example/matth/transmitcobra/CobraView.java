package com.example.matth.transmitcobra;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Matth on 7/10/2017.
 */

public class CobraView extends RelativeLayout {

    private Paint colorBlack, colorWhite, colorRed, colorBlue, colorGreen;
    private ArrayList<String> colorList = new ArrayList<>();
    private Integer blockSize;
    private SaveValues saveValues;
    int xC = 0;
    int yC = 0;

    public void initialize() {
        colorBlack = new Paint();
        colorWhite = new Paint();
        colorRed = new Paint();
        colorBlue = new Paint();
        colorGreen = new Paint();
        colorBlue.setColor(Color.BLUE);
        colorRed.setColor(Color.RED);
        colorGreen.setColor(Color.GREEN);
        colorWhite.setColor(Color.WHITE);
        colorBlack.setColor(Color.BLACK);
        blockSize = 16;
        setBackgroundColor(Color.WHITE);
    }

    public CobraView(Context context) {
        super(context);
        initialize();
    }

    public CobraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CobraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int startPoint = 6;
        canvas = drawCorners(blockSize, canvas);
        canvas = drawTiming(startPoint, blockSize, canvas);
        canvas = drawMat(startPoint, blockSize, canvas);
    }


    private Canvas drawMat(int startPoint, int blockSize, Canvas canvas) {
        int startX = blockSize * 6;
        int startY = blockSize * 6;
        for (int i = 0; i < colorList.size(); i++) {
            Paint paint = new Paint();
            switch (colorList.get(i)) {
                case "W":
                    paint.setColor(Color.WHITE);
                    break;
                case "R":
                    paint.setColor(Color.RED);
                    break;
                case "G":
                    paint.setColor(Color.GREEN);
                    break;
                case "B":
                    paint.setColor(Color.BLUE);
                    break;
            }
            canvas.drawRect(startX, startY, startX + blockSize, startY + blockSize, paint);
            startX += blockSize;
            if (startX >= getWidth() - (blockSize * 6)) {
                startY += blockSize;
                startX = blockSize * 6;
            }
        }
        return canvas;
    }

    public void setNewMat(Integer colorBack,String name) {
        setBackgroundColor(colorBack);
        saveValues = new SaveValues(getContext(),name);
        Paint[] paints = new Paint[]{colorWhite, colorGreen, colorRed, colorBlue};
        Random random = new Random();
        colorList.clear();
        for (int y = blockSize * 6; y < getHeight() - (blockSize * 7); y += blockSize) {
            for (int x = blockSize * 6; x < getWidth() - (blockSize * 6); x += blockSize) {
                Paint color = paints[random.nextInt(4)];
                switch (color.getColor()) {
                    case Color.WHITE:
                        colorList.add("W");
                        break;
                    case Color.RED:
                        colorList.add("R");
                        break;
                    case Color.GREEN:
                        colorList.add("G");
                        break;
                    case Color.BLUE:
                        colorList.add("B");
                        break;
                }
                saveValues.saveBarCode(colorList.get(colorList.size() - 1));
            }
        }
        saveValues.close();
        invalidate();
    }

    private Canvas drawTiming(int startPoint, int blockSize, Canvas canvas) {
        for (int i = blockSize * startPoint; i < canvas.getWidth() - blockSize * (startPoint); i += blockSize * 2) {
            canvas.drawRect(i, 0, i + blockSize, 6 * blockSize, colorBlack);
            canvas.drawRect(i, getHeight() - 6 * blockSize, i + blockSize, getHeight(), colorBlack);
        }

        for (int i = blockSize * startPoint; i < canvas.getHeight() - blockSize * startPoint; i += blockSize * 2) {
            canvas.drawRect(0, i , 6 * blockSize, i+blockSize, colorBlack);
            canvas.drawRect(getWidth() - 6 * blockSize, i , getWidth(), i+ blockSize, colorBlack);
        }
        return canvas;
    }

    private Canvas drawCorners(int blockSize, Canvas canvas) {

        int count = 0;
        for (int x = blockSize; x < 4 * blockSize; x += blockSize) {
            for (int y = blockSize; y < 4 * blockSize; y += blockSize) {
                if (count == 4) {
                    canvas.drawRect(x, y, x + blockSize, y + blockSize, colorBlack);
                } else {
                    canvas.drawRect(x, y, x + blockSize, y + blockSize, colorBlue);
                }
                count++;
            }
        }

        count = 0;
        for (int x = canvas.getWidth() - blockSize * 4; x < canvas.getWidth() - blockSize; x += blockSize) {
            for (int y = blockSize; y < 4 * blockSize; y += blockSize) {
                if (count == 4) {
                    canvas.drawRect(x, y, x + blockSize, y + blockSize, colorBlack);
                } else {
                    canvas.drawRect(x, y, x + blockSize, y + blockSize, colorRed);
                }
                count++;
            }
        }

        count = 0;
        for (int x = blockSize; x < 4 * blockSize; x += blockSize) {
            for (int y = canvas.getHeight() - blockSize * 4; y < canvas.getHeight() - blockSize; y += blockSize) {
                if (count == 4) {
                    canvas.drawRect(x, y, x + blockSize, y + blockSize, colorBlack);
                } else {
                    canvas.drawRect(x, y, x + blockSize, y + blockSize, colorGreen);
                }
                count++;
            }
        }

        count = 0;
        for (int x = canvas.getWidth() - blockSize * 4; x < canvas.getWidth() - blockSize; x += blockSize) {
            for (int y = canvas.getHeight() - blockSize * 4; y < canvas.getHeight() - blockSize; y += blockSize) {
                if (count == 4) {
                    canvas.drawRect(x, y, x + blockSize, y + blockSize, colorBlack);
                } else {
                    canvas.drawRect(x, y, x + blockSize, y + blockSize, colorBlue);
                }
                count++;
            }
        }
        return canvas;
    }

    public ArrayList<String> getColorList() {
        return colorList;
    }

    public int getxC() {
        return xC;
    }

    public int getyC() {
        return yC;
    }

    public Integer getBlockSize() {
        return blockSize;
    }


}
