package cn.demo.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.demo.test.PullRefreshListView.OnRefreshListener;

public class TestActivity extends Activity {
	private PullRefreshListView listView;
	// 最后的可视项索引
	// private int visibleLastIndex = 0;
	@SuppressWarnings("unused")
	// 当前窗口可见项总数
	private int visibleItemCount;
	// 模拟数据集的条数
	private int datasize = 50;
	private MyListAdapter adapter;
	private View loadMoreView;
	private Button loadMoreButton;
	private Handler handler = new Handler();
	List<News> news;
	int k = 0;
	
	EditText edt1 ;
    EditText edt2 ;
    AutoCompleteTextView edt3 ;
    String playerChanged;
    String playerChanged2;
    int b = 0;
    String []a;
    News s;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		loadMoreView = getLayoutInflater().inflate(R.layout.loadmore, null);
		loadMoreButton = (Button) loadMoreView
				.findViewById(R.id.loadMoreButton);
		loadMoreButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loadMoreButton.setText("正在加载中..."); // 设置按钮文字
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						loadMoreData();
						adapter.notifyDataSetChanged();
						loadMoreButton.setText("查看更多..."); // 恢复按钮文字
					}
				}, 2000);
			}
		});

		listView = (PullRefreshListView) findViewById(R.id.lvNews);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int print,
					long id) {
				News s=news.get(print-1);
				setTitle(s.getTitle()+s.getContent());
			}
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, final View v,
					final int print, long id) {
				final String[] arrayFruit = new String[] {"删除", "添加", "修改", "查找"};
		        
		  Dialog dialog = new AlertDialog.Builder(TestActivity.this)
		              .setTitle("请选择···")
		              .setIcon(R.drawable.ic_launcher)
		              .setItems(arrayFruit, new DialogInterface.OnClickListener() {
		         
		         public void onClick(DialogInterface dialog, int which) {
		        	 if (which==0) {
		        		 news.remove(print - 1);
			            	k++;
			            	adapter.notifyDataSetChanged();
					}else if (which==1) {
						 LayoutInflater layoutInflater = LayoutInflater.from(TestActivity.this); 
			    	        final View myLoginView = layoutInflater.inflate(R.layout.login, null); 
			    	        Dialog alertDialog = new AlertDialog.Builder(TestActivity.this). 
			    	                setTitle("确定添加"). 
			    	                setIcon(R.drawable.ic_launcher). 
			    	                setView(myLoginView). 
			    	                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
			    	 
			    	                    @Override 
			    	                    public void onClick(DialogInterface dialog, int which) { 
			    	                    	edt1 = (EditText)myLoginView.findViewById(R.id.edt1);
			    			    	        edt2 = (EditText)myLoginView.findViewById(R.id.edt2);
			    	                    	News tianjia = new News();
			    			            	tianjia.setTitle(edt1.getText().toString());
			    			            	tianjia.setContent(edt2.getText().toString());
			    			    			news.add(tianjia);
			    			    			adapter.notifyDataSetChanged();
			    			    			k--;
			    	                    } 
			    	                }). 
			    	                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
			    	 
			    	                    @Override 
			    	                    public void onClick(DialogInterface dialog, int which) { 
			    	                    	Toast.makeText(TestActivity.this, "添加失败！", Toast.LENGTH_SHORT).show();
			    	                    } 
			    	                }). 
			    	                create(); 
			    	        alertDialog.show(); 
					}else if (which==2) {
						LayoutInflater layoutInflater = LayoutInflater.from(TestActivity.this); 
		    	        final View myLoginView = layoutInflater.inflate(R.layout.login, null); 
		    	        TextView c = (TextView) v.findViewById(R.id.newstitle);
		    	        TextView d = (TextView) v.findViewById(R.id.newscontent);
		    	        playerChanged = c.getText().toString();
		    	        playerChanged2 = d.getText().toString();
		    	        edt1 = (EditText)myLoginView.findViewById(R.id.edt1);
		    	        edt2 = (EditText)myLoginView.findViewById(R.id.edt2);
		    	        edt1.setText(playerChanged);
		    	        edt2.setText(playerChanged2);
		    	        Dialog alertDialog = new AlertDialog.Builder(TestActivity.this). 
		    	        setTitle("确定修改"). 
    	                setIcon(R.drawable.ic_launcher). 
    	                setView(myLoginView). 
    	                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
    	                    @Override 
    	                    public void onClick(DialogInterface dialog, int which) { 
    	                    	News xiugai = new News();
    	                    	xiugai.setTitle(edt1.getText().toString());
    	                    	xiugai.setContent(edt2.getText().toString());
    			            	news.set(print-1, xiugai);
    			    			adapter.notifyDataSetChanged();
    	                    } 
    	                }). 
    	                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
    	 
    	                    @Override 
    	                    public void onClick(DialogInterface dialog, int which) { 
    	                    	Toast.makeText(TestActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
    	                    } 
    	                }). 
    	                create(); 
    	        alertDialog.show(); 
					}else{
						int set = adapter.getCount() ;
						a = new String[set];
						System.out.println(set+"--------------");
                    	for (int i = 0; i < set; i++) {
                    		s=news.get(i);
                    		a[i] = s.getTitle();
                    		System.out.println("a["+i+"]"+"="+a[i]);
						}
						 LayoutInflater layoutInflater = LayoutInflater.from(TestActivity.this); 
			    	        final View myLoginView = layoutInflater.inflate(R.layout.login1, null); 
			    	        edt3 = (AutoCompleteTextView)myLoginView.findViewById(R.id.autoCompleteTextView1);
	                    	ArrayAdapter<String> av = new ArrayAdapter<String>(TestActivity.this,
	                                 android.R.layout.simple_dropdown_item_1line, a);
	                    	edt3.setAdapter(av);
			    	        Dialog alertDialog = new AlertDialog.Builder(TestActivity.this). 
			    	                setTitle("确定查找"). 
			    	                setIcon(R.drawable.ic_launcher). 
			    	                setView(myLoginView). 
			    	                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
			    	 
			    	                    @Override 
			    	                    public void onClick(DialogInterface dialog, int which) {
			    	                    	String tit = edt3.getText().toString();
			    	                    	int set1 = adapter.getCount() ;
			    	                    	for (int i = 0; i < set1; i++) {
			    	                    		News s=news.get(i);
			    	                    		if (s.getTitle().equals(tit)) {
													b = i;
												}
											}
											news.add(0, news.get(b));
											news.remove(b+1);
											b = 0 ;
											adapter.notifyDataSetChanged();
			    	                    } 
			    	                }). 
			    	                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
			    	 
			    	                    @Override 
			    	                    public void onClick(DialogInterface dialog, int which) { 
			    	                    	Toast.makeText(TestActivity.this, "查找失败！", Toast.LENGTH_SHORT).show();
			    	                    } 
			    	                }). 
			    	                create(); 
			    	        alertDialog.show(); 
			    	        //这里不建议设置宽高  容易超出屏幕范围
			    	        alertDialog.getWindow().setLayout(150, 400);
						
					}
		          Toast.makeText(TestActivity.this, arrayFruit[which], Toast.LENGTH_SHORT).show();
		         }
		        })
		        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
		         
		         public void onClick(DialogInterface dialog, int which) {
		          
		         }
		        })
		        .create();
		  dialog.show();
				
				
				
				
				
				
				
				
				//////////////////////////////////////////////////////////////
