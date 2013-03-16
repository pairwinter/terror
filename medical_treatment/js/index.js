$(function () {
    $.templates({
        companyListTemplate: $("#companyListTemplate").html(),
        companyTemplate: $("#companyTemplate").html()
    });
    var CompanyView=Backbone.View.extend({
        tagName:"tr",
        initialize:function () {
            this.model.on("change:visibility",this.changeVisibility,this);
            this.model.on("change:color",this.changeColor,this);
        },
        render:function(){
            this.$el.html($.render.companyListTemplate(this.model.toJSON()));
            return this;
        },
        changeVisibility:function(){
            if(this.model.get("visibility")){
                this.$el.show();
            }else{
                this.$el.hide();
            }
        },changeColor:function(){
            if(this.model.get("color")){
                this.$("td").addClass("bgcolor");
            }else{
                this.$("td").removeClass("bgcolor");
            }
        },
        events:{
            "click":"click",
            "click td":"showCompanyInfo",
            "mouseenter":"mouseenter",
            "mouseleave":"mouseleave"
        },
        click:function(e){
            $("td.td_click").removeClass("td_click");
            this.$("td").addClass("td_click");
        },
        showCompanyInfo:function(e){
            $("#companyInfo").show().html($.render.companyTemplate(this.model.toJSON()));
        },
        mouseenter:function(e){
            this.$("td").addClass("td_mouseenter");
        },
        mouseleave:function(e){
            this.$("td").removeClass("td_mouseenter");
        }
    });
    var IndexView=Backbone.View.extend({
        color:false,
        initialize:function(){
            this.collection.on("add",this.collectionAddOne,this);
        },
        collectionAddOne:function(company){
            this.color = !this.color;
            company.set("color",this.color);
            var view = new CompanyView({model: company});
            this.$el.append(view.render().el);
        }
    });
    var collection = new Backbone.Collection();
    var indexView = new IndexView({el:$("#compayTableBody"),collection:collection});
    collection.add(datas);
    window.collection = collection;
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
    $("#reset").click(function(){
        $("#companyInfo").hide();
        $("#type").val("");
        $("#boothNumber").val("");
        $("#key").val("");
        search();
    });

});
function search(){
    $("#companyInfo").hide();
    $("#compayTableBody").find("td.td_click").removeClass("td_click");
    var type = $.trim($("#type").val());
    var boothNumber = $.trim($("#boothNumber").val());
    var key = $.trim($("#key").val());
    var newData = [];
    var color = false;
    collection.each(function(model){
        var modelData = model.toJSON();
        var visibility = true;
        if(type){
            visibility = $.inArray(type,modelData.types)>-1;
        }
        if(boothNumber && visibility){
            boothNumber = boothNumber.toUpperCase();
            visibility = (modelData.boothNum.indexOf(boothNumber)>-1);
        }
        if(key && visibility){
            visibility = visibility && ((modelData.nameChina+modelData.nameEnglish).indexOf(key)>-1)
        }
        if(visibility){
            color = !color;
            model.set("color",color);
        }
        model.set("visibility",visibility);
    });
}
