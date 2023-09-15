package com.example.resepku.CRUD.Adapter
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.resepku.CRUD.DB.itemDb
import com.example.resepku.R


class itemAdapter(private val itmList:ArrayList<itemDb>): RecyclerView.Adapter<itemAdapter.itemHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    class itemHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val judul: TextView = itemView.findViewById(R.id.Judul_rv)
        val deskripsi: TextView = itemView.findViewById(R.id.deskripsi_rv)
        val gambar: ImageView = itemView.findViewById(R.id.img_rv)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return itemHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return itmList.size
    }

    override fun onBindViewHolder(holder: itemAdapter.itemHolder, position: Int) {
        val currentItem = itmList[position]
        holder.judul.text = currentItem.judul.toString()
        holder.deskripsi.text = currentItem.bahan2.toString()
//        holder.judul.setText(currentItem.judul.toString())
//        holder.deskripsi.setText(currentItem.bahan2.toString())
        val bytes = android.util.Base64.decode(currentItem.img, android.util.Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        holder.gambar.setImageBitmap(bitmap)

    }
}

//    override fun getItemCount(): Int {
//        return itmList.size
//    }
