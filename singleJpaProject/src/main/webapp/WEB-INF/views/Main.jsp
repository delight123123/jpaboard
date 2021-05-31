<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@include file="inc/mainTop.jsp" %>
<form action="<c:url value=''/>" name="frmPage" method="post">
	<input type="hidden" name="searchCondition"
		value="${param.searchCondition}" id="aa1"> <input
		type="hidden" name="searchKeyword" value="${param.searchKeyword}"
		id="aa2"> <input type="hidden" name="currentPage"
		value="${pagingInfo.currentPage }" id="aa3">

</form>


<div class="content-wrapper">
	<div class="card">
		<div class="card-body" style="width: 100%; overflow: auto;">
			<table class="table text-center" style="width: 100%;">
				<colgroup>
					<col style="width: 10%;" />
					<col style="width: 50%;" />
					<col style="width: 15%;" />
					<col style="width: 15%;" />
					<col style="width: 10%;" />
				</colgroup>
				<thead style="width: 100%;">
					<tr class="">
						<th scope="col">번호</th>
						<th scope="col">제목</th>
						<th scope="col">작성자</th>
						<th scope="col">작성일</th>
						<th scope="col">조회수</th>
					</tr>
				</thead>
				<tbody style="width: 100%;">
					<c:if test="${empty list }">
						<tr>
							<td colspan="5">해당 글이 존재하지 않습니다.</td>
						</tr>
					</c:if>
					<c:if test="${!empty list }">
						<c:forEach var="vo" items="${list }">
							<tr>
								<c:if test="${vo.delflag=='Y' }">
									<td>${vo.reboardNo}</td>
									<td style="text-align: left; color: gray;">삭제된 글입니다.</td>
									<td>${vo.registerVo.userid}</td>
									<td><fmt:formatDate value="${vo.reboardReg }"
											pattern="yyyy-MM-dd" /></td>
									<td>${vo.readcount}</td>
								</c:if>
								<c:if test="${vo.delflag=='N' }">
									<td>${vo.reboardNo}</td>
									<td style="text-align: left"><c:forEach begin="0"
											end="${vo.step }" var="a">
											<c:if test="${a<vo.step }">
												<img src="<c:url value='/resources/images/re.gif'/>">
											</c:if>
											
										</c:forEach> 
										<a href="<c:url value='/readCnt?reNo=${vo.reboardNo }'/>">
											<!-- 제목이 긴 경우 일부만 보여주기--> <c:if
												test="${fn:length(vo.reboardTitle)>30}">
									${fn:substring(vo.reboardTitle, 0,30)}...
								</c:if> <c:if test="${fn:length(vo.reboardTitle)<=30}">
									${vo.reboardTitle}
								</c:if>
									</a> <!-- 24시간 이내의 글인 경우 new 이미지 보여주기 --> 
									<fmt:parseDate var="toDay_D"  value="${toDay }" pattern="yyyy-MM-dd"/>
									<fmt:parseDate var="regDay_D" value="${vo.reboardReg }"  pattern="yyyy-MM-dd"/>
									<fmt:parseNumber var="toDay_N" value="${toDay_D.time / (1000*60*60*24)}" integerOnly="true" />
									<fmt:parseNumber var="regDay_N" value="${regDay_D.time / (1000*60*60*24)}" integerOnly="true" /> 
									<c:if test="${upfileCount[vo.reboardNo]>0 }">
										<img src='<c:url value='/resources/images/file.gif'/>' >
									</c:if>
									
									<c:if
											test="${(today_N - regDay_N)<24 }">
											<img src="<c:url value='/resources/images/new.gif'/>"
												alt="new 이미지">
										</c:if></td>
									<td>${vo.registerVo.userid}</td>
									<td><fmt:formatDate value="${vo.reboardReg }"
											pattern="yyyy-MM-dd" /></td>
									<td>${vo.readcount}</td>
								</c:if>
							</tr>
						</c:forEach>
					</c:if>

				</tbody>
			</table>

			<div class="divPage text-center">
				<!-- 이전블럭으로 이동 -->
				<c:if test="${pagingInfo.firstPage>1 }">
					<button type="button"
						class='btn btn-social-icon btn-outline-youtube btn-sm'
						onclick="pageFunc(${pagingInfo.firstPage-1})">&lt;&lt;</button>
				</c:if>
				<!-- 페이지 번호 추가 -->

				<c:forEach var="i" begin="${pagingInfo.firstPage }"
					end="${pagingInfo.lastPage }">
					<c:if test="${i==pagingInfo.currentPage }">
						<span class='btn btn-success btn-sm'>${i }</span>
					</c:if>
					<c:if test="${i!=pagingInfo.currentPage }">
						<input type='button' value="${i }" class='btn btn-danger btn-sm'
							onclick="pageFunc(${i})">
					</c:if>
				</c:forEach>
				<!--  페이지 번호 끝 -->

				<!-- 다음블럭으로 이동 -->
				<c:if test="${pagingInfo.lastPage<pagingInfo.totalPage }">
					<button type="button"
						class="btn btn-social-icon btn-outline-youtube btn-sm"
						onclick="pageFunc(${pagingInfo.lastPage+1})">&gt;&gt;</button>
				</c:if>
			</div>
			<div class="divSearch">
				<form name="frmSearch" method="post" action='<c:url value=' /main'/>
				'>
				<div class="row" style="margin: 0 0 0 0;">
					<div class="col-sm-2"></div>
					<select name="searchCondition"
						class="form-control form-control-sm col-sm-2"
						style="display: inline;">
						<option value="reboard_title"<c:if
								test="${param.searchCondition=='reboard_title'}"> 
			            		selected="selected"
			            	</c:if> >제목
						</option>
						<option value="reboard_content"<c:if
								test="${param.searchCondition=='reboard_content'}"> 
			            		selected="selected"
			            	</c:if> >내용
						</option>
						<option value="userid"<c:if
								test="${param.searchCondition=='userid'}"> 
			            		selected="selected"
			            	</c:if> >작성자
						</option>
					</select> <input type="text" name="searchKeyword" title="검색어 입력"
						class="form-control form-control-sm col-sm-5"
						style="display: inline;" value="${param.searchKeyword }">
					<input type="submit" class="form-control form-control-sm col-sm-1"
						style="display: inline;" value="검색">
					<div class="col-sm-2"></div>
				</div>
				</form>
			</div>

			<div class="divBtn text-right">
				<a href='<c:url value=' /write'/>' class="btn btn-light btn-sm">쓰기</a>
			</div>

		</div>
	</div>
</div>



<%@include file="inc/mainBottom.jsp" %>

<script type="text/javascript">
	$(function() {
		$("#mainBoard").addClass("active");
	});
	
	function pageFunc(curPage){
		document.frmPage.currentPage.value=curPage;
		document.frmPage.submit();
	}
</script>