$(document).ready(function() {

    // var disabledDate = ['2020-7-1', '2020-7-2','2020-7-3'];
    $('#starttime').datetimepicker();
    $('#endtime').datetimepicker();

});


function check(){
    console.log("on submit")
    let starttime=new Date($("#starttime").val()).getTime();
    let endtime=new Date($("#endtime").val()).getTime();


    if(endtime-starttime<=0){
        alert("the to date can not less than from date");
        return false;
    }
    $("input[name='startTime']").val(starttime);
    $("input[name='endTime']").val(endtime);
    // return false;
    return true;
}