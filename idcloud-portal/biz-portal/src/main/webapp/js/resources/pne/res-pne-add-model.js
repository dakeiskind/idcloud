// 新增主机 
var addPneNetworkModel = function () {
		var me = this;

	    // 验证新增画面
		this.initValidator = function(){
			$('#addPneNetworkForm').jqxValidator({
		        rules: [  
		                // 网络名称
		                  { input: '#add-pne-networkName', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-pne-networkName', message: '网络名称不能超过64个字符', action: 'keyup, blur', rule: 'length=1,64' },
		                  
		                  // 子网
		                  { input: '#add-pne-subnet', message: '不能为空', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-pne-subnet', message: '子网不能超过32个字符', action: 'keyup, blur', rule: 'length=1,32' },
	                      { input: '#add-pne-subnet', message: '请输入正确的IP地址', action: 'keyup, blur',rule: function (input, commit) {
	                    	  var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	                    	  if(!pattern.test(input.val())){
                	  				return false;
                	  			}else{
                	  				return true;
                	  			}
                	  		}
		                  },
		                  // 子网掩码
		                  { input: '#add-pne-subnetMask', message: '不能为空!', action: 'keyup, blur', rule: 'required' },
		                  { input: '#add-pne-subnetMask', message: '请输入正确的子网掩码地址', action: 'keyup, blur',rule: function (input, commit) {
	                    	  	if(IPUtil.isExist(IPUtil.subnetMask,input.val())){
                	  				return true;
                	  			}else{
                	  				return false;
                	  			}
                	  		}
		                  },
		                  
		                  // 网关
		                  { input: '#add-pne-gateway', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-pne-subnet").val() == null || $("#add-pne-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-pne-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                	  		}
		                  },
		                  { input: '#add-pne-gateway', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-pne-subnetMask").val() == null || $("#add-pne-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-pne-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
               	  		}
		                  },
		                  { input: '#add-pne-gateway', message: '输入的网关不在网段内', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 	
		                	  if(IPUtil.isEqualIPAddress($("#add-pne-subnet").val(),input.val(),$("#add-pne-subnetMask").val())){
              	  				return true;
              	  			}else{
              	  				return false;
              	  			}
               	  		}
		                  },
		                  
		                  // 保留IP段1-开始
		                  { input: '#add-pne-ipRetainStart1', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-pne-subnet").val() == null || $("#add-pne-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-pne-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                	  		}
		                  },
		                  { input: '#add-pne-ipRetainStart1', message: '请输入小于结束IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-pne-ipRetainEnd1").val() == null || $("#add-pne-ipRetainEnd1").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize(input.val(),$("#add-pne-ipRetainEnd1").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                	  		}
		                  },
		                  { input: '#add-pne-ipRetainStart1', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-pne-subnetMask").val() == null || $("#add-pne-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-pne-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
               	  		}
		                  },
		                  { input: '#add-pne-ipRetainStart1', message: '输入的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	                    	    	
	  		                	    if(IPUtil.isEqualIPAddress($("#add-pne-subnet").val(),input.val(),$("#add-pne-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
              	  		}
		                  },
		                  // 结束
		                  { input: '#add-pne-ipRetainEnd1', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-pne-subnet").val() == null || $("#add-pne-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-pne-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                	  		}
		                  },
		                  { input: '#add-pne-ipRetainEnd1', message: '请输入大于开始IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-pne-ipRetainStart1").val()== null || $("#add-pne-ipRetainStart1").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize($("#add-pne-ipRetainStart1").val(),input.val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                	  		}
		                  },
		                  { input: '#add-pne-ipRetainEnd1', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-pne-subnetMask").val() == null || $("#add-pne-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-pne-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
               	  		}
		                  },
		                  { input: '#add-pne-ipRetainEnd1', message: '输入的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-pne-subnet").val(),input.val(),$("#add-pne-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
            	  		    }
		                  },
		                   
		                  // 保留IP段2-开始和结束
		                  { input: '#add-pne-ipRetainStart2', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-pne-subnet").val() == null || $("#add-pne-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-pne-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                	  		}
		                  },
		                  { input: '#add-pne-ipRetainStart2', message: '请输入小于结束IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-pne-ipRetainEnd2").val()== null || $("#add-pne-ipRetainEnd2").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize(input.val(),$("#add-pne-ipRetainEnd2").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                	  		}
		                  },
		                  { input: '#add-pne-ipRetainStart2', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-pne-subnetMask").val() == null || $("#add-pne-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-pne-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
               	  		}
		                  },
		                  { input: '#add-pne-ipRetainStart2', message: '输入的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-pne-subnet").val(),input.val(),$("#add-pne-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
            	  		}
		                  },
		                  // 结束
		                  { input: '#add-pne-ipRetainEnd2', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-pne-subnet").val() == null || $("#add-pne-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-pne-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                	  		}
		                  },
		                  { input: '#add-pne-ipRetainEnd2', message: '请输入大于开始IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-pne-ipRetainStart2").val()== null || $("#add-pne-ipRetainStart2").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize($("#add-pne-ipRetainStart2").val(),input.val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                	  		}
		                  },
		                  { input: '#add-pne-ipRetainEnd2', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-pne-subnetMask").val() == null || $("#add-pne-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-pne-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
               	  		}
		                  },
		                  { input: '#add-pne-ipRetainEnd2', message: '保留的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-pne-subnet").val(),input.val(),$("#add-pne-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
          	  		    }
		                  },
		                  // 保留IP段3-开始和结束
		                  { input: '#add-pne-ipRetainStart3', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-pne-subnet").val() == null || $("#add-pne-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-pne-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                	  		}
		                  },
		                  { input: '#add-pne-ipRetainStart3', message: '请输入小于结束IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-pne-ipRetainEnd3").val()== null || $("#add-pne-ipRetainEnd3").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize(input.val(),$("#add-pne-ipRetainEnd3").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
		                	  }
		                	  
                	  		}
		                  },
		                  { input: '#add-pne-ipRetainStart3', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-pne-subnetMask").val() == null || $("#add-pne-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-pne-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
               	  		}
		                  },
		                  { input: '#add-pne-ipRetainStart3', message: '保留的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-pne-subnet").val(),input.val(),$("#add-pne-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	    }
            	  		}
		                  },
		                  // 结束
		                  { input: '#add-pne-ipRetainEnd3', message: '请先输入正确的子网', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  }
		                	  if($("#add-pne-subnet").val() == null || $("#add-pne-subnet").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   var pattern=/^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
	 	                    	   if(!pattern.test($("#add-pne-subnet").val())){
	                   	  				return false;
	                   	  			}else{
	                   	  				return true;
	                   	  			}
	                    	   } 
	                    	   
                	  		}
		                  },
		                  { input: '#add-pne-ipRetainEnd3', message: '请输入大于开始IP段的IP', action: 'keyup, blur',rule: function (input, commit) {
		                	  if($("#add-pne-ipRetainStart3").val()== null || $("#add-pne-ipRetainStart3").val() == ""){
		                		  if(input.val() == null || input.val() == ""){
		                    	        return true; 
		                    	  }
		                	  }else{
		                		  if(IPUtil.judgeIpSize($("#add-pne-ipRetainStart3").val(),input.val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;3
	                  	  			}
		                	  }
		                	  
                	  		}
		                  },
		                  { input: '#add-pne-ipRetainEnd3', message: '请先输入正确的子网掩码', action: 'keyup, blur',rule: function (input, commit) {
		                	  if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	  } 
		                	  if($("#add-pne-subnetMask").val() == null || $("#add-pne-subnetMask").val() == ""){
	                    		   return false;
	                    	   }else{
	                    		   if(IPUtil.isExist(IPUtil.subnetMask,$("#add-pne-subnetMask").val())){
	                  	  				return true;
	                  	  			}else{
	                  	  				return false;
	                  	  			}
	                    	   } 
               	  		}
		                  },
		                  { input: '#add-pne-ipRetainEnd3', message: '保留的IP不在网段中', action: 'keyup, blur',rule: function (input, commit) {
	                    	    if(input.val() == null || input.val() == ""){
	                    	        return true; 
	                    	    }else{
	  		                	    if(IPUtil.isEqualIPAddress($("#add-pne-subnet").val(),input.val(),$("#add-pne-subnetMask").val())){
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
			$('#addPneNetworkForm').on('validationSuccess', function (event) {
				 var pool = Core.parseJSON($("#addPneNetworkForm").serializeJson());
				 // 外网类型
				 pool.networkType = "02";
				 // 判断VLAN ID不能为空！
				 if($("#add-pne-re-vlanId").val() == null || $("#add-pne-re-vlanId").val() == ''){
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
		 					$("#addPneNetworkWindow").jqxWindow('close');
		 					// 刷新datagrid
		 					var pne = new poolPneDatagridModel();
		 					pne.searchPoolPneInfo();
		 			    },
		 			    failure:function(data){
							$("#addPneNetworkWindow").jqxWindow('close');
		 			    }
		 			});
		     });
	    };
	    
	    // 初始化window
	    this.initPopWindow = function(){
	    	$("#addPneNetworkWindow").jqxWindow({
	            width: 600, 
	            height:360,
	            theme:currentTheme,
	            resizable: false,  
	            isModal: true, 
	            autoOpen: false, 
	            cancelButton: $("#addPneNetworkCancel"), 
	            modalOpacity: 0.3,
	            initContent:function(){
	            	// 初始化组件
	                $("#add-pne-networkName").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-pne-subnet").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-pne-subnetMask").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-pne-gateway").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-pne-dns1").jqxInput({placeHolder: "", height: 22, width: 150, minLength: 1,theme:currentTheme});
	                $("#add-pne-dns2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-pne-ipRetainStart1").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-pne-ipRetainEnd1").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-pne-ipRetainStart2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-pne-ipRetainEnd2").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-pne-ipRetainStart3").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-pne-ipRetainEnd3").jqxInput({placeHolder: "", height: 22, width:150, minLength: 1,theme:currentTheme});
	                $("#add-pne-description").jqxInput({placeHolder: "", height: 46, width:400, minLength: 1,theme:currentTheme});

	                $("#addPneNetworkSave").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                $("#addPneNetworkCancel").jqxButton({ width: '50',height:"25",theme:currentTheme});
	                
	                // 初始化下拉列表框 
	        		var codesearch = new codeModel({width:150,autoDropDownHeight:true,dropDownWidth:150,async:false});
	        		codesearch.getCommonCode("add-pne-ipType","IP_TYPE",false);
	        		codesearch.getCustomCode("add-pne-resTopologySid","/objStorages/findVeBySid","resTopologyName","resTopologySid",false,"POST",{resTopologySid:resTopologySid});

	            }
	        });
	    };
	    
	    // 判断操作按钮，初始化按钮
	    this.initAddContent = function(){
	    	// 初始化组件
	        $("#add-pne-networkName").val("");
	        $("#add-pne-subnet").val("");
	        $("#add-pne-subnetMask").val("");
	        $("#add-pne-gateway").val("");
	        $("#add-pne-dns1").val("");
	        $("#add-pne-dns2").val("");
	        $("#add-pne-ipType").val("");
	        $("#add-pne-re-vlanId").val("");
	        $("#add-pne-ipRetainStart1").val("");
	        $("#add-pne-ipRetainEnd1").val("");
	        $("#add-pne-ipRetainStart2").val("");
	        $("#add-pne-ipRetainEnd2").val("");
	        $("#add-pne-ipRetainStart3").val("");
	        $("#add-pne-ipRetainEnd3").val("");
	        $("#add-pne-re-vlanPool").val("");
	        $("#add-pne-resTopologySid").val("");
	     };
 };
 
