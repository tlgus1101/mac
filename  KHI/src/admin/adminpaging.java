package admin;

public class adminpaging {

	private int currentPage; // 1111111111
	private int totalCount; // 11ü 1Խù1 11
	private int totalPage; // 11ü 111111 11
	private int blockCount; // 11 11111111 1Խù111 11
	private int blockPage; // 11 ȭ1鿡 111111 111111 11
	private int startCount; // 11 1111111111 111111 1Խñ111 1111 11ȣ
	private int endCount; // 11 1111111111 111111 1Խñ111 11 11ȣ
	private int startPage; // 1111 111111
	private int endPage; // 111111 111111

	private StringBuffer pagingHtml;

	// 1111¡ 111111
	public adminpaging(int currentPage, int totalCount, int blockCount, int blockPage, int optionNum) {

		this.blockCount = blockCount;
		this.blockPage = blockPage;
		this.currentPage = currentPage;
		this.totalCount = totalCount;

		// 11ü 111111 11
		totalPage = (int) Math.ceil((double) totalCount / blockCount);
		if (totalPage == 0) {
			totalPage = 1;
		}

		// 1111 11111111 11ü 111111 111111 ũ11 11ü 111111 1111 1111
		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		// 1111 11111111 ó1111 111111 1111 11ȣ 11111111.
		startCount = (currentPage - 1) * blockCount;
		endCount = startCount + blockCount - 1;

		// 1111 11111111 111111 111111 11 111ϱ1.
		startPage = (int) ((currentPage - 1) / blockPage) * blockPage + 1;
		endPage = startPage + blockPage - 1;

		// 111111 11111111 11ü 111111 111111 ũ11 11ü 111111 1111 1111
		if (endPage > totalPage) {
			endPage = totalPage;
		}

		// 1111 block 111111
		pagingHtml = new StringBuffer();
		if (currentPage > blockPage) {
			if (optionNum == 1) {
				pagingHtml.append("<a href=QminiAction.action?currentPage=" + (startPage - 1) + ">");
				pagingHtml.append(" 이전 ");
				pagingHtml.append("</a>");
			} else {
				pagingHtml.append("<a href=CminiAction.action?currentPage=" + (startPage - 1) + ">");
				pagingHtml.append(" 이전 ");
				pagingHtml.append("</a>");
			}
		}

		pagingHtml.append("&nbsp;|&nbsp;");

		// 111111 11ȣ.1111 11111111 1111111111 11111ϰ1 11ũ11 1111.
		for (int i = startPage; i <= endPage; i++) {
			if (i > totalPage) {
				break;
			}
			if (i == currentPage) {
				pagingHtml.append("&nbsp;<b> <font color='red'>");
				pagingHtml.append(i);
				pagingHtml.append("</font></b>");
			} else {
				if (optionNum == 1) {
					pagingHtml.append("&nbsp;<a href='QminiAction.action?currentPage=");
					pagingHtml.append(i);
					pagingHtml.append("'>");
					pagingHtml.append(i);
					pagingHtml.append("</a>");
				} else {
					pagingHtml.append("&nbsp;<a href='CminiAction.action?currentPage=");
					pagingHtml.append(i);
					pagingHtml.append("'>");
					pagingHtml.append(i);
					pagingHtml.append("</a>");
				}
			}

			pagingHtml.append("&nbsp;");
		}

		pagingHtml.append("&nbsp;&nbsp;|&nbsp;&nbsp;");

		// 1111 block 111111
		System.out.println(totalPage - startPage+" "+blockPage);
		if (totalPage - startPage >= blockPage) {
			if (optionNum == 1) {
				pagingHtml.append("<a href=QminiAction.action?currentPage=" + (endPage + 1) + ">");
				pagingHtml.append("다음 ");
				pagingHtml.append("</a>");
			} else {
				pagingHtml.append("<a href=CminiAction.action?currentPage=" + (endPage + 1) + ">");
				pagingHtml.append("다음 ");
				pagingHtml.append("</a>");
			}
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