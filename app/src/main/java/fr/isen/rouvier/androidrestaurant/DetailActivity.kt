package fr.isen.rouvier.androidrestaurant

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import fr.isen.rouvier.androidrestaurant.databinding.ActivityDetailBinding
import fr.isen.rouvier.androidrestaurant.model.Cart
import fr.isen.rouvier.androidrestaurant.model.Item
import fr.isen.rouvier.androidrestaurant.model.ItemCart

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        var quantity = 1

        val item = intent.getSerializableExtra(CategoryActivity.ITEM_KEY) as Item

        binding.foodTitle.text = item.name_fr
        binding.detailsFood.text = item.ingredients.joinToString(", ", transform = { it.name_fr })
        binding.buttonPrice.text = "Ajouter au panier : "+item.prices[0].price + "€"

        binding.buttonPLus.setOnClickListener{
            quantity++
            Log.i("quantity",quantity.toString())
            display(quantity,quantity*item.prices[0].price.toFloat())

        }
        binding.buttonMine.setOnClickListener{
            if(quantity!=1) {
                quantity--
            }
            display(quantity,quantity*item.prices[0].price.toFloat())
        }

        /*binding.buttonPrice.setOnClickListener{

            ShoppingCart.updateCart(ItemCart(item.images[0],item.name_fr,quantity,item.prices[0].price.toFloat()),this)
            setupBadge()
            Snackbar.make(binding.root,"$quantity ${item.name_fr} bien ajouté au panier", Snackbar.LENGTH_SHORT ).show()
        }*/

        binding.detailSlider.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.detailSlider.adapter = CarouselAdapter(this,item.images)

        //binding.detailSlider.adapter = carouselAdapter




    }
    @SuppressLint("SetTextI18n")
    private fun display(quantity : Int, price : Float) {
        binding.itemQuantity.text = quantity.toString()
        binding.buttonPrice.text = "Ajouter au panier : " + price.toString() + "€"
    }
}