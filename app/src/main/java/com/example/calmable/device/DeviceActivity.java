package com.example.calmable.device;


import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.calmable.R;
import com.example.calmable.SampleApplication;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceActivity extends AppCompatActivity {


    public static int finalRate;
    boolean stopThread = false;

    private static final String TAG = "DeviceActivity";
    public static final String DEVICE_MACADDR = "device_macaddr";

    ProgressDialog mProgressDialog;
    CRPBleClient mBleClient;
    CRPBleDevice mBleDevice;
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


    private Button button2;

    private String bandFirmwareVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);

        imgConnect = findViewById(R.id.imgConnect);
        imgDisconnect = findViewById(R.id.imgDisconnect);

        button2 = findViewById(R.id.btn_start_measure_heart_rate);

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
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBleDevice != null) {
            mBleDevice.disconnect();
        }
    }


    void connect() {
        mProgressDialog.show();
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
                        //imgConnect.setVisibility(View.VISIBLE);
                        testSet();
                        break;
                    case CRPBleConnectionStateListener.STATE_CONNECTING:
                        state = R.string.state_connecting;
                        break;
                    case CRPBleConnectionStateListener.STATE_DISCONNECTED:
                        state = R.string.state_disconnected;
                        mProgressDialog.dismiss();
                        updateTextView(btnBleDisconnect, getString(R.string.connect));
                        tvConnectState.setTextColor(Color.RED);
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
//                Log.d(TAG, "on24HourMeasureResult: started " );
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

        Log.d(TAG, "onPause: --- okay");

        //stopThread = false;
        //ExampleRunnable runnable = new ExampleRunnable();
        //new Thread(runnable).start();


    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ---> okay");

        mBleConnection.syncTime();
        Log.d(TAG, "Measuring : started ");
    }

    class ExampleRunnable implements Runnable {

        @Override
        public void run() {

            mBleConnection.startMeasureDynamicRate();


            for (int q = 0; q >= 0; q++) {

                Log.d(TAG, "run: " + q + " = " + finalRate);

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
            Log.d(TAG, "onMeasuring: " + rate);
            //finalRate = rate;
            updateTextView(tvHeartRate, String.format(getString(R.string.heart_rate), rate));

            //To save
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.calmable", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("heartRate", rate);
            editor.commit();
        }

        @Override
        public void onOnceMeasureComplete(int rate) {
            finalRate = rate;
            Log.d(TAG, "onOnceMeasureComplete: " + rate);


        }

        @Override
        public void onMeasureComplete(CRPHeartRateInfo info) {
            if (info != null && info.getMeasureData() != null) {
                for (Integer integer : info.getMeasureData()) {
                    Log.d(TAG, "onMeasureComplete XXX : " + integer);
                }
            }
        }

        @Override
        public void on24HourMeasureResult(CRPHeartRateInfo info) {
            List<Integer> data = info.getMeasureData();
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
            Log.d(TAG, "onTransCpmplete");
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
    }


}