//				Dialog alertDialog = new AlertDialog.Builder(TestActivity.this). 
//		        setTitle("确定删除？"). 
//		        setMessage("您确定删除该条信息吗？"). 
//		        setIcon(R.drawable.ic_launcher). 
//		        setPositiveButton("确定", new DialogInterface.OnClickListener() { 
//		             
//		            @Override 
//		            public void onClick(DialogInterface dialog, int which) { 
//		            	news.remove(print - 1);
//		            	k++;
//		            	adapter.notifyDataSetChanged();
//		            } 
//		        }). 
//		        setNegativeButton("添加", new DialogInterface.OnClickListener() { 
//		             
//		            @Override 
//		            public void onClick(DialogInterface dialog, int which) { 
//
//		    			 // 取得自定义View  
//		    	        LayoutInflater layoutInflater = LayoutInflater.from(TestActivity.this); 
//		    	        final View myLoginView = layoutInflater.inflate(R.layout.login, null); 
//		    	        Dialog alertDialog = new AlertDialog.Builder(TestActivity.this). 
//		    	                setTitle("确定添加"). 
//		    	                setIcon(R.drawable.ic_launcher). 
//		    	                setView(myLoginView). 
//		    	                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
//		    	 
//		    	                    @Override 
//		    	                    public void onClick(DialogInterface dialog, int which) { 
//		    	                    	edt1 = (EditText)myLoginView.findViewById(R.id.edt1);
//		    			    	        edt2 = (EditText)myLoginView.findViewById(R.id.edt2);
//		    	                    	News tianjia = new News();
//		    			            	tianjia.setTitle(edt1.getText().toString());
//		    			            	tianjia.setContent(edt2.getText().toString());
//		    			    			news.add(tianjia);
//		    			    			adapter.notifyDataSetChanged();
//		    			    			k--;
//		    	                    } 
//		    	                }). 
//		    	                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
//		    	 
//		    	                    @Override 
//		    	                    public void onClick(DialogInterface dialog, int which) { 
//		    	                    	Toast.makeText(TestActivity.this, "添加失败！", Toast.LENGTH_SHORT).show();
//		    	                    } 
//		    	                }). 
//		    	                create(); 
//		    	        alertDialog.show(); 
//		            } 
//		        }). 
//		        setNeutralButton("修改", new DialogInterface.OnClickListener() { 
//		             
//		            @Override 
//		            public void onClick(DialogInterface dialog, int which) { 
//		            	LayoutInflater layoutInflater = LayoutInflater.from(TestActivity.this); 
//		    	        final View myLoginView = layoutInflater.inflate(R.layout.login, null); 
//		    	        TextView c = (TextView) v.findViewById(R.id.newstitle);
//		    	        TextView d = (TextView) v.findViewById(R.id.newscontent);
//		    	        playerChanged = c.getText().toString();
//		    	        playerChanged2 = d.getText().toString();
//		    	        edt1 = (EditText)myLoginView.findViewById(R.id.edt1);
//		    	        edt2 = (EditText)myLoginView.findViewById(R.id.edt2);
//		    	        edt1.setText(playerChanged);
//		    	        edt2.setText(playerChanged2);
//		    	        Dialog alertDialog = new AlertDialog.Builder(TestActivity.this). 
//		    	        setTitle("确定修改"). 
//    	                setIcon(R.drawable.ic_launcher). 
//    	                setView(myLoginView). 
//    	                setPositiveButton("确定", new DialogInterface.OnClickListener() { 
//    	                    @Override 
//    	                    public void onClick(DialogInterface dialog, int which) { 
//    	                    	News xiugai = new News();
//    	                    	xiugai.setTitle(edt1.getText().toString());
//    	                    	xiugai.setContent(edt2.getText().toString());
//    			            	news.set(print-1, xiugai);
//    			    			adapter.notifyDataSetChanged();
//    	                    } 
//    	                }). 
//    	                setNegativeButton("取消", new DialogInterface.OnClickListener() { 
//    	 
//    	                    @Override 
//    	                    public void onClick(DialogInterface dialog, int which) { 
//    	                    	Toast.makeText(TestActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
//    	                    } 
//    	                }). 
//    	                create(); 
//    	        alertDialog.show(); 
//		            } 
//		        }). 
//		        create(); 
//		alertDialog.show(); 
		  ///////////////////////////////////////////////////////
				return false;
			}
		});
		listView.addFooterView(loadMoreView); // 设置列表底部视图
		initializeAdapter();
		listView.setAdapter(adapter);
		listView.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						loadMoreData();
						adapter.notifyDataSetChanged();
						listView.onRefreshComplete();
					}
				}, 2000);
			}
		});
	}

	// @Override
	// public void onScrollStateChanged(AbsListView view, int scrollState) {
	// // 数据集最后一项的索引
	// int itemsLastIndex = adapter.getCount() - 1;
	// int lastIndex = itemsLastIndex + 1;
	// if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
	// && visibleLastIndex == lastIndex) {
	// // 如果是自动加载,可以在这里放置异步加载数据的代码
	// }
	// }
	//
	// @Override
	// public void onScroll(AbsListView view, int firstVisibleItem,
	// int visibleItemCount, int totalItemCount) {
	// this.visibleItemCount = visibleItemCount;
	// visibleLastIndex = firstVisibleItem + visibleItemCount - 1;
	// // 如果所有的记录选项等于数据集的条数，则移除列表底部视图
	// if (totalItemCount == datasize + 1) {
	// listView.removeFooterView(loadMoreView);
	// Toast.makeText(this, "数据全部加载完!", Toast.LENGTH_LONG).show();
	// }
	// }

	/**
	 * 初始化ListView的适配器
	 */
	private void initializeAdapter() {
		news = new ArrayList<News>();
		for (int i = 1; i <= 10; i++) {
			News items = new News();
			items.setTitle("alb新闻" + i);
			items.setContent("----你快点来南京----" + i);
			news.add(items);
		}
		adapter = new MyListAdapter(news);
	}

	/**
	 * 加载更多数据
	 */
	private void loadMoreData() {
		int count = adapter.getCount() + k;
		if (count == datasize) {
			Toast.makeText(this, "没有更新!", Toast.LENGTH_LONG).show();
			return;
		}
		if (count + 10 <= datasize) {
			for (int i = count + 1; i <= count + 10; i++) {
				News item = new News();
				item.setTitle("alb新闻" + i);
				item.setContent("----苍老师来南京----" + i);
				adapter.addNewsItem(item);
			}
		} else {
			for (int i = count + 1; i <= datasize; i++) {
				News item = new News();
				item.setTitle("alb新闻" + i);
				item.setContent("----苍老师来南京----" + i);
				adapter.addNewsItem(item);
			}
		}
	}

	class MyListAdapter extends BaseAdapter {
		List<News> newsItems;

		public MyListAdapter(List<News> newsitems) {
			this.newsItems = newsitems;
		}

		@Override
		public int getCount() {
			return newsItems.size();
		}

		@Override
		public Object getItem(int position) {
			return newsItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.list_item, null);
			}
			// 新闻标题
			TextView tvTitle = (TextView) view.findViewById(R.id.newstitle);
			tvTitle.setText(newsItems.get(position).getTitle());
			// 新闻内容
			TextView tvContent = (TextView) view.findViewById(R.id.newscontent);
			tvContent.setText(newsItems.get(position).getContent());
			return view;
		}

		/**
		 * 添加数据列表项
		 * 
		 * @param newsitem
		 */
		public void addNewsItem(News newsitem) {
			newsItems.add(newsitem);
		}
	}
}