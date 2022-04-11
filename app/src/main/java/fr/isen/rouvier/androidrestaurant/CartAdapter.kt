package fr.isen.rouvier.androidrestaurant

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.rouvier.androidrestaurant.databinding.ActivityCartBinding
import fr.isen.rouvier.androidrestaurant.databinding.CellCartBinding
import fr.isen.rouvier.androidrestaurant.model.Cart
import fr.isen.rouvier.androidrestaurant.model.ItemCart

class CartAdapter(private val cart: fr.isen.rouvier.androidrestaurant.Cart, private val mListener: (ItemCart) -> Unit) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private lateinit var binding: CellCartBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = CellCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //return holder.display(cart.listItem[position])
    }

    override fun getItemCount(): Int {
        return cart.listItem.size
    }

    class ViewHolder(private val binding: CellCartBinding,private val mListener: (ItemCart) -> Unit): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun display(item : ItemCart){
            binding.detailsPrice.text = "${item.price} € * ${item.quantity} = ${item.price*item.quantity.toFloat()} €"
            binding.idDish.text = item.name
            Picasso.get().load(item.image.ifEmpty { null })
                .placeholder(R.drawable.home_picture)
                .error(R.drawable.plat)
                .into(binding.imDish)
            binding.btReduce.setOnClickListener {
                mListener(item) }
        }
    }
}