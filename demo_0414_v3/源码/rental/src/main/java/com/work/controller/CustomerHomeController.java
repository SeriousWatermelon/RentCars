package com.work.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.work.enums.CarStatusEnum;
import com.work.model.*;
import com.work.service.*;
import com.work.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 稻草人 on 2019/4/13.
 */
@Slf4j
@RestController
@RequestMapping("/customer/home")
public class CustomerHomeController {

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("/index");
    }

    @GetMapping("/cars")
    public ModelAndView cars(
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "8") Integer size,
            Map<String,Object> map){
        Page<Car> cars = carService.findUserCarsByPage(page,size);
        PageInfo<Car> carPageInfo = cars.toPageInfo();
        map.put("pageInfo",carPageInfo);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/customer/cars");
    }

    @PostMapping(value = "/searchCars")
    public ModelAndView searchCars(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="size",defaultValue = "8") Integer size,
            Car car, Map<String,Object> map){
        car.setStatus(CarStatusEnum.RECORD.getCode());
        car.setDisable(false);
        Page<Car> cars = carService.searchByPage(car,page,size);
        PageInfo<Car> pageInfo = cars.toPageInfo();

        map.put("pageInfo",pageInfo);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/customer/cars",map);
    }

    @GetMapping("/introduce")
    public ModelAndView introduce(){
        return new ModelAndView("/customer/introduce");
    }

    @GetMapping("/user")
    public ModelAndView user(
            @RequestParam(name = "id") Integer id,
            Map<String,Object> map){
        User user = userService.findUserById(id);
        map.put("userInfo",user);
        return new ModelAndView("/customer/userinfo",map);
    }

    @PostMapping("/saveUser")
    public ModelAndView saveUser(
            User user, Map<String,Object> map){
        userService.updateUserInfo(user);
        map.put("msg","Your Information Update Successful");
        map.put("url","/customer/home/index");
        return new ModelAndView("/common/success",map);
    }

    @GetMapping("/history")
    public ModelAndView history(
            @RequestParam(name = "id") Integer id,
            @RequestParam(name = "page",defaultValue = "1") Integer page,
            @RequestParam(name = "size",defaultValue = "8") Integer size,
            Map<String,Object> map){
        Page<Order> orders = orderService.findOrdersByUserId(id,page,size);
        PageInfo<Order> orderPageInfo = orders.toPageInfo();
        map.put("pageInfo",orderPageInfo);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/customer/record",map);
    }

    @PostMapping(value = "/searchOrder")
    public ModelAndView searchOrder(
            @RequestParam(name = "id") Integer id,
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="size",defaultValue = "8") Integer size,
            Order order, Map<String,Object> map){
        order.setUserId(id);
        if(order.getOrderId()==""){
            order.setOrderId(null);
        }
        if(order.getUserName()==""){
            order.setUserName(null);
        }
        if(order.getUserPhone()==""){
            order.setUserPhone(null);
        }
        Page<Order> orders = orderService.searchUserOrdersByPage(order,page,size);
        PageInfo<Order> orderPageInfo = orders.toPageInfo();
        map.put("pageInfo",orderPageInfo);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/customer/record",map);
    }


    @GetMapping("/cancelOrder")
    public ModelAndView cancelOrder(
            @RequestParam(value="orderId") String orderId,
            Map<String,Object> map) {
        //1. 修改订单状态
        orderService.cancleOrder(orderId);
        //2. 恢复车辆状态
        Order order = orderService.findById(orderId);
        Car car = carService.findById(order.getCarId());
        car.setStatus(CarStatusEnum.RECORD.getCode());
        carService.updateCar(car);
        map.put("msg","Operation Success");
        map.put("url","/customer/home/history?id="+order.getUserId());
        return new ModelAndView("/common/success",map);
    }

    @GetMapping("/getCarList")
    public String getCarList(@RequestParam(name = "pickupLocation") String pickupLocation){
        Location l = locationService.findLocationByName(pickupLocation);
        List<Car> carList = l.getCars();
        Map<String,Object> map = new HashMap<>();
        if(carList.size()==0 || CollectionUtils.isEmpty(carList)){
            map.put("status",-1);
            map.put("msg","No Cars Belong to" + pickupLocation);
        }else{
            map.put("status",0);
            map.put("msg","success");
            map.put("cars",carList);
        }
        String result = JSON.toJSONString(map);
        return result;
    }

    @GetMapping("/editOrder")
    public ModelAndView editOrder(
            @RequestParam(name = "orderId",required = true) String orderId,
            Map<String,Object> map ){
        Order order = orderService.findById(orderId);
        if(order == null){
            map.put("msg","This Order Is Not Exist");
            map.put("url","/customer/home/history?id="+order.getUserId());
        }
        List<Location> locationList = locationService.findLocationList();
        map.put("locationList",locationList);
        map.put("orderInfo",order);

        return new ModelAndView("/customer/recordEdit");
    }

    @PostMapping("/saveOrders")
    public ModelAndView saveOrders(
            Order order,Map<String,Object> map,
            @RequestParam(name = "puTime") String puTime,
            @RequestParam(name = "doTime") String doTime,
            @RequestParam(name = "carId") int carId){
        Car car = carService.findById(carId);
        if(car == null){
            map.put("msg","This Car Is Not Exist");
            map.put("url","/customer/home/history?id="+order.getUserId());
            return new ModelAndView("/common/error",map);
        }
        User user = userService.findByUsername(order.getUserName());
        if(user == null){
            map.put("msg","This Customer Is Not Exist");
            map.put("url","/customer/home/history?id="+order.getUserId());
            return new ModelAndView("/common/error",map);
        }
        order.setUserId(user.getId());
        order.setUserName(user.getUsername());
        order.setUserPhone(user.getPhone());
        order.setUserAddress(user.getAddress());

        order.setCarMaker(car.getMaker());
        order.setCarType(car.getType());

        BigDecimal amount = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try{
            Date pickupTime = sdf.parse(puTime.replace("T"," "));
            Date dropoffTIme = sdf.parse(doTime.replace("T"," "));
            order.setPickupTime(pickupTime);
            order.setDropoffTime(dropoffTIme);
            //计算租金
            long pickupTimeLong = pickupTime.getTime();
            long dropoffTImeLong = dropoffTIme.getTime();
            // 结束时间-开始时间
            long dayHours = (dropoffTImeLong-pickupTimeLong)/(60*60*1000);
            BigDecimal hours = new BigDecimal(dayHours);
            amount = hours.multiply(car.getRent());
            order.setOrderAmount(amount);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(order.getOrderId()=="" || order.getOrderId()==null){//新增
            order.setOrderId(KeyUtil.getUniqueKey());
            orderService.addOrder(order);
        }else{//修改
            order.setUpdateTime(new Date());
            orderService.updateOrder(order);
        }

        map.put("msg","Operation Success");
        map.put("url","/customer/home/history?id="+order.getUserId());
        return new ModelAndView("/common/success",map);
    }


    @GetMapping("/orderComment")
    public ModelAndView orderComment(
            @RequestParam(name = "orderId",required = true) String orderId,
            Map<String,Object> map ){
        Order order = orderService.findById(orderId);
        if(order == null){
            map.put("msg","This Order Is Not Exist");
            map.put("url","/customer/home/history?id="+order.getUserId());
        }
        map.put("order",order);
        Comment comment = commentService.findCommentById(orderId);
        map.put("comment",comment);
        return new ModelAndView("/customer/recordComment",map);
    }

    @PostMapping("/saveComment")
    public ModelAndView saveComment(Comment comment,Map<String,Object> map){
        if(comment.getOrderId() == "" || comment.getCommentScore() == null || comment.getCommentDesc() ==""){
            map.put("msg","Comment failed");
            map.put("url","/customer/home/orderComment?orderId="+comment.getOrderId());
        }
        Comment comment1 = commentService.findCommentById(comment.getOrderId());
        if(comment1 == null){
            commentService.addComment(comment);
            map.put("msg","Comment Successful");
        }else{
            commentService.updateComment(comment);
            map.put("msg","Comment Update Successful");
        }
        Order order = orderService.findById(comment.getOrderId());
        map.put("url","/customer/home/history?id="+order.getUserId());
        return new ModelAndView("/common/success",map);
    }


    @GetMapping("/map")
    public ModelAndView map(@RequestParam(name = "type") String type,Map<String,Object> map){
        List<Location> locations = locationService.findLocationList();
        map.put("locationList",JSON.toJSONString(locations));
        map.put("type",type);
        return new ModelAndView("/customer/map",map);
    }

    @GetMapping("/comfirmLocation")
    public ModelAndView comfirmLocation(@RequestParam(name = "type") String type,@RequestParam(name = "location") String location,Map<String,Object> map){
        map.put("location",location);
        map.put("type",type);
        return new ModelAndView("/index",map);
    }

    @PostMapping("/rentCar")
    public ModelAndView rentCar(
            Order order,Map<String,Object> map,
            @RequestParam(name = "puTime") String puTime,
            @RequestParam(name = "doTime") String doTime,
            @RequestParam(name = "carId") int carId){
        Car car = carService.findById(carId);
        if(car == null){
            map.put("msg","This Car Is Not Exist");
            map.put("url","/customer/home/index");
            return new ModelAndView("/common/error",map);
        }

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if(user == null){
            map.put("msg","This Customer Is Not Exist");
            map.put("url","/customer/home/history?id="+order.getUserId());
            return new ModelAndView("/common/error",map);
        }
        order.setUserId(user.getId());
        order.setUserName(user.getUsername());
        order.setUserPhone(user.getPhone());
        order.setUserAddress(user.getAddress());

        order.setCarMaker(car.getMaker());
        order.setCarType(car.getType());

        BigDecimal amount = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try{
            Date pickupTime = sdf.parse(puTime.replace("T"," "));
            Date dropoffTIme = sdf.parse(doTime.replace("T"," "));
            order.setPickupTime(pickupTime);
            order.setDropoffTime(dropoffTIme);
            //计算租金
            long pickupTimeLong = pickupTime.getTime();
            long dropoffTImeLong = dropoffTIme.getTime();
            // 结束时间-开始时间
            long dayHours = (dropoffTImeLong-pickupTimeLong)/(60*60*1000);
            BigDecimal hours = new BigDecimal(dayHours);
            amount = hours.multiply(car.getRent());
            order.setOrderAmount(amount);
        }catch (Exception e){
            e.printStackTrace();
        }
        order.setOrderId(KeyUtil.getUniqueKey());
        orderService.addOrder(order);


        map.put("msg","Operation Success");
        map.put("url","/customer/home/history?id="+order.getUserId());
        return new ModelAndView("/common/success",map);
    }

}
