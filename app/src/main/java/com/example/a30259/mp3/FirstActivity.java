package com.example.a30259.mp3;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import static com.example.a30259.mp3.R.styleable.View;

public class FirstActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private MediaPlayer mediaPlayer2 = new MediaPlayer();
    private MediaPlayer mediaPlayer3 = new MediaPlayer();
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Button play = (Button)findViewById(R.id.go);
        Button pause = (Button)findViewById(R.id.pause);
        Button stop = (Button)findViewById(R.id.stop);
        Button next = (Button)findViewById(R.id.next);
        Button last = (Button)findViewById(R.id.last);



        File sdCard = Environment.getExternalStorageDirectory();
        File directory_pictures = new File(sdCard, "a.mp3");
        final String s = directory_pictures.toString()+"";

       //Log.d("FirstActivity",directory_pictures.toString()+"");

       final List a = getFilesAllName("/storage/emulated/0/");
        ArrayAdapter adapter = new ArrayAdapter(FirstActivity.this,android.R.layout.simple_list_item_1,a);
        ListView listView = (ListView)findViewById(R.id.a);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = a.get(i)+"";
                String b = s.substring(s.lastIndexOf("/"));

                File file = new File(Environment.getExternalStorageDirectory(),b);
                try{
                mediaPlayer2.setDataSource(file.getPath());
                mediaPlayer2.prepare();
                }catch (Exception e){
                    Log.d("FirstActivity",e.toString());
                }

                if(!mediaPlayer2.isPlaying()){
                    mediaPlayer2.start();
                }
                count = i;


            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mediaPlayer2.isPlaying()){
                    mediaPlayer2.start();
                    Log.d("FirstActivity",s);
                    Log.d("FirstActivity",a.toString()+"");

                }
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer2.isPlaying()){
                    mediaPlayer2.pause();
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer2.isPlaying()){
                    mediaPlayer2.reset();
                    initMediaPlayer();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                count++;
               
                String b = a.get(count)+"";
                String c = b.substring(s.lastIndexOf("/"));

                File file = new File(Environment.getExternalStorageDirectory(),c);
                try{
                    mediaPlayer2.reset();
                    mediaPlayer2.setDataSource(file.getPath());
                    mediaPlayer2.prepare();
                }catch (Exception e){
                    Log.d("FirstActivity",e.toString());
                }

                if(!mediaPlayer2.isPlaying()){
                    mediaPlayer2.start();
                }



            }
        });

        if(ContextCompat.checkSelfPermission(FirstActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(FirstActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else{
            initMediaPlayer();
        }


    }
    private void initMediaPlayer(){
        try{
            File file = new File(Environment.getExternalStorageDirectory(),"a.mp3");
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        }catch(Exception e){
            Log.d("FirstActivity",e.toString());
        }

    }

    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResult){
        switch (requestCode){
            case 1:
                if(grantResult.length>0&&grantResult[0] == PackageManager.PERMISSION_GRANTED){
                    initMediaPlayer();
                }else{
                    finish();

                }
                break;
            default:

        }
    }
    protected void onDestroy(){
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
    public static List<String> getFilesAllName(String path) {
        File file=new File(path);
        File[] files=file.listFiles();
        if (files == null){Log.e("error","空目录");return null;}
        List<String> s = new ArrayList<>();
        for(int i =0;i<files.length;i++){
            if(bfFind(files[i].getAbsolutePath(),"mp3",0)==1||bfFind(files[i].getAbsolutePath(),"flac",0)==1){
            s.add(files[i].getAbsolutePath());}else{
                continue;
            }
        }
        return s;
    }

    public static int bfFind(String S, String T, int pos) {
        char[] arr1 = S.toCharArray();
        char[] arr2 = T.toCharArray();
        int i = pos;
        int j = 0;
        while(i < arr1.length && j < arr2.length) {
            if(arr1[i] == arr2[j]) {
                i++;
                j++;
            }
            else {
                i = i - j + 1;
                j = 0;
            }
        }
        if(j == arr2.length) return 1;
        else return -1;
    }



}
