package com.example.sumwb.mmmusicplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by sumwb on 2016-09-19.
 */
public class PlayMain extends AppCompatActivity {
    private SeekBar seekBar;
    private TextView playing;
    MainActivity ma = new MainActivity();
    Button play2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicplaying);

        seekBar = (SeekBar) findViewById(R.id.seekbar);
        playing = (TextView) findViewById(R.id.time);
        play2 = (Button) findViewById(R.id.play2);

        play2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ma.playSong(ma.MEDIA_PATH);
            }
        });

        //seekbar를 눌러서 재생 위치 변경
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    ma.mp.seekTo(progress);
                }
                int m = progress / 60000;
                int s = (progress % 60000) / 1000;
                String strTime = String.format("%02d:%02d", m, s);
                playing.setText(strTime);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}
