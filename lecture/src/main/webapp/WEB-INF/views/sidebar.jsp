<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
  * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        list-style: none;
      }
      body {
        font-family: Arial, sans-serif;
      }
      .wrapper {
        width: 250px;
        background-color: #e2e2e2;
        height: 100vh;
        position: fixed;
        top: 0;
        left: 0;
        transform: translateX(0);
        transition: transform 0.3s ease;
        overflow-y: auto;
      }
      .wrapper.hide {
        transform: translateX(-250px);
      }
      .menu-control {
        position: fixed;
        top: 100px;
        left: 250px; /* 사이드바의 너비와 동일하게 설정 */
        z-index: 1000;
        background-color: #a20131;
        color: white;
        border: none;
        padding: 10px 10px;
        cursor: pointer;
        font-size: 18px;
        border-radius: 0 5px 5px 0;
        transition: left 0.3s ease;
      }
      .wrapper.hide + .menu-control {
        left: 0; /* 사이드바가 숨겨지면 버튼을 왼쪽으로 이동 */
      }
      .nav {
        padding: 0; /* 패딩 제거 */
      }
      .nav-header {
        display: flex; /* 플렉스 박스로 변경 */
        background-color: #e0e0e0;
        color: white;
        margin-bottom: 0; /* 마진 제거 */
        padding: 20px 20px 5px;
      }
      .nav-header span {
        flex: 1; /* 동일한 너비로 분할 */
        cursor: pointer;
        padding: 20px 0;
        text-align: center;
        background-color: #b3b3b3;
      }
      .nav-header span.is-active {
        color: white;
        background: #a20131;
      }
      .nav-main {
        padding: 0 20px 20px;
      }
      .nav-menu {
        list-style: none;
        padding: 0;
        margin: 0;
      }
      .nav-menu li {
        border-bottom: 1px solid #ccc; /* 하단 테두리 추가 */
      }
      .nav-menu li span {
        display: block;
        padding: 15px 0;
        cursor: pointer;
        border-radius: 0; /* 둥근 모서리 제거 */
        transition: background 0.3s;
      }
      .nav-menu li span:hover {
        background: #f5f5f5; /* 호버 시 배경색 변경 */
      }
      .nav-menu li:last-child {
        border-bottom: none; /* 마지막 아이템의 테두리 제거 */
      }
      /* 상위 메뉴 항목을 볼드체로 설정 */
      .nav-menu > li > span {
        font-weight: bold;
      }
      /* 서브 메뉴 항목의 글자 크기를 12px로 설정하고, 볼드체 해제 */
      .nav-menu li ul li span {
        font-size: 12px;
        font-weight: normal;
      }
      .nav-footer {
        padding: 20px;
        background: #e0e0e0;
        position: absolute;
        bottom: 0;
        width: 100%;
      }
      .btn-footer {
        width: 100%;
        padding: 10px;
        margin-bottom: 10px;
        background: #a20131;
        color: white;
        border: none;
        cursor: pointer;
        font-size: 16px;
      }
      .copy {
        text-align: center;
        font-size: 12px;
        color: #666;
      }
</style>
</head>
<body>
	<div class="wrapper">
		<div class="nav">
			<div class="nav-header">
				<span class="is-active">KOREAN</span> <span>ENGLISH</span>
			</div>
			<div class="nav-main">
				<ul class="nav-menu">
					<li id="menu_sugang"><span>수강신청</span></li>
					<li id="menu_basket"><span>수강희망/관심과목 등록</span></li>
					<li class="has-child is-opened"><span class="is-active">과목조회</span>
						<ul style="display: block; padding-left: 20px; margin: 0">
							<li><span id="menu_hakbu" class="is-active">학부 과목조회</span></li>
							<li><span id="menu_similar">학부 유사과목</span></li>
						</ul></li>
					<li class="has-child is-opened"><span>안내사항</span>
						<ul style="display: block; padding-left: 20px; margin: 0">
							<li><span id="menu_noti_1">수강신청 안내</span></li>
							<li><span id="menu_room">강의실 안내</span></li>
							<li id="menu_fee"><span>계절수업료납부 안내</span></li>
							<li><span id="menu_stdno">신입생 학번 안내</span></li>
						</ul></li>
					<li><span>포털미사용자 비밀번호 변경</span></li>
				</ul>
			</div>
			<div class="nav-footer">
				<button type="button" class="btn-footer">사용자 매뉴얼 (PC)</button>
				<button type="button" class="btn-footer">사용자 매뉴얼 (모바일앱)</button>
				<div class="copy">
					Copyright © 2020 Korea University.<br />All Rights Reserved.
				</div>
			</div>
		</div>
	</div>

	<button class="menu-control" onclick="toggleSidebar()">◀</button>

	
</body>

</body>
</html>