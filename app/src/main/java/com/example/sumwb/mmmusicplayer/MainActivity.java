package com.example.sumwb.mmmusicplayer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public MediaPlayer mp = new MediaPlayer();
    public String[] titleList = null;
    public ArrayList<String> MusicList = null;
    public FilenameFilter filter;        //확장자 확인
    public int currentPosition = 0;    //재생할 곡의 위치
    public String MEDIA_PATH = new String("/storage/sdcard1/Music/");
    public Button play;
    public ImageView album;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musiclist);

        play = (Button) findViewById(R.id.play);
        album = (ImageView) findViewById(R.id.albumImg);

        filter = new FilenameFilter() {
            @Override
            public boolean accept(File file, String name) {
                return name.endsWith(".mp3");
            }
        };
        File file = new File(MEDIA_PATH);
        File[] files = file.listFiles(filter);
        String[] titleList = new String[files.length];

        for (int i = 0; i < files.length; i++) {
            titleList[i] = files[i].getName();
        }
        MusicList = new ArrayList<String>(Arrays.asList(titleList));

        // adapter 만들기
        CustomAdapter adapter = new CustomAdapter(
                getApplicationContext(), // 현재 화면의 제어권자
                R.layout.song_items, // 한행을 담당할 Layout
                MusicList); // 데이터

        ListView lv = (ListView) findViewById(R.id.music_list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(itemClickListner);

        play.setOnClickListener(playButton);

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,PlayMain.class);
                startActivity(intent);
            }
        });
    }

    public View.OnClickListener playButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mp.isPlaying() == false) {
                mp.start();
                play.setBackgroundResource(R.mipmap.pause);
            } else {
                mp.pause();
                play.setBackgroundResource(R.mipmap.play);
            }
        }
    };
    //리스트뷰 아이템을 눌렀을 때
    public AdapterView.OnItemClickListener itemClickListner = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            currentPosition = position;
            playSong(MEDIA_PATH + MusicList.get(position));
        }
    };

    //노래 시작
    public void playSong(String songPath) {
        try {
            mp.reset();
            mp.setDataSource(songPath);
            mp.prepare();
            mp.start();
            play.setBackgroundResource(R.mipmap.pause);

            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nextSong();
                }
            });
        } catch (IOException e) {
        }
    }

    //다음 곡 시작
    public void nextSong() {
        if (++currentPosition >= MusicList.size()) {
            currentPosition = 0;
        } else {
            Toast.makeText(getApplicationContext(), "다음 곡을 재생합니다", Toast.LENGTH_SHORT).show();
            playSong(MEDIA_PATH + MusicList.get(currentPosition));
        }
    }
}
