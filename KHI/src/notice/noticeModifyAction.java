package notice;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

import org.apache.commons.io.FileUtils;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.opensymphony.xwork2.ActionSupport;

public class noticeModifyAction extends ActionSupport {
	public static Reader reader;
	public static SqlMapClient sqlMapper;

	private noticeVO paramClass; // 파라미터를 저장할 객체
	private noticeVO resultClass; // 쿼리 결과 값을 저장할 객체

	private int currentPage; // 현재 페이지

	private int n_no;
	private String n_subject;
	private String n_content;
	private String old_file;

	private File upload; // 파일 객체
	private String uploadContentType; // 컨텐츠 타입
	private String uploadFileName; // 파일 이름
	private String fileUploadPath = "C:\\java\\App\\KHI\\WebContent\\images\\"; // 업로드 경로.
	// 전송된 것을 받기 위해 변수 선언

	public noticeModifyAction() throws IOException {

		reader = Resources.getResourceAsReader("sqlMapConfig.xml"); // sqlMapConfig.xml 파일의 설정내용을 가져온다.
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader); // sqlMapConfig.xml의 내용을 적용한 sqlMapper 객체 생성.
		reader.close();
	}

	// 게시글 수정
	public String execute() throws Exception {

		// 파라미터와 리절트 객체 생성.
		paramClass = new noticeVO();
		resultClass = new noticeVO();

		paramClass.setN_no(getN_no());
		paramClass.setN_subject(getN_subject());
		paramClass.setN_content(getN_content());

		sqlMapper.update("notice.updateNotice", paramClass);

		if (getUpload() != null) {
			String file_name = "file_" + getN_no();
			String file_ext = getUploadFileName().substring(getUploadFileName().lastIndexOf('.') + 1,
					getUploadFileName().length());

			// 이전 파일 삭제
			// 이미 업로드되어있는 파일을 삭제한다.
			File deleteFile = new File(fileUploadPath + getOld_file());
			deleteFile.delete();

			// 새 파일 업로드
			File destFile = new File(fileUploadPath + file_name + "." + file_ext);
			FileUtils.copyFile(getUpload(), destFile);

			// 파일 정보 파라미터 설정.
			paramClass.setN_file_orgname(getUploadFileName());
			paramClass.setN_file_savname(file_name + "." + file_ext);

			// 파일 정보 업데이트.
			sqlMapper.update("notice.updateFile", paramClass);
		}
		// 수정이 끝나면 view 페이지로 이동.
		resultClass = (noticeVO) sqlMapper.queryForObject("notice.selectOne", getN_no());

		return SUCCESS;

	}

	public static Reader getReader() {
		return reader;
	}

	public static void setReader(Reader reader) {
		noticeModifyAction.reader = reader;
	}

	public static SqlMapClient getSqlMapper() {
		return sqlMapper;
	}

	public static void setSqlMapper(SqlMapClient sqlMapper) {
		noticeModifyAction.sqlMapper = sqlMapper;
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

	public int getN_no() {
		return n_no;
	}

	public void setN_no(int n_no) {
		this.n_no = n_no;
	}

	public String getN_subject() {
		return n_subject;
	}

	public void setN_subject(String n_subject) {
		this.n_subject = n_subject;
	}

	public String getN_content() {
		return n_content;
	}

	public void setN_content(String n_content) {
		this.n_content = n_content;
	}

	public String getOld_file() {
		return old_file;
	}

	public void setOld_file(String old_file) {
		this.old_file = old_file;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

}
