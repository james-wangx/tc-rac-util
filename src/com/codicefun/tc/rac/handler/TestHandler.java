package com.codicefun.tc.rac.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;

import com.teamcenter.rac.util.MessageBox;

public class TestHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) {
		System.out.println("Start TestHandler");
		MessageBox.post("这是个测试消息", "提示", MessageBox.INFORMATION);
		return null;
	}

}
