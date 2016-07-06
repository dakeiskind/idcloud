// 新增主机 
var addPniNetworkModel = function () {
		var me = this;
		
		// 判断VLAN池名称是否重复
		this.getNetworkName = function(name){
			var Todata = null;
			Core.AjaxRequest({
	 			url : ws_url + "/rest/networks",
	 			type:'POST',
	 			async:false,
	 			params:{
	 				networkName : name
	 			},
	 			callback : function (data) {
	 				Todata = data;
	 			}
	 		 });
			return Todata;
		};
		
		// 判断VLAN池名称是否重复
		this.jugmentIpRepeat = function(subIp,mask){
			var isok = true;
			Core.AjaxRequest({
	 			url : ws_url + "/rest/networks",
	 			type:'post',
	 			params:{
	 				networkType: "01", 
	 	    		parentTopologySid:resTopologySid
	 			},
	 			async:false,
	 			callback : function (data) {
	 				  
	 				  // 子网 
	 				  var newsubIp = subIp.split(".");
	 				  newsubIp[3]=0;
	 				  var strIp = newsubIp.join(".");
	 				  console.log("子网:"+strIp);
	 				  // 掩码
	 				  var newmask = mask.split(".");
	 				  newmask[3]=0;
	 				  var strmask = newmask.join(".");
	 				 console.log("掩码:"+strmask);
	 				  
	 			      for(var i=0;i<data.length;i++){
	 			    	 var netip = (data[i].subnet).split(".");
	 			    	 netip[3] = 0;
	 			    	 var strnetip = netip.join(".");
	 			    	 
	 			    	 var netmask = (data[i].subnetMask).split(".");
	 			    	 netmask[3] = 0;
	 			    	 var strnetmask = netmask.join(".");
	 			    	 
	 			    	 if(strIp ==strnetip && strmask == strnetmask){
	 			    		isok = false; 
	 			    		break;
	 			    	 }
	 			      }
	 			}
	 		 });
			
			return isok;
		};
		
	    // 验证新增画面
		this.initValidator = function(){
			
			$('#addNetworkForm').jqxValidator({
		        rules: [  
		                  // 网络名称
		                  { input: '#add-networkName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-networkName', message: '网络名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
		                  { input: '#add-networkName', message: '网络名称重复，请重新输入', action: 'blur', rule: function (input, commit) {
	  	                  	  	var list = me.getNetworkName(input.val());
	  	                  	  	if(list.length > 0){
	  	                  	  		return false;
	  	                  	  	}else{
	  	                  	  		return true;
	  	                  	  	}
	  	                      }
	  		              },
		  		          { input: '#add-networkName', message: '存在特殊字符，请修改', action: 'blur', rule: function(input,commit){
		  			    		if(/^[.A-Za-z0-9_\-\u4e00-\u9fa5]+$/g.test(input.val())){
		  		  					return true;
		  		  				}else{
		  		  					return false;
		  		  				}
		  			    	}
	  		              },
		                  
		                  // 子网
		                  { input: '#add-subnet', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-subnet', message: '子网不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
	                      { input: '#add-subnet', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
	                    	  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	                    	  if(!pattern.test(input.val())){
                  	  				return false;
                  	  			}else{
                  	  				return true;
                  	  			}
                  	  		}
		                  },
		                  { input: '#add-subnet', message: 'IP段重复，请重新输入', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }else if($("#add-subnet").val() == null || $("#add-subnet").val() == null){
	                    		    return true; 
	                    	  }else{
	                    		  if(me.jugmentIpRepeat(input.val(),$("#add-subnet").val())){
	                    			  return true;
	                    		  }else{
	                    			  return false;
	                    		  }
	                    	  }
                  	  		}
		                  },
		                  // 子网掩码
		                  { input: '#add-subnetMask', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-subnetMask', message: '请输入正确的子网掩码地址', action: 'keyup, blur',rule: function (input, commit) {
	                    	  	if(IPUtil.isExist(IPUtil.subnetMask,input.val())){
                  	  				return true;
                  	  			}else{
                  	  				return false;
                  	  			}
                  	  		}
		                  },
		                  { input: '#add-subnetMask', message: 'IP段重复，请重新输入', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }else if($("#add-subnetMask").val() == null || $("#add-subnetMask").val() == null){
	                    		    return true; 
	                    	  }else{
	                    		  if(me.jugmentIpRepeat($("#add-subnetMask").val(),input.val())){
	                    			  return true;
	                    		  }else{
	                    			  return false;
	                    		  }
	                    	  }
                  	  		}
		                  },
		                  
		                  // 网关
		                  { input: '#add-gateway', message: '请先输入正确的网关', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-gateway").val() == null || $("#add-gateway").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-gateway").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-gateway', message: '请先输入正确的子网关', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-gateway").val() == null || $("#add-gateway").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-gateway").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-gateway', message: '输入的网关不在网段内', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 	
		                	  if(IPUtil.isEqualIPAddress($("#add-subnet").val(),input.val(),$("#add-subnetMask").val())){
                	  				return true;
                	  			}else{
                	  				return false;
                	  			}
                 	  		}
		                  },
		                  
		                  // 保留IP段1-开始
		                  { input: '#add-ipRetainStart1', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-subnet").val() == null || $("#add-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-ipRetainStart1', message: '请输入小于结束IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-ipRetainEnd1").val() == null || $("#add-ipRetainEnd1").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize(input.val(),$("#add-ipRetainEnd1").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                  	  		}
		                  },
		                  { input: '#add-ipRetainStart1', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-subnetMask").val() == null || $("#add-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-ipRetainStart1', message: '输入的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	                    	    	
	  		                	    if(IPUtil.isEqualIPAddress($("#add-subnet").val(),input.val(),$("#add-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
                	  		}
		                  },
		                  // 结束
		                  { input: '#add-ipRetainEnd1', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-subnet").val() == null || $("#add-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-ipRetainEnd1', message: '请输入大于开始IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-ipRetainStart1").val()== null || $("#add-ipRetainStart1").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize($("#add-ipRetainStart1").val(),input.val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                  	  		}
		                  },
		                  { input: '#add-ipRetainEnd1', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-subnetMask").val() == null || $("#add-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-ipRetainEnd1', message: '输入的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-subnet").val(),input.val(),$("#add-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
              	  		    }
		                  },
		                   
		                  // 保留IP段2-开始和结束
		                  { input: '#add-ipRetainStart2', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-subnet").val() == null || $("#add-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-ipRetainStart2', message: '请输入小于结束IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-ipRetainEnd2").val()== null || $("#add-ipRetainEnd2").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize(input.val(),$("#add-ipRetainEnd2").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                  	  		}
		                  },
		                  { input: '#add-ipRetainStart2', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-subnetMask").val() == null || $("#add-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-ipRetainStart2', message: '输入的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-subnet").val(),input.val(),$("#add-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
              	  		}
		                  },
		                  // 结束
		                  { input: '#add-ipRetainEnd2', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-subnet").val() == null || $("#add-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-ipRetainEnd2', message: '请输入大于开始IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-ipRetainStart2").val()== null || $("#add-ipRetainStart2").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize($("#add-ipRetainStart2").val(),input.val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                  	  		}
		                  },
		                  { input: '#add-ipRetainEnd2', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-subnetMask").val() == null || $("#add-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-ipRetainEnd2', message: '保留的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-subnet").val(),input.val(),$("#add-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
            	  		    }
		                  },
		                  // 保留IP段3-开始和结束
		                  { input: '#add-ipRetainStart3', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-subnet").val() == null || $("#add-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-ipRetainStart3', message: '请输入小于结束IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-ipRetainEnd3").val()== null || $("#add-ipRetainEnd3").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize(input.val(),$("#add-ipRetainEnd3").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                  	  		}
		                  },
		                  { input: '#add-ipRetainStart3', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-subnetMask").val() == null || $("#add-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-ipRetainStart3', message: '保留的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-subnet").val(),input.val(),$("#add-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
              	  		}
		                  },
		                  // 结束
		                  { input: '#add-ipRetainEnd3', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-subnet").val() == null || $("#add-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-ipRetainEnd3', message: '请输入大于开始IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-ipRetainStart3").val()== null || $("#add-ipRetainStart3").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize($("#add-ipRetainStart3").val(),input.val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;3
	                  	  			}
		                	  }
		                	  
                  	  		}
		                  },
		                  { input: '#add-ipRetainEnd3', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-subnetMask").val() == null || $("#add-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-ipRetainEnd3', message: '保留的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-subnet").val(),input.val(),$("#add-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
            	  		    }
		                  }
			           ]
				});
	    	
			// 新增ip池验证成功
			$('#addNetworkForm').on('validationSuccess', function (event) {
				 var pool = Core.parseJSON($("#addNetworkForm").serializeJson());
				 // 内网类型
				 pool.networkType = "01";
				 // 判断VLAN ID不能为空！
				 if($("#add-re-vlanId").val() == null || $("#add-re-vlanId").val() == ''){
					 Core.alert({
						 message:"请选择VLAN ID！"
					 });
					 return;
				 }
				 var param = {};
				 param.resPoolSid = resTopologySid;
				 param.parentTopologySid = resTopologySid;
	    		 var newObj = $.extend(pool,param);
		    		 Core.AjaxRequest({
		 				url : ws_url + "/rest/networks/create",
		 				params :newObj,
		 				callback : function (data) {
		 	            	me.initAddContent();
		 					$("#addNetworkWindow").jqxWindow('close');
		 					// 刷新datagrid
		 					var pni = new poolPniDatagridModel();
		 					pni.searchPoolPniInfo();
		 			    },
		 			    failure:function(data){
							$("#addNetworkWindow").jqxWindow('close');
		 			    }
		 			});
		     });
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	$("#addNetworkWindow").jqxWindow({
	            width: 600, 
	            height:360,
	            theme:currentTheme,
	            resizable: false,  
	            isModal: true, 
	            autoOpen: false, 
	            cancelButton: $("#addNetworkCancel"), 
	            modalOpacity: 0.3,
	            initContent:function(){
	            	// 初始化组件
	                $("#add-networkName").jqxInput({placeHolder: "", height: 22, width: 400, minLength: 1,theme:currentTheme});
	                $("#add-subnet").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-subnetMask").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-gateway").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-dns1").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-dns2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-ipRetainStart1").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-ipRetainEnd1").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-ipRetainStart2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-ipRetainEnd2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-ipRetainStart3").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-ipRetainEnd3").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-description").jqxInput({placeHolder: "", height: 46, width:400, minLength: 1,theme:currentTheme});

	                $("#addNetworkSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                $("#addNetworkCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                
	                // 初始化下拉列表框 
	        		var codesearch = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150,async:false});
	        		codesearch.getCommonCode("add-ipType","IP_TYPE",false);
	        		
	            }
	        });
	    };
	    
	    // 判断操作按钮，初始化按钮
	    this.initAddContent = function(){
		    	// 初始化组件
		        $("#add-networkName").val("");
		        $("#add-subnet").val("");
		        $("#add-subnetMask").val("");
		        $("#add-gateway").val("");
		        $("#add-dns1").val("");
		        $("#add-dns2").val("");
		        $("#add-ipType").val("");
		        $("#add-re-vlanId").val("");
		        $("#add-ipRetainStart1").val("");
		        $("#add-ipRetainEnd1").val("");
		        $("#add-ipRetainStart2").val("");
		        $("#add-ipRetainEnd2").val("");
		        $("#add-ipRetainStart3").val("");
		        $("#add-ipRetainEnd3").val("");
	       };
 };
 
