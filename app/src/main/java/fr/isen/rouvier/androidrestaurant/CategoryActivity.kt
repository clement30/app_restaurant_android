package fr.isen.rouvier.androidrestaurant

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.rouvier.androidrestaurant.databinding.ActivityCategoryBinding
import fr.isen.rouvier.androidrestaurant.model.DataResult
import org.json.JSONObject

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryName = intent.getStringExtra("category") ?: ""
        //binding.app_name.text = categoryName

        apiCall(categoryName)
    }

    private fun apiCall(category: String) {
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val queue = Volley.newRequestQueue(this)

        val jsonObject = JSONObject()
        jsonObject.put("id_shop", 1)

        val stringReq = JsonObjectRequest(

            Request.Method.POST, url, jsonObject,
            { response ->
                        val strResp = response.toString()
                        val dataResult = Gson().fromJson(strResp, DataResult::class.java)
                        Log.d("API", strResp)

                        val items = dataResult.data.firstOrNull { it.name_fr == category }?.items ?: arrayListOf()
                        binding.categoryList.adapter = CategoryAdapter(items) {
                            val intent = Intent(this@CategoryActivity, DetailActivity::class.java)
                            intent.putExtra(ITEM_KEY, it)
                            startActivity(intent)
                        }
            },{
                Log.d("API", "message ${it.message}")
            })
        queue.add(stringReq)
    }

    companion object{
        val ITEM_KEY = "item"
    }



}