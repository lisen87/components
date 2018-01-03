package com.leeson.components.views;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;

public class CashEditText extends android.support.v7.widget.AppCompatEditText {

	private TextChangeListener textChangeListener;
	public CashEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		addTextChangedListener(new Watcher());
	}
	
	public interface TextChangeListener{
		void onTextChanged(CharSequence s, int start, int before, int count);
		void afterTextChanged(Editable s);
	}
	
	public void setTextChangeListener(TextChangeListener textChangeListener){
		this.textChangeListener = textChangeListener;
	}
	
	class Watcher implements TextWatcher{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (textChangeListener != null) {
				textChangeListener.onTextChanged(s, start, before, count);
			}
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (s.toString().startsWith(".") ||s.toString().startsWith("0")) {
//				s.insert(0, "0");
				s.delete(0, 1);
			}
			if (s.toString().contains(".")) {
				int PointIndex = s.toString().indexOf(".");
				if (s.toString().length() - PointIndex > 3) {
					s.delete(s.length()-1, s.length());
				}
			}
			if (textChangeListener != null) {
				textChangeListener.afterTextChanged(s);
			}
		}
	}

}
