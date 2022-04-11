package fr.isen.rouvier.androidrestaurant

import java.io.Serializable

data class Cart(

    var listItem : MutableList<ItemCart>
) : Serializable

data class ItemCart(
    var image : String,
    var name : String,
    var quantity : Int,
    var price : Float
): Serializable

