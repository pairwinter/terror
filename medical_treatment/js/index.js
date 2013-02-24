var datas =[];
$(function () {
    $("table tr").each(function(){
        var tds = $(this).children();
        var data = [];
        data.push(tds.eq(1).html());
        data.push(tds.eq(0).html());
        data.push(tds.eq(3).html());
        data.push(tds.eq(2).html());
        datas.push(data);
    });
    $("#data").remove();
    var options = { width: 1020, height: 400, resizable:false,draggable:false,editable:false};
    options.colModel = [
        { title: "公司名称", width: 700, dataType: "string" },
        { title: "展位号", width: 200, dataType: "string", align: "right" }
    ];
    options.dataModel = { data: datas };
    options.rowSelect=function(a,b){
        var data = b.data[b.rowIndx];
        $("#companyInfo").html(data[3]);s
    };
    options.cellSelect=function(e,ui ){
        $("#dataGrid").pqGrid( "setSelection", ui.rowIndx);
    };
    $("#dataGrid").pqGrid(options);
    $("#type").change(search);
    $("#boothNumber").keypress(function(e){
        if(e.keyCode==13){
            search();
        }
    });
    $("#key").keypress(function(e){
        if(e.keyCode==13){
            search();
        }
    });
    $("#search").click(search);
});
function search(){
    var type = $.trim($("#type").val());
    var boothNumber = $.trim($("#boothNumber").val());
    var key = $.trim($("#key").val());
    var newData = [];
    $.each(datas,function(i,d){
        var isAdd = true;
        if(type){
            isAdd = (d[2].indexOf(type)>-1);
        }
        if(boothNumber && isAdd){
            boothNumber = boothNumber.toUpperCase();
            isAdd = (d[1].indexOf(boothNumber)>-1);
        }
        if(key && isAdd){
            isAdd = isAdd && (d[0].indexOf(key)>-1)
        }
        if(isAdd){
            newData.push(d);
        }
    });
    $("#dataGrid").pqGrid( "option", "dataModel.data", newData );
    $("#dataGrid").pqGrid( "refreshDataAndView");
}
