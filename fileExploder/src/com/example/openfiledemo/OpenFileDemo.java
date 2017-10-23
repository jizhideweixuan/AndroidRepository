// filename: OpenFileDemo.java
package com.example.openfiledemo;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;

public class OpenFileDemo extends Activity {
	
	static private int openfileDialogId = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_file_demo);
        
        
        // ���õ�����ťʱ���ļ��Ի���
        findViewById(R.id.button_openfile).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(openfileDialogId);
			}
		});
    }

	@Override
	protected Dialog onCreateDialog(int id) {
		if(id==openfileDialogId){
			Map<String, Integer> images = new HashMap<String, Integer>();
			// ���漸�����ø��ļ����͵�ͼ�꣬ ��Ҫ���Ȱ�ͼ����ӵ���Դ�ļ���
			images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);	// ��Ŀ¼ͼ��
			images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);	//������һ���ͼ��
			images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);	//�ļ���ͼ��
			images.put("wav", R.drawable.filedialog_wavfile);	//wav�ļ�ͼ��
			images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);
			Dialog dialog = OpenFileDialog.createDialog(id, this, "���ļ�", new CallbackBundle() {
				@Override
				public void callback(Bundle bundle) {
					String filepath = bundle.getString("path");
					setTitle(filepath); // ���ļ�·����ʾ�ڱ�����
				}
			}, 
			".wav;",
			images);
			return dialog;
		}
		return null;
	}
}
