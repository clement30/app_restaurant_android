package fr.isen.rouvier.androidrestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.rouvier.androidrestaurant.databinding.ActivityHomeBinding



class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("HomeActivity", "onCreate Called")

        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.buttonPlats.setOnClickListener {
            goToCategory("Plats")
        }

        binding.buttonEntrees.setOnClickListener {
            goToCategory("Entrées")
        }

        binding.buttonDeserts.setOnClickListener {
            goToCategory("Desserts")
        }


    }

    private fun goToCategory(category: String) {
        val toast = Toast.makeText(this@HomeActivity, category, Toast.LENGTH_SHORT)
        toast.show()
        val myIntent = Intent(this, CategoryActivity::class.java)
        myIntent.putExtra("category", category)
        startActivity(myIntent)
    }
}