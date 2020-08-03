
$(document).ready(function() {
    $(".compensation").hide();

    console.log($("input[name='fromTime']").val())
    $('#fromtime').datetimepicker({
        format: 'L',//date only
        format: 'YYYY-MM-DD',
        daysOfWeekDisabled: [0, 6],
        defaultDate: timeStampConvertToyyyMMdd(parseInt($("input[name='fromTime']").val()))
    }).on('dp.change',function(e){
        showDays()
    })

    $('#totime').datetimepicker({
        format: 'L',//date only
        format: 'YYYY-MM-DD',
        daysOfWeekDisabled: [0, 6],
        defaultDate: timeStampConvertToyyyMMdd(parseInt($("input[name='toTime']").val()))
    }).on('dp.change',function(e){
        showDays()
    })

    $('#fromGranularity').datetimepicker({
        format: 'LT',//time only
        format: 'HH',
        enabledHours: [9, 12,18]
    }).on('dp.change',function(e){
        showDays()
    })
    $('#toGranularity').datetimepicker({
        format: 'LT',//time only
        format: 'HH',
        enabledHours: [9, 12,18]
    }).on('dp.change',function(e){
        showDays()
    })


});

function onLeaveTypeChange() {
    console.log("onLeaveTypeChange",$("#leavecategory").val());
    if($("#leavecategory").val()=="COMPENSATION_LEAVE"){
        $(".compensation").fadeIn();
    }else{
        $(".compensation").fadeOut();
    }
}

function showDays() {
    let fromtime=new Date(new Date($("#fromtime").val()).setHours(0,0,0,0)).getTime();
    let totime=new Date(new Date($("#totime").val()).setHours(23,59,59,0)).getTime();
    if(totime<=fromtime){
        return;
    }
    // let days=(totime-fromtime)/(1000*60*60*24);
    // $("#showDays").text(days+" days selectd")
    // console.log(fromtime);
    // console.log(totime);
}

function check(){
    let fromtime=new Date(new Date($("#fromtime").val()).setHours(0,0,0,0)).getTime();
    let totime=new Date(new Date($("#totime").val()).setHours(23,59,59,0)).getTime();
    let reasons=$("input[name='reasons']").val();
    let type=$("#leavecategory").val();

    if(type=="COMPENSATION_LEAVE"){
        if($("#fromGranularity").val()=="09"){
            fromtime=new Date(new Date($("#fromtime").val()).setHours(0,0,0,0)).getTime();
        }else if($("#fromGranularity").val()=="12"){
            fromtime=new Date(new Date($("#fromtime").val()).setHours(12,0,0,0)).getTime();
        }else{
            fromtime=new Date(new Date($("#fromtime").val()).setHours(23,59,59,0)).getTime();
        }
        if($("#toGranularity").val()=="09"){
            totime=new Date(new Date($("#totime").val()).setHours(0,0,0,0)).getTime();
        }else if($("#toGranularity").val()=="12"){
            totime=new Date(new Date($("#totime").val()).setHours(12,0,0,0)).getTime();
        }else{
            totime=new Date(new Date($("#totime").val()).setHours(23,59,59,0)).getTime();
        }
    }


    console.log("fromtime",fromtime);
    console.log("totime",totime);

    if(reasons==""){
        alert("reason can not be empty");
        return false;
    }
    if(totime-fromtime<=0){
        alert("the to date can not less than from date");
        return false;
    }
    $("input[name='fromTime']").val(fromtime);
    $("input[name='toTime']").val(totime);
    // return false;
    return true;
}

function timeStampConvertToyyyMMdd (timeStamp) {
    let date = new Date(timeStamp);
    let Y = date.getFullYear() + '-';
    let M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    let D = date.getDate() + ' ';
    return Y + M + D ;
}
