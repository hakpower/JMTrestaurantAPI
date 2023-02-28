
function login(){
    var m_id=$('#m_id').val();
    var password=$('#password').val();
    
    localStorage.clear();

    $.ajax({
        url:'http://localhost:8080/JMTrestaurantAPI/api/member/login',
        dataType:'json',
        type:'post',
        data:{
            m_id:m_id,
            password:password
        },
        success:function(result){
            var auth_token='';
            if(result.status=='success'){
                if(result.data){
                    auth_token=result.data;
                    localStorage.setItem('auth_token',auth_token);
                    pageInit();
                    location.replace('./restaurant/index.html');
                }
            }
        }
    })
}