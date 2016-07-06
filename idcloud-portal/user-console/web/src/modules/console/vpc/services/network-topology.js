define(["d3"],function(d3) {

    function Network(data) {
      for (var key in data) {
        if ({}.hasOwnProperty.call(data, key)) {
          this[key] = data[key];
        }
      }
      this.iconType = 'text';
      this.icon = '\uf0c2'; // Cloud
      this.collapsed = false;
      this.type = 'network';
      this.instances = 0;
    }

    function ExternalNetwork(data) {
      for (var key in data) {
        if ({}.hasOwnProperty.call(data, key)) {
          this[key] = data[key];
        }
      }
      this.collapsed = false;
      this.iconType = 'text';
      this.icon = '\uf0ac'; // Globe
      this.instances = 0;
    }

    function Router(data) {
      for (var key in data) {
        if ({}.hasOwnProperty.call(data, key)) {
          this[key] = data[key];
        }
      }
      this.iconType = 'text';
      this.icon = '\uf0b2';
      this.svg = 'router';
      this.networks = [];
      this.ports = [];
      this.type = 'router';
    }

    function Server(data) {
      for (var key in data) {
        if ({}.hasOwnProperty.call(data, key)) {
          this[key] = data[key];
        }
      }
      this.iconType = 'text';
      this.icon = '\uf108'; // Server
      this.networks = [];
      this.type = 'instance';
      this.ip_addresses = [];
    }


    var NetworkTopology = function(container,localdata){

        var self = this;
        typeof(container)=='string' && (container=document.getElementById(container));
        this.width = container.clientWidth;
        this.height = container.clientHeight;
        
        this.startTime = 0;
        this.endTime = 0;

        this.data = [];
        this.nodes =  [];
        this.links = [];
        this.zoom = d3.behavior.zoom();
        this.reload_duration = 10000;
        this.data_loaded = false;
        this.drag = d3.behavior.drag()
                .origin(function(d) { return d; })
                .on("dragstart", function(d) {
                  d3.event.sourceEvent.stopPropagation();
                  d3.select(this).classed("dragging", true);
                  self.force.start();
                })
                .on("drag", function(d) {
                  d3.select(this).attr("cx", d.x = d3.event.x).attr("cy", d.y = d3.event.y);
                })
                .on("dragend", function(d) {
                  d3.select(this).classed("dragging", false);
                });


        this.init = function(){

            // 初始化数据
            self.data = {};
            self.data.networks = {};
            self.data.routers = {};
            self.data.servers = {};
            self.data.ports = {};

            // 创建D3画布
            self.create_canvas();

            self.loading();

            // 启动力导图
            self.start_force_direction(0.05,70,-700);
            // 重新获取网络数据
            setTimeout(function(){
              self.retrieve_network_info(true);
            },1000);
            
        };

        this.create_canvas = function(){
            $(container).html("");
            self.outer_group = d3.select(container).append('svg')
              .attr('width', '100%')
              .attr('height', '100%')
              .attr('pointer-events', 'all')
              .append('g')
              .call(self.zoom
                .scaleExtent([0.5,1.3])
                .on('zoom', function() {
                    // 清除掉弹出tips
                    // self.delete_balloon();

                    self.vis.attr('transform', 'translate(' + d3.event.translate + ')scale(' +
                      self.zoom.scale() + ')');
                    self.translate = d3.event.translate;
                  })
                )
              .on('dblclick.zoom', null);

            // 画布背景事件
            self.outer_group.append('rect')
              .attr('width', '100%')
              .attr('height', '100%')
              .attr('fill', 'white')
              .on('click', function() {
                // 隐藏面板
                // $("#nodePanel").hide();

                // self.vis.selectAll('circle')
                //         .style('stroke','black');

                // self.force.start();

                if($("#tips").length > 0){
                   $("#tips").remove();
                }

                self.force.start();
                
              });

             // 赋值新变量
            self.vis = self.outer_group.append('g');
            // self.vis = self.outer_group;
        };

        this.start_force_direction = function(grav, dist, ch){

            // 曲线
            self.curve = d3.svg.line()
              .interpolate('cardinal-closed')
              .tension(0.85);

            self.fill = d3.scale.category10(); // 颜色比例尺

            self.force = d3.layout.force()
              .gravity(grav)
              .linkDistance(function(d) {

                if (d.source.data instanceof Server || d.target.data instanceof Server) {

                  if (d.source.data.networks) {

                    return (dist * d.source.data.networks.length) + (5 * d.target.data.instances) + 20;

                  } else if (d.target.data.networks) {

                    return (dist * d.target.data.networks.length) + (5 * d.source.data.instances) + 20;

                  }

                } else if (d.source.data instanceof Router || d.target.data instanceof Router) {

                  if (d.source.data.networks) {

                    if (d.source.data.networks.length === 0) {
                      return dist + 20;
                    } else if (d.target.data.instances) {
                      return dist * d.source.data.networks.length + (10 * d.target.data.instances) + 20;
                    }
                    return dist * d.source.data.networks.length + 20;

                  } else if (d.target.data.networks) {
                    
                    if (d.target.data.networks.length === 0) {
                      return dist + 20;
                    } else if (d.source.data.instances) {
                      return dist * d.target.data.networks.length + (10 * d.source.data.instances) + 20;
                    }
                    return dist * d.source.data.networks.length + 20;

                  }
                } else {
                  return dist;
                }
              })
              .charge(ch)
              .size([self.width,self.height])
              .nodes(self.nodes)
              .links(self.links)
              .on('tick', function() {
                self.vis.selectAll('g.node')
                  .attr('transform', function(d) {
                    return 'translate(' + d.x + ',' + d.y + ')';
                  });

                self.vis.selectAll('line.link')
                  .attr('x1', function(d) { return d.source.x; })
                  .attr('y1', function(d) { return d.source.y; })
                  .attr('x2', function(d) { return d.target.x; })
                  .attr('y2', function(d) { return d.target.y; });

                self.vis.selectAll('path.hulls')
                  .data(self.convex_hulls(self.vis.selectAll('g.node').data()))
                    .attr('d', function(d) {
                      return self.curve(d.path);
                    })
                  .enter().insert('path', 'g')
                    .attr('class', 'hulls')
                    .style('fill', function(d) {
                      return self.fill(d.group);
                    })
                    .style('stroke', function(d) {
                      return self.fill(d.group);
                    })
                    .style('stroke-linejoin', 'round')
                    .style('stroke-width', 10)
                    .style('opacity', 0.2);
              });
        };

        this.retrieve_network_info = function(force_start){
            // 获取数据
            var data = localdata;
            
            self.data_loaded = true;
            self.load_topology(data);

            if (force_start) {
              var i = 0;
              self.force.start();
              while (i <= 100) {
                self.force.tick();
                i++;
              }
            }

            setTimeout(function() {
              self.retrieve_network_info();
            }, self.reload_duration);
        };

        // 创建拓扑图
        this.load_topology = function(data){
            //var self = this;
            var net, _i, _netlen, _netref, rou, _j, _roulen, _rouref, port, _l, _portlen, _portref,
                ser, _k, _serlen, _serref, obj, vmCount;
            var change = false;
            var filterNode = function(obj) {
              return function(d) {
                return obj == d.data;
              };
            };

            // 网络创建
            _netref = data.networks;
            for (_i = 0, _netlen = _netref.length; _i < _netlen; _i++) {
              net = _netref[_i];
              var network = null;
              if (net['router:external'] === true) {
                network = new ExternalNetwork(net);
              } else {
                network = new Network(net);
              }

              if (!self.already_in_graph(self.data.networks, network)) {
                self.new_node(network);
                change = true;
              } else {
                obj = self.find_by_id(network.id);
                if (obj) {
                  network.collapsed = obj.data.collapsed;
                  network.instances = obj.data.instances;
                  obj.data = network;
                }
              }
              self.data.networks[network.id] = network;
            }

            // 路由创建
            _rouref = data.routers;
            for (_j = 0, _roulen = _rouref.length; _j < _roulen; _j++) {
              rou = _rouref[_j];
              var router = new Router(rou);
              if (!self.already_in_graph(self.data.routers, router)) {
                self.new_node(router);
                change = true;
              } else {
                obj = self.find_by_id(router.id);
                if (obj) {
                  // Keep networks list
                  router.networks = obj.data.networks;
                  // Keep ports list
                  router.ports = obj.data.ports;
                  obj.data = router;
                }
              }
              self.data.routers[router.id] = router;
            }

            // 虚机创建
            _serref = data.servers;
            for (_k = 0, _serlen = _serref.length; _k < _serlen; _k++) {
              ser = _serref[_k];
              var server = new Server(ser);
              if (!self.already_in_graph(self.data.servers, server)) {
                self.new_node(server);
                change = true;
              } else {
                obj = self.find_by_id(server.id);
                if (obj) {
                  // Keep networks list
                  server.networks = obj.data.networks;
                  // Keep ip address list
                  server.ip_addresses = obj.data.ip_addresses;
                  obj.data = server;
                } else if (self.data.servers[server.id] !== undefined) {
                  // This is used when servers are hidden because the network is
                  // collapsed
                    server.networks = self.data.servers[server.id].networks;
                    server.ip_addresses = self.data.servers[server.id].ip_addresses;
                }
              }
              self.data.servers[server.id] = server;
            }

            // 接口
            _portref = data.ports;
            for (_l = 0, _portlen = _portref.length; _l < _portlen; _l++) {
              port = _portref[_l];
              if (!self.already_in_graph(self.data.ports, port)) {
                var device = self.find_by_id(port.device_id);
                var _network = self.find_by_id(port.network_id);
                if (device && _network) {
                  if (port.device_owner == 'compute:nova' || port.device_owner == 'compute:None') {
                    _network.data.instances++;
                    device.data.networks.push(_network.data);
                    if (port.fixed_ips) {
                      for(var ip in port.fixed_ips) {
                        device.data.ip_addresses.push(port.fixed_ips[ip]);
                      }
                    }
                    // Remove the recently added node if connected to a network which is
                    // currently collapsed
                    if (_network.data.collapsed) {
                      if (device.data.networks.length == 1) {
                        self.data.servers[device.data.id].networks = device.data.networks;
                        self.data.servers[device.data.id].ip_addresses = device.data.ip_addresses;
                        self.removeNode(self.data.servers[port.device_id]);
                        vmCount = Number(self.vis.selectAll('.vmCount').filter(filterNode(_network.data))[0][0].textContent);
                        self.vis.selectAll('.vmCount').filter(filterNode(_network.data))[0][0].textContent = vmCount + 1;
                        continue;
                      }
                    }
                  } else if (port.device_owner == 'network:router_interface') {
                    device.data.networks.push(_network.data);
                    device.data.ports.push(port);
                  } else if (device.data.ports) {
                    device.data.ports.push(port);
                  }
                  self.new_link(self.find_by_id(port.device_id), self.find_by_id(port.network_id));
                  change = true;
                } else if (_network && port.device_owner == 'compute:nova') {
                  // Need to add a previously hidden node to the graph because it is
                  // connected to more than 1 network
                  if (_network.data.collapsed) {
                    server = self.data.servers[port.device_id];
                    server.networks.push(_network.data);
                    if (port.fixed_ips) {
                      for(var ip in port.fixed_ips) {
                        server.ip_addresses.push(port.fixed_ips[ip]);
                      }
                    }
                    self.new_node(server);
                    // decrease collapsed vm count on network
                    vmCount = Number(self.vis.selectAll('.vmCount').filter(filterNode(server.networks[0]))[0][0].textContent);
                    if (vmCount == 1) {
                      self.vis.selectAll('.vmCount').filter(filterNode(server.networks[0]))[0][0].textContent = '';
                    } else {
                      self.vis.selectAll('.vmCount').filter(filterNode(server.networks[0]))[0][0].textContent = vmCount - 1;
                    }
                    // Add back in first network link
                    self.new_link(self.find_by_id(port.device_id), self.find_by_id(server.networks[0].id));
                    // Add new link
                    self.new_link(self.find_by_id(port.device_id), self.find_by_id(port.network_id));
                    change = true;
                  }
                }
              }
              self.data.ports[port.id+port.device_id+port.network_id] = port;
            }

            if (change) {
                self.force.start();
            }

        };

        // 会话网络区域
        this.convex_hulls = function(nodes){
            var net, _i, _len, _ref, _h, i;
            var hulls = {};
            var networkids = {};
            var k = 0;
            var offset = 40;

            while ( k < nodes.length) {
              var n = nodes[k];
              if (n.data !== undefined) {
                if (n.data instanceof Server) {
                  _ref = n.data.networks;
                  for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                    net = _ref[_i];
                    if (net instanceof Network) {
                      _h = hulls[net.id] || (hulls[net.id] = []);
                      _h.push([n.x - offset, n.y - offset]);
                      _h.push([n.x - offset, n.y + offset]);
                      _h.push([n.x + offset, n.y - offset]);
                      _h.push([n.x + offset, n.y + offset]);
                    }
                  }
                } else if (n.data instanceof Network) {
                  net = n.data;
                  networkids[net.id] = n;
                  _h = hulls[net.id] || (hulls[net.id] = []);
                  _h.push([n.x - offset, n.y - offset]);
                  _h.push([n.x - offset, n.y + offset]);
                  _h.push([n.x + offset, n.y - offset]);
                  _h.push([n.x + offset, n.y + offset]);

                }
              }
              ++k;
            }
            var hullset = [];
            for (i in hulls) {
              if ({}.hasOwnProperty.call(hulls, i)) {
                hullset.push({group: i, network: networkids[i], path: d3.geom.hull(hulls[i])});
              }
            }

            return hullset;
        };

        this.already_in_graph = function(data, node) {
            // Check for gateway that may not have unique id
            if (data == this.data.ports) {
              for (var p in data) {
                if (JSON.stringify(data[p]) == JSON.stringify(node)) {
                  return true;
                }
              }
              return false;
            }
            // All other node types have UUIDs
            for (var n in data) {
              if (n == node.id) {
                return true;
              }
            }
            return false;
      };

      this.find_by_id = function(id) {

        var obj, _i, _len, _ref;
        _ref = self.vis.selectAll('g.node').data();
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          obj = _ref[_i];
          if (obj.data.id == id) {
            return obj;
          }
        }
        return undefined;
      },

      this.new_node = function(data, x, y) {
            var self = this;
            data = {data: data};
            if (x && y) {
              data.x = x;
              data.y = y;
            }
            self.nodes.push(data);

            var node = self.vis.selectAll('g.node').data(self.nodes);
            var nodeEnter = node.enter().append('g')
              .attr('class', 'node')
              .style('fill', 'white')
              .call(self.drag);

            nodeEnter.append('circle')
              .attr('class', 'frame')
              .attr('r', function(d) {
                switch (Object.getPrototypeOf(d.data)) {
                  case ExternalNetwork.prototype:
                    return 28;
                  case Network.prototype:
                    return 22;
                  case Router.prototype:
                    return 25;
                  case Server.prototype:
                    return 18;
                }
              })
              .style('fill', 'white')
              .style('stroke', 'black')
              .style('stroke-width', 3);

            switch ( data.data.iconType ) {
              case 'text':
                nodeEnter.append('text')
                  .style('fill', 'black')
                  .style('font', '20px FontAwesome')
                  .attr('text-anchor', 'middle')
                  .attr('dominant-baseline', 'central')
                  .text(function(d) { return d.data.icon; })
                  .attr('transform', function(d) {
                    switch (Object.getPrototypeOf(d.data)) {
                      case ExternalNetwork.prototype:
                        return 'scale(2.5)';
                      case Network.prototype:
                        return 'scale(1.2)';
                         case Router.prototype:
                        return 'scale(1.5)';
                      case Server.prototype:
                        return 'scale(0.8)';
                    }
                  });
                break;
              case 'path':
                nodeEnter.append('path')
                  .attr('class', 'svgpath')
                  .style('fill', 'black')
                  .attr('d', function(d) { return self.svgs(d.data.svg); })
                  .attr('transform', function() {
                    return 'scale(1.2)translate(-16,-15)';
                  });
               break;
            }
                
            nodeEnter.append('text')
              .attr('class', 'nodeLabel')
              .style('display',function() {
                var labels = true;
                if (labels) {
                  return 'inline';
                } else {
                  return 'none';
                }
              })
              .style('fill','black')
              .text(function(d) {
                return d.data.name;
              })
              .attr('transform', function(d) {
                switch (Object.getPrototypeOf(d.data)) {
                  case ExternalNetwork.prototype:
                    return 'translate(40,3)';
                  case Network.prototype:
                    return 'translate(35,3)';
                  case Router.prototype:
                    return 'translate(30,3)';
                  case Server.prototype:
                    return 'translate(25,3)';
                }
              });

            if (data.data instanceof Network || data.data instanceof ExternalNetwork) {
              nodeEnter.append('svg:text')
                .attr('class','vmCount')
                .style('fill', 'black')
                .style('font-size','20')
                .text('')
                .attr('transform', 'translate(26,38)');
            }
           
            nodeEnter.on('mousedown', function(d) {
                self.startTime = new Date().getTime();
            });

            nodeEnter.on('mouseup', function(d) {
                self.endTime = new Date().getTime();
            });

            nodeEnter.on('click', function(d) {
                console.log(d3.event.x);
                if((self.endTime - self.startTime) > 100){
                  return;
                }
                // 填充数据
                if($("#tips").length > 0){
                   $("#tips").remove();
                }

                var top = d3.event.y-100;
                var left = d3.event.x-190;

                var middleStr = "";
                var bottomStr = "";

                if(d.data.type == 'instance'){
                    var _tr ='';
                    for(var i=0; i<localdata.ports.length;i++){
                        if(d.data.id == localdata.ports[i].device_id){
                           for(var j=0;j<localdata.ports[i].fixed_ips.length;j++){
                              _tr += '<tr><td width="60px" align="left">'+localdata.ports[i].fixed_ips[j].ip_address+'</td><td>&nbsp;&nbsp;&nbsp;'+localdata.ports[i].fixed_ips[j].subnet_id+'</td></tr>';
                           }
                        }
                    }
                    // 虚机
                    middleStr += '<div><table style="font-size:10px;"><tr><th align="left">IP地址</th><th></th></tr>'+_tr+'</table></div>';
                    bottomStr += '<button class="button bg-red button-little" style="margin-top: 5px;margin-right: 5px;">终止实例</button>';
                }
                else if(d.data.type == 'network'){
                    // 网络
                    var _tr ='';
                    for(var i=0; i<localdata.networks.length;i++){
                        if(d.data.id == localdata.networks[i].id){
                           for(var j=0;j<localdata.networks[i].subnets.length;j++){
                              _tr += '<tr><td width="60px" align="left">'+localdata.networks[i].subnets[j].cidr+'</td><td>&nbsp;&nbsp;&nbsp;'+localdata.networks[i].subnets[j].id+'</td></tr>';
                           }
                        }
                    }
                    middleStr += '<div><table style="font-size:10px;" width="100%"><tr><th align="left" valign="middle" height="30">子网</th><th align="right" valign="middle"><button class="button button-little">创建子网</button></th></tr>'+_tr+'</table></div>';
                    bottomStr += '<button class="button bg-red button-little" style="margin-top: 5px;margin-right: 5px;">删除网络</button>';

                }else if(d.data.type == 'router'){
                    // 路由
                    var _tr ='';
                    for(var i=0; i<localdata.routers.length;i++){
                        if(d.data.id == localdata.routers[i].id){
                           for(var j=0;j<localdata.routers[i].external_gateway_info.external_fixed_ips.length;j++){
                              _tr += '<tr><td width="60px" align="left">'+localdata.routers[i].external_gateway_info.external_fixed_ips[j].ip_address+'</td><td>&nbsp;&nbsp;&nbsp;'+localdata.routers[i].external_gateway_info.external_fixed_ips[j].subnet_id+'</td></tr>';
                           }
                        }
                    }
                    middleStr += '<div><table style="font-size:10px;" width="100%"><tr><th align="left" valign="middle" height="30"><b>接口</b></th><th align="right" valign="middle"><button class="button button-little">添加接口</button></th></tr>'+_tr+'</table></div>';
                    bottomStr += '<button class="button bg-red button-little" style="margin-top: 5px;margin-right: 5px;">删除路由</button>';
                }else{
                    // 外网
                    var _tr ='';
                    middleStr += '<div><table style="font-size:10px;" width="100%"><tr><th align="left" valign="middle" height="40"><b>子网</b></th><th align="right" valign="middle"><button class="button button-little">创建子网</button></th></tr>'+_tr+'</table></div>';
                }

                var str = '<div id="tips" style="top:'+top+'px;left:'+left+'px">'+
                          '<div class="content">'+
                            '<div class="title"><div style="float:left;width:80%;font-size:14px;"><b>'+d.data.name+'</b></div><div style="float:left;width:20%;text-align:right"><i id="iconRemove" class="icon-times" style="cursor:pointer"></i></div></div>'+
                            '<div class="info"><table><tr><td width="60px;"align="right">ID</td><td>&nbsp;&nbsp;&nbsp;'+d.data.id+'</td></tr><tr><td align="right">状态</td><td>&nbsp;&nbsp;&nbsp;<i class="icon-circle text-green"></i>&nbsp;'+d.data.status+'</td></tr></table></div>'+
                            middleStr+
                          '</div>'+
                          '<div class="footer">'+
                            bottomStr +
                          '</div>'+
                          '<div class="arrow">'+
                          '<div class="box1"></div>'+
                          '</div></div>';

                $("#networkTopo").append(str);

                $("#iconRemove").on("click",function(){
                    if($("#tips").length > 0){
                      $("#tips").remove();
                       self.force.start();
                    }
                });

                self.force.stop();
            });

            nodeEnter.on('mouseover', function(d) {
              self.vis.selectAll('line.link').filter(function(z) {
                if (z.source === d || z.target === d) {
                  return true;
                } else {
                  return false;
                }
              }).style('stroke-width', '3px');
            });

            nodeEnter.on('mouseout', function() {
              self.vis.selectAll('line.link').style('stroke-width','1px');
            });
      };

      this.new_link = function(source, target) {
        var self = this;
        self.links.push({source: source, target: target});
        var line = self.vis.selectAll('line.link').data(self.links);
        line.enter().insert('line', 'g.node')
          .attr('class', 'link')
          .attr('x1', function(d) { return d.source.x; })
          .attr('y1', function(d) { return d.source.y; })
          .attr('x2', function(d) { return d.target.x; })
          .attr('y2', function(d) { return d.target.y; })
          .style('stroke', 'black')
          .style('stroke-width', 2);
      };

      this.loading = function() {
        
        var load_text = self.vis.append('text')
            .style('fill', 'black')
            .style('font-size', '40')
            .attr('x', '47%')
            .attr('y', '50%')
            .text('');
        var counter = 0;

        var timer = setInterval(function() {
            var i;
            var str = '';
            for (i = 0; i <= counter; i++) {
              str += '.';
            }
            load_text.text(str);
            if (counter >= 9) {
              counter = 0;
            } else {
              counter++;
            }
            if (self.data_loaded) {
              clearInterval(timer);
              load_text.remove();
            }
        }, 100);
      };

      this.svgs = function(name) {
        switch (name) {
          case 'router':
            return 'm 26.628571,16.08 -8.548572,0 0,8.548571 2.08,-2.079998 6.308572,6.30857 4.38857,-4.388572 -6.308571,-6.30857 z m -21.2571429,-4.159999 8.5485709,0 0,-8.5485723 -2.08,2.08 L 5.5314281,-0.85714307 1.1428571,3.5314287 7.4514281,9.84 z m -3.108571,7.268571 0,8.548571 8.5485709,0 L 8.7314281,25.657144 15.039999,19.325715 10.674285,14.96 4.3428571,21.268573 z M 29.737142,8.8114288 l 0,-8.54857147 -8.548572,0 2.08,2.07999987 -6.308571,6.3085716 4.388572,4.3885722 6.308571,-6.3085723 z';
          default:
            return '';
        }
      };
    }



    return NetworkTopology;
});