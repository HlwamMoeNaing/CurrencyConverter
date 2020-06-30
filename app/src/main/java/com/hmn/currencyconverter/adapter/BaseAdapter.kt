package com.hmn.currencyconverter.adapter

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.hmn.currencyconverter.view.holder.BaseViewHolder

abstract class BaseAdapter<T : BaseViewHolder<W>, W>(context: Context) : RecyclerView.Adapter<T>() {
    private var mData: MutableList<W> = ArrayList()
    protected var mLayoutInflater: LayoutInflater = LayoutInflater.from(context)


    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        holder.setData(mData[position])
    }


    fun setNewData(newData: List<W>) {
        if (newData.isEmpty()) {
            mData.clear()
        } else {
            mData = newData as MutableList<W>
        }
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): W? {
        return if (position < mData.size) mData[position] else null
    }


    fun getItems(): List<W> {
        return mData
    }


    fun clearData() {
        mData = arrayListOf()
        notifyDataSetChanged()
    }


}