// 新增自定义网络
var addCustomNetworkModel = function () {
		var me = this;
	    // 验证新增画面
		this.initValidator = function(){
			$('#addCustomNetworkForm').jqxValidator({
		        rules: [  
		                  // 网络名称
		                  { input: '#add-custom-networkName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-custom-networkName', message: '网络名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
		                  
		                  // 子网
		                  { input: '#add-custom-subnet', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-custom-subnet', message: '子网不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
	                      { input: '#add-custom-subnet', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
	                    	  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	                    	  if(!pattern.test(input.val())){
                  	  				return false;
                  	  			}else{
                  	  				return true;
                  	  			}
                  	  		}
		                  },
		                  // 子网掩码
		                  { input: '#add-custom-subnetMask', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-custom-subnetMask', message: '请输入正确的子网掩码地址', action: 'keyup, blur',rule: function (input, commit) {
	                    	  	if(IPUtil.isExist(IPUtil.subnetMask,input.val())){
                  	  				return true;
                  	  			}else{
                  	  				return false;
                  	  			}
                  	  		}
		                  },
		                  // 网关
		                  { input: '#add-custom-gateway', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-custom-subnet").val() == null || $("#add-custom-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-custom-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-custom-gateway', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-custom-subnetMask").val() == null || $("#add-custom-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-custom-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-custom-gateway', message: '输入的网关不在网段内', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 	
		                	  if(IPUtil.isEqualIPAddress($("#add-custom-subnet").val(),input.val(),$("#add-custom-subnetMask").val())){
                	  				return true;
                	  			}else{
                	  				return false;
                	  			}
                 	  		}
		                  },
		                  
		                  // 保留IP段1-开始
		                  { input: '#add-custom-ipRetainStart1', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-custom-subnet").val() == null || $("#add-custom-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-custom-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-custom-ipRetainStart1', message: '请输入小于结束IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-custom-ipRetainEnd1").val() == null || $("#add-custom-ipRetainEnd1").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize(input.val(),$("#add-custom-ipRetainEnd1").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                  	  		}
		                  },
		                  { input: '#add-custom-ipRetainStart1', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-custom-subnetMask").val() == null || $("#add-custom-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-custom-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-custom-ipRetainStart1', message: '输入的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	                    	    	
	  		                	    if(IPUtil.isEqualIPAddress($("#add-custom-subnet").val(),input.val(),$("#add-custom-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
                	  		}
		                  },
		                  // 结束
		                  { input: '#add-custom-ipRetainEnd1', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-custom-subnet").val() == null || $("#add-custom-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-custom-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-custom-ipRetainEnd1', message: '请输入大于开始IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-custom-ipRetainStart1").val()== null || $("#add-custom-ipRetainStart1").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize($("#add-custom-ipRetainStart1").val(),input.val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                  	  		}
		                  },
		                  { input: '#add-custom-ipRetainEnd1', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-custom-subnetMask").val() == null || $("#add-custom-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-custom-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-custom-ipRetainEnd1', message: '输入的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-custom-subnet").val(),input.val(),$("#add-custom-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
              	  		    }
		                  },
		                   
		                  // 保留IP段2-开始和结束
		                  { input: '#add-custom-ipRetainStart2', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-custom-subnet").val() == null || $("#add-custom-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-custom-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-custom-ipRetainStart2', message: '请输入小于结束IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-custom-ipRetainEnd2").val()== null || $("#add-custom-ipRetainEnd2").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize(input.val(),$("#add-custom-ipRetainEnd2").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                  	  		}
		                  },
		                  { input: '#add-custom-ipRetainStart2', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-custom-subnetMask").val() == null || $("#add-custom-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-custom-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-custom-ipRetainStart2', message: '输入的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-custom-subnet").val(),input.val(),$("#add-custom-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
              	  		}
		                  },
		                  // 结束
		                  { input: '#add-custom-ipRetainEnd2', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-custom-subnet").val() == null || $("#add-custom-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-custom-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-custom-ipRetainEnd2', message: '请输入大于开始IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-custom-ipRetainStart2").val()== null || $("#add-custom-ipRetainStart2").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize($("#add-custom-ipRetainStart2").val(),input.val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                  	  		}
		                  },
		                  { input: '#add-custom-ipRetainEnd2', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-custom-subnetMask").val() == null || $("#add-custom-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-custom-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-custom-ipRetainEnd2', message: '保留的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-custom-subnet").val(),input.val(),$("#add-custom-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
            	  		    }
		                  },
		                  // 保留IP段3-开始和结束
		                  { input: '#add-custom-ipRetainStart3', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-custom-subnet").val() == null || $("#add-custom-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-custom-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-custom-ipRetainStart3', message: '请输入小于结束IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-custom-ipRetainEnd3").val()== null || $("#add-custom-ipRetainEnd3").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize(input.val(),$("#add-custom-ipRetainEnd3").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                  	  		}
		                  },
		                  { input: '#add-custom-ipRetainStart3', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-custom-subnetMask").val() == null || $("#add-custom-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-custom-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-custom-ipRetainStart3', message: '保留的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-custom-subnet").val(),input.val(),$("#add-custom-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
              	  		}
		                  },
		                  // 结束
		                  { input: '#add-custom-ipRetainEnd3', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-custom-subnet").val() == null || $("#add-custom-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-custom-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                  	  		}
		                  },
		                  { input: '#add-custom-ipRetainEnd3', message: '请输入大于开始IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-custom-ipRetainStart3").val()== null || $("#add-custom-ipRetainStart3").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize($("#add-custom-ipRetainStart3").val(),input.val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;3
	                  	  			}
		                	  }
		                	  
                  	  		}
		                  },
		                  { input: '#add-custom-ipRetainEnd3', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-custom-subnetMask").val() == null || $("#add-custom-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-custom-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
                 	  		}
		                  },
		                  { input: '#add-custom-ipRetainEnd3', message: '保留的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-custom-subnet").val(),input.val(),$("#add-custom-subnetMask").val())){
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
			$('#addCustomNetworkForm').on('validationSuccess', function (event) {
				 var pool = Core.parseJSON($("#addCustomNetworkForm").serializeJson());
				 
				 // 判断外部网络是否为空
				 if(pool.extNetId == null || pool.extNetId == ""){
					 Core.alert({message:"关联外部网络不能为空！",type:"info"});
					 return;
				 }
				 
				 // 内网类型
				 pool.networkType = "03";
	    		 Core.AjaxRequest({
	 				url : ws_url + "/rest/networks/create/custom",
	 				params :pool,
	 				callback : function (data) {
	 	            	me.initAddContent();
	 					$("#addCustomNetworkWindow").jqxWindow('close');
	 					// 刷新datagrid
	 					var custom = new mgtObjCustomNetworkModel();
	 					var rowindex = $('#jqxgridBiz').jqxGrid('getselectedrowindex');
 				   		var cusData = $('#jqxgridBiz').jqxGrid('getrowdata', rowindex);
	 					var mgtObjSid = cusData.mgtObjSid;
	 					custom.searchInfo(mgtObjSid);
	 			    },
	 			    failure:function(data){
						$("#addNetworkWindow").jqxWindow('close');
	 			    }
	 			});
		     });
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	$("#addCustomNetworkWindow").jqxWindow({
	            width: 600, 
	            height:360,
	            theme:currentTheme,
	            resizable: false,  
	            isModal: true, 
	            autoOpen: false, 
	            cancelButton: $("#addCustomNetworkCancel"), 
	            modalOpacity: 0.3,
	            initContent:function(){
	            	// 初始化组件
	                $("#add-custom-networkName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-custom-subnet").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-custom-subnetMask").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-custom-gateway").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-custom-dns1").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-custom-dns2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-custom-ipRetainStart1").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-custom-ipRetainEnd1").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-custom-ipRetainStart2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-custom-ipRetainEnd2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-custom-ipRetainStart3").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-custom-ipRetainEnd3").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-custom-description").jqxInput({placeHolder: "", height: 46, width:400, minLength: 1,theme:currentTheme});

	                $("#addCustomNetworkSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                $("#addCustomNetworkCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	        		
	            }
	        });
	    };
	    
	    // 判断操作按钮，初始化按钮
	    this.initAddContent = function(){
		    	// 初始化组件
		        $("#add-custom-networkName").val("");
		        $("#add-custom-subnet").val("");
		        $("#add-custom-subnetMask").val("");
		        $("#add-custom-gateway").val("");
		        $("#add-custom-dns1").val("");
		        $("#add-custom-dns2").val("");
		        $("#add-custom-ipType").val("");
		        $("#add-custom-re-vlanId").val("");
		        $("#add-custom-ipRetainStart1").val("");
		        $("#add-custom-ipRetainEnd1").val("");
		        $("#add-custom-ipRetainStart2").val("");
		        $("#add-custom-ipRetainEnd2").val("");
		        $("#add-custom-ipRetainStart3").val("");
		        $("#add-custom-ipRetainEnd3").val("");
	       };
 };
 
