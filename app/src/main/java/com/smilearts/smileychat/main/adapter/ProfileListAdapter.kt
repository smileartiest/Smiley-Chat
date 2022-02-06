package com.smilearts.smileychat.main.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smilearts.smileychat.R
import com.smilearts.smileychat.main.callback.ProfileCallBack
import com.smilearts.smileychat.main.model.RegisterModel
import com.smilearts.smileychat.utils.TempData
import de.hdodenhof.circleimageview.CircleImageView

class ProfileListAdapter(
    private val activity: Activity,
    private val list: List<RegisterModel>,
    private val callBack: ProfileCallBack
) : RecyclerView.Adapter<ProfileListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(activity.applicationContext).inflate(R.layout.row_friends_list , parent , false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        Glide.with(activity.applicationContext).load(model.userProPicUrl).into(holder.proPic)
        holder.proName.text = model.userName
        holder.subTitle.text = model.userStatus
        if (TempData(activity.applicationContext).getProfileID() == model.userID) {
            holder.actionTxt.visibility = View.GONE
            holder.subTitle.text = "It's your account."
        }
        holder.actionTxt.setOnClickListener {
            callBack.chooseProfile(model)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val proPic: CircleImageView = itemView.findViewById(R.id.row_frnds_list_propic)
        val proName: TextView = itemView.findViewById(R.id.row_frnds_list_name)
        val subTitle: TextView = itemView.findViewById(R.id.row_frnds_list_subtitle)
        val actionTxt: TextView = itemView.findViewById(R.id.row_frnds_list_action)
    }

}