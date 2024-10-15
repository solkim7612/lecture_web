<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta charset="UTF-8">
<title>고려대학교 수강신청시스템</title>
<link href="/lecture/css/menu.css" rel="stylesheet">
<script type="text/javascript" src="/lecture/js/menu.js"></script>

</head>
<body class="main">
	
	<div class="wrap-container">
		<div class="nav">
			
			<div class="nav-header">
				<span onClick="fnLang('KOR')" class="is-active">KOREAN</span>
				<span onClick="fnLang('ENG')" class="">ENGLISH</span>
			</div>
			
			<div class="nav-main">
				<ul class="nav-menu">
						<li id="menu_sugang"><span onclick="fnNotLogin('menu_sugang');">수강신청</span></li>
						<li id="menu_basket"><span onclick="fnNotLogin('menu_basket');">수강희망/관심과목 등록</span></li>
						<li class="has-child is-opened"><span>과목조회</span>
						<ul>
							<li><span onClick="javascript:fnMenuLoad('/view?attribute=lectHakbu',this.id);" id="menu_hakbu">학부 과목조회</span></li>
							<li><span onClick="fnMenuLoad('/view?attribute=lectSimilar',this.id);" id="menu_similar">학부 유사과목</span></li>
						</ul>
						</li>
						<li class="has-child is-opened"><span>안내사항</span></li>
				</ul>
			</div>
			
			<div class="nav-footer">
				<div id="manual_KOR">
				<button type="button" class="btn-footer" onClick="window.open('about:blank').location.href='/resources/manual/manual_web.pdf'"><span id="manualPC">사용자 매뉴얼 (PC)</span><i class="sw-icon-download"></i></button>
				<button type="button" class="btn-footer" onClick="window.open('about:blank').location.href='/resources/manual/manual_mobile.pdf'"><span id="manualMO">사용자 매뉴얼 (Mobile)</span><i class="sw-icon-download"></i></button>
				</div>
				<div id="manual_ENG" style="display:none;">
				<button type="button" class="btn-footer" onClick="window.open('about:blank').location.href='/resources/manual/manual_web_eng.pdf'"><span id="manualPC">User Manual (PC)</span><i class="sw-icon-download"></i></button>
				<button type="button" class="btn-footer" onClick="window.open('about:blank').location.href='/resources/manual/manual_mobile_eng.pdf'"><span id="manualMO">User Manual (Mobile)</span><i class="sw-icon-download"></i></button>				
				</div>
				<button type="button" class="btn-footer btn-main" id="linkSite"><span>관련사이트</span><i class="sw-icon-plus"></i></button>
				<div class="layer-site">
					<ul>
						<li><a href="http://portal.korea.ac.kr" target="_blank">KUPID</a></li>
						<li><a href="http://www.korea.ac.kr" target="_blank">홈페이지</a></li>
						<li><a href="http://registrar.korea.ac.kr" target="_blank">교육정보</a></li>
					</ul>
				</div>
				<div class="copy">Copyright © 2020 Korea University.<br>All Rights Reserved.</div>
			</div>
			
		</div>
		
		<div class="container">
			<div class="nav-control is-opened" title="메뉴닫기"></div>
			<div class="header">
				<div class="is-left">
					<img src="/lecture/img/logo.png">
					<span class="title">수강신청 시스템</span>
				</div>
			</div>
			<div id="contents" class="contents"></div>
		</div>
		
	</div>
</body>
</html>