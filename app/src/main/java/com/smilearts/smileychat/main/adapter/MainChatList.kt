package com.smilearts.smileychat.main.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.smilearts.smileychat.R
import com.smilearts.smileychat.main.model.ChatListModel
import de.hdodenhof.circleimageview.CircleImageView

class MainChatList(
    private val activity: Activity,
    private val list: List<ChatListModel>
) : RecyclerView.Adapter<MainChatList.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(activity.applicationContext).inflate(R.layout.row_main_chat_list , parent , false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        Glide.with(activity.applicationContext).load(model.chatProfilePic).placeholder(R.drawable.account_icon).into(holder.proPic)
        holder.title.text = model.chatName
        holder.subTitle.text = model.chatLastOnline
        if (model.chatStatus) {
            holder.onlineStatus.visibility = View.VISIBLE
        } else {
            holder.onlineStatus.visibility = View.GONE
        }
        holder.card.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val proPic: CircleImageView = itemView.findViewById(R.id.row_mainchat_propic)
        val onlineStatus: ImageView = itemView.findViewById(R.id.row_mainchat_online)
        val title: TextView = itemView.findViewById(R.id.row_mainchat_title)
        val subTitle: TextView = itemView.findViewById(R.id.row_mainchat_subtitlle)
        val card: MaterialCardView = itemView.findViewById(R.id.row_mainchat_card)
    }

}