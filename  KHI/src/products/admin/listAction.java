package products.admin;
/*	�����ϰ� ���� ��
 * 	1. ���� ������ �������� ��ǰ���
 * 	2. ���� ������ �������� ��ǰ���-�̸� �˻� �޼ҵ�
 * 	3. ���� ������ �������� ��ǰ���-��ȣ �˻� �޼ҵ�
 * 
 */

import com.opensymphony.xwork2.ActionSupport;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient; 
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
 
import java.util.*;
import java.io.Reader;
import java.io.IOException;

import products.ProductsVO;
import products.admin.pagingAction;

public class listAction extends ActionSupport {

	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	private List<ProductsVO> list = new ArrayList<ProductsVO>();
	private String searchKeyword;
	private int searchNum;
	
	private int currentPage = 1;	//���� ������
	private int totalCount; 		// �� �Խù��� ��
	private int blockCount = 10;	// �� ��������  �Խù��� ��
	private int blockPage = 5; 	// �� ȭ�鿡 ������ ������ ��
	private String pagingHtml; 	//����¡�� ������ HTML
	private pagingAction page; 	// ����¡ Ŭ����
	
	private int sortByQuan;
	private int sortByOption;
	
	public static final int QUANDESC = 1;
	public static final int QUANASE = 2;
	public static final int OPTIONDESE = 1;
	public static final int OPTIONASE = 2;
	
	
	// ������ �����ε�
	public listAction() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		// sqlMapConfig.xml ������ ���������� �����´�.
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		// sqlMapConfig.xml�� ������ ������ sqlMapper ��ü ����.
		reader.close();
	}
	
	
	

	public String execute() throws Exception{

		if(getSearchKeyword() != null) {
			return searchPd();
		}
		
		
		if(sortByQuan == QUANDESC) {
			list = sqlMapper.queryForList("products.p_selectAllQuanDesc");
			
			totalCount = list.size();
			page = new pagingAction(currentPage, totalCount, blockCount, blockPage);
			pagingHtml = page.getPagingHtml().toString();
			
			int lastCount = totalCount;
			
			if(page.getEndCount()<totalCount)
				lastCount = page.getEndCount()	+1;
			
			list = list.subList(page.getStartCount(), lastCount);
		}else if(sortByQuan == QUANASE) {
			list = sqlMapper.queryForList("products.p_selectAllQuanAsc");
			
			totalCount = list.size();// ��ü ���� ����
			page = new pagingAction(currentPage, totalCount, blockCount, blockPage);
			pagingHtml = page.getPagingHtml().toString();
			
			int lastCount = totalCount;
			
			if(page.getEndCount()<totalCount)
				lastCount = page.getEndCount()	+1;
			
			list = list.subList(page.getStartCount(), lastCount);
		}else if(sortByOption == OPTIONDESE) {
			list = sqlMapper.queryForList("products.p_selectAllOptionDesc");
			
			totalCount = list.size();// ��ü ���� ����
			page = new pagingAction(currentPage, totalCount, blockCount, blockPage);
			pagingHtml = page.getPagingHtml().toString();
			
			int lastCount = totalCount;
			
			if(page.getEndCount()<totalCount)
				lastCount = page.getEndCount()	+1;
			
			list = list.subList(page.getStartCount(), lastCount);
		}else if(sortByOption == OPTIONASE) {
			list = sqlMapper.queryForList("products.p_selectAllOptionAsc");
			
			totalCount = list.size();// ��ü ���� ����
			page = new pagingAction(currentPage, totalCount, blockCount, blockPage);
			pagingHtml = page.getPagingHtml().toString();
			
			int lastCount = totalCount;
			
			if(page.getEndCount()<totalCount)
				lastCount = page.getEndCount()	+1;
			
			list = list.subList(page.getStartCount(), lastCount);
		}else{
			// �Ǹ� ���� ��� ����Ʈ�� �����´�.
			list = sqlMapper.queryForList("products.p_selectAll");
			
			// ��ü ���� ������ ���Ѵ�.
			totalCount = list.size();// ��ü ���� ����
			page = new pagingAction(currentPage, totalCount, blockCount, blockPage);
			pagingHtml = page.getPagingHtml().toString();
			// ������ html�����ϱ�
			
			int lastCount = totalCount;
			// ���� ���������� ������ ������ ���� ��ȣ �����ϱ�
			
			if(page.getEndCount()<totalCount)
				lastCount = page.getEndCount()	+1;
			
			list = list.subList(page.getStartCount(), lastCount);
		}
		
		return SUCCESS;
	}
	
	// ���� �Խ��� �Ǹ������ǰ list �׼�
		public String finishSale() throws Exception{
			
			// �˻����� �߰�
			if(getSearchKeyword() != null) {
				return searchPd();
			}
			
			// �Ǹ� ����� ��� ����Ʈ�� �����´�.
			list = sqlMapper.queryForList("products.p_finishSale");
			
			// ��ü ���� ������ ���Ѵ�.
			totalCount = list.size();// ��ü ���� ����
			page = new pagingAction(currentPage, totalCount, blockCount, blockPage);
			pagingHtml = page.getPagingHtml().toString();
			// ������ html�����ϱ�
			
			int lastCount = totalCount;
			// ���� ���������� ������ ������ ���� ��ȣ �����ϱ�
			
			if(page.getEndCount()<totalCount)
				lastCount = page.getEndCount()	+1;
			
			list = list.subList(page.getStartCount(), lastCount);
			
			return SUCCESS;
		}
	
	
	// �˻� �޼ҵ�
	public String searchPd() throws Exception {
		
		String searchType = null;
		
		if(searchNum == 0) { 
			list = sqlMapper.queryForList("products.p_selectSearchName", "%"+getSearchKeyword()+"%");
		}
		if(searchNum == 1) { 
			list = sqlMapper.queryForList("products.p_selectSearchNo", "%"+getSearchKeyword()+"%");
		}
		if(searchNum == 2) {
			
			if(getSearchKeyword().equals("발코니")){
				searchType= "1";
			}
			if(getSearchKeyword().equals("내창")){
				searchType= "2";
			}
			if(getSearchKeyword().equals("악세사리")){
				searchType= "3";
			}
			
			list = sqlMapper.queryForList("products.p_selectSearchType", searchType);

		}
		
		totalCount = list.size();// ��ü ���� ����
		page = new pagingAction(currentPage, totalCount, blockCount, blockPage);
		pagingHtml = page.getPagingHtml().toString();
	
		int lastCount = totalCount;
		if(page.getEndCount() < totalCount)
			lastCount = page.getEndCount() + 1;
		list = list.subList(page.getStartCount(), lastCount); 
		return SUCCESS;
	}


	public List<ProductsVO> getList() {
		return list;
	}



	public void setList(List<ProductsVO> list) {
		this.list = list;
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



	public int getBlockCount() {
		return blockCount;
	}



	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
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



	public pagingAction getPage() {
		return page;
	}



	public void setPage(pagingAction page) {
		this.page = page;
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



	public int getSortByQuan() {
		return sortByQuan;
	}



	public void setSortByQuan(int sortByQuan) {
		this.sortByQuan = sortByQuan;
	}



	public int getSortByOption() {
		return sortByOption;
	}



	public void setSortByOption(int sortByOption) {
		this.sortByOption = sortByOption;
	}

	
	
}
