package com.work.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.work.model.Car;
import com.work.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Date;
import java.util.UUID;

/**
 * Created by 稻草人 on 2019/4/7.
 */

@Controller
@RequestMapping(value = "/staff/car")
@Slf4j
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping(value = "/list")
    public ModelAndView list(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="size",defaultValue = "8") Integer size,
            Map<String,Object> map){
        Page<Car> cars = carService.findByPage(page,size);
        PageInfo<Car> pageInfo = cars.toPageInfo();
        map.put("pageInfo",pageInfo);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/staff/carList",map);
    }

    @PostMapping(value = "/search")
    public ModelAndView search(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="size",defaultValue = "8") Integer size,
            Car car, Map<String,Object> map){
        Page<Car> cars = carService.searchByPage(car,page,size);
        PageInfo<Car> pageInfo = cars.toPageInfo();

        map.put("pageInfo",pageInfo);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/staff/carList",map);
    }

    @GetMapping(value = "/disabled")
    public ModelAndView disabled(
            @RequestParam(value="cid") Integer cid,
            Map<String,Object> map){
        Car car = carService.findById(cid);
        if(car == null){
            map.put("msg","No Such car information");
            map.put("url","/staff/car/list");
            return new ModelAndView("/common/error",map);
        }
        if(car.getDisable()){ // 汽车被禁用，则修改为可用
            car.setDisable(false);
        }else{  //汽车可用，则修改为禁用
            car.setDisable(true);
        }
        carService.updateCar(car);
        map.put("msg","Disabled Success");
        map.put("url","/staff/car/list");
        return new ModelAndView("/common/success",map);
    }

    @GetMapping(value = "/edit")
    public ModelAndView edit(
            @RequestParam(value = "cid",required = false) Integer cid,
            Map<String,Object> map){
        if(cid != null ){
            Car car = carService.findById(cid);
            map.put("carInfo",car);
        }
        return new ModelAndView("/staff/carEdit");
    }

    @PostMapping(value = "/save")
    public ModelAndView save(
            Car car, Map<String,Object> map,String product_time,
            @RequestParam(value = "iconFile") MultipartFile iconFile){
        // 保存图片
        String imgSrc = uploadImg(iconFile);
        // 完健对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date productTime = sdf.parse(product_time);
            //System.out.println(product_time+"    "+productTime);
            car.setProductTime(productTime);
            car.setCicon(imgSrc);
            if(car.getCid()!=null){//更新
                carService.updateCar(car);
            }else{//新增
                car.setDisable(false);
                carService.addCar(car);
            }
        }catch(Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/staff/car/list");
        }
        map.put("msg","Operation Success");
        map.put("url","/staff/car/list");
        return new ModelAndView("common/success",map);
    }



    /**
     * 图片存储
     * @param file
     * @return
     */
    private String uploadImg(MultipartFile file){
        if (file.isEmpty()) {
            return null;
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D:/rental/imgs/";
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filename = "/imgs/" + fileName;
        return filename;
    }

}








