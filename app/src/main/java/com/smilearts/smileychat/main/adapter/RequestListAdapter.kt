package com.smilearts.smileychat.main.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smilearts.smileychat.R
import com.smilearts.smileychat.main.callback.RequestCallBack
import com.smilearts.smileychat.main.model.RegisterModel
import com.smilearts.smileychat.main.model.RequestModel
import de.hdodenhof.circleimageview.CircleImageView

class RequestListAdapter(
    private val activity: Activity,
    private val list: List<RegisterModel>,
    private val callBack: RequestCallBack
) : RecyclerView.Adapter<RequestListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(activity.applicationContext).inflate(R.layout.row_request_list, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        Glide.with(activity.applicationContext).load(model.userProPicUrl).placeholder(R.drawable.account_icon).into(holder.proPic)
        holder.proName.text = model.userName
        holder.subTitle.text = model.userStatus
        holder.actionAccept.setOnClickListener {
            callBack.accept(model)
        }
        holder.actionCancel.setOnClickListener {
            callBack.cancel(model)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val proPic: CircleImageView = itemView.findViewById(R.id.row_req_profile_pic)
        val proName: TextView = itemView.findViewById(R.id.row_req_name)
        val subTitle: TextView = itemView.findViewById(R.id.row_req_status)
        val actionAccept: TextView = itemView.findViewById(R.id.row_req_action_accept)
        val actionCancel: TextView = itemView.findViewById(R.id.row_req_action_cancel)
    }

}