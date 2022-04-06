package com.example.calmable.device;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crrepa.ble.CRPBleClient;
import com.crrepa.ble.conn.CRPBleConnection;
import com.crrepa.ble.conn.CRPBleDevice;
import com.crrepa.ble.conn.bean.CRPHeartRateInfo;
import com.crrepa.ble.conn.bean.CRPMovementHeartRateInfo;
import com.crrepa.ble.conn.listener.CRPBleConnectionStateListener;
import com.crrepa.ble.conn.listener.CRPBleECGChangeListener;
import com.crrepa.ble.conn.listener.CRPBloodOxygenChangeListener;
import com.crrepa.ble.conn.listener.CRPBloodPressureChangeListener;
import com.crrepa.ble.conn.listener.CRPHeartRateChangeListener;
import com.example.calmable.Home;
import com.example.calmable.MusicPlayer;
import com.example.calmable.R;
import com.example.calmable.SampleApplication;
import com.example.calmable.VideoPlayerActivity;
import com.example.calmable.sample.JsonPlaceHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import a.b.a.J;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeviceActivity extends AppCompatActivity {

    private static final String TAG = "DeviceActivity";
    public static final String DEVICE_MACADDR = "device_macaddr";

    // send HR to server
    ArrayList<Integer> listOFServerHRData;
    ArrayList<String> listOfTxtData;
    ArrayList<String> listOfServerReportData;
    ArrayList<String> listOfTxtReportData;
    public static ArrayList videoReportData=new ArrayList();
    File filNameHeartRate, fileNameServerReportData;
    JsonPlaceHolder jsonPlaceHolder;
    public static ArrayList videoRelaxation_index = new ArrayList();
    ArrayList videoIntervention = new ArrayList();
    public static ArrayList musicRelaxation_index = new ArrayList();
    Retrofit retrofit;
    public static boolean connected = false;
    JSONObject objHRServer;
    HashMap<String, Object> srHashMap;
    public static ArrayList musicIntervention = new ArrayList();

    public static int finalRate;
    public static int measuringHR;
    boolean stopThread = false;
    String timeAndHR, serverData;
    int q;

    ProgressDialog mProgressDialog;
    CRPBleClient mBleClient;
    static CRPBleDevice mBleDevice;
    CRPBleConnection mBleConnection;
    boolean isUpgrade = false;

    @BindView(R.id.tv_connect_state)
    TextView tvConnectState;

    @BindView(R.id.tv_heart_rate)
    TextView tvHeartRate;

    @BindView(R.id.tv_blood_pressure)
    TextView tvBloodPressure;

    @BindView(R.id.btn_ble_connect_state)
    Button btnBleDisconnect;

    @BindView(R.id.tv_blood_oxygen)
    TextView tvBloodOxygen;

    private ImageView imgConnect, imgDisconnect;


    private TextView tvConnectMsg1, tvConnectMsg2;

    private String bandFirmwareVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        imgConnect = findViewById(R.id.imgConnect);
        imgDisconnect = findViewById(R.id.imgDisconnect);
        tvConnectMsg1 = findViewById(R.id.tvConnectMsg1);
        tvConnectMsg2 = findViewById(R.id.tvConnectMsg2);

        //button2 = findViewById(R.id.btn_start_measure_heart_rate);

        ButterKnife.bind(this);
        //initView();
        mProgressDialog = new ProgressDialog(this);
        String macAddr = getIntent().getStringExtra(DEVICE_MACADDR);
        if (TextUtils.isEmpty(macAddr)) {
            finish();
            return;
        }

        mBleClient = SampleApplication.getBleClient(this);
        mBleDevice = mBleClient.getBleDevice(macAddr);
        if (mBleDevice != null && !mBleDevice.isConnected()) {
            connect();
            connected = true;
        }


        // clear server datatxt when activity create
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(getCacheDir() + "/serverData.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.print("");
        writer.close();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBleDevice != null) {
            mBleDevice.disconnect();
        }
    }


    void connect() {
        //mProgressDialog.show();
        mProgressDialog = ProgressDialog.show(this,
                "Connecting Watch", "Please Wait");
        mBleConnection = mBleDevice.connect();
        mBleConnection.setConnectionStateListener(new CRPBleConnectionStateListener() {
            @Override
            public void onConnectionStateChange(int newState) {
                Log.d(TAG, "onConnectionStateChange: " + newState);
                int state = -1;
                switch (newState) {
                    case CRPBleConnectionStateListener.STATE_CONNECTED:
                        state = R.string.state_connected;
                        mProgressDialog.dismiss();
                        updateTextView(btnBleDisconnect, getString(R.string.disconnect));
                        tvConnectState.setTextColor(Color.GREEN);
//                        tvConnectMsg1.setText("Your watch is successfully connected.");
//                        tvConnectMsg2.setText(" Press \'GO HOME\' button and enjoy the CALMable");
                        //imgConnect.setVisibility(View.VISIBLE);
                        testSet();

                        // update heart rate after connect watch
                        stopThread = false;
                        ExampleRunnable runnable = new ExampleRunnable();
                        new Thread(runnable).start();

                        // to send data for the server
                        filNameHeartRate = new File(getCacheDir() + "/serverData.txt");

                        // create file for save final server date
                        fileNameServerReportData = new File(getCacheDir() + "/ServerReportData.txt");

                        startActivity(new Intent(getApplicationContext(), Home.class));

                        break;
                    case CRPBleConnectionStateListener.STATE_CONNECTING:
                        state = R.string.state_connecting;
                        tvConnectMsg1.setText("");
                        tvConnectMsg2.setText("");
                        break;
                    case CRPBleConnectionStateListener.STATE_DISCONNECTED:
                        state = R.string.state_disconnected;
                        mProgressDialog.dismiss();
                        updateTextView(btnBleDisconnect, getString(R.string.connect));
                        tvConnectState.setTextColor(Color.RED);
                        tvConnectMsg1.setText("Your watch is not connected.");
                        tvConnectMsg2.setText("Go back and try again");
                        //imgDisconnect.setVisibility(View.VISIBLE);
                        break;
                }
                updateConnectState(state);
            }
        });

        mBleConnection.setHeartRateChangeListener(mHeartRateChangListener);
        mBleConnection.setBloodPressureChangeListener(mBloodPressureChangeListener);
        mBleConnection.setBloodOxygenChangeListener(mBloodOxygenChangeListener);
        mBleConnection.setECGChangeListener(mECGChangeListener);

    }


    private void testSet() {
        Log.d(TAG, "testSet");
        mBleConnection.syncTime();
    }


    @OnClick(R.id.btn_ble_connect_state)
    public void onConnectStateClick() {
        if (mBleDevice.isConnected()) {
            mBleDevice.disconnect();
        } else {
            mBleDevice.connect();
        }
    }


    @OnClick({
            R.id.btn_start_measure_heart_rate, R.id.btn_stop_measure_heart_rate,
            R.id.btn_start_measure_blood_pressure, R.id.btn_stop_measure_blood_pressure,
            R.id.btn_start_measure_blood_oxygen, R.id.btn_stop_measure_blood_oxygen})

    public void onViewClicked(View view) {
        if (!mBleDevice.isConnected()) {
            return;
        }
        switch (view.getId()) {

            // Measure Heart Rate
            case R.id.btn_start_measure_heart_rate:
                //mBleConnection.startMeasureDynamicRate();
                //mBleConnection.startMeasureOnceHeartRate();
                break;
            case R.id.btn_stop_measure_heart_rate:
                mBleConnection.stopMeasureDynamicRtae();
//                mBleConnection.stopMeasureOnceHeartRate();
                break;

            //  Measure Blood Pressure
            case R.id.btn_start_measure_blood_pressure:
                mBleConnection.startMeasureBloodPressure();
                break;
            case R.id.btn_stop_measure_blood_pressure:
                mBleConnection.stopMeasureBloodPressure();
                break;

            // Measure Blood Oxygen
            case R.id.btn_start_measure_blood_oxygen:
                mBleConnection.startMeasureBloodOxygen();
                break;
            case R.id.btn_stop_measure_blood_oxygen:
                mBleConnection.stopMeasureBloodOxygen();
                break;

            default:
                break;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause:");

//        stopThread = false;
//        ExampleRunnable runnable = new ExampleRunnable();
//        new Thread(runnable).start();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ---> okay");

        //mBleConnection.syncTime();
        //crpHeartRateInfo.getStartMeasureTime();
        Log.d(TAG, "Measuring : started ");
    }

    class ExampleRunnable implements Runnable {

        @Override
        public void run() {

            for (q = 0; q >= 0; q++) {

                mBleConnection.startMeasureOnceHeartRate();
                Log.d(TAG, "run : " + q + " = " + finalRate);

//                timeAndHR = q + " , " + finalRate;
//                //To save
//                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable", 0);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("timeAndHR", timeAndHR);
//                editor.commit();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stopThread(View view) {
        stopThread = true;
    }


    CRPHeartRateChangeListener mHeartRateChangListener = new CRPHeartRateChangeListener() {
        @Override
        public void onMeasuring(int rate) {

            measuringHR = rate;

            Log.d(TAG, q + "s  ,  " + "onMeasuring : " + rate + "bpm");

            //updateTextView(tvHeartRate, String.format(getString(R.string.heart_rate), rate));

            timeAndHR = q + "s  ,  " + "onMeasuring : " + rate + "bpm";

            //To save
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("timeAndHR2", timeAndHR);
            editor.commit();


            listOFServerHRData = new ArrayList<>();
            listOFServerHRData.add(q);
            listOFServerHRData.add(measuringHR);
            Log.d("LISTCHECK", String.valueOf(listOFServerHRData));
            String rating=q+","+measuringHR+",";
            Log.d("Rate",rating);




            if (MusicPlayer.isStarted) {
                musicIntervention.add(rating);
                Log.d("ListMus", String.valueOf(musicIntervention));
                if (musicIntervention.size() == 12) {
                    postMusicIntervention();
                    musicIntervention.clear();
                }
            }

            if (VideoPlayerActivity.isStarted) {
                videoIntervention.add(rating);
//                videoIntervention.remove(0);
                Log.d("ListVid", String.valueOf(videoIntervention));
                if (videoIntervention.size()==12) {
                    Log.d("VideoData", String.valueOf(videoIntervention));
                    postVideoData();
                    videoIntervention.clear();
                }
            }

            // call writeData method
            try {
                writeHRData();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onOnceMeasureComplete(int rate) {
            finalRate = rate;
            Log.d(TAG, q + "s  ,  " + "onOnceMeasureComplete: " + rate + "bpm");

            timeAndHR = q + "s  ,  " + "onOnceMeasureComplete: " + rate + "bpm";

            //To save HR data
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("heartRate", rate);
            editor.putString("timeAndHR", timeAndHR);
            editor.commit();


            // add HR to save for txt
            listOFServerHRData = new ArrayList<>();
            if(videoIntervention.add(finalRate))
                listOFServerHRData.add(finalRate);


            // call writeData method
            try {
                writeHRData();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onMeasureComplete(CRPHeartRateInfo info) {
            if (info != null && info.getMeasureData() != null) {
                for (Integer integer : info.getMeasureData()) {
                    Log.d(TAG, "onMeasureComplete : " + integer);
                }
            }
        }

        @Override
        public void on24HourMeasureResult(CRPHeartRateInfo info) {
            List<Integer> data = info.getMeasureData();
            Log.d(TAG, "on24HourMeasureResult: started ");
            Log.d(TAG, "on24HourMeasureResult: " + data.size());

        }

        @Override
        public void onMovementMeasureResult(List<CRPMovementHeartRateInfo> list) {
            for (CRPMovementHeartRateInfo info : list) {
                if (info != null) {
                    Log.d(TAG, "onMovementMeasureResult: " + info.getStartTime());
                }
            }
        }
    };

    private void postVideoData() {
        Gson gson = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(100, TimeUnit.SECONDS)
//                .readTimeout(100,TimeUnit.SECONDS).build();


        retrofit = new Retrofit.Builder().baseUrl("http://192.168.8.101:5000/")

                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
//
//        Log.d(TAG, "Current Date and Time--->" + currentDateandTime);
        Log.d("VideoInterventionSize", String.valueOf(videoIntervention.size()));
        Log.d("VideoIntervention", String.valueOf(videoIntervention));
        videoIntervention.remove(0);
        videoIntervention.add(11, currentDateandTime);

        JSONArray jsArray = new JSONArray(videoIntervention);
        Log.d("JsonArray", String.valueOf(jsArray));

        Call<Object> call3 = jsonPlaceHolder.PostVideoData(jsArray);
        call3.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                Toast.makeText(getApplicationContext(), "Post Successful", Toast.LENGTH_SHORT).show();

                //Log.d(TAG, "-----onResponse-----: " + response);

                Log.d(TAG, "* video response code : " + response.code());
                Log.d(TAG, "video response message : " + response.message());
                Log.d(TAG, "video Relax index : " + response.body());
//                Log.d(TAG, "video response code : " + response.body().getClass().getSimpleName());

                ArrayList list = new ArrayList();
                list = (ArrayList) response.body();
                Log.d("ListCheck", String.valueOf(list));
                videoReportData.add(response.body());
                Log.d("VideoRel", String.valueOf(videoReportData));
                Log.d("VideoData", String.valueOf(videoIntervention));

                LinkedTreeMap treeMap = (LinkedTreeMap) list.get(0);

                videoRelaxation_index.add(treeMap.get("relaxation"));
                Log.d("VideoIndexList", String.valueOf(videoRelaxation_index));


            }

            //
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed Video Post Relaxation", Toast.LENGTH_SHORT).show();
                Log.d("ErrorVal:Relaxation", String.valueOf(t));
                Log.d(TAG, "onFailure: " + t);

            }
        });
    }

    private void postMusicIntervention() {
        Gson gson = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(100, TimeUnit.SECONDS)
//                .readTimeout(100,TimeUnit.SECONDS).build();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        musicIntervention.remove(0);
        musicIntervention.add(11, currentDateandTime);


        retrofit = new Retrofit.Builder().baseUrl("http://192.168.8.101:5000/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        JSONArray jsArray1 = new JSONArray(musicIntervention);

        Call<Object> call3 = jsonPlaceHolder.PostMusicData(jsArray1);
        call3.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                Toast.makeText(getApplicationContext(), "Post Successful", Toast.LENGTH_SHORT).show();

                //Log.d(TAG, "-----onResponse-----: " + response);

                Log.d(TAG, "* music response code : " + response.code());
                Log.d(TAG, "music response message : " + response.message());
                Log.d(TAG, "music Relax index : " + response.body());
//                Log.d(TAG, "music response code : " + response.body().getClass().getSimpleName());
//                Log.d(TAG, "music response code : " + response.body().getClass().getSimpleName());

                ArrayList list = new ArrayList();
                list = (ArrayList) response.body();
                Log.d("MusicList", String.valueOf(list));
                Log.d("MusicData", String.valueOf(musicIntervention));

                LinkedTreeMap treeMap = (LinkedTreeMap) list.get(0);

                musicRelaxation_index.add(treeMap.get("relaxation"));
                Log.d("MusicIndexList", String.valueOf(musicRelaxation_index));


            }

            //
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed Video Post Relaxation", Toast.LENGTH_SHORT).show();
                Log.d("ErrorVal:Relaxation", String.valueOf(t));
                Log.d(TAG, "onFailure: " + t);

            }
        });
    }


    CRPBloodPressureChangeListener mBloodPressureChangeListener = new CRPBloodPressureChangeListener() {
        @Override
        public void onBloodPressureChange(int sbp, int dbp) {
            Log.d(TAG, "sbp: " + sbp + ",dbp: " + dbp);
            updateTextView(tvBloodPressure,
                    String.format(getString(R.string.blood_pressure), sbp, dbp));
        }
    };


    CRPBloodOxygenChangeListener mBloodOxygenChangeListener = new CRPBloodOxygenChangeListener() {
        @Override
        public void onBloodOxygenChange(int bloodOxygen) {
            updateTextView(tvBloodOxygen,
                    String.format(getString(R.string.blood_oxygen), bloodOxygen));
        }
    };

    CRPBleECGChangeListener mECGChangeListener = new CRPBleECGChangeListener() {
        @Override
        public void onECGChange(int[] ecg) {
            for (int i = 0; i < ecg.length; i++) {
                Log.d(TAG, "ecg: " + ecg[i]);
            }
        }

        @Override
        public void onMeasureComplete() {
            Log.d(TAG, "onMeasureComplete");
        }

        @Override
        public void onTransCpmplete(Date date) {
            Log.d(TAG, "onTransComplete");
        }

        @Override
        public void onCancel() {
            Log.d(TAG, "onCancel");
        }

        @Override
        public void onFail() {
            Log.d(TAG, "onFail");
        }
    };


    private void queryLastMeasureECGData() {
        this.mBleConnection.queryLastMeasureECGData();
    }


    void updateConnectState(final int state) {
        if (state < 0) {
            return;
        }
        updateTextView(tvConnectState, getString(state));
    }

    void updateTextView(final TextView view, final String con) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setText(con);
            }
        });
    }

    public void GoHome(View view) {
        startActivity(new Intent(this, Home.class));
        finish();
    }

    //Server part
    private void shareHRToServer() {

        Gson gson = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(100, TimeUnit.SECONDS)
//                .readTimeout(100,TimeUnit.SECONDS).build();

        retrofit = new Retrofit.Builder().baseUrl("http://192.168.8.101:5000/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        // setting custom timeouts
//        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        client.connectTimeout(15, TimeUnit.SECONDS);
//        client.readTimeout(15, TimeUnit.SECONDS);
//        client.writeTimeout(15, TimeUnit.SECONDS);
//
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl("http://192.168.8.186:5000/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(client.build())
//                    .build();
//        }


        jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        Log.d(TAG, "Current Date and Time--->" + currentDateandTime);
        Log.d("List", String.valueOf(listOfTxtData));

        listOfTxtData.add(11, currentDateandTime);

        JSONArray jsArray = new JSONArray(listOfTxtData);

        //JSONArray jsArray1 = new JSONArray(musicIntervention);

        Log.d("Json", String.valueOf(jsArray));


        Log.d(TAG, "txt file data : " + listOfTxtData);
        Log.d(TAG, "json arr data : " + jsArray);

        //send heart rate to the server
        Call<Object> call3 = jsonPlaceHolder.PostRelaxationData(jsArray);
        call3.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                Toast.makeText(getApplicationContext(), "Post Successful", Toast.LENGTH_SHORT).show();

                //Log.d(TAG, "-----onResponse-----: " + response);

                Log.d(TAG, "HR - response code : " + response.code());
                Log.d(TAG, "HR - response message : " + response.message());
                Log.d(TAG, "HR - Relax index : " + response.body());
//                Log.d(TAG, "response code : " + response.body().getClass().getSimpleName());

                serverData = String.valueOf(response.body());
                Log.d(TAG, "server data string : " + serverData);

                listOfServerReportData = new ArrayList<>();
                listOfServerReportData.add(serverData);

                Log.d(TAG, "onResponse: " + listOfServerReportData);

                try {
                    writeServerReportData();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

            //
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed Post Relaxation", Toast.LENGTH_SHORT).show();
                Log.d("ErrorVal:Relaxation", String.valueOf(t));
                Log.d(TAG, "onFailure: " + t);

            }
        });

//        JSONArray jsonArray1 = new JSONArray(DeviceActivity.musicIntervention);
//        Call<Object> call = jsonPlaceHolder.PostRelaxationData(jsonArray1);
//        call.enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//
//                Toast.makeText(getApplicationContext(), "Post Successful", Toast.LENGTH_SHORT).show();
//
//                //Log.d(TAG, "-----onResponse-----: " + response);
//
//                Log.d(TAG, "* music response code : " + response.code());
//                Log.d(TAG, "music response message : " + response.message());
//                Log.d(TAG, "music Relax index : " + response.body());
////                Log.d(TAG, "music response code : " + response.body().getClass().getSimpleName());
//                ArrayList list = new ArrayList();
//                list = (ArrayList) response.body();
//                Log.d("ArrayListMusic", String.valueOf(list));
//                musicRelaxation_index.add(list.get(0));
//                Log.d("Relaxation Indexes", String.valueOf(musicRelaxation_index));
//
//
//            }
//
//
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Failed Music Post Relaxation", Toast.LENGTH_SHORT).show();
//                Log.d("ErrorVal:Relaxation", String.valueOf(t));
//                Log.d(TAG, "onFailure: " + t);
//            }
//        });


//        /**
//         *   report part
//         */
//        Call<Object> call4 = jsonPlaceHolder.PostReportData(jsArray);
//        call4.enqueue(new Callback<Object>() {
//            @Override
//            public void onResponse(Call<Object> call, Response<Object> response) {
//
//                Toast.makeText(getApplicationContext(), "Post Successful - Report", Toast.LENGTH_SHORT).show();
//
//                //Log.d(TAG, "-----onResponse-----: " + response);
//
//                Log.d(TAG, "RPT - response code : " + response.code());
//                Log.d(TAG, "RPT - response message : " + response.message());
//                Log.d(TAG, "RPT - Relax index : " + response.body());
////                Log.d(TAG, "response code : " + response.body().getClass().getSimpleName());
//
//
//            }
//
//            //
//            @Override
//            public void onFailure(Call<Object> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Failed Post Relaxation", Toast.LENGTH_SHORT).show();
//                Log.d("ErrorVal:Report", String.valueOf(t));
//                Log.d(TAG, "onFailure: " + t);
//
//            }
//        });

    }

    private void shareRptDataToServer() {

        Gson gson = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(100, TimeUnit.SECONDS)
//                .readTimeout(100,TimeUnit.SECONDS).build();


        retrofit = new Retrofit.Builder().baseUrl("http://192.168.8.101:5000/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        // setting custom timeouts
//        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        client.connectTimeout(15, TimeUnit.SECONDS);
//        client.readTimeout(15, TimeUnit.SECONDS);
//        client.writeTimeout(15, TimeUnit.SECONDS);
//
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl("http://192.168.8.186:5000/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(client.build())
//                    .build();
//        }

        jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);

        JSONArray jsArray = new JSONArray(listOfTxtReportData);

        //JSONArray jsArray1 = new JSONArray(musicIntervention);


        Log.d(TAG, "server method report : " + listOfTxtReportData);
        Log.d(TAG, "json arr data : " + jsArray);

        /**
         *   report part
         */
        Log.d("Json List", String.valueOf(listOfTxtReportData));
        Log.d("JsonReport", String.valueOf(jsArray));
        Call<Object> call4 = jsonPlaceHolder.PostReportData(jsArray);
        call4.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

                Toast.makeText(getApplicationContext(), "Post Successful - Report", Toast.LENGTH_SHORT).show();

                //Log.d(TAG, "-----onResponse-----: " + response);

                Log.d(TAG, "RPT - response code : " + response.code());
                Log.d(TAG, "RPT - response message : " + response.message());
                Log.d(TAG, "RPT - Relax index : " + response.body());
//                Log.d(TAG, "response code : " + response.body().getClass().getSimpleName());

            }

            //
            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed Post Relaxation", Toast.LENGTH_SHORT).show();
                Log.d("ErrorVal:Report", String.valueOf(t));
                Log.d(TAG, "onFailure: " + t);

            }
        });

    }

    private void writeHRData() throws FileNotFoundException {

        //Writing data to txt file
        try {
            //File filNameHeartRate = new File(getCacheDir() + "/serverData.txt");
            //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            filNameHeartRate.createNewFile();
            if (!filNameHeartRate.exists()) {
                filNameHeartRate.mkdirs();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(filNameHeartRate, true));
            int size = listOFServerHRData.size();

            writer.newLine();

            for (int i = 0; i < size; i++) {
                writer.write(listOFServerHRData.get(i).toString());
                writer.write(",");

                writer.flush();
                //Toast.makeText(this, "Data has been written to Report File", Toast.LENGTH_SHORT).show();
            }

            writer.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        // write hr to txt - end


        // read txt file server data
        listOfTxtData = new ArrayList<String>();
        int arrSize;

        Scanner s = new Scanner(new File(getCacheDir() + "/serverData.txt"));
        while (s.hasNext()) {
            listOfTxtData.add(s.next());
        }

        arrSize = listOfTxtData.size();
        Log.d(TAG, "txt data ---> : " + listOfTxtData);
        Log.d(TAG, "array size ---> : " + arrSize);
        s.close();


        if (arrSize == 11) {

            shareHRToServer();

        } else if (arrSize >= 11) {
            // clear txt file
            PrintWriter writer;
            try {
                writer = new PrintWriter(getCacheDir() + "/serverData.txt");
                writer.print("");
                writer.close();
            } catch (
                    FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void writeServerReportData() throws FileNotFoundException {

        //Writing data to txt file
        try {
            //File filNameHeartRate = new File(getCacheDir() + "/serverData.txt");
            //File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            fileNameServerReportData.createNewFile();
            if (!fileNameServerReportData.exists()) {
                fileNameServerReportData.mkdirs();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileNameServerReportData, true));
            int size = listOfServerReportData.size();

            writer.newLine();

            for (int i = 0; i < size; i++) {
                writer.write(listOfServerReportData.get(i).toString());
                //writer.write(",");
                writer.flush();
                //Toast.makeText(this, "Data has been written to Report File", Toast.LENGTH_SHORT).show();
            }

            writer.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        // read data
        // read txt file server data
        listOfTxtReportData = new ArrayList<String>();
        int arrSize;

        Scanner s = new Scanner(new File(getCacheDir() + "/ServerReportData.txt"));
        while (s.hasNext()) {
            listOfTxtReportData.add(s.next());
        }

        arrSize = listOfTxtReportData.size();
        Log.d(TAG, "++++ report txt data ++++> : " + listOfTxtReportData);
        Log.d(TAG, "array size ++++> : " + arrSize);
        s.close();


        if (arrSize == 12) {

            shareRptDataToServer();


        } else if (arrSize >= 12) {
            // clear txt file
            PrintWriter writer;
            try {
                writer = new PrintWriter(getCacheDir() + "/ServerReportData.txt");
                writer.print("");
                writer.close();
            } catch (
                    FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}