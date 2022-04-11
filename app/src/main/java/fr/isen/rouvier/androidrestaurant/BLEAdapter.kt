package fr.isen.rouvier.androidrestaurant

import android.annotation.SuppressLint
import android.bluetooth.le.ScanResult

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BLEAdapter(val bleList: ArrayList<ScanResult>, val mListener: (ScanResult) -> Unit) : RecyclerView.Adapter<BLEAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var adresse: TextView = view.findViewById(R.id.addressText)
        var name: TextView = view.findViewById(R.id.nameText)
        var rssi: TextView = view.findViewById(R.id.rssiText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val bleView =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_ble_service, parent, false)
        return MyViewHolder(bleView)
    }

    @SuppressLint("MissingPermission")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i("XXX", "onBindViewHolder")
        val result = bleList[position]
        holder.adresse.text = result.device.address
        holder.name.text = result.device.name
        holder.rssi.text = result.rssi.toString()

        holder.itemView.setOnClickListener {
            mListener(result)
        }
    }

    fun addElement(result: ScanResult) {
        bleList.add(result)
    }

    fun addResultToBleList(scanResult: ScanResult){
        val indexOfResult = bleList.indexOfFirst {
            it.device.address == scanResult.device.address
        }
        if (indexOfResult != -1) {
            bleList[indexOfResult] = scanResult
            notifyItemChanged(indexOfResult)
        } else {
            bleList.add(scanResult)
            //notifyItemIserted(bleList.size)
        }
    }

    override fun getItemCount(): Int {
        return bleList.size
    }
}