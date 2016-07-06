package com.h3c.idcloud.core.service.res.provider;


import com.h3c.idcloud.core.persist.res.dao.ResHostMapper;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyConfigMapper;
import com.h3c.idcloud.core.persist.res.dao.ResTopologyMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmMapper;
import com.h3c.idcloud.core.persist.res.dao.ResVmVncPortMapper;
import com.h3c.idcloud.core.pojo.dto.res.ResHost;
import com.h3c.idcloud.core.pojo.dto.res.ResTopology;
import com.h3c.idcloud.core.pojo.dto.res.ResTopologyConfig;
import com.h3c.idcloud.core.pojo.dto.res.ResVm;
import com.h3c.idcloud.core.pojo.dto.res.ResVmVncPort;
import com.h3c.idcloud.core.service.res.api.ResVmVncPortService;
import com.h3c.idcloud.infrastructure.common.pojo.Criteria;
import com.h3c.idcloud.infrastructure.common.pojo.RESTHttpResponse;
import com.h3c.idcloud.infrastructure.common.util.JsonUtil;
import com.h3c.idcloud.infrastructure.common.util.PropertiesUtil;
import com.h3c.idcloud.infrastructure.common.util.RSETClientUtil;
import com.h3c.idcloud.infrastructure.common.util.SpringContextHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Res vm vnc port service 类.
 *
 * @author Chaohong.Mao
 */
@Component
public class ResVmVncPortServiceImpl implements ResVmVncPortService {

    private static final Logger logger = LoggerFactory.getLogger(ResVmVncPortServiceImpl.class);
    @Autowired
    private ResVmVncPortMapper resVmVncPortMapper;
    @Autowired
    private ResTopologyConfigMapper resTopologyConfigMapper;
    @Autowired
    private ResVmMapper resVmMapper;
    @Autowired
    private ResHostMapper resHostMapper;
    @Autowired
    private ResTopologyMapper resTopologyMapper;

    /**
     * list 已占用端口号 key  分配的端口号 查看分配的端口号是否占用，如果占用则分配下一个端口
     *
     * @param list  the list
     * @param key   the key
     * @param start the start
     * @param end   the end
     * @return the int
     */
    public static int position(List<Long> list, int key, Integer start, Integer end) {
        if (start == null) {
            start = 0;
        }
        if (end == null) {
            end = list.size() - 1;
        }
        if (start > end || end > list.size() || start < 0) {
            System.out.println("Error Paramerers!");
            System.exit(0);
        }
        int pos = find_By_BinarySearch(list, key, start, end);
        if (pos != -1) {
            return -1;
        } else {
            return 1;
        }
    }

    /**
     * 調用查找方法
     *
     * @param list the list
     * @param key  the key
     * @param low  the low
     * @param high the high
     * @return the int
     */
    private static int find_By_BinarySearch(List<Long> list, int key, Integer low, Integer high) {
        return binarySearch(list, key, low, high);
    }

    /**
     * 二分法，查找key是否已经存在list中
     *
     * @param list the list
     * @param key  the key
     * @param low  the low
     * @param high the high
     * @return the int
     */
    private static int binarySearch(List<Long> list, int key, int low, int high) {
        if (low > high) {
            return -1;
        }
        int mid = (low + high) / 2;
        if (list.get(mid) == key) {
            return mid;
        } else if (list.get(mid) < key) {
            return binarySearch(list, key, mid + 1, high);
        } else {
            return binarySearch(list, key, low, mid - 1);
        }
    }

