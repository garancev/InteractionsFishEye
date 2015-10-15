package ihm.garance.fisheye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    FishEyeView myFisheye;
    private SeekBar seekBarO;
    private double o = 100;
    private SeekBar seekBarZ;
    private double z = 600;
    private SeekBar seekBarR;
    private double r = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myFisheye = (FishEyeView)findViewById(R.id.fisheyeView);
        setSeekBars();
    }
    private void setSeekBars() {
        seekBarO = (SeekBar) findViewById(R.id.seekBarO);
        seekBarO.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                o = progress;
                myFisheye.deformPoints(o, r, z);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {            }
        });

        seekBarR = (SeekBar) findViewById(R.id.seekBarR);
        seekBarR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                r = progress;
                myFisheye.deformPoints(o, r, z);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {            }
        });
        seekBarZ = (SeekBar) findViewById(R.id.seekBarZ);
        seekBarZ.setOnSeekBarChangeListener((new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                z = progress;
                myFisheye.deformPoints(o, r, z);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        }));
    }

    public void tryFishEye(View view) {
        System.out.println("woo tas clique");
        myFisheye.deformPoints(o, r, z);
    }

    public void resetFish(View view) {
        System.out.println("t'es pas drôle :( ");
        myFisheye.resetPoints();
    }

}
