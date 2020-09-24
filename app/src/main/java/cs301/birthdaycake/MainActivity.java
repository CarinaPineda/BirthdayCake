package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private Button blowout;
    private SeekBar seekbar;
    private LinearLayout verticalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        CakeView cv = findViewById(R.id.cakeview);
        CakeController cc = new CakeController(cv);
        cv.setOnTouchListener(cc);

      blowout = findViewById(R.id.button_blowout);
      blowout.setOnClickListener(cc);

      Switch candles = findViewById(R.id.switch1);
      candles.setOnCheckedChangeListener(cc);

        seekbar = findViewById(R.id.seekBar);
        seekbar.setOnSeekBarChangeListener(cc);

        verticalLayout = findViewById(R.id.topVerticalLayout);
        verticalLayout.setOnClickListener(cc);

    }
    public void goodbye(View button) {
        Log.i("button", "Goodbye");

    }

}
