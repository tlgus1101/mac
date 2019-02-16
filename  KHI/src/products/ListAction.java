package products;

import com.opensymphony.xwork2.ActionSupport;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

import java.util.*;
import java.io.Reader;
import java.io.IOException;

import products.ProductsVO;
import products.WinPagingAction;

public class ListAction extends ActionSupport{

	public static Reader reader;
	public static SqlMapClient sqlMapper;
	
	private List<ProductsVO> list = new ArrayList<ProductsVO>();
	// bean
	
	private int currentPage = 1;
	private int totalCount;
	private int blockCount=9;
	private int blockPage = 5;
	private String pagingHtml;
	private WinPagingAction page;
	// paging�� �ʿ��� �͵�
	
	// ������ �����ε��ϱ�
	public ListAction() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml"); // ���Ǳ� ���� ��������
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader); // sql�� ���� �̷��� SQL�� ��� ����
		reader.close(); // �ʿ� ���� �� �ݴ´�.
	}
	
	public String execute() throws Exception{
		// ��ü����Ʈ �ҷ����� �޼ҵ�. �̰� ���߿� admin���� ��� ����Ʈ�� �ҷ��� �� ����Ѵ�.
		list = sqlMapper.queryForList("products.p_selectOut");
		// ���ڴ� ����Ʈ�� �ҷ��´�.
		
		totalCount = list.size(); // list��ü�� ũ��- totalCount
		page = new WinPagingAction(currentPage, totalCount, blockCount, blockPage);
		pagingHtml = page.getPagingHtml().toString();
		
		int lastCount = totalCount;
		if(page.getEndCount()<totalCount) // ������ �������� ��ü ������ ������
			lastCount = page.getEndCount()+1; // +1 �ϱ�.
		// ��������� ����¡�ϱ�	
//		System.out.println("��Ż: "+totalCount);
		list = list.subList(page.getStartCount(), lastCount);
		// list��ü�� ù�ٺ��� ���ٱ��� ��ƿ´�.
//		System.out.println("����Ʈ: "+list);
		return SUCCESS;
	}
	
	
	public String listIn() throws Exception{
		// ���� ������ �������� ��â ����Ʈ�� �ҷ��´�.
		list = sqlMapper.queryForList("products.p_selectIn");
		
		totalCount = list.size();
		page = new WinPagingAction(currentPage, totalCount, blockCount, blockPage);
		pagingHtml = page.getPagingHtml().toString();
		
		int lastCount = totalCount;
		if(page.getEndCount()<totalCount)
			lastCount = page.getEndCount()+1;
		
//		System.out.println("��â ��Ż: "+totalCount);
		list = list.subList(page.getStartCount(), lastCount);
		// list��ü�� ù�ٺ��� ���ٱ��� ��ƿ´�.
//		System.out.println("��â ����Ʈ: "+list);
		
		return SUCCESS;
	}
	
	public String listAcc() throws Exception{
		// ���� ������ �������� �Ǽ����� ����Ʈ�� �ҷ��´�.
		list = sqlMapper.queryForList("products.p_selectAcc");
		
		totalCount = list.size();
		page = new WinPagingAction(currentPage, totalCount, blockCount, blockPage);
		pagingHtml = page.getPagingHtml().toString();
		
		int lastCount = totalCount;
		if(page.getEndCount()<totalCount)
			lastCount = page.getEndCount()+1;
		
//		System.out.println("�Ǽ� ��Ż: "+totalCount);
		list = list.subList(page.getStartCount(), lastCount);
		// list��ü�� ù�ٺ��� ���ٱ��� ��ƿ´�.
//		System.out.println("�Ǽ� ����Ʈ: "+list);	
		
		return SUCCESS;
	}
	
	
	public static Reader getReader() {
		return reader;
	}

	public static void setReader(Reader reader) {
		ListAction.reader = reader;
	}

	public static SqlMapClient getSqlMapper() {
		return sqlMapper;
	}

	public static void setSqlMapper(SqlMapClient sqlMapper) {
		ListAction.sqlMapper = sqlMapper;
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

	public List<ProductsVO> getList() {
		return list;
	}

	public void setList(List<ProductsVO> list) {
		this.list = list;
	}

	public WinPagingAction getPage() {
		return page;
	}

	public void setPage(WinPagingAction page) {
		this.page = page;
	}
}
