package window;

public class windowPaging {
	private int currentPage;   // ���������� 1
	private int totalCount;	 // ��ü �Խù� ��
	private int totalPage;	 // ��ü ������ ��
	private int blockCount;	 // �� ��������  �Խù��� �� 10
	private int blockPage;	 // �� ȭ�鿡 ������ ������ �� 5
	private int startCount;	 // �� ���������� ������ �Խñ��� ���� ��ȣ
	private int endCount;	 // �� ���������� ������ �Խñ��� �� ��ȣ
	private int startPage;	 // ���� ������
	private int endPage;	 // ������ ������

	
	private StringBuffer pagingHtml; //���� �� ��� �͵��� ���⿡
	
	public windowPaging() {
		
	}
	//������
	public windowPaging(int currentPage, int totalCount, int blockCount, int blockPage, int searchNum, String isSearch) {
		this.blockCount = blockCount;
		this.blockPage = blockPage;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		//��ü ������ ��
		totalPage = (int)Math.ceil((double) totalCount/blockCount); //��ü�Խù� �� / �� �������� �Խù��� ��
		if(totalPage == 0) {
			totalPage = 1;
		} //���� �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if(currentPage > totalPage) {
			currentPage = totalPage;
		} //���� �������� ó���� ������ ���� ��ȣ ��������
		startCount = (currentPage - 1) * blockCount;  //0~9
		endCount = startCount + blockCount - 1;
		//������������ ������ ������ �� ���ϱ�
		startPage = (int)((currentPage - 1) / blockPage) * blockPage + 1; //0~4
		endPage = startPage + blockPage - 1;
		//������ �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		//����
		pagingHtml = new StringBuffer();
		if(currentPage > blockPage) {
			if(isSearch != "")
				pagingHtml.append("<a href=winListAction.action?currentPage=" + (startPage - 1) + "&searchKeyword="+isSearch+"&searchNum="+searchNum+">");
			else
				pagingHtml.append("<a href=winListAction.action?currentPage=" + (startPage - 1) + ">");
			pagingHtml.append("����");
			pagingHtml.append("</a>");
		}
			
		pagingHtml.append("&nbsp;|&nbsp;");
		//������ ��ȣ, ���� �������� ���������� �����ϰ� ��ũ�� ����.
		for(int i = startPage; i <= endPage; i++) {
			if(i>totalPage) {
				break;
			}
			if(i == currentPage) {
				pagingHtml.append("&nbsp;<b> <font color='red'>");
				pagingHtml.append(i);
				pagingHtml.append("</font></b>");
			} else {
				pagingHtml.append("&nbsp;<a href='winListAction.action?currentPage=");
				pagingHtml.append(i); //������ �̵��ǰ� ��ũ �ɾ��ش�.
				if(isSearch != "")
					pagingHtml.append("&searchKeyword="+isSearch);
				pagingHtml.append("'>");
				pagingHtml.append(i);
				pagingHtml.append("</a>");
			}
			pagingHtml.append("&nbsp;");
		}
		
		pagingHtml.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		
		// ���� block������
		if(totalPage - startPage >= blockPage) {
			pagingHtml.append("nbsp;<a href='winListAction.action?currentPage=");
			pagingHtml.append((endPage+1));
			if(isSearch != "")
				pagingHtml.append("&searchKeyword="+isSearch);
			pagingHtml.append("'>");
			pagingHtml.append("����");
			pagingHtml.append("</a>");
		}
	}
	public void windowPagingList(int currentPage, int totalCount, int blockCount, int blockPage, int searchNum, String isSearch) {
		this.blockCount = blockCount;
		this.blockPage = blockPage;
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		//��ü ������ ��
		totalPage = (int)Math.ceil((double) totalCount/blockCount); //��ü�Խù� �� / �� �������� �Խù��� ��
		if(totalPage == 0) {
			totalPage = 1;
		} //���� �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if(currentPage > totalPage) {
			currentPage = totalPage;
		} //���� �������� ó���� ������ ���� ��ȣ ��������
		startCount = (currentPage - 1) * blockCount;  //0~9
		endCount = startCount + blockCount - 1;
		//������������ ������ ������ �� ���ϱ�
		startPage = (int)((currentPage - 1) / blockPage) * blockPage + 1; //0~4
		endPage = startPage + blockPage - 1;
		//������ �������� ��ü ������ ������ ũ�� ��ü ������ ���� ����
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		//����
		pagingHtml = new StringBuffer();
		if(currentPage > blockPage) {
			if(isSearch != "")
				pagingHtml.append("<a href=adminList.action?currentPage=" + (startPage - 1) + "&searchKeyword="+isSearch+"&searchNum="+searchNum+">");
			else
				pagingHtml.append("<a href=adminList.action?currentPage=" + (startPage - 1) + ">");
			pagingHtml.append("����");
			pagingHtml.append("</a>");
		}
			
		pagingHtml.append("&nbsp;|&nbsp;");
		//������ ��ȣ, ���� �������� ���������� �����ϰ� ��ũ�� ����.
		for(int i = startPage; i <= endPage; i++) {
			if(i>totalPage) {
				break;
			}
			if(i == currentPage) {
				pagingHtml.append("&nbsp;<b> <font color='red'>");
				pagingHtml.append(i);
				pagingHtml.append("</font></b>");
			} else {
				pagingHtml.append("&nbsp;<a href='adminList.action?currentPage=");
				pagingHtml.append(i); //������ �̵��ǰ� ��ũ �ɾ��ش�.
				if(isSearch != "")
					pagingHtml.append("&searchKeyword="+isSearch);
				pagingHtml.append("'>");
				pagingHtml.append(i);
				pagingHtml.append("</a>");
			}
			pagingHtml.append("&nbsp;");
		}
		
		pagingHtml.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		
		// ���� block������
		if(totalPage - startPage >= blockPage) {
			pagingHtml.append("nbsp;<a href='adminList.action?currentPage=");
			pagingHtml.append((endPage+1));
			if(isSearch != "")
				pagingHtml.append("&searchKeyword="+isSearch);
			pagingHtml.append("'>");
			pagingHtml.append("����");
			pagingHtml.append("</a>");
		}
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

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
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

	public int getStartCount() {
		return startCount;
	}

	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}

	public int getEndCount() {
		return endCount;
	}

	public void setEndCount(int endCount) {
		this.endCount = endCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public StringBuffer getPagingHtml() {
		return pagingHtml;
	}

	public void setPagingHtml(StringBuffer pagingHtml) {
		this.pagingHtml = pagingHtml;
	}

	
	
	
}

