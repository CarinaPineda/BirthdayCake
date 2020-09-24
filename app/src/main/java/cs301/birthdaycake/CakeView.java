package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

public class CakeView extends SurfaceView implements View.OnTouchListener {

    private CakeModel cake;
    private int touchX;
    private int touchY;
    private boolean balloon;

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();
    Paint balloonPaint = new Paint();
    Paint stringPaint = new Paint();


    /* These constants define the dimensions of the cake.  While defining constants for things
        like this is good practice, we could be calculating these better by detecting
        and adapting to different tablets' screen sizes and resolutions.  I've deliberately
        stuck with hard-coded values here to ease the introduction for CS371 students.
     */
    public static final float cakeTop = 400.0f;
    public static final float cakeLeft = 100.0f;
    public static final float cakeWidth = 1200.0f;
    public static final float layerHeight = 200.0f;
    public static final float frostHeight = 50.0f;
    public static final float candleHeight = 300.0f;
    public static final float candleWidth = 40.0f;
    public static final float wickHeight = 30.0f;
    public static final float wickWidth = 6.0f;
    public static final float outerFlameRadius = 30.0f;
    public static final float innerFlameRadius = 15.0f;


    /**
     * ctor must be overridden here as per standard Java inheritance practice.  We need it
     * anyway to initialize the member variables
     */
    public CakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        cake = new CakeModel();
        //This is essential or your onDraw method won't get called
        setWillNotDraw(false);

        //Setup our palette
        cakePaint.setColor(0xFFC755B5);  //violet-red
        cakePaint.setStyle(Paint.Style.FILL);
        frostingPaint.setColor(0xFFFFFACD);  //pale yellow
        frostingPaint.setStyle(Paint.Style.FILL);
        candlePaint.setColor(0xFF32CD32);  //lime green
        candlePaint.setStyle(Paint.Style.FILL);
        outerFlamePaint.setColor(0xFFFFD700);  //gold yellow
        outerFlamePaint.setStyle(Paint.Style.FILL);
        innerFlamePaint.setColor(0xFFFFA500);  //orange
        innerFlamePaint.setStyle(Paint.Style.FILL);
        wickPaint.setColor(Color.BLACK);
        wickPaint.setStyle(Paint.Style.FILL);
        balloonPaint.setColor(Color.BLUE);
        stringPaint.setColor(Color.BLACK);
        stringPaint.setStyle(Paint.Style.STROKE);
        stringPaint.setStrokeWidth(5.0f);


        setBackgroundColor(Color.WHITE);  //better than black default

        this.setOnTouchListener(this);

    }

    /**
     * draws a candle at a specified position.  Important:  the left, bottom coordinates specify
     * the position of the bottom left corner of the candle
     */
    public void drawCandle(Canvas canvas, float left, float bottom) {
        if (cake.hasCandle) {
            canvas.drawRect(left, bottom - candleHeight, left + candleWidth, bottom, candlePaint);
            if (cake.candleLit) {
                //draw the outer flame
                float flameCenterX = left + candleWidth / 2;
                float flameCenterY = bottom - wickHeight - candleHeight - outerFlameRadius / 3;
                canvas.drawCircle(flameCenterX, flameCenterY, outerFlameRadius, outerFlamePaint);

                //draw the inner flame
                flameCenterY += outerFlameRadius / 3;
                canvas.drawCircle(flameCenterX, flameCenterY, innerFlameRadius, innerFlamePaint);

                //draw the wick
                float wickLeft = left + candleWidth / 2 - wickWidth / 2;
                float wickTop = bottom - wickHeight - candleHeight;
                canvas.drawRect(wickLeft, wickTop, wickLeft + wickWidth, wickTop + wickHeight, wickPaint);
            }
        }
    }


        /**
         * onDraw is like "paint" in a regular Java program.  While a Canvas is
         * conceptually similar to a Graphics in javax.swing, the implementation has
         * many subtle differences.  Show care and read the documentation.
         *
         * This method will draw a birthday cake
         */
        @Override
        public void onDraw(Canvas canvas){

            //top and bottom are used to keep a running tally as we progress down the cake layers
            float top = cakeTop;
            float bottom = cakeTop + frostHeight;

            //Frosting on top
            canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
            top += frostHeight;
            bottom += layerHeight;

            //Then a cake layer
            canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);
            top += layerHeight;
            bottom += frostHeight;

            //Then a second frosting layer
            canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, frostingPaint);
            top += frostHeight;
            bottom += layerHeight;

            //Then a second cake layer
            canvas.drawRect(cakeLeft, top, cakeLeft + cakeWidth, bottom, cakePaint);

            //Now a candle in the center
            for(int i=1; i<= cake.candleCount; i++) {
                drawCandle(canvas, cakeLeft + i*cakeWidth/6, cakeTop);
                //drawCandle(canvas, cakeLeft + cakeWidth / 4 - candleWidth / 4, cakeTop);
            }

            //draw a balloon if the boolean balloon is true
            if (balloon) {
                drawBalloon(canvas, touchX, touchY);
            }

        }//onDraw

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            //when a touch event happens on this view record the position of the event and
            //set the boolean balloon variable to true so the onDraw method knows to draw the balloon
            touchX =  (int) event.getX();
            touchY = (int) event.getY();
            balloon = true;
            this.invalidate();
            return true;
        }

         //create the balloon
        public void drawBalloon(Canvas canvas, float left, float top) {
            //draw the oval and string for the balloon;
            canvas.drawOval(left, top, left+60.0f, top-75.0f, balloonPaint);
            canvas.drawLine(left+30.0f, top, left+30.0f, top+90.0f, stringPaint);
        }


        public CakeModel getCakeModel () {
            return this.cake;
        }

    }//class CakeView


