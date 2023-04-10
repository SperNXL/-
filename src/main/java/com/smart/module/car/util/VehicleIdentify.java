package com.smart.module.car.util;

import com.smart.common.enums.VehicleMessage;
import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class VehicleIdentify {

    private String picPath;

    private String vehicleCode;
    private String[] args;

    private Process proc;

    public VehicleIdentify(String picPath) throws IOException {
        this.picPath=picPath;
        this.args=new String[]{
                "D:\\SoftWrae\\Anaconda\\python.exe",
                "predict.py",
                picPath
        };
        this.proc = Runtime.
                getRuntime().
                exec(args,null,new File("D:\\Study\\毕业设计\\license-plate-recognition"));
    }

    public Map<VehicleMessage, String> getVehicleCode(){
        try {
            //错误流
            BufferedReader error = new BufferedReader(new InputStreamReader(proc.getErrorStream(),"GBK"));
            String err = null;
            while ((err = error.readLine()) != null) {
                System.out.println("=====error：" + err);
            }

            //获取python返回的结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));
            String line=null;
            List message=new LinkedList();
            while ((line=in.readLine())!=null){
                message.add(line);
                //System.out.println(line);
            }

            String results = message.get(message.size() - 1).toString();
            String[] codeAndColor=results.split(",");
            Map<VehicleMessage,String> vehicleMessage=new HashMap<>();
            vehicleMessage.put(VehicleMessage.VEHICLE_CODE,codeAndColor[0]);
            vehicleMessage.put(VehicleMessage.CODE_COLOR,codeAndColor[1]);
            log.info("车牌号："+vehicleMessage.get("vehicleCode"));
            log.info("车牌颜色："+vehicleMessage.get("CodeColor"));

            return vehicleMessage;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
