<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="kr">
<head>
    <%@ include file="/include/header.jspf" %>
</head>
<body>
<%@ include file="/include/navigation.jspf" %>

<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-12">
        <div class="panel panel-default">
         <header class="qna-header">
         	<h2 class="qna-title"> ${question.title}  </h2>
         </header>
          <div class="content-main">
              <article class="article">
                  <div class="article-header">
                      <div class="article-header-thumb">
                          <img src="https://graph.facebook.com/v2.3/100000059371774/picture" class="article-author-thumb" alt="">
                      </div>
                      <div class="article-header-text">
                          <a href="#" class="article-author-name">kimmunsu</a>
                          <a href="#" class="article-header-time" title="퍼머링크">
                              <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${question.createdDate}" />
                              <i class="icon-link"></i>
                          </a>
                      </div>
                  </div>
                  <div class="article-doc">
                      ${question.contents}
                  </div>
                  <div class="article-util">
                      <ul class="article-util-list">
                          <li>
                              <a class="link-modify-article" href="#">수정</a>
                          </li>
                          <li>
                              <form class="form-delete" action="#" method="POST">
                                  <input type="hidden" name="_method" value="DELETE">
                                  <button class="link-delete-article" type="submit">삭제</button>
                              </form>
                          </li>
                          <li>
                              <a class="link-modify-article" href="/">목록</a>
                          </li>
                      </ul>
                  </div>
              </article>

              <div class="qna-comment">
                  <div class="qna-comment-slipp">
                      <p class="qna-comment-count"><strong>2</strong>개의 의견</p>
                      <div class="qna-comment-slipp-articles">
						<c:forEach items="${answers}" var="each">
							<article class="article">
                              <div class="article-header">
                                  <div class="article-header-thumb">
                                      <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">
                                  </div>
                                  <div class="article-header-text">
                                      <a href="#" class="article-author-name">자바지기</a>
                                      <a href="#answer-1434" class="article-header-time" title="퍼머링크">
                                      <div class="article-header-thumb>
                                          <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${question.createdDate}" />
                                      </div>
                                      </a>
                                  </div>
                              </div>
                              <div class="article-doc comment-doc">
                                  ${each.contents}
                              </div>
                              <div class="article-util">
                                  <ul class="article-util-list">
                                      <li>
                                          <a class="link-modify-article" href="/questions/413/answers/1405/form">수정</a>
                                      </li>
                                      <li>
                                          <form class="form-delete" action="/questions/413/answers/1405" method="POST">
                                              <input type="hidden" name="_method" value="DELETE">
                                              <button type="submit" class="link-delete-article">삭제</button>
                                          </form>
                                      </li>
                                  </ul>
                              </div>
                          </article>
						</c:forEach>
						
                         <div class="answerWrite">
                          <form class="submit-write">
                              <div class="form-group" style="padding:14px;">
                                  <textarea class="form-control" placeholder="Update your status"></textarea>
                              </div>
                              <button class="btn btn-success pull-right" type="button">Post</button>
                              <div class="clearfix" />
                          </form>
                          </div>
                      </div>
                  </div>
              </div>
          </div>
        </div>
    </div>
</div>

<%@ include file="/include/footer.jspf" %>
	</body>
</html>