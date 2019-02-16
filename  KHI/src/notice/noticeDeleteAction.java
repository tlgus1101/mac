package notice;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.opensymphony.xwork2.ActionSupport;

public class noticeDeleteAction extends ActionSupport {
	
	public static Reader reader;
	public static SqlMapClient sqlMapper;

	private noticeVO paramClass; //파라미터를 저장할 객체
	private noticeVO resultClass; //쿼리 결과 값을 저장할 객체

	private int currentPage;	//현재 페이지
	
	private String fileUploadPath = "C:\\Java\\KhiClassProject\\";
	
	private int n_no;
	
	// 생성자
		public noticeDeleteAction() throws IOException {
			
			reader = Resources.getResourceAsReader("sqlMapConfig.xml"); // sqlMapConfig.xml 파일의 설정내용을 가져온다.
			sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader); // sqlMapConfig.xml의 내용을 적용한 sqlMapper 객체 생성.
			reader.close();
		}
		
		// 게시글 글 삭제
		public String execute() throws Exception {
			
			//파라미터와 리절트 객체 생성.
			paramClass = new noticeVO();
			resultClass = new noticeVO();
			
			// 해당 번호의 글을 가져온다.
			resultClass = (noticeVO) sqlMapper.queryForObject("notice.selectOne", getN_no());

			//서버 파일 삭제
			File deleteFile = new File(fileUploadPath + resultClass.getN_file_savname());
			deleteFile.delete();

			// 삭제할 항목 설정.
			paramClass.setN_no(getN_no());
					
			// 삭제 쿼리 수행.(DB 삭제)
			sqlMapper.update("notice.deleteNotice", paramClass);

			return SUCCESS;
		}

		public static Reader getReader() {
			return reader;
		}

		public static void setReader(Reader reader) {
			noticeDeleteAction.reader = reader;
		}

		public static SqlMapClient getSqlMapper() {
			return sqlMapper;
		}

		public static void setSqlMapper(SqlMapClient sqlMapper) {
			noticeDeleteAction.sqlMapper = sqlMapper;
		}

		public noticeVO getParamClass() {
			return paramClass;
		}

		public void setParamClass(noticeVO paramClass) {
			this.paramClass = paramClass;
		}

		public noticeVO getResultClass() {
			return resultClass;
		}

		public void setResultClass(noticeVO resultClass) {
			this.resultClass = resultClass;
		}

		public int getCurrentPage() {
			return currentPage;
		}

		public void setCurrentPage(int currentPage) {
			this.currentPage = currentPage;
		}

		public String getFileUploadPath() {
			return fileUploadPath;
		}

		public void setFileUploadPath(String fileUploadPath) {
			this.fileUploadPath = fileUploadPath;
		}

		public int getN_no() {
			return n_no;
		}

		public void setN_no(int n_no) {
			this.n_no = n_no;
		}
		

}
