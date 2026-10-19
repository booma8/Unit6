package com.codepath.randompet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PetAdapter(private val petList: List<String>, private val authorList: List<String>) : RecyclerView.Adapter<PetAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pet_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        Glide.with(holder.itemView)
            .load(petList[position])
            .centerCrop()
            .into(holder.petImage)

        holder.theUrl.text = "URL for this image is:\n ${petList[position]}"
        holder.theAuthor.text = "Author for this image is:\n ${authorList[position]}"
    }

    override fun getItemCount() = petList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val petImage: ImageView
        val theUrl: TextView
        val theAuthor: TextView

        init {
            // Find our RecyclerView item's ImageView for future use
            petImage = view.findViewById(R.id.pet_image)
            theUrl = view.findViewById(R.id.urlView)
            theAuthor = view.findViewById(R.id.authorView)
        }
    }
}
