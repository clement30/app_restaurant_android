package fr.isen.rouvier.androidrestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.rouvier.androidrestaurant.databinding.ActivityDetailBinding
import fr.isen.rouvier.androidrestaurant.model.Item

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val item = intent.getSerializableExtra(CategoryActivity.ITEM_KEY) as Item
        //binding.titrePlat.text = item.name_fr

        val carouselAdapter = CarouselAdapter(this, item.images)

        //binding.detailSlider.adapter = carouselAdapter


    }
}