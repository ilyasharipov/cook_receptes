package com.testproject.recipes.ui.main

import com.squareup.picasso.Picasso
import com.testproject.recipes.R
import com.testproject.recipes.data.RecipeData
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.item_recipe.view.*

class RecipeItem(
    val recipeData: RecipeData,
    val onClick: (uuid: String) -> Unit
): Item<GroupieViewHolder>() {
    override fun getLayout(): Int = R.layout.item_recipe
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            root.item_recipe_name.text = recipeData.name
            root.item_recipe_short_description.text = recipeData.description
            Picasso.get()
                .load(recipeData.images.first())
                .placeholder(R.drawable.red_box)
                .error(R.drawable.red_box)
                .into(root.item_recipe_image)
            root.setOnClickListener { onClick.invoke(recipeData.uuid) }
        }
    }
}