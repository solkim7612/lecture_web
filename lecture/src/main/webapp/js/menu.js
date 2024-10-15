document.addEventListener('DOMContentLoaded', function () {
    const navControl = document.querySelector('.nav-control');
    const nav = document.querySelector('.nav');

    navControl.addEventListener('click', function () {
        nav.classList.toggle('is-opened');
        
        this.title = nav.classList.contains('is-opened') ? '메뉴닫기' : '메뉴열기';
    });
});