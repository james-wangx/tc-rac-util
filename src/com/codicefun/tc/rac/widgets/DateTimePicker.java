package com.codicefun.tc.rac.widgets;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;

public class DateTimePicker extends Composite {

	private DateTime datePicker;
	private DateTime timePicker;
	private Button enableCheck; // 用来表示是否启用日期时间

	public DateTimePicker(Composite parent, int style) {
		super(parent, style);
		createControls();
	}

	private void createControls() {
		GridLayout layout = new GridLayout(3, false); // 多一列放 CheckBox
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayout(layout);

		// 日期选择
		datePicker = new DateTime(this, SWT.DATE | SWT.DROP_DOWN);
		datePicker.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		// 时间选择
		timePicker = new DateTime(this, SWT.TIME | SWT.DROP_DOWN);
		timePicker.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));

		// 复选框：是否启用
		enableCheck = new Button(this, SWT.CHECK);
//		enableCheck.setText("Enable");
		enableCheck.setSelection(false); // 默认未选中
		enableCheck.addListener(SWT.Selection, e -> updateEnabledState());

		Color bg = getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE);
		this.setBackground(bg); // DateTimePicker 的背景
		// 默认禁用
		updateEnabledState();
	}

	// 根据 CheckBox 更新控件可用状态
	private void updateEnabledState() {
		boolean enabled = enableCheck.getSelection();
		datePicker.setEnabled(enabled);
		timePicker.setEnabled(enabled);
	}

	// 获取选择的日期+时间（可能为空）
	public LocalDateTime getDateTime() {
		if (!enableCheck.getSelection()) {
			return null; // 表示没选
		}
		return LocalDateTime.of(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDay(),
				timePicker.getHours(), timePicker.getMinutes(), timePicker.getSeconds());
	}

	// 设置日期+时间（支持 null）
	public void setDateTime(LocalDateTime dt) {
		if (dt == null) {
			enableCheck.setSelection(false);
			updateEnabledState();
		} else {
			enableCheck.setSelection(true);
			updateEnabledState();
			datePicker.setDate(dt.getYear(), dt.getMonthValue() - 1, dt.getDayOfMonth());
			timePicker.setTime(dt.getHour(), dt.getMinute(), dt.getSecond());
		}
	}

	@Override
	public String toString() {
		if (getDateTime() == null) {
			return "";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		return getDateTime().format(formatter);
	}
}
