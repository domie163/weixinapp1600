
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 学习足迹
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/xuexizuji")
public class XuexizujiController {
    private static final Logger logger = LoggerFactory.getLogger(XuexizujiController.class);

    private static final String TABLE_NAME = "xuexizuji";

    @Autowired
    private XuexizujiService xuexizujiService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表非注册的service
    @Autowired
    private ShipinService shipinService;
    //注册表service
    @Autowired
    private YonghuService yonghuService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        CommonUtil.checkMap(params);
        PageUtils page = xuexizujiService.queryPage(params);

        //字典表数据转换
        List<XuexizujiView> list =(List<XuexizujiView>)page.getList();
        for(XuexizujiView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        XuexizujiEntity xuexizuji = xuexizujiService.selectById(id);
        if(xuexizuji !=null){
            //entity转view
            XuexizujiView view = new XuexizujiView();
            BeanUtils.copyProperties( xuexizuji , view );//把实体数据重构到view中
            //级联表 用户
            //级联表
            YonghuEntity yonghu = yonghuService.selectById(xuexizuji.getYonghuId());
            if(yonghu != null){
            BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setYonghuId(yonghu.getId());
            }
            //级联表 视频信息
            //级联表
            ShipinEntity shipin = shipinService.selectById(xuexizuji.getShipinId());
            if(shipin != null){
            BeanUtils.copyProperties( shipin , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setShipinId(shipin.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody XuexizujiEntity xuexizuji, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,xuexizuji:{}",this.getClass().getName(),xuexizuji.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("用户".equals(role))
            xuexizuji.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<XuexizujiEntity> queryWrapper = new EntityWrapper<XuexizujiEntity>()
            .eq("shipin_id", xuexizuji.getShipinId())
            .eq("yonghu_id", xuexizuji.getYonghuId())
            .eq("xuexizuji_types", 1)
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XuexizujiEntity xuexizujiEntity = xuexizujiService.selectOne(queryWrapper);
        if(xuexizujiEntity!=null){
            xuexizujiEntity.setXuexizujiTypes(2);
            xuexizujiService.updateById(xuexizujiEntity);
        }
        xuexizuji.setXuexizujiTime(new Date());
        xuexizuji.setInsertTime(new Date());
        xuexizuji.setCreateTime(new Date());
        xuexizuji.setXuexizujiTypes(1);
        xuexizujiService.insert(xuexizuji);
        return R.ok();
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody XuexizujiEntity xuexizuji, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,xuexizuji:{}",this.getClass().getName(),xuexizuji.toString());
        XuexizujiEntity oldXuexizujiEntity = xuexizujiService.selectById(xuexizuji.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("用户".equals(role))
//            xuexizuji.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<XuexizujiEntity> queryWrapper = new EntityWrapper<XuexizujiEntity>()
            .notIn("id",xuexizuji.getId())
            .andNew()
            .eq("shipin_id", xuexizuji.getShipinId())
            .eq("yonghu_id", xuexizuji.getYonghuId())
            .eq("xuexizuji_types", xuexizuji.getXuexizujiTypes())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XuexizujiEntity xuexizujiEntity = xuexizujiService.selectOne(queryWrapper);
        if(xuexizujiEntity==null){
            xuexizujiService.updateById(xuexizuji);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<XuexizujiEntity> oldXuexizujiList =xuexizujiService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        xuexizujiService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 结束学习
     */
    @RequestMapping("/jieshuxuexi")
    public R jieshuxuexi(Integer shipinId, HttpServletRequest request){
        Wrapper<XuexizujiEntity> queryWrapper = new EntityWrapper<XuexizujiEntity>()
                .eq("shipin_id", shipinId)
                .eq("yonghu_id", request.getSession().getAttribute("userId"))
                .eq("xuexizuji_types", 1);
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XuexizujiEntity xuexizujiEntity = xuexizujiService.selectOne(queryWrapper);
        YonghuEntity yonghuEntity = yonghuService.selectById(xuexizujiEntity.getYonghuId());
        if(xuexizujiEntity == null){
            return R.error("未查找到数据");
        }
        long time = (new Date().getTime() - xuexizujiEntity.getXuexizujiTime().getTime())/1000/60;
        if(time>0){
            yonghuEntity.setXuexiNumber(yonghuEntity.getXuexiNumber()+Integer.valueOf(String.valueOf(time)));
        }
        xuexizujiEntity.setXuexizujiTypes(3);
        yonghuService.updateById(yonghuEntity);
        xuexizujiService.updateById(xuexizujiEntity);
        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<XuexizujiEntity> xuexizujiList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            XuexizujiEntity xuexizujiEntity = new XuexizujiEntity();
//                            xuexizujiEntity.setShipinId(Integer.valueOf(data.get(0)));   //视频 要改的
//                            xuexizujiEntity.setYonghuId(Integer.valueOf(data.get(0)));   //用户 要改的
//                            xuexizujiEntity.setXuexizujiTime(sdf.parse(data.get(0)));          //开始时间 要改的
//                            xuexizujiEntity.setXuexizujiTypes(Integer.valueOf(data.get(0)));   //学习状态 要改的
//                            xuexizujiEntity.setInsertTime(date);//时间
//                            xuexizujiEntity.setCreateTime(date);//时间
                            xuexizujiList.add(xuexizujiEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        xuexizujiService.insertBatch(xuexizujiList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }





    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = xuexizujiService.queryPage(params);

        //字典表数据转换
        List<XuexizujiView> list =(List<XuexizujiView>)page.getList();
        for(XuexizujiView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        XuexizujiEntity xuexizuji = xuexizujiService.selectById(id);
            if(xuexizuji !=null){


                //entity转view
                XuexizujiView view = new XuexizujiView();
                BeanUtils.copyProperties( xuexizuji , view );//把实体数据重构到view中

                //级联表
                    YonghuEntity yonghu = yonghuService.selectById(xuexizuji.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
                //级联表
                    ShipinEntity shipin = shipinService.selectById(xuexizuji.getShipinId());
                if(shipin != null){
                    BeanUtils.copyProperties( shipin , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setShipinId(shipin.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody XuexizujiEntity xuexizuji, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,xuexizuji:{}",this.getClass().getName(),xuexizuji.toString());
        Wrapper<XuexizujiEntity> queryWrapper = new EntityWrapper<XuexizujiEntity>()
            .eq("shipin_id", xuexizuji.getShipinId())
            .eq("yonghu_id", xuexizuji.getYonghuId())
            .eq("xuexizuji_types", xuexizuji.getXuexizujiTypes())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        XuexizujiEntity xuexizujiEntity = xuexizujiService.selectOne(queryWrapper);
        if(xuexizujiEntity==null){
            xuexizuji.setInsertTime(new Date());
            xuexizuji.setCreateTime(new Date());
        xuexizujiService.insert(xuexizuji);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}
