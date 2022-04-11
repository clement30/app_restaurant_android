package fr.isen.rouvier.androidrestaurant

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.rouvier.androidrestaurant.databinding.ActivityBleactivityBinding
import java.util.jar.Manifest

class BLEActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBleactivityBinding

    private val ENABLE_BLUETOOTH_REQUEST_CODE = 1
    private val ALL_PERMISSION_REQUEST_CODE = 100
    private var isScanning = false
    private var adapter: BLEAdapter?= null


    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBleactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when {
            bluetoothAdapter?.isEnabled == true ->
                startLeScanWithPermission(true)
            bluetoothAdapter != null ->
                askBluetoothPermission()
            else -> {
                displayBLEUnAvailable()
            }
        }
        binding.bleScanStateImg.setOnClickListener {
            val test = isScanning
            
            startLeScanWithPermission(!isScanning)
        }
        binding.bleScanStateTitle.setOnClickListener {
            startLeScanWithPermission(!isScanning)
        }


        adapter = BLEAdapter(arrayListOf()){
            val intent = Intent(this, BLEAdapter::class.java)
            intent.putExtra("device", it)
            startActivity(intent)
        }

        binding.deviceList.layoutManager = LinearLayoutManager(this)
        //adapter = BLEAdapter(arrayListOf(), {})
        binding.deviceList.adapter = adapter
    }


    override fun onStop(){
        super.onStop()
        startLeScanWithPermission(false)
    }

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            Log.d("BLEScanActivity", "result : ${result.device.address}, rssi: ${result?.rssi}")
            adapter?.apply {
                addResultToBleList(result)
                notifyDataSetChanged()
            }
        }
    }

    private fun startLeScanWithPermission(enable: Boolean){
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        ){
            startLeScanBLE(enable)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION
                /*Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_SCAN*/
            ), ALL_PERMISSION_REQUEST_CODE)
        }

    }

    @SuppressLint("MissingPermission")
    private fun startLeScanBLE(enable: Boolean) {

        bluetoothAdapter?.bluetoothLeScanner?.apply {
            if(enable) {
                isScanning = true
                startScan(scanCallback)
            } else {
                isScanning = false
                stopScan(scanCallback)
            }
            handlePlayPauseAction()
        }
    }

    private fun displayBLEUnAvailable() {

        binding.bleScanStateImg.isVisible = false
        binding.bleScanStateTitle.text = getString(R.string.ble_scan_device_error)
        binding.bleScanProgression.isVisible = false
    }

    private fun askBluetoothPermission(){
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        if(ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_REQUEST_CODE)
        }
    }

    private fun handlePlayPauseAction() {

        if(isScanning) {
            binding.bleScanStateImg.setImageResource(R.drawable.icon_pause)
            binding.bleScanStateTitle.text = getString(R.string.ble_scan_pause)
            binding.bleScanProgression.isIndeterminate = true
        }
        else {
            binding.bleScanStateImg.setImageResource(R.drawable.icon_play)
            binding.bleScanStateTitle.text = getString(R.string.ble_scan_play)
            binding.bleScanProgression.isIndeterminate = false
        }
    }

    companion object {
        private const val ENABLE_BLUETOOTH_REQUEST_CODE = 1
        private const val ALL_PERMISSIONS_REQUEST =100
        const val DEVICE_KEY = "device"
    }
}