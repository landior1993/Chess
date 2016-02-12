package com.saphala.chess;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static final String SERVER_NAME = "xinuc.org";
    public static final String SERVER_PORT = "7387";
    private int jml = 0;
    private ClientAsyncTask client;
    private Initials init;
    private HashMap<Character,Integer>map = new HashMap<>();
    private HashMap<Integer,Integer> hashTinggi = new HashMap<>();
    private TextView textView,textviewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textview);
        textviewInfo = (TextView) findViewById(R.id.textInfo);
        textviewInfo.setVisibility(View.GONE);
        if (CheckInternet.isNetworkAvailable(this)) {
            client = new ClientAsyncTask();
            String [] tcpDetails = new String[]{SERVER_NAME, SERVER_PORT};
            client.execute(tcpDetails);
            textviewInfo.setVisibility(View.VISIBLE);
        } else {
            textviewInfo.setVisibility(View.GONE);
            textView.setText("NO INTERNET!");
        }

        // masukin imageview ke gridlayout
        init = new Initials(this);

        //buat posisi di arrayimage
        int tambah = 7;
        for (int i = 97;i<=104;i++){
            char s = (char)i;
            map.put(s, i - 97);
            hashTinggi.put(i-96,tambah--);
        }

    }

    @Override
    protected void onDestroy() {
        if (client !=null) client.cancel(true); //tutup socket waktu user keluar dari app
        super.onDestroy();
    }

    public void updateText(final String s){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                init.clearImageGrid();
                textView.setText(s);
                for (int i = 0;i<s.length();i+=4){
                    char pos1 = s.charAt(i + 1);
                    int panjangBawah = map.get(pos1);
                    int tinggiAtas = s.charAt(i+2);
                    tinggiAtas-=48;

                    // update UI
                    if (s.charAt(i) == 'K') init.setImageGrid(hashTinggi.get(tinggiAtas),panjangBawah,'K');
                    else if (s.charAt(i) == 'Q') init.setImageGrid(hashTinggi.get(tinggiAtas),panjangBawah,'Q');
                    else if (s.charAt(i) == 'B') init.setImageGrid(hashTinggi.get(tinggiAtas),panjangBawah,'B');
                    else if (s.charAt(i) == 'N') init.setImageGrid(hashTinggi.get(tinggiAtas),panjangBawah,'N');
                    else if (s.charAt(i) == 'R') init.setImageGrid(hashTinggi.get(tinggiAtas),panjangBawah,'R');
                    else if (s.charAt(i) == 'k') init.setImageGrid(hashTinggi.get(tinggiAtas),panjangBawah,'k');
                    else if (s.charAt(i) == 'q') init.setImageGrid(hashTinggi.get(tinggiAtas),panjangBawah,'q');
                    else if (s.charAt(i) == 'b') init.setImageGrid(hashTinggi.get(tinggiAtas),panjangBawah,'b');
                    else if (s.charAt(i) == 'n') init.setImageGrid(hashTinggi.get(tinggiAtas),panjangBawah,'n');
                    else if (s.charAt(i) == 'r') init.setImageGrid(hashTinggi.get(tinggiAtas),panjangBawah,'r');

                }
            }
        });
    }

    class ClientAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                Socket socket = new Socket(params[0], Integer.parseInt(params[1]));
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                result = br.readLine();
                while (result != null && !client.isCancelled()){
                    updateText(result);
                    System.out.println(jml++ + " " + result);
                    result = br.readLine();
                }
                socket.close();
            } catch (UnknownHostException | NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            return result;
        }
    }
}
