package cs301.birthdaycake;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;

public class CakeController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {
    private CakeView cv;
    private CakeModel cm;

    public CakeController(CakeView cv) {
        this.cv = cv;
        this.cm = cv.getCakeModel();
    }

    @Override
    public void onClick(View view) {
        Log.d("Button", "Receiving Clicks");
        cm.candleLit = false;
        cv.invalidate();

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            cm.hasCandle = true;
        } else {
            cm.hasCandle = false;
        }

        cv.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b) {
            cm.candleCount = i;
            cv.invalidate();
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        cm.xCoord = (int) event.getX();
        cm.yCoord = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }


        return false;
    }


/*
1.	Your onClick() method should modify the model to indicate that the candles are not lit.
            2.	Call the invalidate() method on the CakeView object to let it know that it needs to redraw itself.
            3.	Modify the drawCandle() method in the CakeView class to so that it will not draw the candle flame if the model indicates they should not be lit.
*/

}

