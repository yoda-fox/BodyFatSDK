package com.scale.jartest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.util.Log;

import com.scale.bluetoothlibrary.util.StringUtil;

public class BluetoothUtil {
    private static BluetoothUtil bluetoothUtil;
    public BluetoothManager mBluetoothManager;
    public BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner scanner;
    private BluetoothSearchListener bluetoothSearchListener;
    private final String TAG = "BluetoothUtil";
    private boolean searchStatus;

    public static BluetoothUtil getInstance() {
        if (bluetoothUtil == null) {
            bluetoothUtil = new BluetoothUtil();
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
            byte[] scanRecord = result.getScanRecord().getBytes();
            if (StringUtil.bytes2HexString(scanRecord).startsWith("10 FF")) {
                if (scanRecord.length >= 11 && (StringUtil.getBit(scanRecord[10], 0) == 1)) {
                    stopSearchDevice();
                    bluetoothSearchListener.onSearchCallback(result);
                }
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