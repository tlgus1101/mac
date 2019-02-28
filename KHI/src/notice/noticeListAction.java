package notice;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.opensymphony.xwork2.ActionSupport;



public class noticeListAction extends ActionSupport {
	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	private List<noticeVO> list= new ArrayList<noticeVO>();
	
	private String searchKeyword;
	private int searchNum;
	
	private int currentPage = 1; //����������
	private int totalCount; // �� �Խù� ��
	private int blockCount = 10; //�� �������� �Խù��� ��
	private int blockPage =5; //�� ȭ�鿡 ������ ������ ��
	private String pagingHtml; //����¡�� ������ HTML
	private noticePagingAction page; //����¡Ŭ����
	private int num =0;
	
	public noticeListAction() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		
		reader.close();
	}
	
	//�Խ��� LIST �׼�
		public String execute() throws Exception {
			if (getSearchKeyword() != null) { //�˻� Ű���尡 ������
				return search(); //search()�޼ҵ� ����
			}

			//��� ���� �����ͼ� list�� �ִ´�.
			list = sqlMapper.queryForList("notice.selectAll");

			totalCount = list.size(); //��ü �� ������ ���Ѵ�.
			
			//pagingAction ��ü ����
			page = new noticePagingAction(currentPage, totalCount, blockCount, blockPage, num, "");
			//pagingAction ������
			pagingHtml = page.getPagingHtml().toString();
			//������ HTML ����, toString�� �ٿ��ָ� String���� ����
			
			//���� ���������� ������ ������ ���� ��ȣ ���� ->�� �Խù��� ���� ������ �۹�ȣ�� �����Ѵ�.
			int lastCount = totalCount;

			//���� �������� ������ ���� ��ȣ�� ��ü�� ������ �� ��ȣ���� ������
			if (page.getEndCount() < totalCount)
				lastCount = page.getEndCount() + 1;
			//�� ���������� ������ �Խñ��� �� ��ȣ�� +1 ���ش�.

			//��ü ����Ʈ���� ���� ��������ŭ�� ����Ʈ�� �����´�.
			list = list.subList(page.getStartCount(), lastCount);
			return SUCCESS;
		}

		//�˻� ��� (0=����,1=����)
		public String search() throws Exception {

			// searchKeyword = new String(searchKeyword.getBytes("iso-8859-1"),"euc-kr") ;
			// System.out.println(searchKeyword);
			// System.out.println(searchNum);
			
			if (searchNum == 0) {
				list = sqlMapper.queryForList("notice.selectSearchS", "%" + getSearchKeyword() + "%");
			}
			if (searchNum == 1) {
				list = sqlMapper.queryForList("notice.selectSearchC", "%" + getSearchKeyword() + "%");
			}

			totalCount = list.size();
			
			//pagingAction ��ü ����
			page = new noticePagingAction(currentPage, totalCount, blockCount, blockPage, searchNum, getSearchKeyword());
			//pagingAction ������
			pagingHtml = page.getPagingHtml().toString();

			//���� ���������� ������ ������ ���� ��ȣ ���� ->�� �Խù��� ���� ������ �۹�ȣ�� �����Ѵ�.
			int lastCount = totalCount;
			
			//���� �������� ������ ���� ��ȣ�� ��ü�� ������ �� ��ȣ���� ������
			if (page.getEndCount() < totalCount)
				lastCount = page.getEndCount() + 1;
			//�� ���������� ������ �Խñ��� �� ��ȣ�� +1 ���ش�.

			//��ü ����Ʈ���� ���� ��������ŭ�� ����Ʈ�� �����´�.
			list = list.subList(page.getStartCount(), lastCount);
			return SUCCESS;
		}

	public static Reader getReader() {
		return reader;
	}

	public static void setReader(Reader reader) {
		noticeListAction.reader = reader;
	}

	public static SqlMapClient getSqlMapper() {
		return sqlMapper;
	}

	public static void setSqlMapper(SqlMapClient sqlMapper) {
		noticeListAction.sqlMapper = sqlMapper;
	}

	public List<noticeVO> getList() {
		return list;
	}

	public void setList(List<noticeVO> list) {
		this.list = list;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getSearchNum() {
		return searchNum;
	}

	public void setSearchNum(int searchNum) {
		this.searchNum = searchNum;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getBlockPage() {
		return blockPage;
	}

	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}

	public String getPagingHtml() {
		return pagingHtml;
	}

	public void setPagingHtml(String pagingHtml) {
		this.pagingHtml = pagingHtml;
	}

	public noticePagingAction getPage() {
		return page;
	}

	public void setPage(noticePagingAction page) {
		this.page = page;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	

}
