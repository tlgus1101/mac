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
	
	private int currentPage = 1; //현재페이지
	private int totalCount; // 총 게시물 수
	private int blockCount = 10; //한 페이지에 게시물의 수
	private int blockPage =5; //한 화면에 보여줄 페이지 수
	private String pagingHtml; //페이징을 구현한 HTML
	private noticePagingAction page; //페이징클래스
	private int num =0;
	
	public noticeListAction() throws IOException{
		reader = Resources.getResourceAsReader("sqlMapConfig.xml");
		sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
		
		reader.close();
	}
	
	//게시판 LIST 액션
		public String execute() throws Exception {
			if (getSearchKeyword() != null) { //검색 키워드가 있으면
				return search(); //search()메소드 리턴
			}

			//모든 글을 가져와서 list에 넣는다.
			list = sqlMapper.queryForList("notice.selectAll");

			totalCount = list.size(); //전체 글 갯수를 구한다.
			
			//pagingAction 객체 생성
			page = new noticePagingAction(currentPage, totalCount, blockCount, blockPage, num, "");
			//pagingAction 생성자
			pagingHtml = page.getPagingHtml().toString();
			//페이지 HTML 생성, toString을 붙여주면 String으로 저장
			
			//현재 페이지에서 보여줄 마지막 글의 번호 설정 ->총 게시물의 수를 마지막 글번호로 설정한다.
			int lastCount = totalCount;

			//현재 페이지의 마지막 글의 번호가 전체의 마지막 글 번호보다 작으면
			if (page.getEndCount() < totalCount)
				lastCount = page.getEndCount() + 1;
			//한 페이지에서 보여줄 게시글의 끝 번호를 +1 해준다.

			//전체 리스트에서 현재 페이지만큼의 리스트만 가져온다.
			list = list.subList(page.getStartCount(), lastCount);
			return SUCCESS;
		}

		//검색 기능 (0=제목,1=내용)
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
			
			//pagingAction 객체 생성
			page = new noticePagingAction(currentPage, totalCount, blockCount, blockPage, searchNum, getSearchKeyword());
			//pagingAction 생성자
			pagingHtml = page.getPagingHtml().toString();

			//현재 페이지에서 보여줄 마지막 글의 번호 설정 ->총 게시물의 수를 마지막 글번호로 설정한다.
			int lastCount = totalCount;
			
			//현재 페이지의 마지막 글의 번호가 전체의 마지막 글 번호보다 작으면
			if (page.getEndCount() < totalCount)
				lastCount = page.getEndCount() + 1;
			//한 페이지에서 보여줄 게시글의 끝 번호를 +1 해준다.

			//전체 리스트에서 현재 페이지만큼의 리스트만 가져온다.
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