    /**
     * 根据文件当前位置，向文件中插入数据
     *
     * @param destFileName the dest file name
     * @return boolean
     */
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            return false;
        }
        //判断目标文件所在的目录是否存在
        if (!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            if (!file.getParentFile().mkdirs()) {
                return false;
            }
        }
        //创建目标文件
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获得 tokens.
     *
     * @param file   the file
     * @param tokens the tokens
     * @return the tokens
     */
    public static boolean getTokens(String file, String tokens) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(tokens);
            out.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 创建虚拟机时添加vnc服务端口号 传虚拟换环境sid
     */
    @Override
    public String getVNCPort(String resTopologySid) {
        int portStart = 0;
        int portEnd = 0;
        int ports = 0;
        String openvnc = "";
        Criteria example = new Criteria();
        List<String> listSid = new ArrayList<String>();
        //查询数据库获得开始，终止vnc端口号和是否开启vnc服务
        example.put("resTopologySid", resTopologySid);
        String vncport = "";
        List<ResTopologyConfig> list = this.resTopologyConfigMapper.selectByParams(example);
        for (ResTopologyConfig rtc : list) {
            String jsonMap = JsonUtil.toJson(rtc);
            ResTopologyConfig rtcStr;

            rtcStr = JsonUtil.fromJson(jsonMap, ResTopologyConfig.class);
            if ("vnc_connect_port_start".equals(rtcStr.getConfigKey())) {
                portStart = Integer.parseInt(rtcStr.getConfigValue());
            }
            if ("vnc_connect_port_end".equals(rtcStr.getConfigKey())) {
                portEnd = Integer.parseInt(rtcStr.getConfigValue());
            }
            if ("open_vnc".equals(rtcStr.getConfigKey())) {
                openvnc = rtcStr.getConfigValue();
            }
        }
        if ("true".equals(openvnc)) {
            //查询数据库获取已经占用的端口
            //如果数据库中没有占用的端口，则默认分配第一个起始端口
            List<ResVmVncPort> vncPortList = this.resVmVncPortMapper.selectByParamByVmSid(example);
            if (vncPortList.size() == 0) {
                vncport = String.valueOf(portStart);
            } else {
                List<Long> occupiedPort = this.create_Array(resTopologySid);
                for (int i = portStart; i <= portEnd; i++) {
                    //调用查找方法，找出未分配端口
                    ports = position(occupiedPort, i, 0, null);
                    if (ports == 1) {
                        vncport = String.valueOf(i);
                        break;
                    }
                }
            }
        } else {
            vncport = "";
            return vncport;
        }
        return vncport;
    }

    /**
     * 添加VNC端口号 如果vm已经存在，判断vm是否已经分配vnc端口号 如没有，则调用adapter对vm添加vnc端口 如已分配，则返回"已分配" resVmSid:虚拟机sid
     * resTopologySid：所属虚拟化环境sid providerUrl，authUser，authPass：虚拟化环境地址，用户，密码
     * isOpen:vnc端口标示。true：配置vnc端口，false：关闭虚机vnc端口
     *
     * @author jiangwl
     */
    @Override
    public boolean vmResetVNCPort(String resVmSid, String providerUrl, String authUser,
                                  String authPass, boolean isOpen) {
        boolean result = false;
        Criteria criteria = new Criteria();
        criteria.put("resVmSid", resVmSid);
        String vncport = "";
        List<ResVmVncPort> rvvp = this.resVmVncPortMapper.selectByParams(criteria);
        ResVm resvm = this.resVmMapper.selectByPrimaryKey(resVmSid);
        ResHost reshost = this.resHostMapper.selectByPrimaryKey(resvm.getAllocateResHostSid());
        ResTopology
                restopology =
                this.resTopologyMapper.selectByPrimaryKey(reshost.getParentTopologySid());
        //判断vnc端口是否存在，如果存在，则判断是否需要关闭，如果不存在，则添加
        if (rvvp.size() >= 1) {
            try {
                if (!isOpen) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("providerUrl", providerUrl);
                    map.put("authUser", authUser);
                    map.put("authPass", authPass);
                    map.put("vmName", resvm.getVmName());
                    map.put("port", vncport);
                    map.put("isClose", "false");
                    String adapterUrl = PropertiesUtil.getProperty("VMware.adapter.url");
                    String clusterUrl = adapterUrl + "/vm/openVNC";
                    String paramsJson = JsonUtil.toJson(map);
                    RESTHttpResponse responseResult = RSETClientUtil.post(clusterUrl, paramsJson);
                    if (RESTHttpResponse.SUCCESS.equals(responseResult.getStatus())) {
                        result = true;
                        this.deleteVncTokens(resVmSid);
                        this.resVmVncPortMapper.deleteByPrimaryKey(resVmSid);
                        return result;
                    } else {
                        logger.debug("调用adpter失败！");
                        return false;
                    }
                }
            } catch (Exception e) {
                logger.debug("获取VNC服务端口失败");
                e.printStackTrace();
                return false;
            }
        } else if (rvvp.size() == 0) {
            try {
                if (isOpen) {
                    vncport = this.getVNCPort(restopology.getParentTopologySid());
                }
                if (null != vncport) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("providerUrl", providerUrl);
                    map.put("authUser", authUser);
                    map.put("authPass", authPass);
                    map.put("vmName", resvm.getVmName());
                    map.put("port", vncport);
                    map.put("isClose", "true");
                    String adapterUrl = PropertiesUtil.getProperty("VMware.adapter.url");
                    String clusterUrl = adapterUrl + "/vm/openVNC";
                    String paramsJson = JsonUtil.toJson(map);
                    RESTHttpResponse responseResult = RSETClientUtil.post(clusterUrl, paramsJson);
                    if (RESTHttpResponse.SUCCESS.equals(responseResult.getStatus())) {
                        result = true;
                        this.creatVncTokens(resVmSid, vncport);
                        return result;
                    } else {
                        logger.debug("调用adpter失败！");
                        return false;
                    }
                }
            } catch (Exception e) {
                logger.debug("获取VNC服务端口失败");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 创建novnc的tokens文件,并将文件插入数据库 tokens格式：tokenid: hostname:port
     */
    @Override
    public boolean creatVncTokens(String resVmSid, String port) {
        ResVmVncPort rvvp = new ResVmVncPort();
        //获取主机名称
        ResVm rv = this.resVmMapper.selectByPrimaryKey(resVmSid);
        ResHost rh = this.resHostMapper.selectByPrimaryKey(rv.getAllocateResHostSid());
        String hostName = rh.getHostIp();
        //设置tokenid的值
        UUID uuid = UUID.randomUUID();
        //组装tokens格式
        String tokens = uuid + ": " + hostName + ":" + port;
        //将组装好的token写入到文件中
        String fileName = "/usr/noVNC/tokens/vnctokens";
        //String fileName =  "D:\\work\\tempFile.txt";
        boolean file = createFile(fileName);
        boolean result = getTokens(fileName, tokens);
        //写入成功后，将数据插入到数据库中
        if (result) {
            try {
                rvvp.setResVmSid(resVmSid);
                rvvp.setToken(uuid.toString());
                rvvp.setVncPort(Long.valueOf(port));
                rvvp.setHostIp(hostName);
                int resvnc = this.resVmVncPortMapper.insertSelective(rvvp);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 当删除虚拟机或者删除vnc端口的时候，删除novnc的tokens文件
     */
    @Override
    public boolean deleteVncTokens(String resVmSid) {
        ResVm rvm = this.resVmMapper.selectByPrimaryKey(resVmSid);
        ResHost rh = this.resHostMapper.selectByPrimaryKey(rvm.getAllocateResHostSid());
        //String hostName = rh.getHostName();
        String hostName = rh.getHostIp();
        ResVmVncPort rvvp = this.resVmVncPortMapper.selectByPrimaryKey(resVmSid);
        String token = rvvp.getToken();
        Long port = rvvp.getVncPort();
        String tokens = token + ": " + hostName + ":" + port;
        String destFileName = "/usr/noVNC/tokens/vnctokens";
        //String destFileName =  "D:\\work\\tempFile.txt";
        List<String> list;
        try {
            list = this.readFile(destFileName, tokens);
            this.writeFile(destFileName, list);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 开启novnc的webconsole控制台
     */
    @Override
    public String getNoVncWebConsole(String resVmSid, String host) {
        ResVmVncPort rvvp = this.resVmVncPortMapper.selectByPrimaryKey(resVmSid);
        String tokens = rvvp.getToken().trim();
        //String hostName = rfia.getIpAddr("PRIVATE");
        String url = "http://" + host + ":6080/vnc_auto.html?token=" + tokens;
        return url;
    }

    @Override
    public boolean selectByParamByCountVnc(String resTopologySid) {
        int portStart = 0;
        int portEnd = 0;
        int result = this.resVmVncPortMapper.selectByParamByCountVnc(resTopologySid);
        Criteria criteria = new Criteria();
        criteria.put("resTopologySid", resTopologySid);
        List<ResTopologyConfig> list = this.resTopologyConfigMapper.selectByParams(criteria);
        for (ResTopologyConfig rtcStr : list) {
            if ("vnc_connect_port_start".equals(rtcStr.getConfigKey())) {
                portStart = Integer.parseInt(rtcStr.getConfigValue());
            }
            if ("vnc_connect_port_end".equals(rtcStr.getConfigKey())) {
                portEnd = Integer.parseInt(rtcStr.getConfigValue());
            }
        }
        int countPort = (portEnd - portStart) + 1;
        if (result == countPort) {
            return false;
        } else {
            return result <= countPort;
        }

    }

    /**
     * 虚拟机迁移后，修改token配置文件
     *
     * @param path       文件路径
     * @param oldStr     被修改的数据
     * @param replaceStr 替换数据
     */
    @Override
    public void modifyVncTokens(String path, String oldStr, String replaceStr) {
        String temp = "";
        try {
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();
            // 保存该行前面的内容
            for (int j = 1; (temp = br.readLine()) != null && !temp.equals(oldStr); j++) {
                buf = buf.append(temp);
                buf = buf.append(System.getProperty("line.separator"));
            }
            // 将内容插入
            buf = buf.append(replaceStr);
            // 保存该行后面的内容
            while ((temp = br.readLine()) != null) {
                buf = buf.append(System.getProperty("line.separator"));
                buf = buf.append(temp);
            }
            br.close();
            FileOutputStream fos = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分配端口方法
     */
    @Override
    public boolean findVmVncSwitch(String resTopologySid) {
        Criteria example = new Criteria();
        example.put("resTopologySid", resTopologySid);
        String openvnc = "";
        List<ResTopologyConfig> list = this.resTopologyConfigMapper.selectByParams(example);
        for (ResTopologyConfig rtc : list) {
            try {
                if ("open_vnc".equals(rtc.getConfigKey())) {
                    openvnc = rtc.getConfigValue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return !("false".equals(openvnc) || "".equals(openvnc));
    }

    /**
     * 获取已经分配的vnc端口号
     *
     * @param resTopologySid the res topology sid
     * @return list
     */
    public List<Long> create_Array(String resTopologySid) {
        Criteria example = new Criteria();
        ResVmVncPort resVmVncPorts = new ResVmVncPort();
        ResVmVncPortService
                resVmVncPortService =
                SpringContextHolder.getBean("resVmVncPortServiceImpl");
        example.put("resTopologySid", resTopologySid);
        List<ResVmVncPort> vncPortList = this.resVmVncPortMapper.selectByParamByVmSid(example);
        List<Long> portlist = new ArrayList<Long>();
        for (ResVmVncPort resVmVncPort : vncPortList) {
            String jsons = JsonUtil.toJson(resVmVncPort);
            resVmVncPorts = JsonUtil.fromJson(jsons, ResVmVncPort.class);

            portlist.add(resVmVncPorts.getVncPort());
        }
        return portlist;
    }

    /**
     * 根据文件当前位置，删除token配置信息内容
     *
     * @param filePath the file path
     * @param str      the str
     * @return list
     * @throws IOException the io exception
     */
    public List<String> readFile(String filePath, String str) throws IOException {
        File file = new File(filePath);
        BufferedReader reader = null;
        List<String> list = new ArrayList<String>();
        reader = new BufferedReader(new FileReader(file));
        String text = reader.readLine();
        while (text != null) {
            if (!text.trim().equals(str)) {
                list.add(text + "\r\n");
            }
            text = reader.readLine();
        }
        reader.close();
        return list;
    }

    /**
     * Write file.
     *
     * @param filePath the file path
     * @param list     the list
     * @throws IOException the io exception
     */
    public void writeFile(String filePath, List<String> list) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(file);
        for (String s : list) {
            outputStream.write(s.getBytes());
        }
        outputStream.close();
    }
}