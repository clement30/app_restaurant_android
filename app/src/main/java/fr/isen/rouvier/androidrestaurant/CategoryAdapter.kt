package fr.isen.rouvier.androidrestaurant

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.rouvier.androidrestaurant.model.Item

class CategoryAdapter(private val  items: ArrayList<Item>, val mListener: (Item) -> Unit)  : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.cell_category, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menu = items[position]

        holder.dishTile.text = menu.name_fr
        holder.dishPrice.text = menu.prices[0].price +"€"

        Picasso.get().load(menu.images[0].ifEmpty { null })
            .placeholder(R.drawable.logoresto)
            .error(R.drawable.logoresto)
            .into(holder.imageDish)
        holder.itemView.setOnClickListener {
            mListener(menu)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val dishTile: TextView = ItemView.findViewById(R.id.titrePlat)
        val dishPrice: TextView = ItemView.findViewById(R.id.prixPlat)
        val imageDish : ImageView = ItemView.findViewById(R.id.imagePlat)
    }
}