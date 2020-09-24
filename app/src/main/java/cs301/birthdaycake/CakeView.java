package cs301.birthdaycake;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

public class CakeView extends SurfaceView {

    private CakeModel cake;

    /* These are the paints we'll use to draw the birthday cake below */
    Paint cakePaint = new Paint();
    Paint frostingPaint = new Paint();
    Paint candlePaint = new Paint();
    Paint outerFlamePaint = new Paint();
    Paint innerFlamePaint = new Paint();
    Paint wickPaint = new Paint();
    Paint redPaint = new Paint();
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
    public static final float candleHeight = 150.0f;
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
        redPaint.setColor(Color.RED);
        redPaint.setTextSize(50);
        balloonPaint.setColor(Color.BLUE);
        balloonPaint.setStyle(Paint.Style.FILL);
        stringPaint.setColor(Color.BLACK);
        stringPaint.setStyle(Paint.Style.STROKE);
        stringPaint.setStrokeWidth(5);

        setBackgroundColor(Color.WHITE);  //better than black default

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
        public void paintBalloon(Canvas canvas){
        canvas.drawLine(cake.xCoord, cake.yCoord, cake.xCoord,cake.yCoord+300.0f,stringPaint);
        canvas.drawOval(cake.xCoord -50.0f, cake.yCoord -100.0f, cake.xCoord + 50.0f, cake.yCoord +100.0f, balloonPaint);


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
            canvas.drawText("("+ cake.xCoord +", "+ cake.yCoord+ ")",1550.0f,580.0f,redPaint);
            paintBalloon(canvas);

        }//onDraw

        public CakeModel getCakeModel () {
            return this.cake;
        }

    }//class CakeView

    /*:  Modify the birthday cake app so that it reports the x,y location where a touch occurs.
        You should use the Canvas.drawText() method to “draw” this information in the lower
        right-hand corner of the CakeView canvas in red text large enough to be easily read.
        (Don’t add a new View to your layout.) It is ok to hardcode this coordinate for this
        current purpose*/
