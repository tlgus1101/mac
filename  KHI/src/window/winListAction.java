package window;

import com.opensymphony.xwork2.ActionSupport;


import window.windowVO;
import window.windowPaging;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient; 
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
 
import java.util.*;
import java.io.Reader;
import java.io.IOException;


public class winListAction extends ActionSupport {
	public static Reader reader;	//���� ��Ʈ���� ���� reader.
	public static SqlMapClient sqlMapper;	//SqlMapClient API�� ����ϱ� ���� sqlMapper ��ü.

	private List<windowVO> list = new ArrayList<windowVO>();;
	private String searchKeyword;
	private int searchNum;
	
	private int currentPage = 1;	//���� ������
	private int totalCount; 		// �� �Խù��� ��
	private int blockCount = 10;	// �� ��������  �Խù��� ��
	private int blockPage = 5; 	// �� ȭ�鿡 ������ ������ ��
	private String pagingHtml; 	//����¡�� ������ HTML
	private windowPaging page; 	// ����¡ Ŭ����
	private int num=0;

	public winListAction() throws IOException {
		reader = Resources.getResourceAsReader("sqlMapConfig.xml"); //sqlMapConfig.xml������ ���������� �����´�.
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader); //sqlMapconfig.xml�� ������ ������ sqlMapper ��ü ����.
		reader.close();
	}
public String execute() throws Exception {
		
		if(getSearchKeyword() != null) {
			return search();
		}
		list = sqlMapper.queryForList("windowALL"); //������ ������ ����Ʈ�� ����
		totalCount = list.size(); //��ü �� ������ ����
		page = new windowPaging(currentPage, totalCount, blockCount, blockPage, num, ""); //����¡�׼� ��ü����
		pagingHtml = page.getPagingHtml().toString(); //������HTML����.
		//���� ���������� ������ ������ ���� ��ȣ����.
		int lastCount = totalCount;
		//���� �������� ������ ���� ��ȣ�� ��ü�� ������ �� ��ȣ���� ������ lastCount +1��ȣ�� ����
		if(page.getEndCount() < totalCount)
			lastCount = page.getEndCount() + 1;
		//��ü ����Ʈ���� ���� ��������ŭ�� ����Ʈ�� �����´�.
		list = list.subList(page.getStartCount(), lastCount);

		
		return SUCCESS;
	}
public String search() throws Exception {
	if(searchNum == 0) { //��ҷ� �˻�
		list = sqlMapper.queryForList("windowSearchA", "%"+getSearchKeyword()+"%");
	}
	if(searchNum == 1) { //����
		list = sqlMapper.queryForList("windowSearchB", "%"+getSearchKeyword()+"%");
}
	
	totalCount = list.size(); //��Ƴ� ������ �־��ְ� ����¡ �غ� �Ϸ�
	page = new windowPaging(currentPage, totalCount, blockCount, blockPage, searchNum, getSearchKeyword());
	pagingHtml = page.getPagingHtml().toString();
	
	int lastCount = totalCount;
	if(page.getEndCount() < totalCount)
		lastCount = page.getEndCount() + 1;
	list = list.subList(page.getStartCount(), lastCount); 
	return SUCCESS;
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

public List<windowVO> getList() {
	return list;
}
public void setList(List<windowVO> list) {
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

public windowPaging getPage() {
	return page;
}
public void setPage(windowPaging page) {
	this.page = page;
}



}

