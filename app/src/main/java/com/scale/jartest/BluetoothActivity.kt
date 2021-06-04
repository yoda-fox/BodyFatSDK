package com.scale.jartest

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.scale.bluetoothlibrary.bluetooth.BluetoothUtil
import com.scale.bluetoothlibrary.bluetooth.DeviceConfig
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class BluetoothActivity : AppCompatActivity(), BluetoothUtil.BluetoothSearchListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BluetoothUtil.getInstance().init(this)
        BluetoothUtil.getInstance().registerBluetoothListener(this)
        initBluetooth()
    }

    private fun initBluetooth() {
        if (BluetoothUtil.getInstance().mBluetoothAdapter?.isEnabled!!) {
            Thread {
                BluetoothUtil.getInstance().searchDevice("")
            }.start()

        } else {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent, BluetoothUtil.BLUETOOTH_DISCOVERABLE_RESULT)
        }
    }

    override fun onSearchCallback(deviceBean: DeviceConfig) {
        runOnUiThread {
            tv_weight.text =
                String.format(Locale.getDefault(), "%.2f%s", deviceBean.weight, deviceBean.unit)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == BluetoothUtil.REQUEST_CODE_ACCESS_COARSE_LOCATION) {
            initBluetooth()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == BluetoothUtil.BLUETOOTH_DISCOVERABLE_RESULT && resultCode == Activity.RESULT_OK) {
            initBluetooth()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        BluetoothUtil.getInstance().stopSearchDevice()
    }
}