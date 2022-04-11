package fr.isen.rouvier.androidrestaurant

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.rouvier.androidrestaurant.databinding.ActivityCartBinding

class CartActivity : CartCompactActivity() {
    private lateinit var binding: ActivityCartBinding


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.listCart.layoutManager = LinearLayoutManager(this)
        /*binding.listCart.adapter = CartAdapter(ShoppingCart.getCart(this)){ itCart ->
            val item  = itCart.copy()
            item.quantity = -1
            //ShoppingCart.updateCart(item,this)

            updatePrice(ShoppingCart.panier.listItem)
            binding.listCart.adapter?.notifyDataSetChanged()

        }*/
        updatePrice(ShoppingCart.panier.listItem)

    }

    @SuppressLint("SetTextI18n")
    fun updatePrice(list : MutableList<ItemCart>){
        if(list.sumOf{(it.price* it.quantity).toDouble()}!=0.0){
            binding.buyButton.text =  "Commander pour ${list.sumOf{(it.price * it.quantity).toDouble()}} €"
        }
        else{
            binding.buyButton.visibility = View.GONE
        }

    }
}