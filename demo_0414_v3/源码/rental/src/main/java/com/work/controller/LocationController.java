package com.work.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.work.enums.CarStatusEnum;
import com.work.model.Car;
import com.work.model.Location;
import com.work.service.CarService;
import com.work.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/staff/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private CarService carService;


    /**
     * 获取地点列表
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping(value = "/list")
    public ModelAndView list(
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "8") Integer size,
            Map<String,Object> map){
        Page<Location> locationPage = locationService.findLocationByPage(page,size);
        PageInfo<Location> locationPageInfo = locationPage.toPageInfo();
        map.put("pageInfo",locationPageInfo);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/staff/locationList",map);
    }


    @GetMapping("/edit")
    public ModelAndView edit(
            @RequestParam(name = "locationId", required = false) Integer locationId,
            Map<String,Object> map){
        if(locationId != null ){//id不为空，则为修改
            Location location = locationService.findLocationById(locationId);
            map.put("locationInfo",location);
        }
        return new ModelAndView("/staff/locationEdit",map);
    }

    /**
     * 新增停车网点
     * @param location
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView save(Location location,Map<String,Object> map){

        if(location.getLocationId() != null){//更新
            location.setUpdateTime(new Date());
            locationService.updateLocation(location);
            map.put("msg","Update Location Info Success");
        }else{//新增
            locationService.addLocation(location);
            map.put("msg","Add Location Success");
        }
        map.put("url","/staff/location/list");
        return new ModelAndView("/common/success",map);
    }

    @GetMapping("/delete")
    public ModelAndView delete(Integer locationId,Map<String,Object> map){
        Location location = locationService.findLocationById(locationId);
        if(location == null){
            map.put("msg","Cannot find this Location Information");
            map.put("url","/staff/location/list");
        }
        locationService.deleteLocation(location.getLocationId());
        map.put("msg","Delete Success");
        map.put("url","/staff/location/list");
        return new ModelAndView("/common/success",map);
    }


    @PostMapping("/search")
    public ModelAndView search(
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "8") Integer size,
            Location location,Map<String,Object> map){
        Page<Location> locationPage = locationService.searchLocationByPage(location,page,size);
        PageInfo<Location> locationPageInfo = locationPage.toPageInfo();
        map.put("pageInfo",locationPageInfo);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/staff/locationList",map);
    }

    /**
     * 展示停车网点关联的车辆
     * @param page
     * @param size
     * @param locationId
     * @param map
     * @return
     */
    @GetMapping("/showCars")
    public ModelAndView showCars(
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "8") Integer size,
            Integer locationId,Map<String,Object> map){
        Location location = locationService.findLocationById(locationId);

        Page<Car> carPage = carService.findLocationCarByPage(locationId,page,size);
        PageInfo<Car> carPageInfo = carPage.toPageInfo();

        int length = carPageInfo.getList().size();
        if(length == 0){//没有所属的车辆
            map.put("msg","No Cars;Please Add Cars!");
            map.put("url","/staff/location/toAddLocationCars?locationId="+locationId);
            return new ModelAndView("/common/error",map);
        }
        map.put("locationName",location.getLocationName());
        map.put("locationId",locationId);
        map.put("pageInfo",carPageInfo);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/staff/locationCarsList",map);
    }

    /**
     * 移除停车网点关联的车辆
     * @param locationId
     * @param cid
     * @param map
     * @return
     */
    @GetMapping("/remove")
    public ModelAndView remove(Integer locationId,Integer cid,Map<String,Object> map){
        Map<String,Object> params = new HashMap<>();
        params.put("locationId",locationId);
        params.put("cid",cid);
        locationService.removeLocationCar(params);

        Car car = carService.findById(cid);
        car.setStatus(CarStatusEnum.NEW_CAR.getCode());
        locationService.updateCarStatus(car);

        map.put("url","/staff/location/showCars?locationId="+locationId);
        return new ModelAndView("/common/success",map);
    }


    /**
     * 停车网点关联新车
     * @param locationId
     * @param map
     * @return
     */
    @GetMapping("/toAddLocationCars")
    public ModelAndView toAddLocationCars(@RequestParam(name = "locationId") Integer locationId,Map<String,Object> map){
        Location location = locationService.findLocationById(locationId);
        map.put("locationName",location.getLocationName());
        map.put("locationId",locationId);

        //1. 查询没归属的车辆列表
        List<Car> carList = locationService.findNoLocationCar();
        if(carList.isEmpty()){//所有车辆均有停车点，应添加新车
            map.put("msg","No New Cars");
            map.put("url","/staff/car/edit");
            return new ModelAndView("/common/error",map);
        }
        //有新车
        map.put("carList",carList);
        return new ModelAndView("/staff/locationCarsEdit");
    }

    /**
     * 保存关联的新车
     * @param cid
     * @param locationId
     * @param map
     * @return
     */
    @GetMapping("/saveLocationCars")
    public ModelAndView saveLocationCars(
            @RequestParam(name = "cid") Integer cid,
            @RequestParam(name = "locationId") Integer locationId,Map<String,Object> map){
        //1. 修改车辆状态
        Car car = carService.findById(cid);
        car.setStatus(CarStatusEnum.RECORD.getCode());
        locationService.updateCarStatus(car);

        //2. 新增网点和车辆中间表
        Map<String,Object> params = new HashMap<>();
        params.put("locationId",locationId);
        params.put("cid",cid);
        locationService.addLocationCar(params);
        map.put("msg","Add Cars Successful");
        map.put("url","/staff/location/showCars?locationId="+locationId);
        return new ModelAndView("/common/success");
    }
}