//弹出新增网络对话框--仅供网络资源池调用
 function addPneNetworkInfoWindowUsingPn(){
	   var str = '<td align="right">VLAN池:</td><td align="left" ><div id="add-pne-re-vlanPool"></div></td><td align="right"><font style="color:red">*</font>VLAN ID:</td><td align="left"><div data-name="vlanId" id="add-pne-re-vlanId"></div></td>';
	   $("#vlanPnePool").html(str);
	   // 判断是内网还是外网
	    var codesearch = new codeModel({width:150,dropDownWidth:150,async:false});
		// 查询出所有资源分区下面的VLAN池
		
		codesearch.getCustomCode("add-pne-re-vlanPool","/poolVlans","resPoolName","resPoolSid",false,"POST",{parentTopologySid:resTopologySid});
		codesearch.getCustomCode("add-pne-re-vlanId","/vlanRes","vlanId","vlanId",false,"POST",{unused:"",resPoolSid:$("#add-pne-re-vlanPool").val()});
		$("#add-pne-re-vlanId").jqxDropDownList({ autoDropDownHeight:false,dropDownHeight:200});
	  	 // 当VLAN池值变化的时候
	     $('#add-pne-re-vlanPool').on('select', function (event){
		    var args = event.args;
		    if (args) {
			    var item = args.item;
			    var value = item.value;
			    var codesearch = new codeModel({width:150,dropDownWidth:150,async:false});
				// 查询出所有资源分区下面的VLAN池
				codesearch.getCustomCode("add-pne-re-vlanId","/vlanRes","vlanId","vlanId",false,"POST",{unused:"",resPoolSid:$("#add-pne-re-vlanPool").val()});
				$("#add-pne-re-vlanId").jqxDropDownList({ autoDropDownHeight:false,dropDownHeight:200});
		    }                        
		 });
	  
	    var pne = new addPneNetworkModel();
		pne.initAddContent();
	  
	    var windowW = $(window).width(); 
	 	var windowH = $(window).height(); 
	 	// 清除新增残留数据
	 	$("#addPneNetworkWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-360)/2 } });
	 	$("#addPneNetworkWindow").jqxWindow('open');
 }
 
//弹出新增网络对话框
 function addPneNetworkInfoWindow(){
	   var str = '<td align="right">VLAN池:</td><td align="left" ><div id="add-pne-re-vlanPool"></div></td><td align="right"><font style="color:red">*</font>VLAN ID:</td><td align="left"><div data-name="vlanId" id="add-pne-re-vlanId"></div></td>';
	   $("#vlanPnePool").html(str);
	   // 判断是内网还是外网
	  Core.AjaxRequest({
			url : ws_url + "/rest/topology/"+resTopologySid,
			type:"get",
			async:false,
			callback : function (data) {
				var codesearch = new codeModel({width:150,dropDownWidth:150,async:false});
				// 查询出所有资源分区下面的VLAN池
				
				codesearch.getCustomCode("add-pne-re-vlanPool","/poolVlans","resPoolName","resPoolSid",false,"POST",{parentTopologySid:data.parentTopologySid});
				codesearch.getCustomCode("add-pne-re-vlanId","/vlanRes","vlanId","vlanId",false,"POST",{unused:"",resPoolSid:$("#add-pne-re-vlanPool").val()});
				$("#add-pne-re-vlanId").jqxDropDownList({ autoDropDownHeight:false,dropDownHeight:200});
				
		    }
		});
	  	 // 当VLAN池值变化的时候
	     $('#add-pne-re-vlanPool').on('select', function (event){
		    var args = event.args;
		    if (args) {
			    var item = args.item;
			    var value = item.value;
			    var codesearch = new codeModel({width:150,dropDownWidth:150,async:false});
				// 查询出所有资源分区下面的VLAN池
				codesearch.getCustomCode("add-pne-re-vlanId","/vlanRes","vlanId","vlanId",false,"POST",{unused:"yes",resPoolSid:$("#add-pne-re-vlanPool").val()});
				$("#add-pne-re-vlanId").jqxDropDownList({ autoDropDownHeight:false,dropDownHeight:200});
		    }                        
		 });
	  
	    var pne = new addPneNetworkModel();
		pne.initAddContent();
	  
	    var windowW = $(window).width(); 
	 	var windowH = $(window).height(); 
	 	// 清除新增残留数据
	 	$("#addPneNetworkWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-360)/2 } });
	 	$("#addPneNetworkWindow").jqxWindow('open');
 }

 // 提交将选择主机加入池
 function addPneNetworkSubmit(){
 	$('#addPneNetworkForm').jqxValidator('validate');
 }
 
//弹出新增存储window框,供非datagrid列表操作调用
 function addNetworkWindow(){
 	var windowW = $(window).width(); 
 	var windowH = $(window).height(); 
 	// 清除新增残留数据
 	 $("#add-pne-networkName").val("");
     $("#add-pne-subnet").val("");
     $("#add-pne-subnetMask").val("");
     $("#add-pne-gateway").val("");
 	
 	$("#addNetworkWindow").jqxWindow({ position: { x: (windowW-600)/2, y: (windowH-400)/2 } });
 	$("#addNetworkWindow").jqxWindow('open');
 }
  