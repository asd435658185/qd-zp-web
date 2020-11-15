package com.zhiyu.service.handler;

import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

/**
 * 文件上传监听
 * @author wdj
 *
 */

@Component
public class FileUploadProgressListener implements ProgressListener {

	private HttpSession session;

	public void setSession(HttpSession session) {
		this.session = session;
		UploadProgress status = new UploadProgress();// 保存上传状态
		session.setAttribute("fileUploadStatus", status);
	}

	public void update(long bytesRead, long contentLength, int items) {
		UploadProgress status = (UploadProgress) session.getAttribute("fileUploadStatus");
		status.setBytesRead(bytesRead);
		status.setContentLength(contentLength);
		status.setItems(items);

	}

}
