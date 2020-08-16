package com.marcoperini.sliceat.ui.mail

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.marcoperini.sliceat.R

class MailsAdapter(val mailCard: List<CardMail>, val resources : Resources) : RecyclerView.Adapter<MailsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mail_card, parent, false)
        return MailsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mailCard.size
    }

    override fun onBindViewHolder(holder: MailsViewHolder, position: Int) {
        return holder.bind(mailCard[position], resources)
    }
}

class MailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val cardImage: ImageView = itemView.findViewById(R.id.card_mail)
    private val title: TextView = itemView.findViewById(R.id.mail_tilte)

    fun bind(mailCard: CardMail, resources: Resources) {
        title.text = resources.getString(mailCard.mailDescription)
        cardImage.setImageDrawable(resources.getDrawable(mailCard.imageMail))
    }
}
