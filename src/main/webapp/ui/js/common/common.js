$(function(){
    pageInit();
});

function pageInit(){
    if(localStorage.getItem('auth_token')){
        $('.login_status_n').remove();
    }else{
        $('.login_status_y').remove();
    }
};