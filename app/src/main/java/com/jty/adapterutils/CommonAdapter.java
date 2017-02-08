package com.jty.adapterutils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/** 
 * @author jty 
 */

public abstract class CommonAdapter<T> extends BaseAdapter{
	
	LayoutInflater mInflater;
	Context mContext;
	List<T> mDatas;
	final int mItemLayoutId;
	
	public CommonAdapter(Context context,List<T> datas,int itemLayoutId){
		mInflater=LayoutInflater.from(context);
		mContext=context;
		mDatas=datas;
		mItemLayoutId=itemLayoutId;
	}
	
	public int getCount() {
		// TODO Auto-generated method stub

		return mDatas.size();
	}

	public T getItem(int arg0) {
		// TODO Auto-generated method stub
		return mDatas.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		//实例化一个viewHolder  
		final ViewHolder mHolder=getViewHolder(position, convertView, parent);
		convert(mHolder, getItem(position));
		return mHolder.getConvertView();
	}

	public abstract void convert(ViewHolder helper,T item);
	
	private ViewHolder getViewHolder(int position, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		return ViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
	}
	

}
