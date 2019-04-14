package com.work.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.work.enums.CarStatusEnum;
import com.work.enums.OrderStatusEnum;
import com.work.model.Car;
import com.work.model.Location;
import com.work.model.Order;
import com.work.model.User;
import com.work.service.CarService;
import com.work.service.LocationService;
import com.work.service.OrderService;
import com.work.service.UserService;
import com.work.utils.KeyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/staff/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @GetMapping(value = "/list")
    public ModelAndView list(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="size",defaultValue = "8") Integer size,
            Map<String,Object> map){
        Page<Order> orders = orderService.findOrdersByPage(page,size);
        PageInfo<Order> orderPageInfo = orders.toPageInfo();
        map.put("pageInfo",orderPageInfo);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/staff/orderList",map);
    }

    @PostMapping(value = "/search")
    public ModelAndView search(
            @RequestParam(value="page",defaultValue = "1") Integer page,
            @RequestParam(value="size",defaultValue = "8") Integer size,
            Order order, Map<String,Object> map){
        if(order.getOrderId()==""){
            order.setOrderId(null);
        }
        if(order.getUserName()==""){
            order.setUserName(null);
        }
        if(order.getUserPhone()==""){
            order.setUserPhone(null);
        }
        Page<Order> orders = orderService.searchOrdersByPage(order,page,size);
        PageInfo<Order> orderPageInfo = orders.toPageInfo();
        map.put("pageInfo",orderPageInfo);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/staff/orderList",map);
    }

    @GetMapping("/cancle")
    public ModelAndView cancle(
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
        map.put("url","/staff/order/list");
        return new ModelAndView("/common/success",map);
    }



    @GetMapping("/getCarList")
    public String getCarList(@RequestParam(name = "pickupLocation") String pickupLocation){
        Location location = locationService.findLocationByName(pickupLocation);
        List<Car> cars = location.getCars();
        Map<String,Object> map = new HashMap<>();
        if(cars.size()==0 || CollectionUtils.isEmpty(cars)){
            map.put("status",-1);
            map.put("msg","No Cars Belong to" + pickupLocation);
        }else{
            map.put("status",0);
            map.put("msg","success");
            map.put("cars",cars);
        }
        String result = JSON.toJSONString(map);
        return result;
    }


    @GetMapping("/edit")
    public ModelAndView edit(
            @RequestParam(name = "orderId",required = false)String orderId,
            Map<String,Object> map){
        if(orderId != null ){
            Order order = orderService.findById(orderId);
            map.put("orderInfo",order);
        }
        List<Location> locationList = locationService.findLocationList();
        map.put("locationList",locationList);
        return new ModelAndView("/staff/orderEdit");
    }

    @GetMapping("/status")
    public ModelAndView status(
            @RequestParam(name = "orderId") String orderId,
            Map<String,Object> map){
        Order order = orderService.findById(orderId);
        if(order == null){
            map.put("msg","This Order Is Not Exist");
            map.put("url","/staff/order/list");
            return new ModelAndView("/common/error",map);
        }

        if(order.getOrderStatus() == OrderStatusEnum.ORDER_PICKING_UP.getCode()){
            order.setOrderStatus(OrderStatusEnum.ORDER_USING.getCode());
        }else if(order.getOrderStatus() == OrderStatusEnum.ORDER_USING.getCode()){
            order.setOrderStatus(OrderStatusEnum.ORDER_FINISHED.getCode());
        }
        orderService.updateOrder(order);
        map.put("msg","Operation Success");
        map.put("url","/staff/order/list");
        return new ModelAndView("/common/success",map);
    }

    @PostMapping("/save")
    public ModelAndView save(
            Order order,Map<String,Object> map,
            @RequestParam(name = "puTime") String puTime,
            @RequestParam(name = "doTime") String doTime,
            @RequestParam(name = "carId") int carId){

        Car car = carService.findById(carId);
        if(car == null){
            map.put("msg","This Car Is Not Exist");
            map.put("url","/staff/order/list");
            return new ModelAndView("/common/error",map);
        }
        User user = userService.findByUsername(order.getUserName());
        if(user == null){
            map.put("msg","This Customer Is Not Exist");
            map.put("url","/staff/order/list");
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
        map.put("url","/staff/order/list");
        return new ModelAndView("/common/success",map);
    }


}
