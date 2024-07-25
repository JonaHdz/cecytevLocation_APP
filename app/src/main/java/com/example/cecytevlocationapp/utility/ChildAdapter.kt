package com.example.cecytevlocationapp.utility


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cecytevlocationapp.R
import com.example.cecytevlocationapp.data.model.ChildInfo
import com.example.cecytevlocationapp.ui.view.ShowMap


class ChildAdapter : RecyclerView.Adapter<ChildAdapter.childAdapterViewHolder>()  {
    lateinit var contextShowMenuParent : Context
    private var childList = mutableListOf<ChildInfo>()
    inner class childAdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val nameStudent : TextView = itemView.findViewById(R.id.tvNameStudentItemShowMenuParentInput)
        val idStudent : TextView = itemView.findViewById(R.id.tvIdStudentItemShowMenuParentInput)
        val btnShowChildLocation : Button = itemView.findViewById(R.id.btnShowLocationChild)

        fun bind (childItem : ChildInfo){
            nameStudent.text = childItem.name
            idStudent.text = childItem.idStudent
            //agregar funcion del boton de bsucar
            btnShowChildLocation.setOnClickListener{
                val intent = Intent(contextShowMenuParent, ShowMap::class.java)
                contextShowMenuParent.startActivity(intent)
            }
        }
    }

    fun setItem(childItem : ChildInfo){
        childList.add(childItem)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChildAdapter.childAdapterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_show_menu_parent,parent,false)
        return  childAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildAdapter.childAdapterViewHolder, position: Int) {
        val child = childList[position]
        holder.bind(child)
    }

    override fun getItemCount(): Int {
        return childList.count()
    }


}