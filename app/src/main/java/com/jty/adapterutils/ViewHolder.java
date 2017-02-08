package com.jty.adapterutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/** 
 * @author jty 
 */

public class ViewHolder {
	private SparseArray<View> mViews;
	private View mConvertView;
	private int mPosition;
	private Context mContext;
	private ViewHolder(Context mContext,ViewGroup parent,int layoutId,int position){
		
		this.mViews=new SparseArray<View>();
		mConvertView=LayoutInflater.from(mContext).inflate(layoutId, parent, false);
		mPosition=position;
		this.mContext=mContext;
		mConvertView.setTag(this);
		
	}
	/** 
     * 拿到一个ViewHolder对象 
     * @param context 
     * @param convertView 
     * @param parent 
     * @param layoutId 
     * @param position 
     * @return
     */  
	public static ViewHolder get(
			Context context,View convertView,ViewGroup parent,int layoutId,int position){
		if(convertView==null){
			return new ViewHolder(context, parent, layoutId, position);
		}
		return (ViewHolder) convertView.getTag();
		
	}
	
	public View getConvertView(){
		return mConvertView;
	}
	
	/**
	 * 通过控件id加入控件，如果没有则加入views
	 * @param viewId
	 * @return view
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId){
		View view=mViews.get(viewId);
		if(view==null){
			view=mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T)view;
	}
	
	/**
	 * 为TextView设置字符串
	 * @param id
	 * @param text
	 * @return this
	 */
	public ViewHolder setText(int id ,String text){
		TextView textView=getView(id);
		textView.setText(text);
		return this;
		
	}
	
	public ViewHolder setImageResource(int id,int resource){
		ImageView imageView=getView(id);
		imageView.setImageResource(resource);
		return this;
		
	}
	public ViewHolder setImageBitmap(int id, Bitmap bitmap){
		ImageView imageView=getView(id);
		imageView.setImageBitmap(bitmap);
		return this;
	}
	
	@SuppressWarnings("deprecation")
	public ViewHolder setImageUrl(int id,String url){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
//				.showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
//				.showImageForEmptyUri(R.drawable.icon_stub) // 设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
				.defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		
		ImageLoader.getInstance().init(config);	
		ImageLoader.getInstance().displayImage(url, (ImageView)getView(id),options);
		
		return this;
		
	}
	public int position(){
		return mPosition;
		
	}





}