//弹出新增网络对话框
 function addCustomNetworkInfoWindow(){
	 
	   // 初始化下拉列表框 
	 var codesearch = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150,async:false});
	 codesearch.getCommonCode("add-custom-ipType","IP_TYPE",false);
	 
	 $("#add-custom-ipType").jqxDropDownList({ disabled: true }); 
	 
	 var rowindex = $('#jqxgridBiz').jqxGrid('getselectedrowindex');
	 var data = $('#jqxgridBiz').jqxGrid('getrowdata', rowindex);
	 
	 codesearch.getCustomCode("add-custom-extNetId", "/networks/find/extNetwork/"+data.mgtObjSid+"", "networkName", "uuid", false, "GET", null);
	 	
	 var rowindex = $('#jqxgridBiz').jqxGrid('getselectedrowindex');
	 if(rowindex >= 0){
   		var data = $('#jqxgridBiz').jqxGrid('getrowdata', rowindex);
   		var pni = new addCustomNetworkModel();
		pni.initAddContent();
	    $("#add-custom-mgtObjSid").val(data.mgtObjSid);
	    var windowW = $(window).width(); 
	 	var windowH = $(window).height(); 
	 	$("#addCustomNetworkWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-360)/2 } });
	 	$("#addCustomNetworkWindow").jqxWindow('open');
   	 }
	  	
 }

 // 提交将选择主机加入池
 function addCustomNetworkSubmit(){
 	$('#addCustomNetworkForm').jqxValidator('validate');
 }

  