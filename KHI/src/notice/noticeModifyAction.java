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

	private noticeVO paramClass; // �Ķ���͸� ������ ��ü
	private noticeVO resultClass; // ���� ��� ���� ������ ��ü

	private int currentPage; // ���� ������

	private int n_no;
	private String n_subject;
	private String n_content;
	private String old_file;

	private File upload; // ���� ��ü
	private String uploadContentType; // ������ Ÿ��
	private String uploadFileName; // ���� �̸�
	private String fileUploadPath = "C:\\java\\App\\KHI\\WebContent\\images\\"; // ���ε� ���.
	// ���۵� ���� �ޱ� ���� ���� ����

	public noticeModifyAction() throws IOException {

		reader = Resources.getResourceAsReader("sqlMapConfig.xml"); // sqlMapConfig.xml ������ ���������� �����´�.
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader); // sqlMapConfig.xml�� ������ ������ sqlMapper ��ü ����.
		reader.close();
	}

	// �Խñ� ����
	public String execute() throws Exception {

		// �Ķ���Ϳ� ����Ʈ ��ü ����.
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

			// ���� ���� ����
			// �̹� ���ε�Ǿ��ִ� ������ �����Ѵ�.
			File deleteFile = new File(fileUploadPath + getOld_file());
			deleteFile.delete();

			// �� ���� ���ε�
			File destFile = new File(fileUploadPath + file_name + "." + file_ext);
			FileUtils.copyFile(getUpload(), destFile);

			// ���� ���� �Ķ���� ����.
			paramClass.setN_file_orgname(getUploadFileName());
			paramClass.setN_file_savname(file_name + "." + file_ext);

			// ���� ���� ������Ʈ.
			sqlMapper.update("notice.updateFile", paramClass);
		}
		// ������ ������ view �������� �̵�.
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
