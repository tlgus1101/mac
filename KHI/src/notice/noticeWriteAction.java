package notice;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.opensymphony.xwork2.ActionSupport;



public class noticeWriteAction extends ActionSupport {
	
	public static Reader reader; //���� ��Ʈ���� ���� reader.
	public static SqlMapClient sqlMapper; //SqlMapClient API�� ����ϱ� ���� sqlMapper ��ü
	
	private noticeVO paramClass; //�Ķ���͸� ������ ��ü (��ü�� �����ϱ� ����)
	private noticeVO resultClass; //���� ��� ���� ������ ��ü (ó���� ����� �ޱ� ����)
	
	private int currentPage; //���� ������

	private int n_no; //�� ��ȣ
	private String n_subject; //����
	private String n_content; //�� ����
	private String n_file_orgName; //���ε� ������ ���� �̸�
	private String n_file_savName; 
	Calendar today = Calendar.getInstance(); //���� ��¥ ���ϱ�.

	private File upload; //���� ��ü
	private String uploadContentType; //������ Ÿ��
	private String uploadFileName; //���� �̸�
	private String fileUploadPath =  "C:\\java\\App\\KHI\\WebContent\\images\\"; //���ε� ���.
	
	//������
		public noticeWriteAction() throws IOException {
			reader = Resources.getResourceAsReader("sqlMapConfig.xml"); // sqlMapConfig.xml ������ ���������� �����´�.
			sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader); // sqlMapConfig.xml�� ������ ������ sqlMapper ��ü ����.

			reader.close();
		}
		
		//�����
		public String form() throws Exception {
			return SUCCESS;

		}
		
		//�Խ��� WRITE �׼�
		public String execute() throws Exception {
			
			//�Ķ���Ϳ� ����Ʈ ��ü ����
			paramClass = new noticeVO();
			resultClass = new noticeVO();
			
			//����� �׸� ����
			paramClass.setN_subject(getN_subject());
			paramClass.setN_content(getN_content());
			paramClass.setN_regdate(today.getTime());
			
			//��� ���� ����
			sqlMapper.insert("notice.insertNotice", paramClass);
			
			//÷�������� �����ߴٸ� ������ ���ε��Ѵ�.
			if (getUpload() != null) {
				resultClass = (noticeVO) 
						sqlMapper.queryForObject("notice.selectLastNo");
				String file_name = "file_" + resultClass.getN_no();//file_�۹�ȣ
				String file_ext = getUploadFileName().substring( //�����̸�
						getUploadFileName().lastIndexOf('.') + 1,//Ȯ���� �����´�.
						getUploadFileName().length());
				
				File destFile = new File(fileUploadPath + file_name + "." //���ε� ���+�����̸�+.+Ȯ����
						+ file_ext);
				FileUtils.copyFile(getUpload(), destFile);//���ϰ�ü�� destFile�־ copy
				
				paramClass.setN_no(resultClass.getN_no());
				paramClass.setN_file_orgname(getUploadFileName());	//���� ���� �̸�
				paramClass.setN_file_savname(file_name + "." + file_ext); //������ ������ ���� �̸�

	//paramClass : �ڹٺ�
				//���� ���� ������Ʈ.(update�� ȣ���ؼ� �۾��Ѵ�)
				sqlMapper.update("notice.updateFile", paramClass);


			}
			return SUCCESS;
			
		}

		public static Reader getReader() {
			return reader;
		}

		public static void setReader(Reader reader) {
			noticeWriteAction.reader = reader;
		}

		public static SqlMapClient getSqlMapper() {
			return sqlMapper;
		}

		public static void setSqlMapper(SqlMapClient sqlMapper) {
			noticeWriteAction.sqlMapper = sqlMapper;
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

		public String getN_file_orgName() {
			return n_file_orgName;
		}

		public void setN_file_orgName(String n_file_orgName) {
			this.n_file_orgName = n_file_orgName;
		}

		public Calendar getToday() {
			return today;
		}

		public void setToday(Calendar today) {
			this.today = today;
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

		public String getN_file_savName() {
			return n_file_savName;
		}

		public void setN_file_savName(String n_file_savName) {
			this.n_file_savName = n_file_savName;
		}
		
		
	
}
