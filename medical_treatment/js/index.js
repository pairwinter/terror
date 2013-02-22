/**
 * Created with IntelliJ IDEA.
 * User: pairwinter
 * Date: 13-2-19
 * Time: 下午9:42
 * To change this template use File | Settings | File Templates.
 */
function readData(){
    var datas =[]
    //以当前页面文件为基础，找到文件所在的绝对路径。
    var filePath = location.href.substring(0, location.href.indexOf("view/index.html"));
    var path = filePath + "data/data2.accdb";
    //去掉字符串中最前面的"files://"这8个字符。
    path = path.substring(8);
    var updateCnt = 0;
    //生成查询和更新用的sql语句。
    var sqlSelCnt = "SELECT * FROM [data]";
//    var sqlUpdCnt = "UPDATE [COUNT] SET [COUNT] = '";
    //建立连接，并生成相关字符串 www.knowsky.com。
    var con = new ActiveXObject("ADODB.Connection");
    con.Provider = "Microsoft.ACE.OLEDB.12.0";
    con.ConnectionString = "Data Source=" + path;
    con.open;
    var rs = new ActiveXObject("ADODB.Recordset");
    rs.open(sqlSelCnt, con);
    while (!rs.eof) {
        var data = [];
        data.push(rs("cncompany"));
        data.push(rs("zwnumber"));
        data.push(rs("type"));
        data.push(rs("cninfo"));
        datas.push(data);
        rs.moveNext;
    }
    rs.close();
    rs = null;
    con.close();
    con = null;
    return datas;
}

