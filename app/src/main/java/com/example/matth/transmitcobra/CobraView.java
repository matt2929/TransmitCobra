package com.example.matth.transmitcobra;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Matth on 7/10/2017.
 */

public class CobraView extends RelativeLayout {

    private Paint colorBlack, colorWhite, colorRed, colorBlue, colorGreen;
    private ArrayList<String> colorList = new ArrayList<>();
    private Integer blockSize, blockWidth, blockHeight;
    private SaveValues saveValues;

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
        blockSize = 24;
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
        saveValues = new SaveValues(getBlockSize(), getBlockWidth(), getBlockHeight());
        saveValues.saveBarCode(this.getContext(), getColorList());
    }


    private Canvas drawMat(int startPoint, int blockSize, Canvas canvas) {
        Paint[] paints = new Paint[]{colorWhite, colorGreen, colorRed, colorBlue};
        Random random = new Random();
        int countY = 0;

        for (int y = blockSize * startPoint; y < canvas.getHeight() - (blockSize * (startPoint)); y += blockSize) {
            countY++;
            int countX = 0;
            for (int x = blockSize * startPoint; x < canvas.getWidth() - (blockSize * (startPoint + 1)); x += blockSize) {
                countX++;
                Paint color = paints[random.nextInt(4)];
                canvas.drawRect(x, y, x + blockSize, y + blockSize, color);
                switch (color.getColor()) {
                    case Color.WHITE:
                        colorList.add("White");
                        break;
                    case Color.RED:
                        colorList.add("Red");
                        break;
                    case Color.GREEN:
                        colorList.add("Green");
                        break;
                    case Color.BLUE:
                        colorList.add("Blue");
                        break;
                }

            }
            blockWidth = countX;
        }
        blockHeight = countY;
        return canvas;
    }

    public Integer getBlockHeight() {
        return blockHeight;
    }

    public Integer getBlockWidth() {
        return blockWidth;
    }

    private Canvas drawTiming(int startPoint, int blockSize, Canvas canvas) {
        for (int i = blockSize * startPoint; i < canvas.getWidth() - blockSize * startPoint; i += blockSize * 2) {
            canvas.drawRect(i, 2 * blockSize, i + blockSize, 3 * blockSize, colorBlack);
            canvas.drawRect(i, getHeight() - 2 * blockSize, i + blockSize, getHeight() - (3 * blockSize), colorBlack);
        }

        for (int i = blockSize * startPoint; i < canvas.getHeight() - blockSize * startPoint; i += blockSize * 2) {
            canvas.drawRect(2 * blockSize, i + blockSize, 3 * blockSize, i, colorBlack);
            canvas.drawRect(getWidth() - 2 * blockSize, i + blockSize, getWidth() - (3 * blockSize), i, colorBlack);
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

    public Integer getBlockSize() {
        return blockSize;
    }
}
