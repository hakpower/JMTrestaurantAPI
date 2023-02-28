$(function(){
    pageInit();
});

function pageInit(){
    $('.login_status').css('display','inline-block');

    if(localStorage.getItem('auth_token')){
        $('.login_status_n').css('display','none');
    }else{
        $('.login_status_y').css('display','none');
    }
};

function logout(){
    localStorage.clear();
    pageInit();
}