//弹出新增网络对话框--仅供网络资源池调用
 function addNetworkInfoWindowUsingPn(){
	  
	  // 动态新增vlan池相关信息
	  var str = '<td align="right">VLAN池:</td><td align="left" ><div id="add-re-vlanPool"></div></td><td align="right"><font style="color:red">*</font>VLAN ID:</td><td align="left"><div data-name="vlanId" id="add-re-vlanId"></div></td>';
	  $("#vlanPniPool").html(str);
     
	  	// 判断是内网还是外网
	    var codesearch = new codeModel({width:150,dropDownWidth:150,async:false});
		// 查询出所有资源分区下面的VLAN池
		codesearch.getCustomCode("add-re-vlanPool","/poolVlans","resPoolName","resPoolSid",false,"POST",{parentTopologySid:resTopologySid});
		codesearch.getCustomCode("add-re-vlanId","/vlanRes","vlanId","vlanId",false,"POST",{unused:"",resPoolSid:$("#add-re-vlanPool").val()});
		$("#add-re-vlanId").jqxDropDownList({ autoDropDownHeight:false,dropDownHeight:150});
		
	    // 注册动态事件
	  	$('#add-re-vlanPool').on('select', function (event){
		    var args = event.args;
		    if (args) {
			    var item = args.item;
			    var value = item.value;
			    var codesearch = new codeModel({width:150,dropDownWidth:150,async:false});
			    codesearch.getCustomCode("add-re-vlanId","/vlanRes","vlanId","vlanId",false,"POST",{unused:"",resPoolSid:value});
			    $("#add-re-vlanId").jqxDropDownList({ autoDropDownHeight:false,dropDownHeight:200});
		    }                        
		});
	  
	  	var pni = new addPniNetworkModel();
		pni.initAddContent();
	    
	    var windowW = $(window).width(); 
	 	var windowH = $(window).height(); 
	 	$("#addNetworkWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-360)/2 } });
	 	$("#addNetworkWindow").jqxWindow('open');
 }
 
//弹出新增网络对话框
 function addNetworkInfoWindow(){
	  
	  // 动态新增vlan池相关信息
	  var str = '<td align="right">VLAN池:</td><td align="left" ><div id="add-re-vlanPool"></div></td><td align="right"><font style="color:red">*</font>VLAN ID:</td><td align="left"><div data-name="vlanId" id="add-re-vlanId"></div></td>';
	  $("#vlanPniPool").html(str);
     
	  // 判断是内网还是外网
	  Core.AjaxRequest({
			url : ws_url + "/rest/topology/"+resTopologySid,
			type:"get",
			async:false,
			callback : function (data) {
				
				var codesearch = new codeModel({width:150,dropDownWidth:150,async:false});
				// 查询出所有资源分区下面的VLAN池
				codesearch.getCustomCode("add-re-vlanPool","/poolVlans","resPoolName","resPoolSid",false,"POST",{parentTopologySid:data.parentTopologySid});
				codesearch.getCustomCode("add-re-vlanId","/vlanRes","vlanId","vlanId",false,"POST",{unused:"",resPoolSid:$("#add-re-vlanPool").val()});
				$("#add-re-vlanId").jqxDropDownList({ autoDropDownHeight:false,dropDownHeight:150});
				
		    }
		});
	    // 注册动态事件
	  	$('#add-re-vlanPool').on('select', function (event){
		    var args = event.args;
		    if (args) {
			    var item = args.item;
			    var value = item.value;
			    var codesearch = new codeModel({width:150,dropDownWidth:150,async:false});
			    codesearch.getCustomCode("add-re-vlanId","/vlanRes","vlanId","vlanId",false,"POST",{unused:"",resPoolSid:value});
			    $("#add-re-vlanId").jqxDropDownList({ autoDropDownHeight:false,dropDownHeight:200});
		    }                        
		});
	  
	  	var pni = new addPniNetworkModel();
		pni.initAddContent();
	    
	    var windowW = $(window).width(); 
	 	var windowH = $(window).height(); 
	 	$("#addNetworkWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-360)/2 } });
	 	$("#addNetworkWindow").jqxWindow('open');
 }

 // 提交将选择主机加入池
 function addNetworkSubmit(){
 	$('#addNetworkForm').jqxValidator('validate');
 }
 
//弹出新增存储window框,供非datagrid列表操作调用
 function addNetworkWindow(){
 	var windowW = $(window).width(); 
 	var windowH = $(window).height(); 
 	// 清除新增残留数据
 	 $("#add-networkName").val("");
     $("#add-subnet").val("");
     $("#add-subnetMask").val("");
     $("#add-gateway").val("");
 	
 	$("#addNetworkWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-400)/2 } });
 	$("#addNetworkWindow").jqxWindow('open');
 }
  