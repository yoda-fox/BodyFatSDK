package com.scale.bluetoothlibrary.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.util.Log;

import com.scale.bluetoothlibrary.util.StringUtil;

public class ScanUtil {
    private static ScanUtil bluetoothUtil;
    public BluetoothManager mBluetoothManager;
    public BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner scanner;
    private BluetoothSearchListener bluetoothSearchListener;
    private final String TAG = "BluetoothUtil";
    private boolean searchStatus;

    public static ScanUtil getInstance() {
        if (bluetoothUtil == null) {
            bluetoothUtil = new ScanUtil();
        }
        return bluetoothUtil;
    }

    public interface BluetoothSearchListener {
        void onSearchCallback(ScanResult result);
    }

    public void registerBluetoothListener(BluetoothSearchListener bluetoothSearchListener) {
        this.bluetoothSearchListener = bluetoothSearchListener;
    }

    public void init(Context context) {
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        }
        if (mBluetoothAdapter == null) {
            mBluetoothAdapter = mBluetoothManager.getAdapter();
        }
    }

    /**
     * SCAN
     */
    public void searchDevice() {
        if (searchStatus) return;
        if (mBluetoothManager == null || mBluetoothAdapter == null) {
            return;
        }
        scanner = mBluetoothAdapter.getBluetoothLeScanner();
        ScanSettings.Builder settings = new ScanSettings.Builder();
        settings.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
        if (scanner != null) {
            Log.e(TAG, "startScan");
            searchStatus = true;
            scanner.startScan(null, settings.build(), leCallback);
        }
    }

    /**
     * CALLBACK
     */
    private final ScanCallback leCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            String data = StringUtil.bytes2HexString(result.getScanRecord().getBytes());
            if (data.startsWith(BluetoothUtil.COMMON) || data.startsWith(BluetoothUtil.TM)) {
                bluetoothSearchListener.onSearchCallback(result);
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            stopSearchDevice();
            Log.e(TAG, "scanFailed=" + errorCode);
        }
    };

    public void stopSearchDevice() {
        if (scanner != null) {
            scanner.stopScan(leCallback);
            searchStatus = false;
            Log.e(TAG, "stopSearch");
        }
    }
}