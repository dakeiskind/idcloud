define(['app-utils/httpService'], function(http) {

	var codeService = function(){

		var me = this;

		// 获取数据字典表信息
		this.getCommonCode =function(codeName){
			var codeObj = new Object();
			http.AjaxRequest({
				url : "/rest/system/"+codeName,
				type:"get",
				async:false,
				callback : function (data) {
					codeObj = data;
				}
			});
			return codeObj;
		};

		// 条件查询数据字典表信息
	    this.getCommonCodeByConditions =function(params){
	    	var codeObj = new Object();
	    	http.AjaxRequest({
				url :"/rest/system/getCodeByParams", 
				type:"post",
				params:params,
				async:false,
				callback : function (data) {
					
		        } 
		     });
	    	return codeObj;
	    };

	    // 自定义查询方法
		this.getCustomCode =function(url,methodType,params){
			var codeObj = new Object();
	    	http.AjaxRequest({
				url :"/rest"+ url,
				type: methodType = null ? "get":methodType,
				params: params = "" ? null:params,
				async:false,
				callback : function (data) {
					 codeObj = data;
		        }
		    });
	    	return codeObj;
	    };

		/*获取地址省份
		 id 页面省份select的id
		 */
		this.getProvince = function(id){
			http.AjaxRequest({
				url :'/rest/getAddress/getProvince',
				type: 'GET',
				async:false,
				callback : function (data) {
					if(data != null ){
						for(var i = 0;i<data.length;i++){
							$("#"+id).append(
								$("<option value='"+data[i].provinceId+"'>"+data[i].provinceName+"</option>")
							);
						}
					}
				}
			});
		};

		/*获取地址城市
		idArea 页面区县select的id
		id 页面城市select的id
		provinceId 数据库省份的id
		 */
		this.getCity = function(idArea,id,provinceId){
			var returnData;
			http.AjaxRequest({
				url :'/rest/getAddress/getCityByProvince/'+provinceId,
				type: 'GET',
				async:false,
				callback : function (data) {
					if(data != null ){
						$("#"+id).empty();
						for(var i = 0;i<data.length;i++){
							$("#"+id).append(
								$("<option value='"+data[i].cityId+"'>"+data[i].cityName+"</option>")
							);
							if(i == 0){
								returnData = data[i].cityId;
							}
							if(data[i].cityName == '市辖区'){
								break;
							}
						}
					}
				}
			});
			return returnData;
		};

		/*获取地址区县
		 id 页面区县select的id
		 cityId 数据库城市的id
		 */
		this.getArea = function(id,cityId){
			http.AjaxRequest({
				url :'/rest/getAddress/getAreaByCity/'+cityId,
				type: 'GET',
				async:false,
				callback : function (data) {
					if(data != null && data != ""){
						$("#"+id).empty();
						for(var i = 0;i<data.length;i++){
							$("#"+id).append(
								$("<option value='"+data[i].areaId+"'>"+data[i].areaName+"</option>")
							);
						}
					}else{
						$("#"+id).empty();
						$("#"+id).append(
							$("<option>暂无数据</option>")
						);
					}
				}
			});
		};

		// 添加option
		this.setOption =function(id,codeName){
			var data = me.getCommonCode(codeName);
			if(data.data!=null){
				data = data.data;
			}
			for(var i = 0;i<data.length;i++){
				$("#"+id).append($("<option value='"+data[i].codeValue+"'>"+data[i].codeDisplay+"</option>"));
			}
		};

		//添加自定义option
		this.setCustomOption = function (id,url,methodType,params,value) {
			var data = me.getCustomCode(url,methodType,params);
			if(data.data != null){
				data = data.data;
			}
			for(var i = 0;i<data.length;i++){
				$("#"+id).append($("<option value='"+data[i]+"."+value+"'>"+data[i]+"."+value+"</option>"));
			}
		}

		this.setCommonServiceOption = function (id,url,methodType,params) {
			var data = me.getCustomCode(url,methodType,params);
			if(data.data != null){
				data = data.data;
			}
			for(var i = 0;i<data.length;i++){
				$("#"+id).append($("<option value='"+data[i].serviceSid+"'>"+data[i].serviceName+"</option>"));
			}
		}
	}
	
	return new codeService();
});